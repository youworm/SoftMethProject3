package project2;
/**
 * International subclass of NonResident students
 * must pay admin and health insurance fee
 * Study abroad students do not have to pay tuition
 *
 * @author youwen
 */
public class International extends NonResident {
    private static final double HEALTH_INSURANCE = 2650;
    private static final double ADMIN_FEE = 500;
    private static final double UNIVERSITY_FEE = 3891;

    private boolean isStudyAbroad;

    /**
     * Constructs International Student.
     *
     * @param profile student's profile
     * @param major student's major
     * @param creditCompleted credits completed by student
     * @param isStudyAbroad true if student is studying abroad
     */
    public International(Profile profile, Major major, int creditCompleted, boolean isStudyAbroad) {
        super(profile, major, creditCompleted);
        this.isStudyAbroad = isStudyAbroad;
    }

    /**
     * Calculates tuition for international students
     *
     * @param creditsEnrolled number of credits the student is enrolled in
     * @return total tuition
     */
    @Override
    public double tuition(int creditsEnrolled) {
        if (creditsEnrolled <= 0) {
            return 0;
        }
        if (isStudyAbroad) { //study abroad student
            return UNIVERSITY_FEE + HEALTH_INSURANCE + ADMIN_FEE;
        } //full time international student
        double tuition = super.tuition(creditsEnrolled);
        tuition += HEALTH_INSURANCE + ADMIN_FEE;
        return tuition;
    }

    public boolean isStudyAbroad() {return isStudyAbroad;}

}
