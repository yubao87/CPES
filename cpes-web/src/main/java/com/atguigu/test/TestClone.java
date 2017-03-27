package com.atguigu.test;

import java.util.ArrayList;
import java.util.HashMap;


public class TestClone {
	public static void main(String[] args) {
		ArrayList<Integer> arr = new ArrayList<>();
		arr.add(1);
		arr.add(2);
		System.out.println(arr);
		arr.add(1, 5);
		System.out.println(arr);
		HashMap<String, Integer> hs = new HashMap<>();
		hs.put("a", 1);
		hs.put("b", 2);
		hs.put("a", 3);
		System.out.println(hs);
	}
}
