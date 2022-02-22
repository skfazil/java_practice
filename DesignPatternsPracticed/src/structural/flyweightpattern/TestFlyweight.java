package structural.flyweightpattern;

public class TestFlyweight {
	
	public static void main(String[] args) {
		Pen pen = FlyweightFactory.getThickPen("Yellow");
		pen.draw();
		System.out.println(pen.hashCode());
		
		//Since intrinsic and extrinsic properties are same,
		//FlyweightFactory returns the same object.
		Pen pen1 = FlyweightFactory.getThickPen("Yellow");
		pen1.draw();
		System.out.println(pen1.hashCode());
		
		//Since we changed extrinsic property to Green, now it creates a new objet of Pen.
		Pen pen2 = FlyweightFactory.getThickPen("Green");
		pen2.draw();
		System.out.println(pen2.hashCode());
	}

}
