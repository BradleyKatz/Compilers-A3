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
	public static class Interior <ValueType> extends SyntaxTreeNode
	{
		private ArrayList<SyntaxTreeNode> children;

		private ValueType val; // Possible result of computation
		
		public Interior(String inputOp)
		{
			super(inputOp);
			this.children = new ArrayList<SyntaxTreeNode>();
		}
		
		public Interior(String inputOp, SyntaxTreeNode... inputChildren)
		{
			super(inputOp);
			this.children = new ArrayList<SyntaxTreeNode>();
					
			for (SyntaxTreeNode currentChild : inputChildren)
			{
				if (currentChild != null)
				{
					currentChild.parent = this;
					this.children.add(currentChild);
				}
			}
		}
		
		public void addChild(SyntaxTreeNode child)
		{
			this.children.add(child);
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