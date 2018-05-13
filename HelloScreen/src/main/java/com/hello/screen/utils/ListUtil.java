package com.hello.screen.utils;

import java.util.List;

public class ListUtil {

	
	public static <T> List<T> combine(List<T> list1, List<T> list2){
		list1.addAll(list2);
		return list1;
	} 
	
}
