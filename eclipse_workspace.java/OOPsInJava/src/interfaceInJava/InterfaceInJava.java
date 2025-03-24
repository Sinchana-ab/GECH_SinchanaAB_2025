package interfaceInJava;

interface Animal {
    public void makesound();
}

class Dog implements Animal {
    @Override
    public void makesound() {
        System.out.println("Dogs bark");
    }
}

class Cat implements Animal {
    @Override
    public void makesound() {
        System.out.println("Cat sound is meow-meow");
    }
}

public class InterfaceInJava {
    public static void main(String[] args) {
        Dog dog1 = new Dog();
        dog1.makesound();

        Cat cat = new Cat();
        cat.makesound();
    }
}
