package introduction_to_java.first_program;

import java.util.Scanner;

public class NonPremitive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int[] array1 = {1,2,3,4};
//		System.out.println(array1[2]);
//		for(int i = 0;i<array1.length;i++) {
//			System.out.println(i);
//		}
//		String name = "Sinchana";
//		System.out.println(name);
		
		Scanner sc = new Scanner(System.in);
//		sc.next();//when we used to one word it used after space it wont read 
		System.out.println("Enter a name: ");
		String name = sc.next();
		String name1 = sc.nextLine();
		System.out.println("name is: "+name);
		System.out.println(name1);
		sc.close();
	}

}
