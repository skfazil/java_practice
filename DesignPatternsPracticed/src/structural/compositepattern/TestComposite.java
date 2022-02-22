package structural.compositepattern;

public class TestComposite {
	
	public static void main(String[] args) {
		Component hdd = new Leaf("hdd", "3000");
		Component processor = new Leaf("processor", "15000");
		Component ram = new Leaf("ram", "4000");
		Component graphicsCard = new Leaf("graphicsCard", "20000");
		Component mouse = new Leaf("mouse", "500");
		Component keyboard = new Leaf("keyboard", "2000");
		Component webCam = new Leaf("webCam", "2500");
		Component monitor = new Leaf("monitor", "12000");
		
		Composite peripherals = new Composite("peripherals");
		peripherals.addComponent(mouse);
		peripherals.addComponent(keyboard);
		peripherals.addComponent(webCam);
		
		Composite motherBoard = new Composite("motherBoard");
		motherBoard.addComponent(ram);
		motherBoard.addComponent(hdd);
		motherBoard.addComponent(processor);
		motherBoard.addComponent(graphicsCard);
		
		Composite cabinet = new Composite("cabinet");
		cabinet.addComponent(peripherals);
		cabinet.addComponent(motherBoard);
		
		Composite fullyAssembledComputer = new Composite("FullyAssembledComputer");
		fullyAssembledComputer.addComponent(cabinet);
		fullyAssembledComputer.addComponent(monitor);
		
		//
		fullyAssembledComputer.showPrice();
		
		peripherals.showPrice();
		
		
	}

}
