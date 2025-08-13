package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DemoStreams {
	public static void main(String[] args) {
		List<Integer> nums = Arrays.asList(1,2,7,5,4);
		Stream<Integer> data = nums.stream()
				.sorted((a,b)->b-a);
		
		data.forEach(n->System.out.println(n));
		System.out.println( nums.stream()
				.map(n->n*n).collect(Collectors.toList()));
//		long n = data.count();
//		System.out.println(n);
	}
}
