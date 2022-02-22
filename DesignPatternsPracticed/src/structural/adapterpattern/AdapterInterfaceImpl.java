package structural.adapterpattern;

public class AdapterInterfaceImpl extends USSocket implements AdapterInterface {

	@Override
	public Volt convertTo120V() {
		return getVolt();
	}

	@Override
	public Volt convertTo12V() {
		return convertTo(getVolt().voltage, 10);
	}

	@Override
	public Volt convertTo3V() {
		return convertTo(getVolt().voltage, 40);
	}
	
	private Volt convertTo(String voltage,int i) {
		Integer vltg = Integer.parseInt(voltage);
		String newVoltage = String.valueOf(vltg/i);
		return new Volt(newVoltage);
	}

}
