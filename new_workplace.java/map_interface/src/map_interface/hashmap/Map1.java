package map_interface.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Map1 {
	public static void main(String[] args) {
		//creating the map object
		Map<Integer,String> m = new HashMap<Integer, String>();
		m.put(1,"Sinchana");
		m.put(2, "nayana");
		
		System.out.println("the maping object is: "+m);
		for(Map.Entry<Integer, String> element : m.entrySet()) {
			int k = element.getKey();
			String name = element.getValue();
			System.out.println("the element is with key: "+k+" and name is: "+name);
		}
		
		System.out.println(m.containsKey(2));
		System.out.println(m.containsValue("Sinchana"));
		m.remove(2);
		
		System.out.println("the maping object is: "+m);
		//System.out.println(m.containsValue("parvam"));
		m.clear();
		System.out.println("the maping object is: "+m);
		//System.out.println(m.containsKey(2));
		
	}
}
