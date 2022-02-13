package abstractfactorypattern.computer;

public class Laptop implements Computer{

	@Override
	public void getType() {
		System.out.println("THis is a laptop");
	}

}
