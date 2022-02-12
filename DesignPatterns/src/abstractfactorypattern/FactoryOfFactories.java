package abstractfactorypattern;

public class FactoryOfFactories {
	
	public static void main(String[] args) {
		
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
