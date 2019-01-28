import java.io.FileWriter;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/**
 * Tan Shi Terng Leon
 * 4000602
 * lehman.java
 */

/**
 * @author User
 *
 */
public class lehman {

	/**
	 * @param args
	 */
	public static final int SIZE = 32;
	
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			Vector<BigInteger> usedBefore;
			String input;
			BigInteger n, a, exp, testValue;
			int noOfPrimeWit;
			Random rand =  new Random();
			
			System.out.println("Lehman's Test");
			
			/**************************Getting input**************************/
			do {
				System.out.print("Enter a number n: ");
				input = sc.nextLine();
				n = new BigInteger(input);
				if (n.mod(new BigInteger("2")).equals(new BigInteger("0"))) {	//If n is even
					System.out.println("Please enter an odd number");
				}
				else if (n.compareTo(new BigInteger("1")) <= 0) {	//If n is less or equal than 1
					System.out.println("Please enter a positive number greater than 1");
				}
			} while (n.mod(new BigInteger("2")).equals(new BigInteger("0")) || 
					(n.compareTo(new BigInteger("1")) <= 0));
			
			do {
				System.out.print("Enter the number of prime witnesses: ");
				noOfPrimeWit = sc.nextInt();
				if (noOfPrimeWit <= 1) {
					System.out.println("Please enter a number greater than 1");
				}
				else if (noOfPrimeWit > (n.intValue() - 1)) {
					System.out.println("There are only " + (n.intValue() - 1) + " values" +
							" from 1 to n-1 inclusive");
				}
			} while ((noOfPrimeWit <= 1) || (noOfPrimeWit > (n.intValue() - 1)));
			
			sc.close();
			
			exp = n.subtract(new BigInteger("1")).divide(new BigInteger("2"));
			
			//1 and n-1
			BigInteger primeResult1 = new BigInteger("1"), primeResult2 = n.subtract(primeResult1);
			
			/************************Computing and Writing to file************************/
			FileWriter fw = new FileWriter("lehman-dump.txt");
			fw.write("n = " + n + "\nNo of prime witnesses required = " + noOfPrimeWit + "\n");
			usedBefore = new Vector<BigInteger>();
			for (int i = 0; i < noOfPrimeWit; i++) {
				do {
					a = new BigInteger(SIZE, rand);
					
					//Prime witness between 1 to n-1 inclusive
					a = a.mod(n.subtract(new BigInteger("1"))).add(new BigInteger("1"));
				} while (usedBefore.contains(a));	//If the random value a is used before
				
				fw.write(a + "\t");
				usedBefore.add(a);
				
				if (a.gcd(n).compareTo(new BigInteger("1")) == 1) {	//If gcd(a,n) > 1
					System.out.println(n + " is composite");
					fw.write("GCD(a, n) = " + a.gcd(n));
					fw.close();
					return;
				}
				else {
					testValue = a.modPow(exp, n);
					
					//If the test result shows it is composite
					if (!testValue.equals(primeResult1) && !testValue.equals(primeResult2)) {
						System.out.println(n + " is composite");
						fw.write(testValue + " <---Composite");
						fw.close();
						return;
					}
					
					//Prime witness
					fw.write(testValue + "\n");
				}
			}
			
			System.out.println(n + " should be prime\n" +
			"Probability of n not being prime is 2^(-" + noOfPrimeWit + ")");
			
			fw.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
