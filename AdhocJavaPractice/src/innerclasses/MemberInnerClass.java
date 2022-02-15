package innerclasses;

public class MemberInnerClass {
	
	void hello() {
		System.out.println("hello");
	}
	
	public class MyInner{
		
		void where() {
			System.out.println("Inside inner");
			hello();
		}
	}
	
	public static void main(String[] args) {
		MemberInnerClass mem = new MemberInnerClass();
		mem.hello();
		
		MyInner inner = mem.new MyInner();
		
		inner.where();
	}

}
