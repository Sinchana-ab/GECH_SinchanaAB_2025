package streamApi_in_java;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StreamAPIJava {
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(12);
		list.add(22);
		list.add(10);
		list.add(21);
		Stream<Integer> stream =  list.stream();
		Stream<Integer> filter = stream.filter(n->n%2 == 0);
		long count = filter.count();
		System.out.println("the even number count is: "+count);
		
		long counts = list.stream().filter(n->n%2==0).count();
		System.out.println(counts);
	}
}
