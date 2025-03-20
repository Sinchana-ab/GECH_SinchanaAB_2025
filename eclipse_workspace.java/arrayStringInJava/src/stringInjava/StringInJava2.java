package stringInjava;

public class StringInJava2 {
	public static void main(String[] args) {
		/*
		 * 1.compareTo
		 * 2.concate 
		 * 3.toLowercase
		 * 4.toUppercase
		 * 5. equalsignorecase
		 * 6.startwith
		 * 7.endswith
		 * 8. strip
		 * 9.isBlank()
		 * 10. equals
		 * 11.compareIgnoreCase
		 * 12.substring
		 * */
		String str1 = new String("Hello world");
		String str2 = new String("hello world");
		//1.equals
		System.out.println("the String equals: "+str1.equals(str2));
		//2.compare
		System.out.println("the string compared: "+str1.compareTo(str2));
		//3.equalsIgnoreCase
		System.out.println("The string equalsIgnore: "+str1.equalsIgnoreCase(str2));
		//4.compareIgnoreCase
		System.out.println("the string compareToIgnoreCase: "+str1.compareToIgnoreCase(str2));
		//5.concat
		System.out.println("the string concat: "+str1.concat(str2));
		//6. toLowerCase
		System.out.println("the string to lower: "+str1.toLowerCase());
		//7. toUpperCase
		System.out.println("the string upper case: "+str1.toUpperCase());
		//8.startWith
		System.out.println("the string Startwith: "+str1.startsWith("hii"));
		//9.endsWith
		System.out.println("the string ends with: "+str1.endsWith("sinchana"));
		//10.strip
		System.out.println("the string strip: "+str1.strip());
		//11. isBlank isEmpty
		System.out.println("the string isBlank: "+str1.isBlank());
		System.out.println("the string isEmpty: "+str1.isEmpty());
		//12.
		System.out.println("the string isEmpty: "+str1.substring(2));
		
	}
}
