package fibonacci.withoutrecursion;

public class FibonacciWithoutRecursion {
	
	public static void main(String[] args) {
		int x=0; 
		int y=1;
		int z;
		System.out.println(x+" "+y);
		int count=10;
		
		//loop starts from 2 since 0,1 are already printed.
		for(int i=2;i<count;i++) {
			z=x+y;
			System.out.println(z);
			x=y;
			y=z;
		}
	}

}
