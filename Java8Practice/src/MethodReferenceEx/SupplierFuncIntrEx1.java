package MethodReferenceEx;

import java.time.LocalDateTime;
import java.util.function.Supplier;

public class SupplierFuncIntrEx1 {
	
	public static void main(String[] args) {
		Supplier<String> s1 = ()->"Hello";
		
		System.out.println(s1.get());
		
		Supplier<LocalDateTime> s2 = ()->LocalDateTime.now();
		
		System.out.println(s2.get());
		
	}

}
