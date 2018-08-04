package com.hello.screen.utils;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ListUtilTest {

    @Test
    public void combine() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        List<Integer> integers2 = Arrays.asList(5, 6, 7, 8);

        List<Integer> combined = ListUtil.combine(integers, integers2);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);


        assertEquals(expected, combined);

    }

    @Test
    public void combineNull() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        List<Integer> integers2 = null;

        List<Integer> combined = ListUtil.combine(integers, integers2);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4);


        assertEquals(expected, combined);

    }

    @Test
    public void sublist() {
        List<Integer> given = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> list = ListUtil.sublist(given, 0, given.size() - 2);
        List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5, 6);
        assertEquals(expected, list);

    }

    @Test
    public void sublistWithEndIndexBiggerThanSize() {
        List<Integer> given = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> list = ListUtil.sublist(given, 0, given.size() + 20);
        List<Integer> expected = given;
        assertEquals(expected, list);

    }

    @Test
    public void sublistWithStartIndexBiggerThanSize() {
        List<Integer> given = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> list = ListUtil.sublist(given, given.size() + 20, given.size());
        List<Integer> expected = Collections.emptyList();
        assertEquals(expected, list);

    }

}