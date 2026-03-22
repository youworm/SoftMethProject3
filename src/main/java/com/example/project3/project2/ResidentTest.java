package com.example.project3.project2;

import org.junit.jupiter.api.Test;
import util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResidentTest {

    @Test
    public void testFullTimeNoScholarship() {
        Profile profile = new Profile("John", "Doe", new Date(2004, 11, 1));
        Resident student = new Resident(profile, Major.CS, 60);
        student.setScholarship(0);
        double actual = student.tuition(18);
        double expected = 19788;
        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testFullTimeWithScholarship() {
        Profile profile = new Profile("Jane", "Smith", new Date(2004, 11, 1));
        Resident student = new Resident(profile, Major.CS, 60);
        student.setScholarship(10000);
        double actual = student.tuition(18);
        double expected = 9788;
        assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testPartTimeResident() {
        Profile profile = new Profile("Alex", "Brown", new Date(2004, 11, 1));
        Resident student = new Resident(profile, Major.CS, 60);
        student.setScholarship(500);
        double actual = student.tuition(9);
        double expected = 6283.5;
        assertEquals(expected, actual, 0.01);
    }
}