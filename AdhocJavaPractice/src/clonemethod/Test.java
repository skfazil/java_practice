package clonemethod;

public class Test {
	
	public static void main(String[] args) {
		Car car = new Car();
		car.setColor("Green");
		car.setMake("Tata");
		car.setYear("2022");
		
		System.out.println(car);
		Car car1=null;
		try {
			car1 = (Car) car.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(car1);
	}

}
