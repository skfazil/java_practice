package collections.comparable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComparableTest {
	public static void main(String[] args) {
		List<Employee> list = new ArrayList<Employee>();
		list.add(new Employee(103, "Fazil", "SSE"));
		list.add(new Employee(101, "Nasru", "SSE"));
		list.add(new Employee(104, "Baba", "SSE"));
		list.add(new Employee(102, "Ammi", "SSE"));
		
		System.out.println(list);
		
		System.out.println("After sorting");
		Collections.sort(list);
		
		System.out.println(list);
	}

}
