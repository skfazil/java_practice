package structural.decoratorpattern;

public class TestDecorator {
	
	
	/*
	 * Decorator design pattern is used to modify the functionality of an object at
	 * runtime. At the same time other instances of the same class will not be
	 * affected by this, so individual object gets the modified behavior. Decorator
	 * design pattern is one of the structural design pattern (such as Adapter
	 * Pattern, Bridge Pattern, Composite Pattern) and uses abstract classes or
	 * interface with composition to implement.
	 * 
	 * Decorator Design Pattern: 
	 * We use inheritance or composition to extend the
	 * behavior of an object but this is done at compile time and its applicable to
	 * all the instances of the class. We can’t add any new functionality of remove
	 * any existing behavior at runtime – this is when Decorator pattern comes into
	 * picture.
	 * 
	 * Suppose we want to implement different kinds of cars – we can create
	 * interface Car to define the assemble method and then we can have a Basic car,
	 * further more we can extend it to Sports car and Luxury Car.
	 */
	public static void main(String[] args) {
		Car sportsCar = new SportsCar(new BasicCar());
		sportsCar.assemble();
		
		Car sportsLuxuryCar = new SportsCar(new LuxuryCar(new BasicCar()));
		sportsLuxuryCar.assemble();
	}

}
