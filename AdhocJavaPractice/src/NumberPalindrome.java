
public class NumberPalindrome {
	
	public static void main(String[] args) {
		int number = 123321;
		int n,sum=0;
		
		int temp=number;
		
		while(temp>0) {
			n=temp%10;
			sum=(sum*10)+n;
			temp=temp/10;
		}
		
		if(number==sum)
			System.out.println(number+" is Palindrome");
		else
			System.out.println(number+" is not a Palindrome");
	}

}
