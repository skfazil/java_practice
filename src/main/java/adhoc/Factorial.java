package adhoc;

import java.util.Scanner;

public class Factorial {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter desired factorial value");
  		int val = sc.nextInt();
		System.out.println("Your input is "+val);
		int fact = getFactorial(val);
		System.out.println("Result is : "+fact);
		sc.close();
			
	}

	private static int getFactorial(int val) {
		int result=0;
		if(val>1) {
			result=val*getFactorial(val-1);
		}
		return result;
	}

}
