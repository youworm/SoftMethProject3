package com.example.project3.project2;

/**
 * A section class containing list of enrolled students, including course, instructor, classroom and time.
 *
 * @author youwen
 */
public class Section {
    private static final int CAPACITY = 4;

    private Course course;
    private Instructor instructor;
    private Classroom classroom;
    private Time time; //can use other data types(int)
    private Student[] roster;
    private int numStudents;

    /**
     * Section object with course, time, instructor and classroom.
     *
     * @param course the university course
     * @param time the start time of the period
     * @param instructor instructor teaching the course
     * @param classroom classroom where the course takes place
     */
    public Section(Course course, Time time, Instructor instructor, Classroom classroom) {
        this.course = course;
        this.time = time;
        this.instructor = instructor;
        this.classroom = classroom;
        this.roster = new Student[CAPACITY]; //capacity 4
        this.numStudents = 0;
    }
    /**
     * Enrolls a student to the roster.
     * It does nothing if course is full or student is already enrolled.
     * @param student the student being enrolled
     */

    public void enroll(Student student) {
        if (isFull()) { //when course is full
            return;
        }
        if (contains(student)) { //already enrolled
            return;
        }
        //add student
        roster[numStudents] = student;
        numStudents++;
    }
    /**
     * Remove the given student from the roster.
     * It does nothing if the student is not on the list.
     * @param student the student object to be removed from the list.
     */
    public void drop(Student student) {
        for(int i = 0; i < numStudents; i++) {
            if (roster[i] != null && roster[i].equals(student)) {
                roster[i] = roster[numStudents - 1]; //replace student w last student then remove last student(duplicate)
                roster[numStudents - 1] = null;
                numStudents--;
                return;
            }
        }
    }

    /**
     * Checks if student is in the list.
     * @param student student being checked
     * @return true if student is in the list.
     */
    public boolean contains(Student student) {
        for(int i = 0; i < numStudents; i++) {
            if (roster[i] != null && roster[i].equals(student)) { //** Student.equals should exist in student or student list
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if course is full.
     * @return true if number of students is at capacity.
     */
    public boolean isFull() {
        return numStudents == CAPACITY;
    }

    /**
     * Checks if section has no students enrolled.
     * @return true if number of students is at zero.
     */
    public boolean isEmpty() {
        return numStudents == 0;
    }

    /**
     * prints the string
     */
    public void print() {
        System.out.println(this.toString());

        if (numStudents == 0) {
            System.out.println("\t**No students enrolled**");
        } else {
            System.out.println("\t**Roster**");
            for (int i = 0; i < numStudents; i++) {
                System.out.println("\t[" + roster[i].getProfile() + "]");
            }
        }
    }


    /**
     * Checks if these 2 sections are the same.
     * They are considered equal if they have the same course and meeting time.
     * @param section   the reference object with which to compare.
     * @return true if this section has the same course and meeting time as the second section.
     */
    @Override
    public boolean equals(Object section) {
        if (this == section) {
            return true;
        }
        if(!(section instanceof Section)) {
            return false;
        }
        Section section2 = (Section) section;
        return this.course.equals(section2.course) && this.time.equals(section2.time);
    }

    /**
     * Returns a textual representation of the section
     * @return the correctly formatted section
     */
    @Override
    public String toString() {
        return "[" + course + " " + time + "] "
                + "[" + instructor + "] "
                + "[" + classroom.getRoomNumber() + ", "
                + classroom.getBuilding() + ", "
                + classroom.getCampus() + "]";
    }

    /**
     * Returns classroom of the section
     * @return classroom of the section
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Returns course of the section
     * @return course of the section
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Returns start time of the section
     * @return start time of the section
     */
    public Time getTime() {
        return time;
    }

    /**
     * Returns instructor of the section
     * @return instructor of section
     */
    public Instructor getInstructor() {
        return instructor;
    }

    /**
     * Gets number of students in the section
     * @return number of students
     */
    public int getNumStudents(){
        return numStudents;
    }
}