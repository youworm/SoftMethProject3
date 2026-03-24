package com.example.project3.project2;

/**
 * Represents the majors offered by the university.
 * Each major is associated with its school and a formatted
 * full name.
 * @author Joe Guan
 */
public enum Major {
    CS("CS", "School of Arts & Sciences"),
    ECE("ECE", "School of Engineering"),
    MATH("MATH", "School of Arts & Sciences"),
    ITI("ITI", "School of Communication and Information"),
    BAIT("BAIT", "Rutgers Business School");

    private final String code;      // short name for logic
    private final String school;    // school / display name

    /**
     * Constructor for Major
     * @param code for major
     * @param school for school the major belongs to
     */
    Major(String code, String school) {
        this.code = code;
        this.school = school;
    }

    /**
     * Gets the major code
     * @return major code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets school name
     * @return school name
     */
    public String getSchool() {
        return school;
    }

    /**
     * Gets major code plus school name
     * @return major code + school name
     */
    public String getFullName() {
        return code + "," + school;
    }

    /**
     * toString override for Major enum
     * @return full name of major including school
     */
    @Override
    public String toString() {
        return getFullName();  // keep display as "CS,School of Arts & Sciences"
    }
}