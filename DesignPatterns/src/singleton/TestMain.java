package singleton;

public class TestMain {

	public static void main(String[] args) {
		SingletonEx singleton = SingletonEx.getInstance();
		
		System.out.println(singleton);
		
		SingletonEx singleton1 = SingletonEx.getInstance();
		
		System.out.println(singleton1);
	}
}
