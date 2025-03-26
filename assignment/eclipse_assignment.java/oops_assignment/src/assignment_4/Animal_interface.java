package assignment_4;
interface Animal {
    void eat();
    void makeSound();
}

class Dog implements Animal {
    public void eat() {
        System.out.println("Dog is eating...");
    }

    public void makeSound() {
        System.out.println("Woof! Woof!");
    }
}

class Cat implements Animal {
    public void eat() {
        System.out.println("Cat is eating...");
    }

    public void makeSound() {
        System.out.println("Meow! Meow!");
    }
}

public class Animal_interface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 System.out.println("\n===== Animal Interface Test =====");
	        Animal dog = new Dog();
	        Animal cat = new Cat();
	        dog.eat();
	        dog.makeSound();
	        cat.eat();
	        cat.makeSound();
	}

}
