package collections.comparable;

public class Employee implements Comparable<Employee>{
	
	private Integer rollNo;
	private String name;
	private String title;
	
	
	
	public Employee(Integer rollNo, String name, String title) {
		super();
		this.rollNo = rollNo;
		this.name = name;
		this.title = title;
	}
	public Integer getRollNo() {
		return rollNo;
	}
	public void setRollNo(Integer rollNo) {
		this.rollNo = rollNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "Employee [rollNo=" + rollNo + ", name=" + name + ", title=" + title + "]";
	}
	
	//This is for comparing integers
	/*@Override
	public int compareTo(Employee o) {
		if(this.rollNo>o.rollNo)
			return 1;
		else if(this.rollNo<o.rollNo)
			return -1;
		else return 0;
	}*/
	
	//This is for comparing Strings
	@Override
	public int compareTo(Employee o) {
		return this.name.compareTo(o.getName());
	}

}
