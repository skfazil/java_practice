import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

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
		ArrayList<E>
		BiPredicate<Integer,Integer> p = (x,y)->x>y;
		System.out.println(p.test(10, 5));
	}
}


