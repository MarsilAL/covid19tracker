package covid19tracker.domain;

import java.util.Date;

public class Sighting {

    private double latitude;
    private double longitude;
    private Date instant;


    public Sighting(double v, double v1, Date date) {

        this.instant = date;
        this.latitude = v;
        this.longitude = v1;
    }

    public boolean closeTo(Sighting otherSighting){


        return false;
    }

}
