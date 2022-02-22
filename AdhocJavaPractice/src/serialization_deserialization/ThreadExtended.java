package serialization_deserialization;

public class ThreadExtended extends Thread {
	public void run() {
		System.out.println("\nThread is running now\n");
	}

	public static void main(String[] args) {		
		ThreadExtended threadE = new ThreadExtended();
		threadE.start();
		System.out.println(Thread.currentThread().getName());
	}
}
