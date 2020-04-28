package covid19tracker.domain;

public class User {
    final private String username;
    final private boolean hascovid;
    final private Double  latitude;
    final private Double longitude;
    final private String pswC;

    public User(String username, boolean hascovid, Double latitude, Double longitude, String pswC) {
        this.username = username;
        this.hascovid = hascovid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pswC = pswC;
    }

    public String getUname(){
        return username;
    }

    public boolean getStatus(){
        return hascovid;
    }

    public Double getLatitude(){
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
    public String getPswC() {
        return pswC;
    }

}
