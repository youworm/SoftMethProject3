package com.example.project3.project2;

/**
 * Non-resident subclass of students containing profile, major and credits completed.
 *
 * @author youwen
 */
public class NonResident extends Student {
    private static final int FULL_TIME_MIN_CREDITS = 12;
    private static final int CREDIT_LIMIT = 16;
    private static final double FULL_TIME_TUITION = 35758;
    private static final double PER_CREDIT_RATE = 1162;
    private static final double UNIVERSITY_FEE = 3891;

    /**
     * Constructs a non-resident student
     *
     * @param profile student's profile
     * @param major student's major
     * @param creditCompleted credits completed by student
     */
    public NonResident(Profile profile, Major major, int creditCompleted) {
        super(profile, major, creditCompleted);
    }

    /**
     * Calculates amount of tuition the non-resident has to pay
     * Full time students pay tuition + university fee
     * Part time students pay credits * credit per hour + university fee * 0.5
     * Credits above 16 have to pay extra per credit
     *
     * @param creditsEnrolled number of credits the student is enrolled in
     * @return total amount of tuition they have to pay
     */
    @Override
    public double tuition(int creditsEnrolled) {
        if (creditsEnrolled <= 0) return 0;
        double tuition;
        if (creditsEnrolled >= FULL_TIME_MIN_CREDITS) { //full time student
            tuition = FULL_TIME_TUITION + UNIVERSITY_FEE;
            if (creditsEnrolled > CREDIT_LIMIT) {
                tuition += (creditsEnrolled - CREDIT_LIMIT) * PER_CREDIT_RATE;
            }
        } else { //part time student
            tuition = creditsEnrolled * PER_CREDIT_RATE + (UNIVERSITY_FEE * 0.5);
        }
        return tuition;
    }
}
