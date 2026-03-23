package project2;

/**
 * Represents a student with profile, major and credits completed
 * Also has overrides for equals(), toString(), and compareTo() for comparison and manipulation
 * @author Joe Guan
 */
public abstract class Student implements Comparable<Student> {
    private static final int FRESHMAN_CREDITS_THRESHOLD = 30;
    private static final int SOPHOMORE_CREDITS_THRESHOLD = 60;
    private static final int JUNIOR_CREDITS_THRESHOLD = 90;

    protected Profile profile;
    protected Major major;
    protected int creditCompleted;

    /**
     * Constructor for student with profile major and credits completed
     *
     * @param profile student's profile
     * @param major student's major
     * @param creditCompleted credits completed by student
     */
    public Student(Profile profile, Major major, int creditCompleted) {
        this.profile = profile;
        this.major = major;
        this.creditCompleted = creditCompleted;
    }

    /**
     * Returns the major of the student.
     * @return major of student
     */
    public Major getMajor(){
        return major;
    }

    /**
     * Returns the profile of the student
     * @return profile of student.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * Returns the credits completed of the student.
     * @return credits completed of student
     */
    public int getCreditCompleted(){
        return creditCompleted;
    }

    /**
     * Compares this student to another student to see if they have the same profile
     *
     * @param obj to compare to
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }
        Student other = (Student) obj;
        return this.profile.equals(other.profile);
    }

    /**
     * Compares two students in order of last name, first name, and date of birth.
     * Helper method for sort()
     *
     * @param other student to compare to
     * @return negative integer, 0 or positive integer
     */
    @Override
    public int compareTo(Student other) {
        return this.profile.compareTo(other.profile);
    }

    /**
     * Writes student information in a textual format
     *
     * @return Student information in textual format
     */
    @Override
    public String toString() {
        return "[" + profile + "] [" + major.getFullName() + "] credits earned: "
                + creditCompleted + " [" + getStanding() + "]";
    }

    /**
     * Gets standing of student
     * @return the standing of the student
     */
    public String getStanding() {
        if (creditCompleted < FRESHMAN_CREDITS_THRESHOLD) return "Freshman";
        if (creditCompleted < SOPHOMORE_CREDITS_THRESHOLD) return "Sophomore";
        if (creditCompleted < JUNIOR_CREDITS_THRESHOLD) return "Junior";
        return "Senior";
    }

    /**
     * Makes each subclass implement tuition
     */
    public abstract double tuition(int creditsEnrolled);

}
