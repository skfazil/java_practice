package abstractfactorypattern.animal;

import abstractfactorypattern.animal.Animal;

public class Cat implements Animal{

	@Override
	public void getType() {
		System.out.println("THis is Cat");
	}

	@Override
	public void getSound() {
		System.out.println("Cat says Meowwwwww");
	}

}
