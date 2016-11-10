import java.util.*;

public class SyntaxTreeNode
{
	private String op;
	private SyntaxTreeNode parent = null;
	
	public SyntaxTreeNode(String inputOp)
	{
		this.op = inputOp;
	}
	
	public String toString()
	{
		return this.op;
	}
	
	// Models operators
	public static class Interior extends SyntaxTreeNode
	{
		private ArrayList<SyntaxTreeNode> children;
		
		public Interior(String inputOp, SyntaxTreeNode... inputChildren)
		{
			super(inputOp);
			this.children = new ArrayList<SyntaxTreeNode>();
			
			for (SyntaxTreeNode currentChild : inputChildren)
			{
				currentChild.parent = this;
				this.children.add(currentChild);
			}
		}
		
		public SyntaxTreeNode getChild(int index)
		{
			return this.children.get(index);
		}
		
		public int numChildren()
		{
			return this.children.size();
		}
	}
	
	// Models operands
	public static class Leaf<ValueType> extends SyntaxTreeNode
	{
		private ValueType val; // Lexical value of the operand
		
		public Leaf(String inputOp, ValueType inputVal)
		{
			super(inputOp);
			this.val = inputVal;
		}
		
		public ValueType getValue()
		{
			return this.val;
		}
	}
}