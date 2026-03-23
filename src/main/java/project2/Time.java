package project2;

/**
 * Time class represents list of times available for sections
 * There are 6 periods with a fixed time and period number
 *
 * @author youwen
 */
public enum Time {

    EIGHT_THIRTY(1, "8:30"),
    TEN_TWENTY(2, "10:20"),
    TWELVE_TEN(3, "12:10"),
    FOURTEEN(4, "14:00"),
    FIFTEEN_FIFTY(5, "15:50"),
    SEVENTEEN_FORTY(6, "17:40");

    private final int period;
    private final String timeStr;

    /**
     * Creates a Time constant with a period number and a time string
     * @param period the period number
     * @param timeStr the time which the course takes place
     */
    Time(int period, String timeStr) {
        this.period = period;
        this.timeStr = timeStr;
    }

    /**
     * Returns the time of the given period number.
     * @param period period number
     * @return time of the period number
     */
    public static Time getTime(int period) {
        for(Time t: Time.values()) {
            if(t.getPeriod() == period) {
                return t;
            }
        }
        return null;
    }

    /**
     * Gets the period
     * @return period
     */
    public int getPeriod() {
        return period;
    }

    /**
     * Returns the formatted time string
     * @return the time in HH:MM format
     */
    @Override
    public String toString() {
        return timeStr;
    }
}

