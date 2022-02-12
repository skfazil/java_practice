package abstractfactorypattern;

import abstractfactorypattern.animal.Animal;
import abstractfactorypattern.animal.Cat;
import abstractfactorypattern.animal.Dog;

public class AnimalFactory implements MyAbstractFactory<Animal>{

	@Override
	public Animal create(String type) {
		Animal animal= null;
		if(type.equals("Dog")) {
			animal=new Dog();
		}
		else if(type.equals("Cat")) {
			animal=new Cat();
		}
		return animal;
	}

}
