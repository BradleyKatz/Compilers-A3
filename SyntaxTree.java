import java.util.*;

public class SyntaxTree
{
	private static SyntaxTree instance = null;
	private static SyntaxTreeNode root = null;
	private static LinkedList<SyntaxTreeNode> traversalList = null;
		
	public SyntaxTreeNode.Interior makeInterior(String op, SyntaxTreeNode... children)
	{
		
		SyntaxTreeNode.Interior node = new SyntaxTreeNode.Interior(op, children);
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
	
	public static SyntaxTree getInstance()
	{
		if (instance == null)
			instance = new SyntaxTree();
		
		return instance;
	}
	
	public static void main(String args[])
	{
		SyntaxTree tree = SyntaxTree.getInstance();
		
		SyntaxTreeNode.Leaf p1 = tree.makeLeaf("x", 1234);
		SyntaxTreeNode.Leaf p2 = tree.makeLeaf("y", 5678);
		SyntaxTreeNode.Interior p3 = tree.makeInterior("-", p1, p2);
		SyntaxTreeNode.Leaf p4 = tree.makeLeaf("z", 4587);
		SyntaxTreeNode.Interior p5 = tree.makeInterior("+", p3, p4);
		
		LinkedList<SyntaxTreeNode> testList = tree.getTraversalList();
		
		while (testList.size() > 0)
		{
			System.out.println(testList.getLast());
			testList.removeLast();
		}
	}
}