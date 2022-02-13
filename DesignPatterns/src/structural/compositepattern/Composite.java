package structural.compositepattern;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {

	private String name;
	private List<Component> components = new ArrayList<Component>();

	public Composite(String name) {
		this.name = name;
	}
	
	public void addComponent(Component comp) {
		components.add(comp);
	}

	@Override
	public void showPrice() {
		System.out.println(name);
		for(Component c : components) {
			c.showPrice();
		}
	}

}
