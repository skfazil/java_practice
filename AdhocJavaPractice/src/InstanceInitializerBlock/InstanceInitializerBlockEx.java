package InstanceInitializerBlock;


public class InstanceInitializerBlockEx {
	
	InstanceInitializerBlockEx() {
		System.out.println("Super class is invoked");
	}
	
	{
		System.out.println("Initializer block");
	}
	
	public static void main(String[] args) {
		InstanceInitializerBlockEx i = new InstanceInitializerBlockEx();
	}

}