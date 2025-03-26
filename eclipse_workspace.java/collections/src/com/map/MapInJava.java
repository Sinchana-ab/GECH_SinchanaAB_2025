package com.map;

import java.util.HashMap;
import java.util.Map;

public class MapInJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "One");
		map.put(2, "sec");
		map.put(3, "thi");
		map.put(4, "ten");
		map.put(5, "five");
		map.put(1, "One");
		System.out.println(map);
		map.remove(0);
		System.out.println(map);
	
	}

}
