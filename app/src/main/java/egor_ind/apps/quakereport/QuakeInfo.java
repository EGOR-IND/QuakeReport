package egor_ind.apps.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuakeInfo {

    private double mag;
    private String place;
    private long timeStamp;
    private String url;
    private String LOCATION_SEPERATOR = " of ";

    public QuakeInfo(double mag, String place, long timeStamp, String url) {
        this.mag = mag;
        this.place = place;
        this.timeStamp = timeStamp;
        this.url = url;
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
        if (place.contains(LOCATION_SEPERATOR)) {
            return place.split(LOCATION_SEPERATOR)[0] + ",";
        } else {
            return "Near the";
        }
    }

    public String getPlace() {
        if (place.contains(LOCATION_SEPERATOR)) {
            return place.split(LOCATION_SEPERATOR)[1];
        } else {
            return place;
        }
    }

    public String getUrl() {
        return url;
    }
}
