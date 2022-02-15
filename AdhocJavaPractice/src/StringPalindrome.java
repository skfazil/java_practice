

public class StringPalindrome {
	
	public static void main(String[] args) {
		String s = "madamadam";
		
		char[] charArray = s.toCharArray();
		String reverse = "";
		for(int i=charArray.length-1;i>=0;i--) {
			reverse+=charArray[i];
		}
		System.out.println(reverse);
		if(s.equalsIgnoreCase(reverse)) {
			System.out.println(s+" is a Palindrome");
		}
		else {
			System.out.println(s+" is not a Palindrome");
		}
		
		//Another method using String API
		StringBuilder sb = new StringBuilder(s);
		StringBuilder sb1 = sb.reverse();
		System.out.println(sb1.toString());
	}

}
