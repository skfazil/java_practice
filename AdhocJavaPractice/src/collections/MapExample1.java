package collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapExample1 {
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "a");
		map.put("b", "b");
		map.put("c", "c");
		map.put("d", "d");
		map.put("e", "e");
		map.put("f", "f");
		map.put("g", "g");
		
		Set<Map.Entry<String, String>> entry = map.entrySet();
		Iterator<Map.Entry<String, String>> itr = entry.iterator();
		while(itr.hasNext()) {
			Entry<String, String> e = itr.next();
			System.out.println(e.getKey()+" "+e.getValue());
		}
	}

}
