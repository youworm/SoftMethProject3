package com.example.project3;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.example.project3.project2.*;
import com.example.project3.util.Date;
import com.example.project3.util.Sort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Frontend handles all command-line interactions for the registration system.
 * It parses user input, validates commands, and delegates operations to
 * StudentList and Schedule.
 *
 * @author Joe Guan
 */
public class Controller {
    private static final int CREDIT_LIMIT_ALL = 20;
    private static final int CREDIT_LIMIT_INTERNATIONAL_STUDY_ABROAD = 12;
    private static final int FRESHMAN_CREDITS_THRESHOLD = 30;
    private static final int SOPHOMORE_CREDITS_THRESHOLD = 60;
    private static final int JUNIOR_CREDITS_THRESHOLD = 90;

    private StudentList studentList;
    private Schedule schedule;

    // --- Add Student Tab Fields ---
    @FXML
    private TextField addFnameField;      // first name
    @FXML
    private TextField addLnameField;      // last name
    @FXML
    private TextField addDobField;        // DOB
    @FXML
    private TextField addCreditsField;

    @FXML private ToggleGroup majorGroup;
    @FXML private RadioButton csMajor;
    @FXML private RadioButton eceMajor;
    @FXML private RadioButton itiMajor;
    @FXML private RadioButton mathMajor;
    @FXML private RadioButton baitMajor;

    @FXML private ToggleGroup residencyGroup;
    @FXML private ToggleGroup nonResidentGroup;
    @FXML private ToggleGroup stateGroup;

    @FXML private RadioButton residentRadio;
    @FXML private RadioButton nonResidentRadio;

    @FXML private RadioButton triStateRadio;
    @FXML private RadioButton internationalRadio;

    @FXML private RadioButton nyRadio;
    @FXML private RadioButton ctRadio;

    @FXML private CheckBox addStudyAbroadCheck;

    // Remove student fields
    @FXML
    private TextField removeFnameField;
    @FXML
    private TextField removeLnameField;
    @FXML
    private TextField removeDobField;

    // Enroll student fields
    @FXML
    private TextField enrollFnameField;
    @FXML
    private TextField enrollLnameField;
    @FXML
    private TextField enrollDobField;
    @FXML
    private TextField enrollCourseCodeField;
    @FXML
    private TextField enrollPeriodField;



    // Offer fields
    @FXML
    private ComboBox<Course> offerCourseCodeField;
    @FXML
    private ComboBox<Time> offerPeriodField;
    @FXML
    private ComboBox<Instructor> offerInstructorField;
    @FXML
    private ComboBox<Classroom> offerRoomField;

    // Course Period Profile
    // Drop fields
    @FXML
    private TextField dropFnameField;
    @FXML
    private TextField dropLnameField;
    @FXML
    private TextField dropDobField;
    @FXML
    private TextField dropCourseCodeField;
    @FXML
    private TextField dropPeriodField;
    @FXML
    private TextArea outputArea;

    // Scholarship fields
    @FXML
    private TextField scholarshipFnameField;
    @FXML
    private TextField scholarshipLnameField;
    @FXML
    private TextField scholarshipDobField;
    @FXML
    private TextField scholarshipAmountField;

