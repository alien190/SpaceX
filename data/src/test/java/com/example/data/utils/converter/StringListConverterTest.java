package com.example.data.utils.converter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StringListConverterTest {

    List<String> mStringList;

    @Before
    public void setUp() throws Exception {
        mStringList = new ArrayList<>();
        mStringList.add("one");
        mStringList.add("two");
        mStringList.add("three");
        mStringList.add("four");
        mStringList.add("five");
    }

    @After
    public void tearDown() throws Exception {
        mStringList = null;
    }

    @Test
    public void toListString() {
        List<String> stringList = StringListConverter.toListString("one-two-three-four-five");
        assertEquals(mStringList.size(), stringList.size());
        for (int i = 0; i < mStringList.size(); i++) {
            assertEquals(mStringList.get(i), stringList.get(i));
        }
    }

    @Test
    public void fromStringList() {
        String result = StringListConverter.fromStringList(mStringList);
        assertEquals("one-two-three-four-five", result);
    }
}