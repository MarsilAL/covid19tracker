package covid19tracker.domain;

public class User {
    final private String username;
    final private String hascovid;
    final private Double  latitude;
    final private Double longitude;

    public User(String username, String hascovid, Double latitude, Double longitude) {
        this.username = username;
        this.hascovid = hascovid;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUname(){
        return username;
    }

    public String getStatus(){
        return hascovid;
    }

    public Double getLatitude(){
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

}
