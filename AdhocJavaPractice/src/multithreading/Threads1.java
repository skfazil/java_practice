package multithreading;

public class Threads1 extends Thread{
	
	@Override
	public void run() {
		System.out.println("Thread is running");
	}
	
	public static void main(String[] args) {
		Threads1 t1 = new Threads1();
		t1.start();
		System.out.println(t1.getName());
	}

}
