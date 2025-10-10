package streamApi_in_java;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StreamAPI1 {
	public static void main(String[] args) {
		//List<String> names = Arrays.asList("sinchana","nayana","pavan");
		//names.stream().map(String::toUpperCase).forEach(System.out::println);
		
//		List<String> name = Arrays.asList("sinchana","nayana","pavan","abhi","swathi");
//		name.stream().filter(s->s.startsWith("s")).forEach(System.out::println);
//		name.stream().filter(StreamAPI1::startsWith).forEach(System.out::println);
//		List<String> s = name.stream().filter(StreamAPI1::startsWith).collect(Collectors.toList());
//		System.out.println(s);
//		
		
		
		System.out.println("-------------------------");
		List<Integer> list = Arrays.asList(12,20,1,30,8,3,10);
		List<Integer> li = list.stream().filter(s->s>10).map(s-> s*s).collect(Collectors.toList());
		System.out.println(li);
	}

	private static boolean startsWith(String string) {
		return string.startsWith("s");
	}
}
