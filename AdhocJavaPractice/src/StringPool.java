
public class StringPool {
	
	public static void main(String[] args) {
		String s1 = "Hello";
		String s2 = "Hello";
		
		System.out.println(s1.hashCode()+" "+s2.hashCode());
		System.out.println(s1==s2);
		
		String s3 = new String("Brother");
		String s4 = new String("Brother");
		
		System.out.println(s3.hashCode()+" "+s4.hashCode());
		System.out.println(s3==s4);//Checks both reference and value, since for new keyboard, JVM is forced to creates a new object everytime
	}

}
