package project2;
/**
 * Represents all the courses offered in the system.
 * Each course contains credit information, standing prerequisite,
 * and optional major prerequisite.
 *
 * @author youwen
 */
public enum Course {
    CS100(4, "Freshman", null),
    CS200(4, "Sophomore", null),
    CS300(4, "Junior", "CS"),
    CS400(4, "Junior", "CS"),
    CS442(3, "Junior", null),
    PHY100(5, "Freshman", null),
    PHY200(5, "Sophomore", null),
    ECE300(4, "Junior", "ECE"),
    ECE400(4, "Senior", "ECE"),
    CCD(4, "Freshman", null),
    HST(3, "Freshman", null);

    private final int credits;
    private final String standingPrereq;
    private final String majorPrereq;  // null means no restriction

    /**
     * Creates a Course with credit information, standing prerequisite and major prerequisite.
     * @param credits the number of credit hours
     * @param standingPrereq standing required to take course
     * @param majorPrereq major required to take course
     */
    Course(int credits, String standingPrereq, String majorPrereq) {
        this.credits = credits;
        this.standingPrereq = standingPrereq;
        this.majorPrereq = majorPrereq;
    }
    /**
     * Returns the number of credit hours for the course.
     * @return the credit hours
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Returns the required standing to take the course
     * @return standing prerequisite
     */
    public String getStandingPrereq() {
        return standingPrereq;
    }

    /**
     * Returns the required major to take the course
     * @return major prerequisite
     */
    public String getMajorPrereq() {
        return majorPrereq;
    }
}
