package com.example.project3.project2;

/**
 * Represents all the classrooms in the system.
 * Each classroom contains the room number, building and the campus.
 *
 * @author youwen
 */
public enum Classroom {
    HIL114("HIL114", "Hill Center", "Busch"),
    ARC103("ARC103", "Allison Road Classroom", "Busch"),
    BEAUD("BEAUD", "Beck Hall", "Livingston"),
    TIL232("TIL232", "Tillett Hall", "Livingston"),
    AB2225("AB2225", "Academic Building", "College Avenue"),
    MU302("MU302", "Murray Hall", "College Avenue");

    private final String roomNumber;
    private final String building;
    private final String campus;

    /**
     * Creates a Classroom with the room number, building and campus.
     * @param roomNumber room number of the classroom
     * @param building building that the classroom is in
     * @param campus campus that the classroom is on
     */
    Classroom(String roomNumber, String building, String campus) {
        this.roomNumber = roomNumber;
        this.building = building;
        this.campus = campus;
    }

    /**
     * Returns the room number of the classroom
     * @return room number of classroom
     */
    public String getRoomNumber() {
        return roomNumber;
    }

    /**
     * Returns the building where the classroom is in
     * @return building of classroom
     */
    public String getBuilding() {
        return building;
    }

    /**
     * Returns the campus the classroom is on
     * @return campus of classroom
     */
    public String getCampus() {
        return campus;
    }

    /**
     * Returns classroom information in readable format
     * @return classroom in roomNumber, building, campus format
     */
    @Override
    public String toString() {
        // Format: ROOMNUMBER, Building, Campus
        return roomNumber + ", " + building + ", " + campus;
    }
}

