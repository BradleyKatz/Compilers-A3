import java.util.*;

public class SyntaxTree
{
	private static SyntaxTree instance = null;
	private static SyntaxTreeNode root = null;
	private static LinkedList<SyntaxTreeNode> traversalList = null;
	
	public SyntaxTreeNode.Interior makeInterior(String op)
	{
		SyntaxTreeNode.Interior node = new SyntaxTreeNode.Interior(op);
		root = node;
		
		return node;
	}
	
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
		
		//TREE 1
//		SymbolTableTree.getInstance().addEntry(new SymbolTableEntry("x", SymbolTableEntry.VARIABLE, SymbolTableEntry.INT, null));
//		SymbolTableTree.getInstance().addEntry(new SymbolTableEntry("i", SymbolTableEntry.VARIABLE, SymbolTableEntry.INT, null));
//		
//		SyntaxTreeNode.Leaf l1 = tree.makeLeaf("x", SymbolTableTree.getInstance().getEntry("x"));
//		SyntaxTreeNode.Leaf l2 = tree.makeLeaf("0", 0);		
//		SyntaxTreeNode.Interior i1 = tree.makeInterior("=", l1, l2);
//		
//		SyntaxTreeNode.Leaf l3 = tree.makeLeaf("i", SymbolTableTree.getInstance().getEntry("i"));
//		SyntaxTreeNode.Leaf l4 = tree.makeLeaf("1", 1);		
//		SyntaxTreeNode.Interior i2 = tree.makeInterior("=", l3, l4);
//		
//		SyntaxTreeNode.Leaf l5 = tree.makeLeaf("i", SymbolTableTree.getInstance().getEntry("i"));
//		SyntaxTreeNode.Leaf l6 = tree.makeLeaf("10", 10);	
//		SyntaxTreeNode.Interior i3 = tree.makeInterior("<", l5, l6);
//		
//		SyntaxTreeNode.Leaf l7 = tree.makeLeaf("x", SymbolTableTree.getInstance().getEntry("x"));
//		SyntaxTreeNode.Leaf l8 = tree.makeLeaf("i", SymbolTableTree.getInstance().getEntry("i"));
//		SyntaxTreeNode.Leaf l9 = tree.makeLeaf("i", SymbolTableTree.getInstance().getEntry("i"));
//		SyntaxTreeNode.Leaf l10 = tree.makeLeaf("x", SymbolTableTree.getInstance().getEntry("x"));
//		SyntaxTreeNode.Interior i4 = tree.makeInterior("*", l8, l9);
//		SyntaxTreeNode.Interior i5 = tree.makeInterior("+", i4, l10);
//		SyntaxTreeNode.Interior i6 = tree.makeInterior("=", l7, i5);
//		
//		SyntaxTreeNode.Leaf l11 = tree.makeLeaf("i", SymbolTableTree.getInstance().getEntry("i"));
//		SyntaxTreeNode.Leaf l12 = tree.makeLeaf("i", SymbolTableTree.getInstance().getEntry("i"));
//		SyntaxTreeNode.Leaf l13 = tree.makeLeaf("1", 1);
//		SyntaxTreeNode.Interior i7 = tree.makeInterior("+", l12, l13);
//		SyntaxTreeNode.Interior i8 = tree.makeInterior("=", l11, i7);
//		
//		SyntaxTreeNode.Interior i9 = tree.makeInterior("statements", i6, i8);
//		
//		SyntaxTreeNode.Interior i10 = tree.makeInterior("while", i3, i9);
//		
//		SyntaxTreeNode.Leaf l14 = tree.makeLeaf("x", SymbolTableTree.getInstance().getEntry("x"));
//		SyntaxTreeNode.Interior i11 = tree.makeInterior("print", l14);
//		
//		SyntaxTreeNode.Interior i12 = tree.makeInterior("statements", i1, i2, i10, i11);
		
