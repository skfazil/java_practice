package MethodReferenceEx;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckingNullConditionEx {
	
	public static void main(String[] args) {
		Stream<String> stream = Stream.of("A","B","C","","E");
		
		CheckingNullConditionEx checkNull = new CheckingNullConditionEx();
		
		//Objects::nonNull doesn't check for empty quotes ""
		List<String> list1 = stream.filter(Objects::nonNull)
				.collect(Collectors.toList());
		
		System.out.println(list1);
		
		//Below custom method can be written to check custom quotes as well.
		List<String> list2 = stream.filter(checkNull::isNotNull)
		.collect(Collectors.toList());
		
		System.out.println(list2);
	}
	
	public boolean isNotNull(String s) {
		return (s!=null && !"".equalsIgnoreCase(s))?true:false;
	}

}
