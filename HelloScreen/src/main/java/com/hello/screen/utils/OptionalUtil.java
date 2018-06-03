package com.hello.screen.utils;

import java.util.Optional;

public class OptionalUtil {

    public static <T> T ifNullGet(T nullable, T supplier) {
        return Optional.ofNullable(nullable)
                .orElse(supplier);
    }
}
