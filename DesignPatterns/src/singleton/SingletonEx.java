package singleton;

public class SingletonEx implements Cloneable{
	
	private static SingletonEx instance;
	
	private SingletonEx() {
		
	}
	
	public static SingletonEx getInstance() {
		if(instance==null) {
			instance=new SingletonEx();
		}
		return instance;
	}
	
	//ThreadSafe implementation using synchronized method
	//Drawback is, can be overhead on the execution
	public static synchronized SingletonEx getThreadSafeInstance() {
		if(instance==null) {
			instance=new SingletonEx();
		}
		return instance;
	}
	
	//Another lock based simple threadsafe implementaion using synchronized block
	public static SingletonEx getThreadSafetyInstance() {
		if(instance==null) {
			synchronized (SingletonEx.class) {
				instance=new SingletonEx();
			}
		}
		return instance;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		//This way we restrict the object creation using clone.
		throw new CloneNotSupportedException();
	}

}
