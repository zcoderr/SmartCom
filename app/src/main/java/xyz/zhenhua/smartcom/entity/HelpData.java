package xyz.zhenhua.smartcom.entity;

import java.io.Serializable;

/**
 * Created by zachary on 16/9/11.
 */

public class HelpData implements Serializable {
    private String username;
    private String title;
    private String buf;
    private String north;
    private String east;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBuf() {
        return buf;
    }

    public void setBuf(String buf) {
        this.buf = buf;
    }

    public String getNorth() {
        return north;
    }

    public void setNorth(String north) {
        this.north = north;
    }

    public String getEast() {
        return east;
    }

    public void setEast(String east) {
        this.east = east;
    }
}
