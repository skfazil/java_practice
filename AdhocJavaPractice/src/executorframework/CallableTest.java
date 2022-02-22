package executorframework;

import java.util.concurrent.Callable;

public class CallableTest implements Callable<String>{

	@Override
	public String call() throws Exception {
		//Perform any business logic here.
		return "Callable successfully submitted";
	}

}
