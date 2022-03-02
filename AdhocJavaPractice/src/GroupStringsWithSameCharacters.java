import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GroupStringsWithSameCharacters {

	public static void main(String[] args) {
		String[] words = { "may", "student", "students", "dog", "studentssess", "god", "cat", "act", "tab", "bat",
				"flow", "wolf", "lambs", "amy", "yam", "balms", "looped", "poodle" };
		
		Map<String, List<String>> groupedList = new HashMap<String, List<String>>();
		
		for(int i=0;i<words.length;i++) {
			char[] cArr = words[i].toCharArray();
			Arrays.sort(cArr);
			String val = String.valueOf(cArr);
			
			if(groupedList.containsKey(val)){
				List<String> matchList = groupedList.get(val);
				matchList.add(words[i]);
				groupedList.put(val,matchList);
			}
			else {
				List<String> list = new ArrayList<String>();
				list.add(words[i]);
				groupedList.put(val,list);
			}
				
		}
		List<List<String>> finalList = new ArrayList<List<String>>();
		
		Set<String> keys = groupedList.keySet();
		keys.forEach(key->{
			finalList.add(groupedList.get(key));
		});
		System.out.println(finalList);
	}

}
