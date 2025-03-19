package control_statement;

import java.util.Scanner;

public class Weekdays {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a days number:");
		int day = sc.nextInt();
		switch (day) {
		case 1-> System.out.println("weekdays");
		case 2-> System.out.println("weekdays");
		case 3-> System.out.println("weekdays");
		case 4-> System.out.println("weekdays");
		case 5-> System.out.println("weekdays");
		case 6-> System.out.println("weekend");
		case 7-> System.out.println("weekend");
		default->System.out.println("invalid day");
		}
		System.out.println("multiple case in single case");
		switch(day) {
		case 1,2,3,4,5-> System.out.println("weekdays");
		case 6,7->System.out.println("weekend");
		default-> System.out.println("invalid day4");
		}
		System.out.println("using if else ladder");
		if(day == 1 || day ==2 ||  day ==3 ||  day == 4 ||  day == 5) {
			System.out.println("weekdays");
		}
		else if(day == 6 || day == 7) {
			System.out.println("weekends");
		}
		else {
			System.out.println("invalid day");
		}
		
		String res = switch(day) {
		case 1,2,3,4,5-> "weekdays";
		case 6,7->"weekend";
		default-> "invalid day4";
		};
		System.out.println("the by storing into one variable  the result is "+res);
	}
}