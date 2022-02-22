package executorframework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ExecutorTest1 {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Callable<String> callable = ()->{
			Thread.sleep(3000);
			return "Callable executed";
		};
		ExecutorService service = Executors.newFixedThreadPool(10);
		Future<String> future = service.submit(callable);
		System.out.println("Future result "+future.get());
		service.shutdown();
		
		ScheduledExecutorService scheduleExecutor = Executors.newSingleThreadScheduledExecutor();
		ScheduledFuture<String> scheduledFuture = scheduleExecutor.schedule(callable, 3, TimeUnit.SECONDS);
		System.out.println("Scheduled extr "+scheduledFuture.get());
		if(!scheduleExecutor.isShutdown()) {
			if(scheduleExecutor.awaitTermination(timeout, unit))
		}
	}

}
