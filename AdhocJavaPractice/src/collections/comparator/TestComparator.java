package collections.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import collections.comparable.Employee;

public class TestComparator {

	public static void main(String[] args) {
		List<Employee> list = new ArrayList<Employee>();
		list.add(new Employee(103, "Fazil", "SSE"));
		list.add(new Employee(101, "Nasru", "SSE"));
		list.add(new Employee(104, "Baba", "SSE"));
		list.add(new Employee(102, "Ammi", "SSE"));
		
		System.out.println(list);
		
		Collections.sort(list, new NameComparator());
		
		System.out.println(list);
		
		//Java8 implementation since Comparator is Functional interface with one method.
		Comparator<Employee> rollNoComparator = (Employee e1,Employee e2)->{
			if(e1.getRollNo()>e2.getRollNo())
				return 1;
			else if(e1.getRollNo()<e2.getRollNo())
				return -1;
			else return 0;
		};
		
		Collections.sort(list,rollNoComparator);
		System.out.println(list);
	}
}
