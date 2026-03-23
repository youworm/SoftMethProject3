package project2;

/**
 * TriState subclass of NonResident students
 * Students must be from NY or CT
 *
 * @author youwen
 */
public class TriState extends NonResident {
    private static final int FULL_TIME_MIN_CREDITS = 12;
    private static final double NY_DISCOUNT = 4000;
    private static final double CT_DISCOUNT = 5000;

    private String state; //NY or CT

    /**
     * Constructs a TriState student
     *
     * @param profile student's profile
     * @param major student's major
     * @param creditCompleted credits completed by student
     */
    public TriState(Profile profile, Major major, int creditCompleted, String state) {
        super(profile, major, creditCompleted);
        if (state == null) {
            this.state = "";
        } else {
            this.state = state.toUpperCase();
        }
    }

    /**
     * Calculates tuition for tri-state student
     * Discount only applies for full time student
     *
     * @param creditsEnrolled number of credits the student is enrolled in
     * @return tuition for tri state student
     */
    @Override
    public double tuition(int creditsEnrolled) {
        if (creditsEnrolled <= 0) {
            return 0;
        }
        double tuition = super.tuition(creditsEnrolled);
        if (creditsEnrolled >= FULL_TIME_MIN_CREDITS) {
            if(state.equals("NY")) {
                tuition -= NY_DISCOUNT;
            } else if (state.equals("CT")) {
                tuition -= CT_DISCOUNT;
            }
        }
        if (tuition < 0) {
            tuition = 0;
        }
        return tuition;
    }

    /**
     * Returns state code of tri state student
     *
     * @return either NY or CT
     */
    public String getState() {
        return state;
    }
}