    /**
     * Initalizes the state of radio buttons and drop-downs.
     */
    @FXML
    private void initialize() {
        offerCourseCodeField.getItems().setAll(Course.values());
        offerPeriodField.getItems().setAll(Time.values());
        offerInstructorField.getItems().setAll(Instructor.values());
        offerRoomField.getItems().setAll(Classroom.values());
        // Default disable everything
        triStateRadio.setDisable(true);
        internationalRadio.setDisable(true);
        nyRadio.setDisable(true);
        ctRadio.setDisable(true);
        addStudyAbroadCheck.setDisable(true);

        // Resident vs Non-Resident
        residentRadio.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                triStateRadio.setDisable(true);
                internationalRadio.setDisable(true);
                triStateRadio.setSelected(false);
                internationalRadio.setSelected(false);

                nyRadio.setDisable(true);
                ctRadio.setDisable(true);
                nyRadio.setSelected(false);
                ctRadio.setSelected(false);

                addStudyAbroadCheck.setDisable(true);
                addStudyAbroadCheck.setSelected(false);
            }
        });

        nonResidentRadio.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            triStateRadio.setDisable(!isNowSelected);
            internationalRadio.setDisable(!isNowSelected);

            if (!isNowSelected) {
                triStateRadio.setSelected(false);
                internationalRadio.setSelected(false);

                nyRadio.setDisable(true);
                ctRadio.setDisable(true);
                nyRadio.setSelected(false);
                ctRadio.setSelected(false);

                addStudyAbroadCheck.setDisable(true);
                addStudyAbroadCheck.setSelected(false);
            }
        });

        // TriState selected → enable states
        triStateRadio.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            nyRadio.setDisable(!isNowSelected);
            ctRadio.setDisable(!isNowSelected);
            if (!isNowSelected) {
                nyRadio.setSelected(false);
                ctRadio.setSelected(false);
            }
            // TriState and International are mutually exclusive
            if (isNowSelected) internationalRadio.setSelected(false);
        });

        // International selected → enable study abroad
        internationalRadio.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            addStudyAbroadCheck.setDisable(!isNowSelected);
            if (!isNowSelected) addStudyAbroadCheck.setSelected(false);

            // International and TriState are mutually exclusive
            if (isNowSelected) triStateRadio.setSelected(false);

            // NY/CT should be disabled if International is selected
            nyRadio.setDisable(isNowSelected || !nonResidentRadio.isSelected());
            ctRadio.setDisable(isNowSelected || !nonResidentRadio.isSelected());
        });

    }

    /**
     * Constructs a Frontend instance and initializes the student list
     * and course schedule.
     */
    public Controller() {
        studentList = new StudentList();
        schedule = new Schedule();
    }

    /**
     * Writes message from handle methods to output area
     * @param message to be written in output area
     */
    private void print(String message) {
        outputArea.appendText(message + "\n");
    }
    /**
     * Gets student type as a string
     * Helper method for handle tuition and handle add
     * @param student to get type from
     * @return Tristate, Resident, Nonresident, International study abroad, or International
     */
    private static String getStudentType(Student student) {
        String studentType = "";
        if (student instanceof TriState tri) {
            studentType = "Tristate: " + tri.getState();
        } else if (student instanceof International intl) {
            studentType = intl.isStudyAbroad() ? "International study abroad" : "International";
        } else if (student instanceof NonResident) {
            studentType = "Nonresident";
        } else if (student instanceof Resident) {
            studentType = "Resident";
        }
        return studentType;
    }

    /**
     * Parses a date string in MM/DD/YYYY format into a Date object.
     *
     * @param dobStr the date string
     * @return a Date object representing the parsed date
     */
    private Date parseDate(String dobStr) {
        String[] parts = dobStr.split("/");
        int month = Integer.parseInt(parts[0]);
        int day = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        return new Date(year, month, day);
    }

    /**
     * Finds a student in the student list by profile.
     *
     * @param profile the student's profile
     * @return the matching Student or null if not found
     */
    private Student findStudent(Profile profile) {
        for (Student s : studentList) {
            if (s != null && s.getProfile().equals(profile)) {
                return s;
            }
        }
        return null;
    }

    /**
     * Parses a course code into a Course enum.
     *
     * @param courseCode the course code string
     * @return the corresponding Course or null if invalid
     */
    private Course parseCourse(String courseCode) {
        for (Course c : Course.values()) {
            if (c.name().equals(courseCode.toUpperCase())) {
                return c;
            }
        }
        return null;
    }

    /**
     * Parses a period string into a Time enum.
     *
     * @param periodStr the period number as a string
     * @return the corresponding Time or null if invalid
     */
    private Time parseTime(String periodStr) {
        try {
            int period = Integer.parseInt(periodStr);
            return Time.getTime(period);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Finds a section by course and time.
     *
     * @param course the course
     * @param time   the time period
     * @return the matching Section or null if not found
     */
    private Section findSection(Course course, Time time) {
        for (Section s : schedule.getSections()) {
            if (s != null && s.getCourse() == course && s.getTime() == time) {
                return s;
            }
        }
        return null;
    }

    /**
     * Checks whether a student is enrolled in any section.
     *
     * @param student the student
     * @return true if enrolled, false otherwise
     */
    private boolean isStudentEnrolled(Student student) {
        for (Section s : schedule.getSections()) {
            if (s != null && s.contains(student)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines a student's academic standing based on completed credits.
     *
     * @param student the student
     * @return the standing string
     */
    private String getStanding(Student student) {
        int credits = student.getCreditCompleted();
        if (credits < FRESHMAN_CREDITS_THRESHOLD) return "Freshman";
        if (credits < SOPHOMORE_CREDITS_THRESHOLD) return "Sophomore";
        if (credits < JUNIOR_CREDITS_THRESHOLD) return "Junior";
        return "Senior";
    }

    /**
     * Checks if enrolling would cause a time conflict for the student.
     *
     * @param student the student
     * @param time    the time period
     * @return true if a conflict exists, false otherwise
     */
    private boolean hasTimeConflict(Student student, Time time) {
        for (Section s : schedule.getSections()) {
            if (s != null && s.contains(student)
                    && s.getTime().equals(time)) {
                print(
                        "Time conflict: [" + student.getProfile()
                                + "] enrolled in another class at period "
                                + time.getPeriod());
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether enrolling in a new course would exceed the credit limit.
     *
     * @param student   the student
     * @param newCourse the course to be added
     * @return true if credit limit would be exceeded, false otherwise
     */
    private boolean exceedsCreditLimit(Student student, Course newCourse) {
        int totalCredits = 0;

        for (Section s : schedule.getSections()) {
            if (s != null && s.contains(student)) {
                totalCredits += s.getCourse().getCredits();
            }
        }

        int newTotal = totalCredits + newCourse.getCredits();

        if (student instanceof International intl && intl.isStudyAbroad()) {
            if (newTotal > CREDIT_LIMIT_INTERNATIONAL_STUDY_ABROAD) {
                print(
                        "International student study abroad cannot enroll more than 12 credits.");
                return true;
            }
        } else {
            if (newTotal > CREDIT_LIMIT_ALL) {
                print(
                        "Cannot enroll [" + student.getProfile() + "]; now has " + totalCredits
                                + " will exceeds credit limit of " + CREDIT_LIMIT_ALL + ".");
                return true;
            }
        }

        return false;
    }

    /**
     * Parses an instructor name into an Instructor enum.
     *
     * @param instructorStr the instructor name
     * @return the Instructor or null if invalid
     */
    private Instructor parseInstructor(String instructorStr) {
        for (Instructor i : Instructor.values()) {
            if (i.name().equalsIgnoreCase(instructorStr)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Checks if an instructor has a time conflict.
     *
     * @param instructor the instructor
     * @param time       the time period
     * @return true if a conflict exists, false otherwise
     */
    private boolean hasInstructorTimeConflict(Instructor instructor, Time time) {
        for (Section s : schedule.getSections()) {
            if (s != null &&
                    s.getInstructor().equals(instructor) &&
                    s.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Parses a classroom identifier into a Classroom enum.
     *
     * @param roomStr the room identifier
     * @return the Classroom or null if invalid
     */
    private Classroom parseClassroom(String roomStr) {
        for (Classroom r : Classroom.values()) {
            if (r.getRoomNumber().equalsIgnoreCase(roomStr)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Checks if a classroom is available at a given time.
     *
     * @param classroom the classroom
     * @param time      the time period
     * @return true if available, false otherwise
     */
    private boolean isClassroomAvailable(Classroom classroom, Time time) {
        for (Section s : schedule.getSections()) {
            if (s != null &&
                    s.getClassroom().equals(classroom) &&
                    s.getTime().equals(time)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts a standing string into a numeric level.
     *
     * @param standing the standing string
     * @return the standing level
     */
    private int standingLevel(String standing) {
        switch (standing) {
            case "Freshman":
                return 1;
            case "Sophomore":
                return 2;
            case "Junior":
                return 3;
            case "Senior":
                return 4;
            default:
                return 0;
        }
    }

    /**
     * Recalculate credits as current credits plus enrolled credits
     * @param student to check credits
     * @return integer of total credits
     */
    private int recalcCredits(Student student) {
        int total = student.getCreditCompleted();

        for (Section section : schedule.getSections()) {
            if (section != null && section.contains(student)) {
                total += section.getCourse().getCredits();
            }
        }

        return total;
    }


    /**
     * Handles adding students with different resident statuses.
     * Supports AR (Resident), AN (NonResident), AT (TriState), AI (International)
     */
    @FXML
    private void handleAdd() {
        try {
            String fname = addFnameField.getText();
            String lname = addLnameField.getText();
            Date dob = parseDate(addDobField.getText());
            RadioButton selectedMajor = (RadioButton) majorGroup.getSelectedToggle();
            if (selectedMajor == null) {
                print("INVALID: major not selected.");
                return;
            }


            String majorStr = selectedMajor.getText();
            int credits;
            try {
                credits = Integer.parseInt(addCreditsField.getText());
            } catch (NumberFormatException e) {
                print("INVALID: " + addCreditsField.getText() + " is not an integer!");
                return;
            }

            Major major = null;
            for (Major m : Major.values()) {
                if (m.name().equals(majorStr)) major = m;
            }

            if (major == null) {
                print("INVALID: " + majorStr + " major does not exist.");
                return;
            }

            if (!dob.isValid()) {
                print("INVALID: " + dob + " is not a valid calendar date!");
                return;
            }

            Calendar today = Calendar.getInstance();
            Calendar birth = Calendar.getInstance();
            birth.set(dob.getYear(), dob.getMonth() - 1, dob.getDay());

            if (!birth.before(today)) {
                print("INVALID: " + dob + " cannot be today or a future day.");
                return;
            }

            int age = today.get(Calendar.YEAR) - dob.getYear();
            if (today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR)) age--;
            if (age < 16) {
                print("INVALID: " + dob + " younger than 16 years old.");
                return;
            }

            if (credits < 0) {
                print("INVALID: " + credits + " credit is negative!");
                return;
            }

            Profile profile = new Profile(fname, lname, dob);
            Student student = null;
            String studentType = "";

            if (residencyGroup.getSelectedToggle() == null) {
                print("INVALID: Residency must be selected.");
                return;
            }

            if (residentRadio.isSelected()) {
                student = new Resident(profile, major, credits);
                studentType = "Resident";

            } else if (nonResidentRadio.isSelected()) {

                if (triStateRadio.isSelected()) {
                    RadioButton selectedState = (RadioButton) stateGroup.getSelectedToggle();
                    if (selectedState == null) {
                        print("State not selected.");
                        return;
                    }

                    String state = selectedState.getText();
                    student = new TriState(profile, major, credits, state);
                    studentType = "Tristate: " + state;

                } else if (internationalRadio.isSelected()) {
                    boolean studyAbroad = addStudyAbroadCheck.isSelected();
                    student = new International(profile, major, credits, studyAbroad);
                    studentType = studyAbroad ? "International study abroad" : "International";
                }
            }

            if (studentList.contains(student)) {
                print("[" + profile + "] student is already in the list.");
                return;
            }

            studentList.add(student);
            print("[" + profile + "][" + studentType + "] added to the list.");

        } catch (Exception e) {
            print("Missing data tokens.");
        }
    }

    /**
     * Checks for valid state code for Tristate students
     * @param str, code to be checked
     * @return true if the code is valid, false otherwise.
     */
    public boolean isValidState(String str) {
        String[] validStates = {"NY", "CT", "NJ"};
        for (String s : validStates) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Handles the remove-student command.
     *
     */
    @FXML
    private void handleRemove() {
        Profile profile = new Profile(
                addFnameField.getText(),
                addLnameField.getText(),
                parseDate(addDobField.getText())
        );

        Student student = findStudent(profile);
        if (student == null) {
            print("[" + profile + "] is not in the student list.");
            return;
        }

        if (isStudentEnrolled(student)) {
            print("[" + profile + "] already enrolled in a section.");
            return;
        }

        studentList.remove(student);
        print("[" + profile + "] removed from the list.");
    }

    /**
     * Handles the enroll command.
     *
     */
    @FXML
    private void handleEnroll() {
        Profile profile = new Profile(
                enrollFnameField.getText(),
                enrollLnameField.getText(),
                parseDate(enrollDobField.getText())
        );

        String courseCode = enrollCourseCodeField.getText();
        Course course = parseCourse(courseCode);
        String period = enrollPeriodField.getText();
        Time time = parseTime(period);
        Student student = findStudent(profile);

        if (student == null) {
            print("INVALID: [" + profile + "] does not exist.");
            return;
        }
        if (course == null) {
            print("INVALID: course name " + courseCode + " does not exist.");
            return;
        }
        if (time == null) {
            print("INVALID: period " + period + " does not exist.");
            return;
        }

        Section section = findSection(course, time);
        if (section == null) {
            print("INVALID: " + course + " " + time + " does not exist.");
            return;
        }

        for (Section s : schedule.getSections()) {
            if (s != null && s.contains(student) && s.getCourse() == course) {
                print("[" + profile + "] already enrolled in " + course);
                return;
            }
        }

        String standingReq = course.getStandingPrereq();
        String standing = getStanding(student);
        if (standingReq != null) {
            int requiredLevel = standingLevel(standingReq);
            int studentLevel = standingLevel(standing);

            if (studentLevel < requiredLevel) {
                print(
                        "Prereq: " + standingReq + " - [" + profile + "] [" + standing + "]"
                );
                return;
            }
        }

        String majorReq = course.getMajorPrereq();
        if (majorReq != null && !majorReq.equals(student.getMajor().getCode())) {
            print("Prereq: major only - [" + profile + "] [" + student.getMajor().getCode() + "]");
            return;
        }

        if (hasTimeConflict(student, time)) {
            return;
        }

        if (exceedsCreditLimit(student, course)) {
            return;
        }

        if (section.isFull()) {
            print("Cannot enroll [" + profile + "], " + section.getCourse() + " " + section.getTime()
                    + " is full.");
            return;
        }

        section.enroll(student);
        print("[" + profile + "] added to " + course + " " + time);
    }

    /**
     * Handles the drop command.
     *
     */
    @FXML
    private void handleDrop() {
        Profile profile = new Profile(enrollFnameField.getText(), enrollLnameField.getText(), parseDate(enrollDobField.getText()));
        String courseStr = enrollCourseCodeField.getText();
        Course course = parseCourse(courseStr);
        String periodStr = enrollPeriodField.getText();
        Time time = parseTime(periodStr);
        Student student = findStudent(profile);

        if (course == null) {
            print("INVALID: course name " + courseStr + " does not exist.");
            return;
        }

        if (time == null) {
            print("INVALID: period " + periodStr + " does not exist.");
            return;
        }

        if (student == null) {
            print("[" + profile + "] is not in the student list.");
            return;
        }

        Section section = findSection(course, time);
        if (section == null || !section.contains(student)) {
            print("[" + profile + "] is not enrolled in this section.");
            return;
        }

        section.drop(student);
        print("[" + profile + "] dropped from " + course + " " + time);
    }

    /**
     * Handles the close-section command.
     */
    @FXML
    private void handleClose() {
        Course course = offerCourseCodeField.getValue();
        Time time = offerPeriodField.getValue();

        if (course == null) {
            print("INVALID: course name does not exist.");
            return;
        }

        if (time == null) {
            print("INVALID: period does not exist.");
            return;
        }

        Section section = findSection(course, time);
        if (section == null) {
            print(course + " " + time + " does not exist.");
            return;
        }

        if (!section.isEmpty()) {
            print(course + " " + time +
                    " cannot be removed [" + section.getNumStudents() + " student(s) enrolled]");
            return;
        }

        schedule.remove(section);
        print(course + " " + time + " removed.");
    }


    /**
     * Handles the offer-section command.
     */
    @FXML
    private void handleOffer() {
        if (offerCourseCodeField.getValue() == null ||
                offerPeriodField.getValue() == null ||
                offerInstructorField.getValue() == null ||
                offerRoomField.getValue() == null) {

            print("Invalid command.");
            return;
        }
        Course course = offerCourseCodeField.getValue();
        if (course == null) {
            print("INVALID: no course selected.");
            return;
        }

        Time time = offerPeriodField.getValue();
        if (time == null) {
            print("INVALID: no period selected.");
            return;
        }

        Instructor instructor = offerInstructorField.getValue();
        if (instructor == null) {
            print("INVALID: no instructor selected.");
            return;
        }

        Classroom classroom = offerRoomField.getValue();
        if (classroom == null) {
            print("INVALID: no classroom selected.");
            return;
        }

        if (!isClassroomAvailable(classroom, time)) {
            print("INVALID: [" + classroom + "] not available."); return;
        }

        Section section = new Section(course, time, instructor, classroom);
        schedule.add(section);
        print("[" + course + " " + time + "] [" + instructor + "] [" + classroom + "] added to the schedule.");
    }

    /**
     * Loads students from the text file "students.txt".
     * Each line represents a Student.
     * Resident status: R=Resident, N=Non-Resident, T=Tristate, I=International
     * Invalid lines are skipped with a message.
     */
    @FXML
    private void handleLoad() {
        File file = new File("src/students.txt");
        if (!file.exists()) {
            print("File students.txt not found.");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                StringTokenizer st = new StringTokenizer(line);
                try {
                    // First token is resident status
                    String resStatus = st.nextToken().toUpperCase();
                    String fname = st.nextToken();
                    String lname = st.nextToken();
                    Date dob = parseDate(st.nextToken());
                    String majorStr = st.nextToken().toUpperCase();
                    int credits = Integer.parseInt(st.nextToken());

                    Major major = null;
                    for (Major m : Major.values()) {
                        if (m.name().equals(majorStr)) major = m;
                    }
                    if (major == null) {
                        print("INVALID: " + majorStr + " major does not exist.");
                        continue;
                    }

                    if (!dob.isValid()) {
                        print("INVALID: " + dob + " is not a valid calendar date!");
                        continue;
                    }

                    Profile profile = new Profile(fname, lname, dob);
                    Student student;
                    String studentType;
                    switch (resStatus) {
                        case "R":
                            studentType = "Resident";
                            student = new Resident(profile, major, credits);
                            break;
                        case "N":
                            studentType = "Nonresident";
                            student = new NonResident(profile, major, credits);
                            break;
                        case "T":

                            String stateStr = st.nextToken();
                            studentType = "Tristate: " + stateStr;
                            student = new TriState(profile, major, credits, stateStr);
                            break;
                        case "I":
                            boolean abroadI = Boolean.parseBoolean(st.nextToken());
                            student = new International(profile, major, credits, abroadI);
                            studentType = abroadI ? "International study abroad" : "International";
                            break;
                        default:
                            print("INVALID: unknown resident status " + resStatus);
                            continue;
                    }

                    if (studentList.contains(student)) {
                        print("[" + profile + "] student is already in the list.");
                        continue;
                    }

                    studentList.add(student);
                    print("[" + profile + "][" + studentType + "] added to the list.");

                } catch (Exception e) {
                    print("INVALID line: " + line);
                }
            }
        }
        catch (FileNotFoundException e) {
            print("Error reading students.txt");
        }
        print("student list loaded from the text file.");

    }

    /**
     * Handles the S command to set scholarship for a Resident student.
     * Only full-time Resident students are eligible.
     *
     */
    @FXML
    private void handleScholarship() {
        try {
            String fname = scholarshipFnameField.getText();
            String lname = scholarshipLnameField.getText();
            Date dob = parseDate(scholarshipDobField.getText());

            int amount;
            try {
                amount = Integer.parseInt(scholarshipAmountField.getText());
            } catch (NumberFormatException e) {
                print("INVALID: amount is not an integer.");
                return;
            }

            Profile profile = new Profile(fname, lname, dob);
            Student student = findStudent(profile);

            if (student == null) {
                print("[" + profile + "] is not in the student list.");
                return;
            }

            if (!(student instanceof Resident)) {
                print("[" + profile + "] is a non-resident not eligible for the scholarship.");
                return;
            }

            Resident resident = (Resident) student;

            int enrolledCredits = 0;
            for (Section s : schedule.getSections()) {
                if (s != null && s.contains(resident)) {
                    enrolledCredits += s.getCourse().getCredits();
                }
            }

            if (enrolledCredits < 12) {
                print("[" + profile + "] enrolled less than 12 credits, not eligible for the scholarship.");
                return;
            }

            if (amount < 0 || amount > 10000) {
                print("INVALID: scholarship amount cannot be negative or greater than $10,000.");
                return;
            }

            resident.setScholarship(amount);
            print("Scholarship $" + String.format("%,d", amount) + " updated for [" + profile + "]");
        } catch (Exception e) {
            print("Invalid input.");
        }
    }



    /**
     * Prints all student's tuition by profile
     */
    @FXML
    private void handleTuition() {
        if (studentList.isEmpty()) { print("Student List is empty!"); return; }

        Sort.sortByProfile(studentList);
        print("* Tuition dues ordered by student. *");

        for (Student student : studentList) {
            Profile profile = student.getProfile();
            int totalCredits = 0;

            String studentType = getStudentType(student);

            print("[" + profile + "][" + studentType + "]");

            // Loop through schedule to find this student's enrollments
            for (Section section : schedule.getSections()) {
                if (section.contains(student)) {
                    int credits = section.getCourse().getCredits();
                    totalCredits += credits;

                    print("\t\t" + section.getCourse()
                            + "[" + section.getTime() + "] [credit: " + credits + "]");
                }
            }

            if (student instanceof International intl && !intl.isStudyAbroad() && totalCredits < 12) {
                print("\t\t**International student must enroll at least 12 credits."); continue;
            }

            if (totalCredits == 0) {
                print("**not enrolled."); continue;
            }

            double tuition = student.tuition(totalCredits);

            print("\t\t**Total credits enrolled: " + totalCredits +
                    " [tuition due: $" + String.format("%,.2f", tuition) + "]");
        }
        print("* end of list *");
    }




    /**
     * prints all students eligible for graduation
     */
    @FXML
    private void handleGraduates() {
        StudentList graduates = new StudentList();

        for (Student student : studentList) {
            int totalCredits = recalcCredits(student);

            if (totalCredits >= 120) {
                graduates.add(student);
            }
        }

        if (graduates.isEmpty()) {
            print("Schedule is empty!");
            return;
        }

        Sort.sortByMajor(graduates);

        print("* List of students eligible for graduation, ordered by major *");

        for (Student s : graduates) {
            String profileStr = "[" + s.getProfile() + "]";
            String majorStr = "[" + s.getMajor().getCode() + "," + s.getMajor().getSchool() + "]";
            print(profileStr + majorStr);
        }

        print("* end of list *");
    }

    @FXML
    private void handlePrintByClassroom() {
        print(schedule.printByClassroom());
    }

    @FXML
    private void handlePrintByCourse() {
        print(schedule.printByCourse());
    }

    @FXML
    private void handlePrintStudentList() {
        print(studentList.print());
    }
}