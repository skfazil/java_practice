package structural.compositepattern;

public class Leaf implements Component{
	private String name;
	private String price;

	public Leaf(String name, String price) {
		super();
		this.name = name;
		this.price = price;
	}

	@Override
	public void showPrice() {
		System.out.println(name+" "+price);
	}

}
