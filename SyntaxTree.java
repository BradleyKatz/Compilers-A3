import java.util.*;

public class SyntaxTree
{
	private static SyntaxTree instance = null;
	private static SyntaxTreeNodeTypes root = null;
	private static LinkedList<SyntaxTreeNodeTypes> traversalList = null;
		
	public SyntaxTreeNodeTypes.SyntaxTreeNode makeNode(String op, SyntaxTreeNodeTypes... children)
	{
		
		SyntaxTreeNodeTypes.SyntaxTreeNode nodeToReturn = new SyntaxTreeNodeTypes.SyntaxTreeNode(op, children);
		root = nodeToReturn;
		
		return nodeToReturn;
	}
	
	public <ValueType> SyntaxTreeNodeTypes.SyntaxTreeLeaf makeLeaf(String op, ValueType lexValue)
	{
		return new SyntaxTreeNodeTypes.SyntaxTreeLeaf(op, lexValue);
	}
	
	// Builds the program stack with a post-order traversal of the syntax tree.
	private void buildListPostorder(SyntaxTreeNodeTypes startPoint)
	{
		if (startPoint == null) return;
		
		if (startPoint instanceof SyntaxTreeNodeTypes.SyntaxTreeNode)
		{
			for (int i = 0; i < ((SyntaxTreeNodeTypes.SyntaxTreeNode) startPoint).numChildren(); i++)
			{
				buildListPostorder(((SyntaxTreeNodeTypes.SyntaxTreeNode) startPoint).getChild(i));
			}
		}
		
		traversalList.push(startPoint);
	}
	
	// Returns a list containing the intermediate program representation in bottom-up order.
	public LinkedList<SyntaxTreeNodeTypes> getTraversalList()
	{
		if (traversalList == null)
		{
			traversalList = new LinkedList<SyntaxTreeNodeTypes>();
			buildListPostorder(root);
			return (LinkedList<SyntaxTreeNodeTypes>)traversalList.clone();
		}
		else
		{
			return (LinkedList<SyntaxTreeNodeTypes>)traversalList.clone();
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
		SyntaxTree instance = SyntaxTree.getInstance();
		
		SyntaxTreeNodeTypes.SyntaxTreeLeaf p1 = instance.makeLeaf("butt1", 1234);
		SyntaxTreeNodeTypes.SyntaxTreeLeaf p2 = instance.makeLeaf("butt2", 5678);
		SyntaxTreeNodeTypes.SyntaxTreeNode p3 = instance.makeNode("-", p1, p2);
		SyntaxTreeNodeTypes.SyntaxTreeLeaf p4 = instance.makeLeaf("butt3", 4587);
		SyntaxTreeNodeTypes.SyntaxTreeNode p5 = instance.makeNode("+", p3, p4);
		
		LinkedList<SyntaxTreeNodeTypes> testList = instance.getTraversalList();
		
		while (testList.size() > 0)
		{
			System.out.println(testList.getLast());
			testList.removeLast();
		}
	}
}