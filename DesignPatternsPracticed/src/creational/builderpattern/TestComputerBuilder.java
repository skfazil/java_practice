package creational.builderpattern;

import creational.builderpattern.Computer.ComputerBuilder;

public class TestComputerBuilder {
	
	public static void main(String[] args) {
		ComputerBuilder builder = new Computer.ComputerBuilder("Corei7", "16GB", "1TB");
		Computer computer = builder.setAddOns("NeedFullHDScreen")
		.setExtraBattery("100Watts")
		.setGraphicsMemory("NVIDIA4GB")
		.build();
		
		System.out.println(computer);
	}
}
