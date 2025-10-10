package exception;

class InvalidAgeException extends Exception{

	public InvalidAgeException(String message) {
		super(message);
	}
	
	
}

public class OwnException {
	
	public static void validateAge(int age) throws InvalidAgeException {
		if(age>=18) {
			System.out.println("you can vote");
		}
		else {
			throw new InvalidAgeException("your can't vote");
		}
	}
	
	public static void main(String[] args) {
		try {
			validateAge(12);
		}
		catch(InvalidAgeException e){
			System.out.println(e.getMessage());
		}
	}
}
