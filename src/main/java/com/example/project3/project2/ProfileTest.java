package com.example.project3.project2;

import org.junit.jupiter.api.Test;
import util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileTest {

    @Test
    public void testCompareNameBefore() {
        Profile thisProfile = new Profile("Natalie", "Ross", new Date(2000, 10, 1));
        Profile otherProfile = new Profile("Natalie", "Smith", new Date(2000, 10, 1));
        assertEquals(-1, thisProfile.compareTo(otherProfile));
    }

    @Test
    public void testSameLastFirstBefore() {
        Profile thisProfile = new Profile("Adrian", "Smith", new Date(2000, 7, 12));
        Profile otherProfile = new Profile("John", "Smith", new Date(2000, 7, 12));
        assertEquals(-1, thisProfile.compareTo(otherProfile));
    }

    @Test
    public void testSameNameEarlierDob() {
        Profile thisProfile = new Profile("Chris", "Rock", new Date(2000, 7, 9));
        Profile otherProfile = new Profile("Chris", "Rock", new Date(2005, 6, 7));
        assertEquals(-1, thisProfile.compareTo(otherProfile));
    }

    @Test
    public void testLastNameAfter() {
        Profile thisProfile = new Profile("Natalie", "Wilson", new Date(2000, 11, 5));
        Profile otherProfile = new Profile("Natalie", "Brown", new Date(2000, 11, 5));
        assertEquals(1, thisProfile.compareTo(otherProfile));
    }

    @Test
    public void testSameLastFirstAfter() {
        Profile thisProfile = new Profile("Zoe", "Smith", new Date(2000, 1, 1));
        Profile otherProfile = new Profile("Adam", "Smith", new Date(2000, 1, 1));
        assertEquals(1, thisProfile.compareTo(otherProfile));
    }

    @Test
    public void testSameNameLaterDob() {
        Profile thisProfile = new Profile("Chris", "Rock", new Date(2005, 6, 7));
        Profile otherProfile = new Profile("Chris", "Rock", new Date(2000, 7, 9));
        assertEquals(1, thisProfile.compareTo(otherProfile));
    }

    @Test
    public void testSameProfile() {
        Profile thisProfile = new Profile("Will", "Lipton", new Date(2002, 5, 5));
        Profile otherProfile = new Profile("Will", "Lipton", new Date(2002, 5, 5));
        assertEquals(0, thisProfile.compareTo(otherProfile));
    }
}