		//TREE 2
//		SymbolTableTree.getInstance().addEntry(new SymbolTableEntry("a", SymbolTableEntry.VARIABLE, SymbolTableEntry.INT, null));
//		SymbolTableTree.getInstance().addEntry(new SymbolTableEntry("b", SymbolTableEntry.VARIABLE, SymbolTableEntry.INT, null));
//		SymbolTableTree.getInstance().addEntry(new SymbolTableEntry("r", SymbolTableEntry.VARIABLE, SymbolTableEntry.INT, null));
//
//		SyntaxTreeNode.Leaf l1 = tree.makeLeaf("a", SymbolTableTree.getInstance().getEntry("a"));
//		SyntaxTreeNode.Leaf l2 = tree.makeLeaf("21", 21);		
//		SyntaxTreeNode.Interior i1 = tree.makeInterior("=", l1, l2);
//		
//		SyntaxTreeNode.Leaf l3 = tree.makeLeaf("b", SymbolTableTree.getInstance().getEntry("b"));
//		SyntaxTreeNode.Leaf l4 = tree.makeLeaf("15", 15);		
//		SyntaxTreeNode.Interior i2 = tree.makeInterior("=", l3, l4);		
//		
//		SyntaxTreeNode.Leaf l5 = tree.makeLeaf("b", SymbolTableTree.getInstance().getEntry("b"));
//		SyntaxTreeNode.Leaf l6 = tree.makeLeaf("0", 0);		
//		SyntaxTreeNode.Interior i3 = tree.makeInterior("<>", l5, l6);
//		
//		SyntaxTreeNode.Leaf l7 = tree.makeLeaf("r", SymbolTableTree.getInstance().getEntry("r"));
//		SyntaxTreeNode.Leaf l8 = tree.makeLeaf("a", SymbolTableTree.getInstance().getEntry("r"));
//		SyntaxTreeNode.Leaf l9 = tree.makeLeaf("b", SymbolTableTree.getInstance().getEntry("r"));
//		SyntaxTreeNode.Interior i4 = tree.makeInterior("%", l8, l9);
//		SyntaxTreeNode.Interior i5 = tree.makeInterior("=", l7, i4);
//		
//		SyntaxTreeNode.Leaf l10 = tree.makeLeaf("a", SymbolTableTree.getInstance().getEntry("a"));
//		SyntaxTreeNode.Leaf l11 = tree.makeLeaf("b", SymbolTableTree.getInstance().getEntry("b"));		
//		SyntaxTreeNode.Interior i6 = tree.makeInterior("=", l10, l11);
//		
//		SyntaxTreeNode.Leaf l12 = tree.makeLeaf("b", SymbolTableTree.getInstance().getEntry("b"));
//		SyntaxTreeNode.Leaf l13 = tree.makeLeaf("r", SymbolTableTree.getInstance().getEntry("r"));	
//		SyntaxTreeNode.Interior i7 = tree.makeInterior("=", l12, l13);
//		
//		SyntaxTreeNode.Interior i8 = tree.makeInterior("statements", i5, i6, i7);
//		
//		SyntaxTreeNode.Interior i9 = tree.makeInterior("while", i3, i8);
//		
//		SyntaxTreeNode.Leaf l14 = tree.makeLeaf("a", SymbolTableTree.getInstance().getEntry("a"));
//		SyntaxTreeNode.Interior i10 = tree.makeInterior("print", l14);
//		
//		SyntaxTreeNode.Interior i11 = tree.makeInterior("statements", i1, i2, i9, i10);

		//TREE 3
		SymbolTableTree.getInstance().addEntry(new SymbolTableEntry("s", SymbolTableEntry.VARIABLE, SymbolTableEntry.INT, null));
		SymbolTableTree.getInstance().addEntry(new SymbolTableEntry("f", SymbolTableEntry.FUNCTION, SymbolTableEntry.INT, null));
		SymbolTableTree.getInstance().addEntry(new SymbolTableEntry("a", SymbolTableEntry.VARIABLE, SymbolTableEntry.INT, null), "f");

		//f
		SyntaxTreeNode.Leaf l1 = tree.makeLeaf("d", SymbolTableTree.getInstance().getEntry("s"));
		SyntaxTreeNode.Leaf l2 = tree.makeLeaf("f", SymbolTableTree.getInstance().getEntry("f"));
		SyntaxTreeNode.Leaf l3 = tree.makeLeaf("f", SymbolTableTree.getInstance().getEntry("f"));
		SyntaxTreeNode.Leaf l4 = tree.makeLeaf("f", SymbolTableTree.getInstance().getEntry("f"));
		SyntaxTreeNode.Interior i1 = tree.makeInterior("+", l3, l4);
		SyntaxTreeNode.Interior i2 = tree.makeInterior("+", l2, i1);
		SyntaxTreeNode.Interior i3 = tree.makeInterior("=", l1, i2);
		//
		
		SyntaxTreeNode.Leaf l1 = tree.makeLeaf("s", SymbolTableTree.getInstance().getEntry("s"));
		SyntaxTreeNode.Leaf l2 = tree.makeLeaf("f", SymbolTableTree.getInstance().getEntry("f"));
		SyntaxTreeNode.Leaf l3 = tree.makeLeaf("f", SymbolTableTree.getInstance().getEntry("f"));
		SyntaxTreeNode.Leaf l4 = tree.makeLeaf("f", SymbolTableTree.getInstance().getEntry("f"));
		SyntaxTreeNode.Interior i1 = tree.makeInterior("+", l3, l4);
		SyntaxTreeNode.Interior i2 = tree.makeInterior("+", l2, i1);
		SyntaxTreeNode.Interior i3 = tree.makeInterior("=", l1, i2);
		
		SyntaxTreeNode.Leaf l5 = tree.makeLeaf("s", SymbolTableTree.getInstance().getEntry("s"));
		SyntaxTreeNode.Interior i6 = tree.makeInterior("print", l5);
		
		SyntaxTreeNode.Interior i11 = tree.makeInterior("statements", i3, i6);		
		
		Interpreter interpreter = new Interpreter(tree);
		interpreter.interpret();
	}
}