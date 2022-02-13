package structural.adapterpattern;

public class Volt {
	
	public String voltage;
	
	public Volt(String voltage) {
		this.voltage=voltage;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	@Override
	public String toString() {
		return "Volt [voltage=" + voltage + "]";
	}

}
