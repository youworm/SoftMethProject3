package com.example.project3.project2;

/**
 * Resident subclass of students containing profile, major and credits completed.
 *
 * @author youwen
 */
public class Resident extends Student {
    private static final int FULL_TIME_MIN_CREDITS = 12;
    private static final int CREDIT_LIMIT = 16;
    private static final double FULL_TIME_TUITION = 14933;
    private static final double PER_CREDIT_RATE = 482;
    private static final double UNIVERSITY_FEE = 3891;
    private static final int MAX_SCHOLARSHIP = 10000;

    private int scholarship;

    /**
     * Constructs a Resident student
     *
     * @param profile student's profile
     * @param major student's major
     * @param creditCompleted credits completed by student
     */
    public Resident(Profile profile, Major major, int creditCompleted) {
        super(profile, major, creditCompleted);
        this.scholarship = 0;
    }

    /**
     * Returns the scholarship amount
     * @return scholarship amount as an int
     */
    public int getScholarship(){return scholarship;}
    /**
     * Sets the scholarship amount which is capped at $10,000
     * it cannot be negative
     * it is only for full-time students
     *
     * @param amount scholarship amount
     */
    public void setScholarship(int amount) {
        if (amount < 0) amount = 0;
        if (amount > MAX_SCHOLARSHIP) {
            amount = MAX_SCHOLARSHIP;
        }
        this.scholarship = amount;
    }

    /**
     * Calculates the amount of tuition the resident has to pay
     *
     * @param creditsEnrolled number of credits the student is taking
     * @return the tuition the student has to pay
     */
    @Override
    public double tuition(int creditsEnrolled) {
        if (creditsEnrolled <= 0) return 0;
        double tuition;
        if(creditsEnrolled >= FULL_TIME_MIN_CREDITS){ //full time student
            tuition = FULL_TIME_TUITION + UNIVERSITY_FEE;
            if (creditsEnrolled > CREDIT_LIMIT) {
                tuition += (creditsEnrolled - CREDIT_LIMIT) * PER_CREDIT_RATE;
            }
            tuition -= scholarship;
            if (tuition < 0) tuition = 0;
        } else { //part time student
            tuition = creditsEnrolled * PER_CREDIT_RATE + (UNIVERSITY_FEE * 0.5);
        }
        return tuition;
    }

}

