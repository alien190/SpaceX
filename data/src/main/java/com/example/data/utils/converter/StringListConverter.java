package com.example.data.utils.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringListConverter {

    @TypeConverter
    public static List<String> toListString(String string) {
        if (string != null && !string.isEmpty()) {
            return Arrays.asList(string.split("-"));
        }
        return new ArrayList<>();
    }

    @TypeConverter
    public static String fromStringList(List<String> stringList) {
        String ret = "";
        if (stringList != null) {
            for (String string : stringList) {
                if (!ret.isEmpty()) {
                    ret = ret + "-";
                }
                ret = ret + string;
            }
        }
        return ret;
    }
}
