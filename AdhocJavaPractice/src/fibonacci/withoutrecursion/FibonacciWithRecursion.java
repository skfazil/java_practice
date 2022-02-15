package fibonacci.withoutrecursion;

public class FibonacciWithRecursion {
	
	public static int fibonacciWithRecursion(int n) {
		if(n==0) {
			return 0;
		}
		else if(n==1 ||n==2) {
			return 1;
		}
		else {
			return fibonacciWithRecursion(n-2)+fibonacciWithRecursion(n-1);
		}
	}
	
	public static void main(String[] args) {
		int maxNumber=10;
		
		for(int i=0;i<maxNumber;i++) {
			System.out.println(fibonacciWithRecursion(i));
		}
	}

}
