package com.hello.screen.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {


    public static <T> List<T> combine(List<T> list1, List<T> list2) {
        List<T> secureList1 = OptionalUtil.ifNullGet(list1, new ArrayList<T>());
        List<T> secureList2 = OptionalUtil.ifNullGet(list2, new ArrayList<T>());

        List<T> res = new ArrayList<>(secureList1);
        res.addAll(secureList2);
        return res;
    }

    public static <T> List<T> sublist(List<T> list, int start, int end) {
        int startIndex = list.size() < start ? list.size() : start;
        int endIndex = list.size() < end ? list.size() : end;

        return list.subList(startIndex, endIndex);
    }
}
