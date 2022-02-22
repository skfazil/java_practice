package builderpattern;

public class Computer {
	
	//Consider these are mandatory
	private String processor;
	private String ram;
	private String hdd;
	
	//Consider these are optional
	private String graphicsMemory;
	private String extraBattery;
	private String addOns;	

	public String getProcessor() {
		return processor;
	}

	public String getRam() {
		return ram;
	}

	public String getHdd() {
		return hdd;
	}

	public String getGraphicsMemory() {
		return graphicsMemory;
	}

	public String getExtraBattery() {
		return extraBattery;
	}

	public String getAddOns() {
		return addOns;
	}
	
	
	@Override
	public String toString() {
		return "Computer [processor=" + processor + ", ram=" + ram + ", hdd=" + hdd + ", graphicsMemory="
				+ graphicsMemory + ", extraBattery=" + extraBattery + ", addOns=" + addOns + "]";
	}

	private Computer(ComputerBuilder computerBuilder) {
		this.processor=computerBuilder.processor;
		this.ram=computerBuilder.ram;
		this.hdd=computerBuilder.hdd;
		this.graphicsMemory=computerBuilder.graphicsMemory;
		this.extraBattery=computerBuilder.extraBattery;
		this.addOns=computerBuilder.addOns;
	}

	
	//Important to make the inner class as static
	public static class ComputerBuilder{
		//Consider these are mandatory
		private String processor;
		private String ram;
		private String hdd;
		
		//Consider these are optional
		private String graphicsMemory;
		private String extraBattery;
		private String addOns;
		
		public ComputerBuilder(String processor,String ram,String hdd) {
			this.processor=processor;
			this.ram=ram;
			this.hdd=hdd;
		}
		
		public ComputerBuilder setGraphicsMemory(String graphicsMemory) {
			this.graphicsMemory=graphicsMemory;
			return this;
		}
		
		public ComputerBuilder setExtraBattery(String extraBattery) {
			this.extraBattery=extraBattery;
			return this;
		}
		
		public ComputerBuilder setAddOns(String addOns) {
			this.addOns=addOns;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
	}

}
