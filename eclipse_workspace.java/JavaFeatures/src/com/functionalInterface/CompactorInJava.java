package com.functionalInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Corrected class name
class FunctionInterface implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 - o2; // Sorts in ascending order
    }
}

// Sort interface using lambda
interface Sort {
    void sort(List<Integer> list);
}

public class CompactorInJava {

    public static void main(String[] args) {
        // First list sorting using FunctionInterface comparator
        List<Integer> list = new ArrayList<>();
        list.addAll(List.of(20, 23, 12, 2, 3, 14));
        Collections.sort(list, new FunctionInterface());
        System.out.println("Sorted using FunctionInterface: " + list);

        // Second list sorting using lambda in Sort interface
        List<Integer> list2 = new ArrayList<>();
        list2.addAll(List.of(20, 21, 12, 2, 3, 1));

        Sort sort1 = (inputList) -> Collections.sort(inputList); // Lambda for default sorting
        sort1.sort(list2);
        System.out.println("Sorted using lambda: " + list2);
        
        ArrayList<Integer> list3 = new ArrayList<Integer>(List.of(20, 21, 12, 2, 3, 1));
        Collections.sort(list3, (a,b)->b-a);
        System.out.println(list3);
        
    }
}
