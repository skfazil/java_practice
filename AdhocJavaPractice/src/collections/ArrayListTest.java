package collections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;



public class ArrayListTest {
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("10");
		list.add("20");
		
		list.add(1, "15");

		list.add("30");

		list.set(2, "25");
		list.add("40");
		list.add("80");
		list.add("60");
		list.add("90");
		
		Collections.sort(list);
		
		Iterator<String> itr = list.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
		
		ListIterator<String> listItr = list.listIterator();
		System.out.println("Forward direction");
		while(listItr.hasNext()) {
			System.out.println(listItr.next());
		}
		
		System.out.println("Backward direction");
		while(listItr.hasPrevious()) {
			System.out.println(listItr.previous());
		}
	}

}
