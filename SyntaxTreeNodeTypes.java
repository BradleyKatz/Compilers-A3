import java.util.*;

public class SyntaxTreeNodeTypes
{
	private String op;
	private SyntaxTreeNodeTypes parent = null;
	
	public SyntaxTreeNodeTypes(String inputOp)
	{
		this.op = inputOp;
	}
	
	public String toString()
	{
		return this.op;
	}
	
	// Models operators
	public static class SyntaxTreeNode extends SyntaxTreeNodeTypes
	{
		private ArrayList<SyntaxTreeNodeTypes> children;
		
		public SyntaxTreeNode(String inputOp, SyntaxTreeNodeTypes... inputChildren)
		{
			super(inputOp);
			this.children = new ArrayList<SyntaxTreeNodeTypes>();
			
			for (SyntaxTreeNodeTypes currentChild : inputChildren)
			{
				currentChild.parent = this;
				this.children.add(currentChild);
			}
		}
		
		public SyntaxTreeNodeTypes getChild(int index)
		{
			return this.children.get(index);
		}
		
		public int numChildren()
		{
			return this.children.size();
		}
	}
	
	// Models operands
	public static class SyntaxTreeLeaf<ValueType> extends SyntaxTreeNodeTypes
	{
		private ValueType val; // Lexical value of the operand
		
		public SyntaxTreeLeaf(String inputOp, ValueType inputVal)
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