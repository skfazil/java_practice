package structural.flyweightpattern;

public class ThickPen implements Pen{
	final BrushSize brushSize=BrushSize.THICK;
	private String color;
	
	public ThickPen(String color) {
		this.color=color;
	}

	@Override
	public void draw() {
		System.out.println("A thick pen with color "+color+" is being used");
	}

}
