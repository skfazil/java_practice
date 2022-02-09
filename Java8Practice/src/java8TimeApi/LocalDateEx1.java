package java8TimeApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalDateEx1 {
	
	public static void main(String[] args) {
		DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate ld = LocalDate.now();
		System.out.println(dtf1.format(ld));
		
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime lt = LocalTime.now();
		System.out.println(dtf2.format(lt));
		
		DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime ldt = LocalDateTime.now();
		System.out.println(dtf3.format(ldt));
	}

}
