package AbstracttClass;

public class TestAbstract extends AbstracttClass{
	
	public static void main(String[] args) {
		TestAbstract test = new TestAbstract();
		test.printMsg();
		test.sayHello();
	}

	@Override
	void printMsg() {
		System.out.println("Printing msg");
	}

}
