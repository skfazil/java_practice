package structural.decoratorpattern;

public class CarDecorator implements Car{
	protected Car car;
	
	public CarDecorator(Car c) {
		this.car=c;
	}

	@Override
	public void assemble() {
		car.assemble();
	}

}
