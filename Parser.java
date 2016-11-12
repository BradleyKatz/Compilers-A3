import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class Parser {
	private Lexer lex = new Lexer();
	private SyntaxTree syntaxTree = SyntaxTree.getInstance();
	private Token lookahead = null;
	private Token token = null;
	private static Hashtable<String, List<String>> FIRST = new Hashtable<String, List<String>>();
	private static Hashtable<String, List<String>> FOLLOW = new Hashtable<String, List<String>>();

	private String currentName, currentFuncName, currentType, currentValue;
	
	private void initializeFIRST() {
        FIRST.put("program", Arrays.asList("def", "int", "double", "if", "while", "print", "return", "ID"));
        FIRST.put("fdecls", Arrays.asList("def", "EPSILON"));
        FIRST.put("fdec", Arrays.asList("def"));
        FIRST.put("fdec_r", Arrays.asList("def", "EPSILON"));
        FIRST.put("params", Arrays.asList("int", "double", "EPSILON"));
		FIRST.put("params_r", Arrays.asList(",", "EPSILON"));
		FIRST.put("fname", Arrays.asList("ID"));
		FIRST.put("declarations", Arrays.asList("int", "double", "EPSILON"));
        FIRST.put("decl", Arrays.asList("int", "double"));
        FIRST.put("decl_r", Arrays.asList("int", "double", "EPSILON"));
        FIRST.put("type", Arrays.asList("int", "double"));
		FIRST.put("varlist", Arrays.asList("ID"));
		FIRST.put("varlist_r", Arrays.asList(",", "EPSILON"));
		FIRST.put("statement_seq", Arrays.asList("if", "while", "print", "return", "ID", "EPSILON"));
        FIRST.put("statement", Arrays.asList("if","while","print","return","ID","EPSILON"));
        FIRST.put("statement_seq_r", Arrays.asList(";", "EPSILON"));
		FIRST.put("opt_else", Arrays.asList("else", "EPSILON"));
		FIRST.put("expr", Arrays.asList("ID", "NUMBER", "("));
		FIRST.put("term", Arrays.asList("ID", "NUMBER", "("));
		FIRST.put("term_r", Arrays.asList("+", "-", "EPSILON"));
		FIRST.put("var_r", Arrays.asList("[","EPSILON"));
        FIRST.put("var", Arrays.asList("ID"));
        FIRST.put("comp", Arrays.asList("<", ">", "==", "<=", ">=", "<>"));
        FIRST.put("bfactor_r_p", Arrays.asList("(", "not", "ID", "NUMBER", "EPSILON"));
        FIRST.put("bfactor", Arrays.asList("(", "not"));
        FIRST.put("bfactor_r", Arrays.asList("and", "EPSILON"));
        FIRST.put("bterm", Arrays.asList("(", "not"));
        FIRST.put("bterm_r", Arrays.asList("or", "EPSILON"));
        FIRST.put("bexpr", Arrays.asList("(", "not"));
        FIRST.put("exprseq_r", Arrays.asList(",", "EPSILON"));
        FIRST.put("exprseq", Arrays.asList("(", "ID", "NUMBER"));
        FIRST.put("factor", Arrays.asList("(", "ID", "NUMBER"));
        FIRST.put("factor_r", Arrays.asList("*", "/", "%", "EPSILON"));
        FIRST.put("factor_r_p", Arrays.asList("(","EPSILON"));
		
		// Missing grt_opt, less_opt, id, id_r, integer, integer_r, double, double_r, decimal, decimal_r, exponent, letter, digit
		// Might only be relevant for lexer. Will add in later if actually needed.
	}
	
	private void initializeFOLLOW() {
        FOLLOW.put("program", Arrays.asList("$"));
        FOLLOW.put("fdecls", Arrays.asList("int", "double", "if", "while", "print", "return", "ID"));
        FOLLOW.put("fdec", Arrays.asList(";"));
        FOLLOW.put("fdec_r", Arrays.asList(";"));
        FOLLOW.put("params", Arrays.asList(")"));
		FOLLOW.put("params_r", Arrays.asList(")"));
		FOLLOW.put("fname", Arrays.asList("("));
		FOLLOW.put("declarations", Arrays.asList("if","while","print","return","ID"));
        FOLLOW.put("decl", Arrays.asList(";"));
        FOLLOW.put("decl_r", Arrays.asList(";"));
        FOLLOW.put("type", Arrays.asList("ID"));
		FOLLOW.put("varlist", Arrays.asList(";",",",".","(",")","]","[","then","+","-","*","/","%","==","<>","<",">"));
		FOLLOW.put("varlist_r", Arrays.asList(";",",",".","(",")","]","[","then","+","-","*","/","%","==","<>","<",">"));
		FOLLOW.put("statement_seq", Arrays.asList(".","fed","fi","od","else"));
        FOLLOW.put("statement", Arrays.asList(".",";","fed","fi","od","else"));
        FOLLOW.put("statement_seq_r", Arrays.asList(".",";","fed","fi","od","else"));
		FOLLOW.put("opt_else", Arrays.asList("fi"));
		FOLLOW.put("expr", Arrays.asList(".",";","fed","fi","od","else",")","=",">","<","]"));
		FOLLOW.put("term", Arrays.asList(".",";","fed","fi","od","else",")","=",">","<","]","+","-","*","/"));
		FOLLOW.put("term_r", Arrays.asList(".",";","fed","fi","od","else",")","=",">","<","]","+","-","*","/"));
		FOLLOW.put("var_r", Arrays.asList(";",",",".","(",")","]","[","then","+","-","*","/","%","==","<>","<",">"));
        FOLLOW.put("var", Arrays.asList(";",",",".","(",")","]","[","then","+","-","*","/","%","==","<>","<",">"));
        FOLLOW.put("comp", Arrays.asList(""));
        FOLLOW.put("bfactor_r_p", Arrays.asList("then","do",")","or","and"));
        FOLLOW.put("bfactor", Arrays.asList("then","do",")","or","and"));
        FOLLOW.put("bfactor_r", Arrays.asList("then","do",")","or","and"));
        FOLLOW.put("bterm", Arrays.asList("then","do",")","or","and"));
        FOLLOW.put("bterm_r", Arrays.asList("then","do",")","or","and"));
        FOLLOW.put("bexpr", Arrays.asList("then","do",")","or"));
        FOLLOW.put("exprseq_r", Arrays.asList(")"));
        FOLLOW.put("exprseq", Arrays.asList(")"));
        FOLLOW.put("factor", Arrays.asList(".",";","fed","fi","od","else",")","=",">","<","]","+","-","*","/"));
        FOLLOW.put("factor_r", Arrays.asList(".",";","fed","fi","od","else",")","=",">","<","]","+","-","*","/"));
        FOLLOW.put("factor_r_p", Arrays.asList(".",";","fed","fi","od","else",")","=",">","<","]","+","-","*","/"));
	}
	
	// Calls Parser
	public static void main(String[] args) throws IOException {
		Parser parser = new Parser();
	}
	
	public Parser() throws IOException {
		initializeFIRST();
		initializeFOLLOW();
		consumeToken(); consumeToken(); // Twice to initialize token & lookahead
		
		program();
		
		System.out.println("\nValid Parse: true");
		System.out.println("");
		SymbolTableTree.getInstance().printSymbolTables();
	}
	
	// RECURSIVE FUNCTIONS
	
	public void program() {
		String first = checkFIRST("program");
		if(first != null) {
			fdecls(); declarations(); statement_seq(); match('.');
		} else
			error();
	}
	
	public void fdecls() {
		String first = checkFIRST("fdecls");
		if(first != null) {
			fdec(); match(';'); fdec_r();
		}
	}
	
	public void fdec() {
		String first = checkFIRST("fdec");
		if(first != null) {
			match("def"); type(); fname(); match("("); params(); match(")"); declarations(); statement_seq(); match("fed");
		}
	}
	
	public void fdec_r() {
		String first = checkFIRST("fdec_r");
		if(first != null) {
			fdec(); match(";"); fdec_r();
		}
	}

	public void params() {
		String first = checkFIRST("params");
		if (first != null) {
			type(); var(); params_r();
		}
	}
	
	public void params_r() {
		String first = checkFIRST("params_r");
		if (first != null) {
			match(","); params();
		}
	}
	
	public void fname() {
		String first = checkFIRST("fname");
		if (first != null) {
			currentName = lookahead.getRepresentation();
			currentFuncName = currentName;
			SymbolTableTree.getInstance().addEntry(new SymbolTableEntry(currentName, SymbolTableEntry.FUNCTION, currentType, null));
			match(TokenType.ID);
		}
		else
			error();
	}
	
	public void declarations() {
		String first = checkFIRST("declarations");
		if(first != null) {
			decl(); match(';'); decl_r();
		}
	}
	
	public void decl() {
		String first = checkFIRST("decl");
		if(first != null) {
			type(); varlist();
		} else
			error();
	}
	
	public void decl_r() {
		String first = checkFIRST("decl_r");
		if(first != null) {
			decl(); match(";"); decl_r();
		}
	}
	
	public void type() {
		String first = checkFIRST("type");
		
		switch(first) {
			case "int":
				currentType = SymbolTableEntry.INT;
				match("int");
				return;
			case "double":
				currentType = SymbolTableEntry.DOUBLE;
				match("double");
				return;
			default:
				error();
		}
	}
	
	public void statement_seq() {
		String first = checkFIRST("statement_seq");
		if(first != null) {
			statement(); statement_seq_r();
		}
	}
	
	public void varlist() {
		String first = checkFIRST("varlist");
		if (first != null) {
			var(); varlist_r();
		} else
			error();
	}
	
	public void varlist_r() {
		String first = checkFIRST("varlist_r");
		if (first != null) {
			match(","); varlist();
		}
	}
	
	public void statement() {
		String first = checkFIRST("statement");
		switch(first) {
			case "ID":
				var(); match("="); expr(); return;
			case "if":
				match("if"); bexpr(); match("then"); statement_seq(); opt_else(); match("fi"); return;
			case "while":
				match("while"); bexpr(); match("do"); statement_seq(); match("od"); return;
			case "print":
				match("print"); expr(); return;
			case "return":
				match("return"); expr(); return;
			default: //Epsilon
				return;
		}
	}
	
	public void statement_seq_r() {
		String first = checkFIRST("statement_seq_r");
		if(first != null) {
			match(";"); statement_seq();
		}
	}

	public void opt_else() {
		String first = checkFIRST("opt_else");
		if (first != null) {
			match("else"); statement_seq();
		}
	}
	
	public void expr()
	{
		String first = checkFIRST("expr");
		if (first != null) {
			term(); term_r();
		} else
			error();
	}
	
	public void term_r() {
		String first = checkFIRST("term_r");
		
		if (first != null) {
			if (first.equals("+")) {
				match("+"); term(); term_r();
			} else if (first.equals("-")) {
				match("-"); term(); term_r();
			} else
				error();
		}
	}
	
	public void term() {
		String first = checkFIRST("term");
		
		if (first != null) {
			factor(); factor_r();
		} else
			error();
	}
	
	public void factor_r() {
		String first = checkFIRST("factor_r");
		if (first != null) {
			switch(first) {
				case "*":
					match("*"); factor(); factor_r(); return;
				case "/":
					match("/"); factor(); factor_r(); return;
				case "%":
					match("%"); factor(); factor_r(); return;
				default:
					error();
			}
		}
	}
	
	// Careful
	 public void factor() {
		String first = checkFIRST("factor");
		if (first != null) {
			if (first.equals("ID")) {
				match(TokenType.ID); factor_r_p();
			} else if (first.equals("NUMBER")) {
				match(TokenType.DOUBLE); //Technically INT too
			} else if (first.equals("(")) {
				match("("); expr(); match(")");
			} else if (first.equals("ID")) {
				var();
			} else
				error();
		} else {
			error();
		}
	}
 
	public void factor_r_p() {
		String first = checkFIRST("factor_r_p");
		if (first != null) {
			if (first.equals("(")) {
				match("("); exprseq(); match(")");
			}
		}
	}
	
	public void exprseq() {
		String first = checkFIRST("exprseq");
		if (first != null) {
			expr(); exprseq_r();
		}
	}
	
	public void exprseq_r() {
		String first = checkFIRST("exprseq_r");
		if (first != null) {
			match(","); exprseq();
		}
	}
	
	public void bexpr() {
		String first = checkFIRST("bexpr");
		if (first != null) {
			bterm(); bterm_r();
		}
	}
	
	public void bterm_r() {
		String first = checkFIRST("bterm_r");
		if (first != null) {
			match("or"); bterm(); bterm_r();
		}
	}
	
	public void bterm() {
		String first = checkFIRST("bterm");
		if (first != null) {
			bfactor(); bfactor_r();
		} else
			error();
	}
	
	public void bfactor_r() {
		String first = checkFIRST("bfactor_r");
		if (first != null) {
			match("and"); bfactor(); bfactor_r();
		}
	}
	
	public void bfactor() {
		String first = checkFIRST("bfactor");
		switch (first) {
			case "(":
				match("("); bfactor_r_p(); match(")"); return;
			case "not":
				match("not"); bfactor(); return;
			default:
				error();
		}
	}
	
	// Careful
	public void bfactor_r_p() {
		String first = checkFIRST("bfactor_r_p");
		if (FIRST.get("bfactor_r_p").contains(first) && token.getType() == TokenType.COMP) {
			expr(); comp(); expr();
		} else if (FIRST.get("bfactor_r_p").contains(first)) {
			bexpr();
		} else 
			error();
	}
	
	public void comp() {
		String first = checkFIRST("comp");
		if (first != null) {
			match(TokenType.COMP);
		} else
			error();
	}
	
	public void var() {
		String first = checkFIRST("var");
		if (first != null)
		{
			currentName = lookahead.getRepresentation();
			
			if (currentFuncName != null)
				SymbolTableTree.getInstance().addEntry(new SymbolTableEntry(currentName, SymbolTableEntry.VARIABLE, currentType, null), currentFuncName);
			else
				SymbolTableTree.getInstance().addEntry(new SymbolTableEntry(currentName, SymbolTableEntry.VARIABLE, currentType, null));
			match(TokenType.ID); var_r();
		}
		else
			error();
	}
	
	public void var_r() {
		String first = checkFIRST("var_r");
		if (first != null) {
			match("["); expr(); match("]");
		}
	}
	
	// UTILITY FUNCTIONS
	
	public void consumeToken() {
		lookahead = token;
		try {
			if (token == null || (token != null && token.getType() != TokenType.END)) {
				token = lex.getNextToken();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String checkFIRST(String nonterminal) {
		List<String> first = FIRST.get(nonterminal);
		if (first != null) {
			if (lookahead.getType() == TokenType.ID && first.contains("ID")) {
				return "ID";
			} else if ((lookahead.getType() == TokenType.INT || lookahead.getType() == TokenType.DOUBLE) && first.contains("NUMBER")) {
				return "NUMBER";
			} else if (first.contains(lookahead.getRepresentation())) {
				return lookahead.getRepresentation();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public void match() {
		consumeToken();
	}
	
	public void match(char c) {
		boolean isMatch = lookahead.getRepresentation().equals(String.valueOf(c));
		if (isMatch) consumeToken();
		else error();
	}
	
	public void match(String s) {
		boolean isMatch = lookahead.getRepresentation().equals(s);
		if (isMatch) 
		{
			if (s.equals("fed"))
				currentFuncName = null;
			
			consumeToken();
		}
		else error();
	}
	
	public SyntaxTreeNode.Leaf match(TokenType type) {
		boolean isMatch = false;
		SyntaxTreeNode.Leaf node = null;

		if (type == TokenType.INT) { 
			isMatch = true;
			node = syntaxTree.makeLeaf(lookahead.getRepresentation(), Integer.parseInt(lookahead.getRepresentation()));
		} else if (type == TokenType.DOUBLE) {
			isMatch = true;
			node = syntaxTree.makeLeaf(lookahead.getRepresentation(), Double.parseDouble(lookahead.getRepresentation()));
		} else if (type == TokenType.ID){
			isMatch = true;
			
			if (currentFuncName != null)
			{
				node = syntaxTree.makeLeaf(lookahead.getRepresentation(), SymbolTableTree.getInstance().getEntry(lookahead.getRepresentation(), currentFuncName));
			}
			else
			{
				node = syntaxTree.makeLeaf(lookahead.getRepresentation(), SymbolTableTree.getInstance().getEntry(lookahead.getRepresentation()));
			}
		} else {
			isMatch = type == lookahead.getType();
		}

		if (isMatch)
		{
			consumeToken();
		}
		else error();

		return node;
	}
	
	public void error() {
		System.out.println("\nValid Parse: false");
		System.out.println("Error on Line " + lex.getLineNum() + " at token " + lookahead.getRepresentation());
		//StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
		//System.out.println(Arrays.toString(stackTraceElements));
		System.exit(0);
	}
}