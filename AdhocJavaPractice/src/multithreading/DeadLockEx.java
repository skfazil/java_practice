package multithreading;

public class DeadLockEx {
	
	public static void main(String[] args) {
		String resource1 = "Fazil";
		String resource2 = "Shaik";
		
		Runnable r1 = ()->{
			//t1 is looking for lock to be released on resource2
			synchronized (resource1) {
				System.out.println("R1 executing resource 1");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized (resource2) {
					System.out.println("R2 executing resource 2");
				}
			}
			
		};
		
		Runnable r2 = ()->{
			//t2 is looking for lock to be released on resource1/ Leading to Deadlock
			synchronized (resource2) {
				System.out.println("R2 executing resource 2");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				synchronized (resource1) {
					System.out.println("R2 executing resource 1");
				}
			}
			
		};
		
		Thread t1=new Thread(r1);
		Thread t2=new Thread(r2);
		
		t1.start();
		t2.start();
	}

}
