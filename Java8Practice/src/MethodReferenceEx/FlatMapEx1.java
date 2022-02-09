package MethodReferenceEx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMapEx1 {
	public static void main(String[] args) {
		Integer[][] arr = {{1,2,3},{4,5,6},{7,8,9}};
		//Integer[] arr = {1,2,3};
		
		List<Integer> list = Stream.of(arr)
				.flatMap(Stream::of)
				.map(x->x*10)
				.collect(Collectors.toList());
		
		System.out.println(list);
		
		//reduce method.
		List<BigDecimal> list2 = new ArrayList<BigDecimal>();
			list2.add(new BigDecimal(100));
			list2.add(new BigDecimal(200));
			list2.add(new BigDecimal(300));
			list2.add(new BigDecimal(400));
			list2.add(new BigDecimal(500));
		
		BigDecimal flist2 = list2.stream()
		.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		System.out.println(flist2);
		
		
		//Flat map to primitive types
		//flatMap to int
		int[][] intArr = {{1,2,3},{4,5,6},{7,8,9}};
		//int[] intArr = {1,2,3,4,};
		Stream<int[]> sArr = Stream.of(intArr);
		sArr.flatMapToInt(x->Arrays.stream(x))
		.filter(x->x>1)
		.forEach(System.out::println);
	}

}
