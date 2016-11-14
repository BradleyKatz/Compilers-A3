
public class Number <ValueType> {
	
	public static double operation(String op, int i, double d) {
		return operation(op, (double)i, d);
	}
	
	public static double operation(String op, double d, int i) {
		return operation(op, d, (double)i);
	}
	
	public static double operation(String op, double d1, double d2) {
		double result = 0;
		switch (op) {
			case "+": result = d1 + d2;
			case "-": result = d1 - d2;
			case "*": result = d1 * d2;
			case "/": result = d1 / d2;
			case "%": result = d1 % d2;
		}
		return result;
	}
	
	public static int operation(String op, int i1, int i2) {
		int result = 0;
		switch (op) {
			case "+": result = i1 + i2;
			case "-": result = i1 - i2;
			case "*": result = i1 * i2;
			case "/": result = i1 / i2;
			case "%": result = i1 % i2;
		}
		return result;
	}
	
	public static boolean comparison(String op, int i, double d) {
		return comparison(op, (double)i, d);
	}
	
	public static boolean comparison(String op, double d, int i) {
		return comparison(op, d, (double)i);
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
