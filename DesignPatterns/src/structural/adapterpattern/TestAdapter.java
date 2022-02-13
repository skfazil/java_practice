package structural.adapterpattern;

public class TestAdapter {
	
	//The adapter design pattern is a structural design pattern that allows two unrelated/uncommon interfaces to work together. 
	//In other words, the adapter pattern makes two incompatible interfaces compatible without changing their existing code.
	public static void main(String[] args) {
		AdapterInterface adapter = new AdapterInterfaceImpl();
		
		Volt v3 = adapter.convertTo3V();
		System.out.println(v3);
		Volt v12 = adapter.convertTo12V();
		System.out.println(v12);
		Volt v120 = adapter.convertTo120V();
		System.out.println(v120);
	}

}
