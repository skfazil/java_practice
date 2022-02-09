package MethodReferenceEx;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamsIterateEx {
	
	public static void main(String[] args) {
		//iterate example
		Stream.iterate(1, n->n+10)
		.limit(20)
		.forEach(System.out::println);
		
		//iterate example with Fibonacci series.
		Integer count = Stream.iterate(new int[] {0,1},n->new int[] {n[1],n[0]+n[1]})
		.limit(10)
		.map(n->n[0])
		.reduce(0,Integer::sum);
		System.out.println(count);
	}

}
