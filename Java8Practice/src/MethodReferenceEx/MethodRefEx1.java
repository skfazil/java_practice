package MethodReferenceEx;

import java.util.ArrayList;
import java.util.List;

public class MethodRefEx1 {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("AAA");
		list.add("BBB");
		list.add("CCC");
		list.add("DDD");
		
		//list.forEach(DisplayMessage::print);
		
		list.forEach(System.out::println);
		
	}

}
