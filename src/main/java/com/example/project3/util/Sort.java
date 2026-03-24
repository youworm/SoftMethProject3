package com.example.project3.util;

import com.example.project3.project2.Classroom;
import com.example.project3.project2.Section;
import com.example.project3.project2.Student;

/**
 * Sort helper class
 *
 * @author youwen
 */
public class Sort {
    /**
     * Sorts list of students in ascending order by profile using insertion sort
     *
     * @param list list of students to be sorted
     */
    public static void sortByProfile(List<Student> list){
        if(list == null || list.size() <= 1) {
            return;
        }
        for(int i = 1; i < list.size(); i++){
            Student key = list.get(i);
            int j = i - 1;
            while(j >= 0 && list.get(j).compareTo(key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    /**
     * Sorts a list of Sections by course name then section time
     *
     * @param list list of sections to sort
     */
    public static void sortByCourse(List<Section> list) {
        if (list == null || list.size() <= 1) return;
        for (int i = 1; i < list.size(); i++) {
            Section key = list.get(i);
            int j = i - 1;
            while (j >= 0) {
                Section current = list.get(j);
                //compare by course name
                int cmp = current.getCourse().name().compareTo(key.getCourse().name());
                //if same course, compare by time
                if (cmp == 0) {
                    cmp = current.getTime().compareTo(key.getTime());
                }
                if (cmp > 0) {
                    list.set(j + 1, current);
                    j--;
                } else {
                    break;
                }
            }
            list.set(j + 1, key);
        }
    }

    /**
     * Sorts a list of Sections by campus then building
     *
     * @param list list of sections to sort
     */
    public static void sortByClassroom(List<Section> list) {
        if (list == null || list.size() <= 1) return;
        for (int i = 1; i < list.size(); i++) {
            Section key = list.get(i);
            int j = i - 1;
            while (j >= 0) {
                Section current = list.get(j);
                Classroom a = current.getClassroom();
                Classroom b = key.getClassroom();
                // Compare by campus, then building
                int compare = a.getCampus().compareToIgnoreCase(b.getCampus());
                if (compare == 0) {
                    compare = a.getBuilding().compareToIgnoreCase(b.getBuilding());
                }
                if (compare > 0) {
                    list.set(j + 1, current);
                    j--;
                } else {
                    break;
                }
            }
            list.set(j + 1, key);
        }
    }
    /**
     * Sorts a list of students by major (alphabetically by major code)
     *
     * @param list list of students to sort
     */
    public static void sortByMajor(List<Student> list) {
        if (list == null || list.size() <= 1) return;

        for (int i = 1; i < list.size(); i++) {
            Student key = list.get(i);
            int j = i - 1;
            while (j >= 0 && list.get(j).getMajor().getCode().compareToIgnoreCase(key.getMajor().getCode()) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }
}