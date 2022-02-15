package multithreading;

public class ThreadEx2 implements Runnable{

	@Override
	public void run() {
		System.out.println("ThreadEx2 Thread is running");
	}
	
	public static void main(String[] args) {
		Runnable runnable = new ThreadEx2();
		Thread t2 = new Thread(runnable);
		t2.start();
	}

}
