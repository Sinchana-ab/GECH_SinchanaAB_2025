package comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Comparator1 implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {
		
		return o2 - o1;
	}
	
	public static void main(String[] args) {
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(4);
		list1.add(21);
		list1.add(11);
		list1.add(9);
		
		System.out.println(list1);
		Collections.sort(list1, (s1,s2) -> s2 - s1);
		System.out.println(list1);
		Collections.sort(list1);
		System.out.println(list1);
		//Collections.sort(list1, compare(l1,l2));
		System.out.println(list1);
	}

}
