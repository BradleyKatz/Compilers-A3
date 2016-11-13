import java.util.LinkedList;

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
		// If Leaf, doesn't need to do anything, value is computed
		if (node instanceof SyntaxTreeNode.Interior) {
			SyntaxTreeNode.Interior interior = (SyntaxTreeNode.Interior) node;
			
			if (node.toString().equals("=")) {
				process(interior.getChild(1));
				assign(interior.getChild(0).toString(), (int)interior.getChild(1).getValue());
			}
			
			if (node.toString().equals("+")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				node.setValue(add(left, right));
			}
//			
//			if (node.toString().equals("-")) {
//				sub(interior.getChild(0).toString(), process(interior.getChild(1)));
//			}
//			
			if (node.toString().equals("*")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				node.setValue(mul(left, right));
			}
//			
//			if (node.toString().equals("/")) {
//				div(interior.getChild(0).toString(), process(interior.getChild(1)));
//			}
//			
//			if (node.toString().equals("%")) {
//				mod(interior.getChild(0).toString(), process(interior.getChild(1)));
//			}
//			
			if (node.toString().equals("while")) {
				process(interior.getChild(0));
				while ((boolean)interior.getChild(0).getValue() == true) {
					System.out.println(interior.getChild(0).getValue());
					process(interior.getChild(1));
				}
			}
//			
//			if (node.toString().equals("if")) {
//				if (process(interior.getChild(0))) {
//					process(interior.getChild(1));
//				} else {
//					process(interior.getChild(2));
//				}
//			}
//			
			if (node.toString().equals("print")) {
				String symbol = interior.getChild(0).toString();
				print(SymbolTableTree.getInstance().getEntry(symbol).toStringValue());
			}
			
			if (node.toString().equals("<")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				if (left < right)
					interior.setValue(true);
				else
					interior.setValue(false);
			}
		}
	}
	
	public int resolve(SyntaxTreeNode node) {
		int value;
		if (node.getValue() instanceof SymbolTableEntry) {
			String symbol = node.toString();
			value = (int)SymbolTableTree.getInstance().getEntry(symbol).getValue();
		} else {
			value = (int)node.getValue();
		}
		return value;
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
}
