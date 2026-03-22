package com.example.project3.project2;

import com.example.project3.util.Date;

/**
 * Represents a single students profile
 * @author Joe Guan
 */
public class Profile implements Comparable<Profile>{
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructor for new profile
     * @param fname first name
     * @param lname last name
     * @param dob date of birth
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Retrieves the profile's first name
     * @return first name
     */
    public String getFname(){
        return fname;
    }

    /**
     * Retrieves the profile's last name
     * @return last name
     */
    public String getLname(){
        return lname;
    }

    /**
     * Retrieves the profile's date of birth
     * @return date of birth.
     */
    public Date getDob(){
        return dob;
    }

    /**
     * Compares this Profile with another Profile for ordering.
     * Profiles are compared by last name, first name, then date of birth.
     *
     * @param other the Profile to be compared
     * @return -1, 0, or 1 as this Profile
     *         is less than, equal to, or greater than the specified Profile
     */
    @Override
    public int compareTo(Profile other) {
        int last = lname.compareToIgnoreCase(other.lname);
        if (last < 0) return -1;
        if (last > 0) return 1;

        int first = fname.compareToIgnoreCase(other.fname);
        if (first < 0) return -1;
        if (first > 0) return 1;

        int dobCompare = dob.compareTo(other.dob);
        if (dobCompare < 0) return -1;
        if (dobCompare > 0) return 1;

        return 0;
    }

    /**
     * Checks if two profiles are equal
     * @param obj the obj to compare the current profile to.
     * @return true if profiles are equal, false otherwise
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
        final Profile other = (Profile) obj;

        return this.fname.equalsIgnoreCase(other.fname)
                && this.lname.equalsIgnoreCase(other.lname)
                && this.dob.equals(other.dob);
    }

    /**
     * Returns profile in a more readable way
     * @return profile as fname lname dob (in MM/DD/YY)
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * Testbed main() method for Profile class.
     * Unit testing for compareTo()
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Test cases for Profile compareTo()");

        // expecting < 0
        Profile this1 = new Profile("Natalie", "Ross", new Date(2000, 10, 1));
        Profile other1 = new Profile("Natalie", "Smith", new Date(2000, 10, 1));
        System.out.println("Test 1: -1 | Result: " + this1.compareTo(other1));

        Profile this2 = new Profile("Adrian", "Smith", new Date(2000, 7, 12));
        Profile other2 = new Profile("John", "Smith", new Date(2000, 7, 12));
        System.out.println("Test 2: -1 | Result: " + this2.compareTo(other2));

        Profile this3 = new Profile("Chris", "Rock", new Date(2000, 7, 9));
        Profile other3 = new Profile("Chris", "Rock", new Date(2005, 7, 6));
        System.out.println("Test 3: -1 | Result: " + this3.compareTo(other3));

        // expecting > 0
        Profile this4 = new Profile("Natalie", "Wilson", new Date(2000, 11, 5));
        Profile other4 = new Profile("Natalie", "Brown", new Date(2000, 11, 5));
        System.out.println("Test 4: 1 | Result: " + this4.compareTo(other4));

        Profile this5 = new Profile("Zoe", "Smith", new Date(2000, 1, 1));
        Profile other5 = new Profile("Adam", "Smith", new Date(2000, 1, 1));
        System.out.println("Test 5: 1 | Result: " + this5.compareTo(other5));

        Profile this6 = new Profile("Chris", "Rock", new Date(2005, 6, 7));
        Profile other6 = new Profile("Chris", "Rock", new Date(2000, 7, 9));
        System.out.println("Test 6: 1 | Result: " + this6.compareTo(other6));

        // expecting 0
        Profile this7 = new Profile("Will", "Lipton", new Date(2002, 5, 5));
        Profile other7 = new Profile("Will", "Lipton", new Date(2002, 5, 5));
        System.out.println("Test 7: 0 | Result: " + this7.compareTo(other7));
    }


}
