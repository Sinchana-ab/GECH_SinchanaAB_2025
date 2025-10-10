package streamApi_in_java;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StreamAPI2 {
	public static void main(String[] args) {
		List<List<Integer>> list = Arrays.asList(Arrays.asList(3,4),Arrays.asList(5,6));
		System.out.println(list);
		System.out.println("before using flatmap");
		List<Integer> flatmap = list.stream()
				.flatMap(s->s.stream())
				.collect(Collectors.toList());
		System.out.println("using flatmap: "+flatmap);
		
	}
}
