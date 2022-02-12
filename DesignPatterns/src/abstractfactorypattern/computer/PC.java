package abstractfactorypattern.computer;

public class PC implements Computer{

	@Override
	public void getType() {
		System.out.println("This  is a PC");
	}

}
