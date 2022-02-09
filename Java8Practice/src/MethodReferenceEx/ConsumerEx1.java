package MethodReferenceEx;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerEx1 {
	
	public static void main(String[] args) {
		Consumer<Integer> cons = x->System.out.println(x);
		List<Integer> list = Arrays.asList(100,200,250,300);
		
		forEach(list,cons);
	}

	private static <T> void forEach(List<T> list, Consumer<T> cons) {
		// TODO Auto-generated method stub
		for(T t : list) {
			cons.accept(t);
		}
	}

}
