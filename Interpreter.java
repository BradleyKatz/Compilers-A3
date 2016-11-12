import java.util.LinkedList;

import SyntaxTreeNode.Interior;

public class Interpreter {
	private SyntaxTree intermediate;
	
	public Interpreter(SyntaxTree tree) {
		 intermediate = tree;
	}
	
	public void interpret() {
		LinkedList<SyntaxTreeNode> treeNodes = intermediate.getTraversalList();
		SyntaxTreeNode.Interior statements = (SyntaxTreeNode.Interior) treeNodes.getFirst();
		statement_seq(statements);
	}
	
	public void statement_seq(SyntaxTreeNode.Interior statements) {
		for (int i = 0; i < statements.numChildren(); i++) {
			process(statements.getChild(i));
		}		
	}
	
	public void process (SyntaxTreeNode node) {
		if (node instanceof SyntaxTreeNode.Interior) {
			SyntaxTreeNode.Interior interior = (SyntaxTreeNode.Interior) node;
			
			if (node.toString().equals("=")) {
				process(interior.getChild(1));
				assign(interior.getChild(0).toString(), interior.getChild(1).getValue()));
			}
			
			if (node.toString().equals("+")) {
				add(interior.getChild(0).toString(), process(interior.getChild(1)));
			}
			
			if (node.toString().equals("-")) {
				sub(interior.getChild(0).toString(), process(interior.getChild(1)));
			}
			
			if (node.toString().equals("*")) {
				mul(interior.getChild(0).toString(), process(interior.getChild(1)));
			}
			
			if (node.toString().equals("/")) {
				div(interior.getChild(0).toString(), process(interior.getChild(1)));
			}
			
			if (node.toString().equals("%")) {
				mod(interior.getChild(0).toString(), process(interior.getChild(1)));
			}
			
			if (node.toString().equals("while")) {
				while (process(interior.getChild(0))) {
					process(interior.getChild(1));
				}
			}
			
			if (node.toString().equals("if")) {
				if (process(interior.getChild(0))) {
					process(interior.getChild(1));
				} else {
					process(interior.getChild(2));
				}
			}
			
			if (node.toString().equals("print")) {
				// This needs to actually pass symbol table value
				print(interior.getChild(0).toString());
			}
		}
	}
	
	public void assign(String x, int val) {
		SymbolTableTree.getInstance().updateValue(x, val);
	}
	
	public void assign(String x, double val) {
		SymbolTableTree.getInstance().updateValue(x, val);
	}
	
	public int add(int x, int y) {
		return x + y;
	}
	
	public int sub(int x, int y) {
		return x - y;
	}
	
	public int mul(int x, int y) {
		return x * y;
	}

	public int div(int x, int y) {
		return x / y;
	}
	
	public int mod(int x, int y) {
		return x % y;
	}
	
	public void print(String x) {
		System.out.println(x);
	}
	
	public boolean bexpr(String comp, int x, int y) {
		if(comp.equals(">")) return x < y;
		else if(comp.equals("<")) return x < y;
		else if(comp.equals("<=")) return x <= y;
		else if(comp.equals(">=")) return x >= y;
		else if(comp.equals("==")) return x == y;
		else if(comp.equals("<>")) return x != y;
		return false;
	}
}
