package project2;

import util.List;
import util.Sort;

/**
 * Represents course schedule containing lists of sections
 * Initial capacity of the list is 4, and increases by 4 when the list is full.
 *
 * @author youwen
 */
public class Schedule extends List<Section> {
    /**
     * Creates empty schedule with the initial capacity of 4.
     */
    public Schedule() {
        super();
    }

    /**
     * Enroll a student into a section.
     * @param section section that student is being enrolled to
     * @param student student that is enrolling
     */
    public void enroll(Section section, Student student) {
        int index = this.indexOf(section);
        if (index == -1) {
            return;
        }
        this.get(index).enroll(student);
    }

    /**
     * Drops a student from a section.
     * @param section the section the student is being dropped from.
     * @param student the student that is dropping the section.
     */
    public void drop(Section section, Student student) {
        int index = this.indexOf(section);
        if (index == -1) {
            return;
        }
        this.get(index).drop(student);
    }

    /**
     * prints all the sections sorted by campus then building.
     */
    public void printByClassroom() {
        if (this.isEmpty()) {
            System.out.println("Schedule is empty!");
            return;
        }
        System.out.println("* List of sections ordered by campus, building *");
        Sort.sortByClassroom(this);
        for (int i = 0; i < this.size(); i++) {
            this.get(i).print();
        }
        System.out.println("* end of list **");
    }

    /**
     * Prints all the schedule sorted by course name then period.
     */
    public void printByCourse() {

        if (this.isEmpty()) {
            System.out.println("Schedule is empty!");
            return;
        }
        System.out.println("* List of sections ordered by course name, section time *");
        Sort.sortByCourse(this);
        for (int i = 0; i < this.size(); i++) {
            this.get(i).print();
        }
        System.out.println("* end of list *");
    }
    /**
     * Returns a copy of the current sections in the schedule.
     *
     * @return an array containing the sections currently in the schedule
     */
    public Section[] getSections() {
        Section[] copy = new Section[this.size()];
        for (int i = 0; i < this.size(); i++) {
            copy[i] = this.get(i);
        }
        return copy;
    }
}