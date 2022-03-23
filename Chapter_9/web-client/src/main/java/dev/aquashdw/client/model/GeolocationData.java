package dev.aquashdw.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeolocationData {
    private String kr;
    private Long code;
    private String r1;
    private String r2;
    private String r3;
    private Double lat;
    @JsonProperty("long")
    private Double longitude;
    private String net;

    public GeolocationData() {
    }

    public GeolocationData(String kr, Long code, String r1, String r2, String r3, Double lat, Double longitude, String net) {
        this.kr = kr;
        this.code = code;
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
        this.lat = lat;
        this.longitude = longitude;
        this.net = net;
    }

    public String getKr() {
        return kr;
    }

    public void setKr(String kr) {
        this.kr = kr;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getR1() {
        return r1;
    }

    public void setR1(String r1) {
        this.r1 = r1;
    }

    public String getR2() {
        return r2;
    }

    public void setR2(String r2) {
        this.r2 = r2;
    }

    public String getR3() {
        return r3;
    }

    public void setR3(String r3) {
        this.r3 = r3;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }
}
