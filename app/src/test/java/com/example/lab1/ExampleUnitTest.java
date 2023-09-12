package com.example.lab1;

import org.junit.Test;

import static org.junit.Assert.*;

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        MainActivity mainActivity = new MainActivity();
        mainActivity.A = 4;
        mainActivity.B = 4;
        mainActivity.angle = 30;
        assertEquals(mainActivity.getSquare(), 4);
    }
}