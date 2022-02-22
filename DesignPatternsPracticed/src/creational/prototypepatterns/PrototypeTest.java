package creational.prototypepatterns;

import java.util.ArrayList;
import java.util.List;

public class PrototypeTest {
	
	public static void main(String[] args) {
		CEO ceo = new CEO();
		List<String> list = new ArrayList<String>();
		list.add("Ammi");
		list.add("Abba");
		list.add("Nasru");
		list.add("Ali");
		ceo.setEmpList(list);
		
		System.out.println(ceo);
		CEO ceo1 = null;
		try {
			ceo1 = (CEO) ceo.clone();
			System.out.println(ceo1);
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
