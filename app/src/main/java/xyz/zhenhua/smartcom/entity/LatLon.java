package xyz.zhenhua.smartcom.entity;

import java.io.Serializable;

/**
 * Created by zachary on 16/8/29.
 */

public class LatLon implements Serializable {
    private double lat;
    private double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
