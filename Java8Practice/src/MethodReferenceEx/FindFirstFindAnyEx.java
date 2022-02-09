package MethodReferenceEx;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FindFirstFindAnyEx {
	public static void main(String[] args) {
		List<String> list = Arrays.asList("A","B","C","","E");
		
		Optional<String> opt1 = list.stream().findFirst();
		
		if(opt1.isPresent()) {
			String s1 = opt1.get();
			System.out.println(s1);
		}
		
		Optional<String> opt2 = list.stream().findAny();
		
		if(opt2.isPresent()) {
			String s2 = opt2.get();
			System.out.println(s2);
		}
		Stream<String> strm = null; 
		try {
			strm = Files.lines(Paths.get("C:\\Users\\ac01280\\OneDrive - Lumen\\Documents\\Practice\\Java8Practice\\src\\MethodReferenceEx\\FlatMapEx1.java"));
			strm.forEach(System.out::println);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
