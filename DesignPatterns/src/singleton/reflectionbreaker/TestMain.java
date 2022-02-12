package singleton.reflectionbreaker;

public class TestMain {
	
	//To avoid reflection creating an object out of it
	public static void main(String[] args) {
		SingletonEnum s1 = SingletonEnum.INSTANCE;
		
		s1.setValue1(100);
		s1.setValue1(200);
		
		System.out.println(s1.getValue1());
	}

}
