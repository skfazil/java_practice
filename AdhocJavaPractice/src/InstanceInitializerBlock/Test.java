package InstanceInitializerBlock;

public class Test extends InstanceInitializerBlockEx{
	
	Test() {
		super();
		System.out.println("Test class is invoked");
	}
	
	{
		System.out.println("Instance initializer block is invoked");
	}
	
	public static void main(String[] args) {
		Test t = new Test();
		
	}
}
