package structural.flyweightpattern;

public class ThinPen implements Pen{
	final BrushSize brushSize=BrushSize.THIN;
	private String color;
	
	public ThinPen(String color) {
		this.color=color;
	}

	@Override
	public void draw() {
		System.out.println("A thin pen with color "+color+" is being used");
	}

}
