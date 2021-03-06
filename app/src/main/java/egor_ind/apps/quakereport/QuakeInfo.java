package egor_ind.apps.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        Date time = new Date(timeStamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm a");
        return dateFormat.format(time);
    }

    public String getDate() {
        Date date = new Date(timeStamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(date);
    }

    public String getDirection() {
        int ofIndex = place.indexOf("of");
        return place.substring(0, ofIndex+2);
    }

    public String getPlace() {
        int ofIndex = place.indexOf("of");
        return place.substring(ofIndex+3);
    }
}
