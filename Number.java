
public class Number <ValueType> {
	
	public ValueType operation(String op, ValueType x, ValueType y) {
		ValueType result = null;
		if (x instanceof Integer && y instanceof Integer) {
			result = (ValueType) new Integer(operation(op, (int)x, (int)y));
		} else if (x instanceof Integer && y instanceof Double) {
			result = (ValueType) new Double(operation(op, ((Integer)x).doubleValue(), (double)y));
		} else if (x instanceof Double && y instanceof Integer) {
			result = (ValueType) new Double(operation(op, (double)x, ((Integer)y).doubleValue()));
		} else if (x instanceof Double && y instanceof Double) {
			result = (ValueType) new Double(operation(op, (double)x, (double)y));
		}
		return result;
	}
	
	public static double operation(String op, double d1, double d2) {
		double result = 0;
		switch (op) {
			case "+": result = d1 + d2; break;
			case "-": result = d1 - d2; break;
			case "*": result = d1 * d2; break;
			case "/": result = d1 / d2; break;
			case "%": result = d1 % d2; break;
		}
		return result;
	}
	
	public static int operation(String op, int i1, int i2) {
		int result = 0;
		switch (op) {
			case "+": result = i1 + i2; break;
			case "-": result = i1 - i2; break;
			case "*": result = i1 * i2; break;
			case "/": result = i1 / i2; break;
			case "%": result = i1 % i2; break;
		}
		return result;
	}
	
	public boolean comparison(String op, ValueType x, ValueType y) {
		boolean result = false;
		if (x instanceof Integer && y instanceof Integer) {
			result = comparison(op, (int)x, (int)y);
		} else if (x instanceof Integer && y instanceof Double) {
			result = comparison(op, ((Integer)x).doubleValue(), (double)y);
		} else if (x instanceof Double && y instanceof Integer) {
			result = comparison(op, (double)x, ((Integer)y).doubleValue());
		} else if (x instanceof Double && y instanceof Double) {
			result = comparison(op, (double)x, (double)y);
		}
		return result;
	}
	
	public static boolean comparison(String op, double d1, double d2) {
		boolean result = false;
		switch (op) {
			case ">": result = d1 > d2;
			case "<": result = d1 < d2;
			case ">=": result = d1 >= d2;
			case "<=": result = d1 <= d2;
			case "==": result = d1 == d2;
			case "<>": result = d1 != d2;
		}
		return result;
	}
	
	public static boolean comparison(String op, int i1, int i2) {
		boolean result = false;
		switch (op) {
			case ">": result = i1 > i2;
			case "<": result = i1 < i2;
			case ">=": result = i1 >= i2;
			case "<=": result = i1 <= i2;
			case "==": result = i1 == i2;
			case "<>": result = i1 != i2;
		}
		return result;
	}
}
