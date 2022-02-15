package primenumber;

public class AlternatePrimeNumber {

	public static void main(String[] args) {
		int maxNumber = 200;
		boolean skip = false;

		for (int i = 1; i < maxNumber; i++) {
			int count = 0;
			for (int j = 1; j <= i; j++) {
				if (i % j == 0) {
					count++;
				}
			}
			if (count == 2) {
				if (!skip) {
					System.out.println(i);
				}
				if (skip)
					skip = false;
				else
					skip = true;
			}
		}
	}

}
