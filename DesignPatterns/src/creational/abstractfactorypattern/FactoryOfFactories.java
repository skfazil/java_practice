package abstractfactorypattern;

public class FactoryOfFactories {
	
	public static void main(String[] args) {
		//Abstract factory patterns gives the flexibility to the client of not knowing the contrac, but still create the objects user abstraction.
		//This is more of a Factory for Factory methods.
		AnimalFactory af=(AnimalFactory) getFactory("Animal");
		af.create("Dog").getType();
		
		ComputerFactory cf=(ComputerFactory) getFactory("Computer");
		cf.create("Laptop").getType();
	}

	private static MyAbstractFactory getFactory(String factoryType) {
		if(factoryType.equals("Animal")) {
			return new AnimalFactory();
		}
		else if(factoryType.equals("Computer")) {
			return new ComputerFactory();
		}
		return null;
	}

}
