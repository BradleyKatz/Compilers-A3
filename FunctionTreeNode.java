import java.util.*;

public class FunctionTreeNode
{
	private SyntaxTree functionBodyTree;
	private String functionName;
	
	public FunctionTreeNode(String name, SyntaxTree bodyTree)
	{
		functionName = name;
		functionBodyTree = bodyTree;
	}
	
	public String getFunctionName()
	{
		return functionName;
	}
	
	public SyntaxTree getFunctionBody()
	{
		return functionBodyTree;
	}
}