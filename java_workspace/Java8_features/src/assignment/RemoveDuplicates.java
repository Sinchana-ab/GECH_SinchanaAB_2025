package assignment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveDuplicates {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(12, 1, 20,1,30,8,3,10, 20, 30);
		System.out.println("Before removing the duplicates "+list);
		List<Integer> list1 = list.stream().distinct().collect(Collectors.toList());
		System.out.println("After removing elements: "+list1);

	}
}
