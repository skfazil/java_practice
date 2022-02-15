
public class MyTest1 {
	
	void draw() {
		System.out.println("Draw");
	}

	static class Hello{
		void sayHi() {
			System.out.println("HI");
		}
	}
	
	public static void main(String[] args) {
		Hello h = new MyTest1.Hello();
		h.sayHi();
	}
}


