package MethodReferenceEx;

import java.util.function.Function;

public class FunctionalIntrfcEx2 {
	
	public static void main(String[] args) {
		
		//Using Function
		Function<String, Integer> func1 = val -> {
			System.out.println("Entered func1");
			return val.length(); 
		};
		System.out.println(func1.apply("HelloWorld"));
		
		Function<Integer, Integer> func2 = val2 -> 
			{
				System.out.println("Func2 entered");
				return val2*20;
			};
		
		//Using function with chaining.
		System.out.println(func1.andThen(func2).apply("MyBoyTesting"));
	}

}
