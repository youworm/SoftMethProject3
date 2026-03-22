package com.example.project3.project2;

import org.junit.jupiter.api.Test;
import util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateTest {

    @Test
    public void testInvalidMonthLessThan1() {
        Date date = new Date(2008, 0, 11);
        assertFalse(date.isValid());
    }

    @Test
    public void testInvalidMonthGreaterThan12() {
        Date date = new Date(2008, 13, 23);
        assertFalse(date.isValid());
    }

    @Test
    public void testInvalidDay() {
        Date date = new Date(2007, 4, 31);
        assertFalse(date.isValid());
    }

    @Test
    public void testInvalidNonLeapYearFeb29() {
        Date date = new Date(2009, 2, 29);
        assertFalse(date.isValid());
    }

    @Test
    public void testValidDate() {
        Date date = new Date(2004, 11, 30);
        assertTrue(date.isValid());
    }

    @Test
    public void testValidLeapYearFeb29() {
        Date date = new Date(2012, 2, 29);
        assertTrue(date.isValid());
    }
}