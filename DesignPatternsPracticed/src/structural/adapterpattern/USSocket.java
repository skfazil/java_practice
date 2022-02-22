package structural.adapterpattern;

public class USSocket implements Socket{

	//THis is US socket which will output 120V. We can also have anoether class implementing Socket, to have IndianSocket
	//Which will generally output 240V.
	@Override
	public Volt getVolt() {
		return new Volt("120");
	}

}
