package structural.flyweightpattern;

import java.util.HashMap;
import java.util.Map;

public class FlyweightFactory {
	
	private static final Map<String, Pen> pensMap = new HashMap<String, Pen>();
	
	public static Pen getThickPen(String color) {
		String key = color+"_THICK";
		Pen pen = pensMap.get(key);
		
		if(pen!=null) {
			return pen;
		}
		else {
			pen=new ThickPen(color);
			pensMap.put(key,pen);
		}
		return pen;
	}
	
	public static Pen getThinPen(String color) {
		String key = color+"_THIN";
		Pen pen = pensMap.get(key);
		
		if(pen!=null) {
			return pen;
		}
		else {
			pen=new ThinPen(color);
			pensMap.put(key,pen);
		}
		return pen;
	}

}
