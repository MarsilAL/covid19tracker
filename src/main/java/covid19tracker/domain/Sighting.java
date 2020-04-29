package covid19tracker.domain;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Sighting {

    private double latitude;
    private double longitude;
    private Date instant;


    public Sighting(double latitude, double longitude, Date date) {

        this.instant = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public Date getInstant() {
        return instant;
    }

    public boolean closeTo(Sighting otherSighting){

        double anotherLatitude = otherSighting.getLatitude();
        double anotherLongitude = otherSighting.getLongitude();
        Date anotherInstant = otherSighting.getInstant();

        return (chkPlaces(latitude, longitude, anotherLatitude, anotherLongitude) && chkTimes(instant, anotherInstant));
    }

    private boolean chkPlaces(double latitude, double longitude, double anotherLatitude, double anotherLongitude) {

        double earthRadius = 6371000;
        double cLat = Math.toRadians(anotherLatitude-latitude);
        double cLng = Math.toRadians(anotherLongitude-longitude);
        double a = Math.sin(cLat/2) * Math.sin(cLat/2) + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(anotherLatitude)) * Math.sin(cLng/2) * Math.sin(cLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);
        System.out.printf("the first latitude: %s , the first longitude  : %s , the second latitude : %s , the second longitude  : %s", latitude, longitude, anotherLatitude, anotherLongitude);
        System.out.println(" dist: " + dist);
        return dist <= 5;
    }


    private boolean chkTimes(Date instant, Date anotherInstant) {

        System.out.printf("instant #1 : %s , instant #2 : %s", instant, anotherInstant);
        System.out.println(" dist: " + TimeUnit.MILLISECONDS.toMinutes(Math.abs(instant.getTime() - anotherInstant.getTime())) + " minutes");
        return (TimeUnit.MILLISECONDS.toMinutes(Math.abs(instant.getTime() - anotherInstant.getTime()))) <= 5;

    }


}
