import java.util.LinkedList;

public class Interpreter {
	private SyntaxTree intermediate;
	
	public Interpreter(SyntaxTree tree) {
		 intermediate = tree;
	}
	
	public void interpret() {
		LinkedList<SyntaxTreeNode> treeNodes = intermediate.getTraversalList();
		
		while (treeNodes.size() > 0) {
			SyntaxTreeNode node = treeNodes.removeLast();
			if (node instanceof SyntaxTreeNode.Interior) {
				SyntaxTreeNode.Interior interior = (SyntaxTreeNode.Interior) node;
				
				if (node.toString().equals("=")) {
					assign(interior.getChild(0).toString(), interior.getChild(1).getValue());
				}
				
				if (node.toString().equals("while")) {
					if (bexpr(interior.getChild(0))) {
						
					}
				}
				
				if (node.toString().equals("if")) {
					
				}
				
				if (node.toString().equals("print")) {
					// This needs to actually pass symbol table value
					print(interior.getChild(0).toString());
				}
			}
		}
	}
	
	public void assign(String x, int val) {
		SymbolTableTree.getInstance().updateValue(x, val);
	}
	
	public void assign(String x, double val) {
		SymbolTableTree.getInstance().updateValue(x, val);
	}
	
	public void print(String x) {
		System.out.println(x);
	}
	
	public boolean bexpr(SyntaxTreeNode node) {
		return true;
	}
}
