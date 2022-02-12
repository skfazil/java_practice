package abstractfactorypattern;

import abstractfactorypattern.computer.Computer;
import abstractfactorypattern.computer.Laptop;
import abstractfactorypattern.computer.PC;

public class ComputerFactory implements MyAbstractFactory<Computer>{

	@Override
	public Computer create(String type) {
		Computer computer = null;
		if(type.equals("PC")) {
			computer=new PC();
		}
		else if(type.equals("Laptop")) {
			computer=new Laptop();
		}
		return computer;
	}

}
