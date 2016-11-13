import java.util.LinkedList;
import java.util.Stack;

public class Interpreter {
	private SyntaxTree intermediate;
	private Stack runtimeStack = new Stack();
	
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
				int right = resolve(interior.getChild(1));
				assign(interior.getChild(0).toString(), right);
			}
			
			else if (node.toString().equals("+")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				node.setValue(add(left, right));
			}

			else if (node.toString().equals("-")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				node.setValue(sub(left, right));
			}			
			
			else if (node.toString().equals("*")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				node.setValue(mul(left, right));
			}
	
			else if (node.toString().equals("/")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				node.setValue(div(left, right));
			}			

			else if (node.toString().equals("%")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				node.setValue(mod(left, right));
			}
			
			else if (node.toString().equals("while")) {
				process(interior.getChild(0));
				while ((boolean)interior.getChild(0).getValue() == true) {
					statement_seq((SyntaxTreeNode.Interior)interior.getChild(1));
					process(interior.getChild(0));
				}
			}
			
			else if (node.toString().equals("if")) {
				process(interior.getChild(0));
				if ((boolean)interior.getChild(0).getValue() == true) {
					statement_seq((SyntaxTreeNode.Interior)interior.getChild(1));
				} else {
					statement_seq((SyntaxTreeNode.Interior)interior.getChild(2));
				}
			}
			
			else if (node.toString().equals("print")) {
				process(interior.getChild(0));
				int child = resolve(interior.getChild(0));
				print(child);
			}
			
			else if (node.toString().equals("<")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				if (left < right)
					interior.setValue(true);
				else
					interior.setValue(false);
			}
			
			else if (node.toString().equals(">")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				if (left > right)
					interior.setValue(true);
				else
					interior.setValue(false);
			}
			
			else if (node.toString().equals("<=")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				if (left <= right)
					interior.setValue(true);
				else
					interior.setValue(false);
			}
			
			else if (node.toString().equals(">=")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				if (left >= right)
					interior.setValue(true);
				else
					interior.setValue(false);
			}
			
			else if (node.toString().equals("==")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				if (left == right)
					interior.setValue(true);
				else
					interior.setValue(false);
			}
			
			else if (node.toString().equals("<>")) {
				process(interior.getChild(0));
				process(interior.getChild(1));
				int left = resolve(interior.getChild(0));
				int right = resolve(interior.getChild(1));
				if (left != right)
					interior.setValue(true);
				else
					interior.setValue(false);
			}
			
//			else if (node.toString().equals("return")) {
//				runtimeStack.push(interior.getValue());
//			}
			
//			else { //function
//				SymbolTableEntry entry = SymbolTableTree.getInstance().getEntry(interior.toString());
//				if (entry.getIdType().equals("function")) {
//					SyntaxTreeNode.Interior params = (SyntaxTreeNode.Interior) interior.getChild(0);
//					System.out.println(entry);
//					System.out.println(((SyntaxTree)entry.getValue()).getTraversalList());
//					for (int i = 0; i < params.numChildren(); i++) {
//						runtimeStack.push(params.getChild(i));
//						//SymbolTableTree.getInstance().updateValue(x, val, interior.toString());
//					}
//					
//					//function(entry, params);
//					interior.setValue(runtimeStack.pop());
//				}
//			}
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
	
	public void print(int x) {
		System.out.println(x);
	}
}