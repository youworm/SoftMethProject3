package com.example.project3.project2;

import org.junit.jupiter.api.Test;
import util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InternationalTest {

    @Test
    public void testStudyingAbroad() {
        Profile profile = new Profile("Alysa", "Lee", new Date(2002, 7, 28));
        International student = new International(profile, Major.CS, 60, true);
        double actual = student.tuition(12);
        double expected = 7041;
        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testNotStudyingAbroad() {
        Profile profile = new Profile("Mark", "Chen", new Date(2002, 6, 5));
        International student = new International(profile, Major.CS, 60, false);
        double actual = student.tuition(12);
        double expected = 42799;
        assertEquals(expected, actual, 0.01);
    }
}