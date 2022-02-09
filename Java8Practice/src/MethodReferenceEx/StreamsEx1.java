package MethodReferenceEx;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Empl{
	private String name;
	private Integer age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Empl [name=" + name + ", age=" + age + "]";
	}
	
	
}

public class StreamsEx1 {
	public static void main(String[] args) {
		//1.filter
		List<String> list1 = Arrays.asList("Shaik","Fazil","Ruks");
		
		List<String> fList1 = list1.stream()
		.filter(name->!name.equals("Ruks"))
		.collect(Collectors.toList());
		
		fList1.forEach(System.out::println);
		
		//2. findAny() orElse()
		List<Employee> list2 = Arrays.asList(new Employee("A", 30, 1000.0),
				new Employee("B", 31, 2000.0),
				new Employee("C", 32, 3000.0),
				new Employee("D", 33, 4000.0));
		
		Employee e1 = list2.stream()
				.filter(e->!"D".equalsIgnoreCase(e.getName()))
				.findAny()
				.orElse(null);
		System.out.println(e1);
		
		Employee e2 = list2.stream()
				.filter(e->"F".equalsIgnoreCase(e.getName()))
				.findAny()
				.orElse(null);
		System.out.println(e2);
		
		//map
		List<Integer> flist2 = list2.stream()
		//.map(e->e.getName()) //This expression can be written as below
		.map(Employee::getName)
		.map(String::length)
		.collect(Collectors.toList());
		
		System.out.println(flist2);
		
		//Mapping an obj of Employee to class Empl.
		List<Empl> flist3 = list2.stream()
		.map(emp->{
			Empl empl = new Empl();
			empl.setName(emp.getName());
			empl.setAge(emp.getAge());
			return empl;
		}).collect(Collectors.toList());
		
	}

}
