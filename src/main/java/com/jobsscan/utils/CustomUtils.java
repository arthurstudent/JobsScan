package com.jobsscan.utils;

import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public class CustomUtils {

    public static <T> boolean collectionNullCheck(Collection<T> list) {
        return (list == null || list.isEmpty());
    }
}
