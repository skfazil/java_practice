package singleton.ser_deserbreaker;

import java.io.Serializable;

public class SingletonSerDeserbreaker implements Serializable {
	
	private static final SingletonSerDeserbreaker instance = new SingletonSerDeserbreaker();
	
	private SingletonSerDeserbreaker() {
		//Making constructor private so new cannot be used.
	}
	
	public static SingletonSerDeserbreaker getInstance() {
		return instance;
	}
	
	//This method replaces deserialized object with singleton's own instance.
	protected Object readResolve() {
		return instance;
	}

}
