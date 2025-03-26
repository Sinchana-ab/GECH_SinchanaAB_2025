package com.set;

import java.util.TreeSet;

public class TreesetInJava {
	public static void main(String[] args) {
		TreeSet<Integer> treeset = new TreeSet<Integer>((a,b)->b-a);
		treeset.add(2);
		treeset.add(10);
		treeset.add(100);
		treeset.add(12);
		System.out.println(treeset);
		
	}
}
