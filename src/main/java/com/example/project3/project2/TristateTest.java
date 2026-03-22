package com.example.project3.project2;

import org.junit.jupiter.api.Test;
import util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TristateTest {
    private static final double DELTA = 0.01;

    @Test
    public void testFullTimeNY() {
        Profile profile = new Profile("John", "Doe", new Date(2004, 11, 1));
        TriState student = new TriState(profile, Major.CS, 60, "NY");
        double actual = student.tuition(12);
        double expected = 35649; // 39649 - 4000
        assertEquals(expected, actual, DELTA);
    }

    @Test
    public void testPartTime() {
        Profile profile = new Profile("Jane", "Smith", new Date(2004, 11, 1));
        TriState student = new TriState(profile, Major.CS, 60, "NY");
        double actual = student.tuition(9);
        double expected = 12403.5;
        assertEquals(expected, actual, DELTA);
    }
}