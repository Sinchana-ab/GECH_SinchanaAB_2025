package control_statement;

import java.util.Scanner;

public class ControleStatment {
	public static void main(String[] args) {
/*
 controle statement : if, if else, else if, switch
 
 *if(condition){}
 *if(condition){
 *}
 *else{
 *}
 *if(condition){
 *}
 *else if(condition){
 *}
 *else{
 *}
 *
 */
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your age: ");
		byte age = sc.nextByte();
		sc.nextLine();
		if(age<18) {
			System.out.println("your soo young child");
		}
		else if(age>=18 && age<=35) {
			System.out.println("your teenager");
		}
		else if(age>35 && age<=60) {
			System.out.println("your adult");
		}
		else {
			System.out.println("your in oldage");
		}
		
		//Switch
		int day = 2;
		/*
		 * 	switch (day) {
//		case 1: {
//			
//			System.out.println("Monday");
//		}*/
		switch (day) {
		case 1 -> System.out.println("Monday");
		case 2-> System.out.println("Tuesday");
		case 3-> System.out.println("wednesday");
		case 4-> System.out.println("thursday");
		case 5 ->  System.out.println("friday");
		case 6->  System.out.println("saturday");
		case 7->  System.out.println("sunday");
		default->  System.out.println("Invalid name");
		}
		System.out.println("enter a day number");
		int day1 = sc.nextInt();
		switch(day1) {
		case 1 : {
			System.out.println("Monday");
			break;
		}
		}
		sc.close();
	}
}
