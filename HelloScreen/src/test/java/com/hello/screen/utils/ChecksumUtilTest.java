package com.hello.screen.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ChecksumUtilTest {

    @Test
    public void checksumNegativeChangedOrder() {
        int[] arr = new int[]{0, 10, 20, 30, 40, 50, 60, 70, 80};
        int[] arr2 = new int[]{10, 20, 30, 40, 50, 60, 70, 80, 0};
        int checksum = ChecksumUtil.checksum(arr);
        int checksum2 = ChecksumUtil.checksum(arr2);

        assertNotEquals(checksum, checksum2);

    }

    @Test
    public void checksumNegativeOrder() {
        int[] arr = new int[]{0, 10, 20, 30, 40, 50, 60, 70, 80};
        int[] arr2 = new int[]{1, 10, 20, 30, 40, 50, 60, 70, 80};
        int checksum = ChecksumUtil.checksum(arr);
        int checksum2 = ChecksumUtil.checksum(arr2);

        assertNotEquals(checksum, checksum2);

    }


    @Test
    public void checksumPositive() {
        int[] arr = new int[]{0, 10, 20, 30, 40, 50, 60, 70, 80};
        int[] arr2 = new int[]{0, 10, 20, 30, 40, 50, 60, 70, 80};
        int checksum = ChecksumUtil.checksum(arr);
        int checksum2 = ChecksumUtil.checksum(arr2);

        assertEquals(checksum, checksum2);

    }
}