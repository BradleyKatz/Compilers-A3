import java.util.*;

public class SyntaxTree
{
	private static SyntaxTreeNode root = null;
	private static LinkedList<SyntaxTreeNode> traversalList = null;
	
	public SyntaxTreeNode.Interior makeInterior(String op)
	{
		SyntaxTreeNode.Interior node = new SyntaxTreeNode.Interior(op);
		
		if (root == null)
			root = node;
		
		return node;
	}
	
	public SyntaxTreeNode.Interior makeInterior(String op, SyntaxTreeNode... children)
	{
		
		SyntaxTreeNode.Interior node = new SyntaxTreeNode.Interior(op, children);
		
		if (root == null)
			root = node;
		
		return node;
	}
	
	public <ValueType> SyntaxTreeNode.Leaf makeLeaf(String op, ValueType lexValue)
	{
		return new SyntaxTreeNode.Leaf(op, lexValue);
	}
	
	// Builds the program stack with a post-order traversal of the syntax tree.
	private void buildListPostorder(SyntaxTreeNode startPoint)
	{
		if (startPoint == null) return;
		
		if (startPoint instanceof SyntaxTreeNode.Interior)
		{
			for (int i = 0; i < ((SyntaxTreeNode.Interior) startPoint).numChildren(); i++)
			{
				buildListPostorder(((SyntaxTreeNode.Interior) startPoint).getChild(i));
			}
		}
		
		traversalList.push(startPoint);
	}
	
	// Returns a list containing the intermediate program representation in bottom-up order.
	public LinkedList<SyntaxTreeNode> getTraversalList()
	{
		if (traversalList == null)
		{
			traversalList = new LinkedList<SyntaxTreeNode>();
			buildListPostorder(root);
			return (LinkedList<SyntaxTreeNode>)traversalList.clone();
		}
		else
		{
			return (LinkedList<SyntaxTreeNode>)traversalList.clone();
		}
	}
	
	/*
	public static void main(String args[])
	{
		SymbolTableTree.getInstance().addEntry(new SymbolTableEntry("x", SymbolTableEntry.FUNCTION, SymbolTableEntry.INT, null));
		SymbolTableTree.getInstance().addEntry(new SymbolTableEntry("i", SymbolTableEntry.FUNCTION, SymbolTableEntry.INT, null));
		
		SyntaxTree tree = SyntaxTree.getInstance();
		
		SyntaxTreeNode.Leaf l1 = tree.makeLeaf("x", SymbolTableTree.getInstance().getEntry("x"));
		SyntaxTreeNode.Leaf l2 = tree.makeLeaf("0", 0);		
		SyntaxTreeNode.Interior i1 = tree.makeInterior("=", l1, l2);
		
		SyntaxTreeNode.Leaf l3 = tree.makeLeaf("i", SymbolTableTree.getInstance().getEntry("i"));
		SyntaxTreeNode.Leaf l4 = tree.makeLeaf("1", 1);		
		SyntaxTreeNode.Interior i2 = tree.makeInterior("=", l3, l4);
		
		SyntaxTreeNode.Leaf l5 = tree.makeLeaf("i", SymbolTableTree.getInstance().getEntry("i"));
		SyntaxTreeNode.Leaf l6 = tree.makeLeaf("10", 10);	
		SyntaxTreeNode.Interior i3 = tree.makeInterior("<", l5, l6);
		
		SyntaxTreeNode.Leaf l7 = tree.makeLeaf("x", SymbolTableTree.getInstance().getEntry("x"));
		SyntaxTreeNode.Leaf l8 = tree.makeLeaf("i", SymbolTableTree.getInstance().getEntry("i"));
		SyntaxTreeNode.Leaf l9 = tree.makeLeaf("i", SymbolTableTree.getInstance().getEntry("i"));
		SyntaxTreeNode.Leaf l10 = tree.makeLeaf("x", SymbolTableTree.getInstance().getEntry("x"));
		SyntaxTreeNode.Interior i4 = tree.makeInterior("*", l8, l9);
		SyntaxTreeNode.Interior i5 = tree.makeInterior("+", i4, l10);
		SyntaxTreeNode.Interior i6 = tree.makeInterior("=", l7, i5);
		
		SyntaxTreeNode.Interior i7 = tree.makeInterior("while", i3, i6);
		
		SyntaxTreeNode.Leaf l11 = tree.makeLeaf("x", SymbolTableTree.getInstance().getEntry("x"));
		SyntaxTreeNode.Interior i8 = tree.makeInterior("print", l11);
		
		SyntaxTreeNode.Interior i9 = tree.makeInterior("statements", i1, i2, i7, i8);
		
		Interpreter interpreter = new Interpreter(tree);
		interpreter.interpret();
	}
	*/
}