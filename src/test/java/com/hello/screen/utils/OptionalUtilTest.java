package com.hello.screen.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OptionalUtilTest {

    @Test
    public void ifNullGet() {
        String notNullExpectedMessage = "it not null";
        String ifNullMessage = "Ops it is null";
        String actual = OptionalUtil.ifNullGet(notNullExpectedMessage, ifNullMessage);

        assertEquals(notNullExpectedMessage, actual);

    }

    @Test
    public void ifNullGetWithNull() {

        String ifNullMessage = "Ops it is null";
        String actual = OptionalUtil.ifNullGet(null, ifNullMessage);

        assertEquals(ifNullMessage, actual);

    }
}