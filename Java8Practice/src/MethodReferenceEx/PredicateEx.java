package MethodReferenceEx;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateEx {
	
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(100,200,80,75,111,220);
		
		Predicate<Integer> prd1 = i->i<200;
		Predicate<Integer> prd2 = i->i>100;
		
		//Predicate.and()
		List<Integer> collectedList = 
				list.stream().filter(prd1.and(prd2)).collect(Collectors.toList());
		
		//Predicate.or()
				List<Integer> collectedList2 = 
						list.stream().filter(prd1.or(prd2)).collect(Collectors.toList());
				
		//Predicate.negate()
		List<Integer> collectedList3 = 
				list.stream().filter(prd1.or(prd2).negate()).collect(Collectors.toList());				
		
		System.out.println(collectedList);
		System.out.println(collectedList2);
		System.out.println(collectedList3);
	}

}
