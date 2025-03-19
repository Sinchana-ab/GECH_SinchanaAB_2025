package introduction_to_java.first_program;

import java.util.Scanner;

public class UserinputValues {
		public static void main(String[] args) {
			

			/*byte = nextbyte();
			 * short = nextShort();
			 * int = nextInt();
			 * long = nextLong();
			 * float = nextFloat();
			 * double = nextDouble();
			 * boolean = nextBoolean();5
			 * 
			 */
			
			

		int stdAge = 0;
		System.out.println("Enter the student age");
		Scanner sc= new Scanner(System.in);
		stdAge = sc.nextInt();
		System.out.println("The age of student is: "+stdAge);
		sc.nextLine();
		//character 
		System.out.println("Enter your gender: ");
		char gender = sc.nextLine().charAt(0);
		System.out.println("gender is: "+gender);
	}

}
