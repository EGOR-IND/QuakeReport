package egor_ind.apps.quakereport;

import java.text.DateFormat;
import java.util.Calendar;

public class QuakeInfo {

    private double mag;
    private String place;
    private long timeStamp;

    public QuakeInfo(double mag, String place, long timeStamp) {
        this.mag = mag;
        this.place = place;
        this.timeStamp = timeStamp;
    }

    public double getMag() {
        return mag;
    }

    public String getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp*1000);
        return DateFormat.getDateInstance().format(calendar.getTime());
    }

    public String getPlace() {
        return place;
    }
}
