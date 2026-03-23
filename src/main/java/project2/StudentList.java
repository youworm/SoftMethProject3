package project2;

import util.List;
import util.Sort;

/**
 * Represents a list of students and methods to manage the list
 * uses generic util.List implementation
 *
 * @author Joe Guan
 */
public class StudentList extends List<Student> {
    /**
     * Constructs an empty student list
     */
    public StudentList() {
        super();
    }

    /**
     * Prints students sequentially after sorting by profile
     */
    public String print() {
        StringBuilder sb = new StringBuilder();
        if (this.isEmpty()) {
            sb.append("Student list is empty!");
            return sb.toString();
        }

        // Sort by profile
        Sort.sortByProfile(this);
        sb.append("* Student list ordered by last, first name, DOB *\n");

        for (int i = 0; i < this.size(); i++) {
            Student s = this.get(i);

            // Profile
            String profileStr = "[" + s.getProfile() + "]";

            // Major + school
            String majorStr = "[" + s.getMajor().getCode() + "," + s.getMajor().getSchool() + "]";

            // Credits earned
            String creditsStr = "credits earned: " + s.getCreditCompleted();

            // Standing
            String standing = "[" + s.getStanding() + "]";

            // Status-specific info
            String status = "";
            if (s instanceof TriState) {
                status = "[tri-state:" + ((TriState) s).getState() + "]";
            } else if (s instanceof International) {
                International intl = (International) s;
                status = intl.isStudyAbroad() ? "[international:study abroad]" : "[international]";
            } else if (s instanceof NonResident) {
                status = "[non-resident]";
            } else if (s instanceof Resident) {
                Resident res = (Resident) s;
                status = "[resident]";
                if (res.getScholarship() > 0) {
                    status += " [scholarship: $" + String.format("%,d", res.getScholarship()) + "]";
                }
            }

            // Combine and print
            sb.append(profileStr + " " + majorStr + " " + creditsStr + " " + standing + status+"\n");
        }

        sb.append("* end of list **\n");
        return sb.toString();
    }
}
