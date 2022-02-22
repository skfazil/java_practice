
public class MyTest2 {

	public MyTest2() {
		System.out.println("MyTest2 created");
	}

	public void xyz() {
		System.out.println("myTest2");
	}
}

class B extends MyTest2 {
	public B() {
		System.out.println("B created");
	}

	public void xyz() {
		System.out.println("B");
	}
}