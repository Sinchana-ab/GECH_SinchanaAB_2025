package assignment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SortListNumber {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(12,20,1,30,8,3,10);
		List<Integer> list1 = list.stream().sorted().collect(Collectors.toList());
		List<Integer> list2 = list.stream().sorted((a,b)->b-a).collect(Collectors.toList());
		System.out.println("the sorted string is: "+list1);
		System.out.println("the descending order is: "+list2);
	}
}
