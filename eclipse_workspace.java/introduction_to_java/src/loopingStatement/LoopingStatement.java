package loopingStatement;

public class LoopingStatement {
    public static void main(String[] args) {
        System.out.println("For loop");
        int[] array = {1, 2, 3, 4, 5};
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
//
//        System.out.println("using while loop");
//        int i = 0;
//        while (i < array.length) {
//            System.out.println(array[i]);
//            i++;
//        }
//
//        System.out.println("using do while");
//        i = 0;
//        do {
//            System.out.println(array[i]);
//            i++;
//        } while (i < array.length);
//
//        System.out.println("using for each");
//        for (int a : array) {
//            System.out.println(a);
//        }
    }
}
