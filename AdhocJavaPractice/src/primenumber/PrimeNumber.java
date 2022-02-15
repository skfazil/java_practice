package primenumber;

public class PrimeNumber {
	
	public static void main(String[] args) {
		int maxNumber=200;
		
		for(int i=1;i<maxNumber;i++) {
			int count=0;
			for(int j=1;j<=i;j++) {
				if(i%j==0) {
					count++;
				}
			}
			if(count!=2) {
				System.out.println(i);
			}
		}
	}

}
