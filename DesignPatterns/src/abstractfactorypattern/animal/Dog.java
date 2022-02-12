package abstractfactorypattern.animal;

public class Dog implements Animal{

	@Override
	public void getType() {
		System.out.println("This is Dog");
	}

	@Override
	public void getSound() {
		System.out.println("I saw Bowwwww");
	}

}
