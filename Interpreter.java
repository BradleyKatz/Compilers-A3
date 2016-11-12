
public class Interpreter {
	public void Interpretor() {
		
	}
	
	public void assign(String x, int val) {
		SymbolTableTree.getInstance().updateValue(x, val);
	}
	
	public void assign(String x, double val) {
		SymbolTableTree.getInstance().updateValue(x, val);
	}
	
	public void print(int x) {
		System.out.println(x);
	}
	
	public void print(double x) {
		System.out.println(x);
	}
}
