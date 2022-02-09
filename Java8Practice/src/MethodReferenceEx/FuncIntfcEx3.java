package MethodReferenceEx;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class FuncIntfcEx3 {
	
	public static void main(String[] args) {
		List<String> list = Arrays.asList("aa","aba","Doctor","Mia");
		
		FuncIntfcEx3 fi3 = new FuncIntfcEx3();
		
		//Function<String,Integer> func = fi3::getLength;
		Function<String,Integer> func = x->x.length();
		
		Map<String,Integer> map = fi3.getConvertedMapFromList(list,func);
		System.out.println(map);
		map.clear();
		list.forEach(val->{
			
			map.put("a", 100);
			map.put(val, val.length());
		});
		System.out.println(map);
	}
	
	private <T, R> Map<T, R> getConvertedMapFromList(List<T> list, Function<T, R> func) {
		Map<T,R> map = new HashMap<T, R>();
		for(T t : list) {
			map.put(t, func.apply(t));
		}
		return map;
	}

	public Integer getLength(String str) {
		return str.length();
	}

}
