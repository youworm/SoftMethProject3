package com.example.project3.util;
import java.util.Calendar;

/**
 * Represents a single date in MM/DD/YYYY format
 * @author Joe Guan
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    /**
     * Checks if the date can exist
     * @return true if the date can exist false otherwise
     */
    public boolean isValid() {
        Calendar c = Calendar.getInstance();
        c.set(year, month -1, 1);
        return year <= 2026 && month >= 1 && month <= 12 && day >= 1 && day <= c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * Constructor for a Date
     * @param year of the date
     * @param month of the date
     * @param day of the date
     */
    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }
    /**
     * Compares this Date to another object for equality.
     * @param obj the reference object with which to compare.
     * @return true if the given object is a Date with the same year, month, and day, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (obj.getClass() != this.getClass()){
            return false;
        }
        if (this == obj) {
            return true;
        }
        final Date other = (Date) obj;

        return this.year == other.year
                && this.month == other.month
                && this.day == other.day;
    }

    /**
     * Prints date in readable format
     * Follows US date format MM/DD/YYYY
     * @return date in MM/DD/YYYY format
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }
    /**
     * Compares this Date with another Date.
     * @param other the Date to be compared
     * @return a negative integer, zero, or a positive integer as this Date
     *         is earlier than, equal to, or later than the specified Date
     */
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;  // earlier year is smaller
        }
        if (this.month != other.month) {
            return this.month - other.month; // earlier month is smaller
        }
        return this.day - other.day; // earlier day is smaller
    }

    /**
     * Finds year of the date
     * @return year
     */
    public int getYear() {
        return year;
    }

    /**
     * Finds day of the date
     * @return day
     */
    public int getDay(){
        return day;
    }

    /**
     * Finds month of the date
     * @return month
     */
    public int getMonth(){
        return month;
    }

    /**
     * Testbed main() method for Date class.
     * Unit testing for isValid()
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Test cases for Date isValid()");

        Date date1 = new Date(2008, 0, 11);   //invalid month < 1
        System.out.println("Test 1: " + date1 + " = false | Result: " + date1.isValid());

        Date date2 = new Date(2008, 13, 23);  //invalid month > 12
        System.out.println("Test 2: " + date2 + " = false | Result: " + date2.isValid());

        Date date3 = new Date(2007, 4, 31);   //invalid April 31 doesn't exist
        System.out.println("Test 3: " + date3 + " = false | Result: " + date3.isValid());

        Date date4 = new Date(2009, 2, 29);   //feb 29 non leap year
        System.out.println("Test 4: " + date4 + " = false | Result: " + date4.isValid());

        Date date5 = new Date(2004, 11, 30);  //valid date
        System.out.println("Test 5: " + date5 + " = true | Result: " + date5.isValid());

        Date date6 = new Date(2012, 2, 29);   //valid leap year
        System.out.println("Test 6: " + date6 + " = true | Result: " + date6.isValid());
    }

}