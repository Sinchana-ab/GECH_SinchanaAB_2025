package com.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamInJava2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> list = Arrays.asList("one","two","three","four");
		List<String> str = list.stream().map((s)->s.toUpperCase()).collect(Collectors.toList());
		System.out.println(str);
		//long count =  ((Stream<String>) str).count();
	}

}
