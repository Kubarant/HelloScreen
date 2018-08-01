package com.hello.screen.utils;

import java.util.Collection;

public class ChecksumUtil {

    public static int checksum(int[] set) {
        int res = 1;
        for (int i = 0; i < set.length; i++) {
            res ^= set[i];
        }
        return res;
    }


    public static <T> int checksumOfHashCodes(Collection<T> set) {
        int[] hashCodes = set.stream()
                .mapToInt(T::hashCode)
                .toArray();
        return checksum(hashCodes);
    }
}
