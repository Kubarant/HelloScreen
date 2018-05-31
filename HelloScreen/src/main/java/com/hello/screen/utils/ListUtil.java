package com.hello.screen.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {


    public static <T> List<T> combine(List<T> list1, List<T> list2) {
        List<T> res = new ArrayList<>(list1);
        res.addAll(list2);
        return res;
    }

    public static <T> List<T> sublist(List<T> list, int start, int end) {
        int endIndex = list.size() < end ? list.size() - 1 : end;
        return list.subList(start, endIndex);
    }
}
