package com.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsInJava {

	public static void main(String[] args) {
		 List<Integer> list = new ArrayList<>();
	        list.addAll(List.of(20,2,4,26,20, 23, 12, 2, 3, 14));
	        Stream<Integer> stream = list.stream();
	        List<Integer> numbers = stream.filter((a)->a%2!=0).collect(Collectors.toList());
	        System.out.println(numbers);
	        List<Integer> numbers2 = list.stream().filter((a)->a%2==0).collect(Collectors.toList());
	        System.out.println(numbers2);
	        Set<Integer> numbers1 = list.stream().filter((a)->a%2==0).collect(Collectors.toSet());
	        System.out.println(numbers1);
	        

	}

}
