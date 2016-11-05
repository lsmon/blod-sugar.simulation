package com.health.iq.model;

import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by lsmon on 11/3/16.
 */
public class BloodSugarLevel {
    public static final String TBL_BLOOD_SUGAR_LEVELS = "blood_sugar_levels";
    public static final int MIN_BLOOD_SUGAR_LEVEL = 80;

    enum columns {
        timestamp,
        blood_sugar_level,
        status
    }

    public BloodSugarLevel() {
        setTimestamp(new Date());
        setBloodSugarLevel(MIN_BLOOD_SUGAR_LEVEL);
    }

    private Date timestamp;
    private double bloodSugarLevel;
    private String currentStatus;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(double bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public JSONObject toJSONObject(){
        JSONObject object = new JSONObject();
        object.put(columns.timestamp.name(),getTimestamp().toString());
        object.put(columns.blood_sugar_level.name(), getBloodSugarLevel());
        object.put(columns.status.name(), getCurrentStatus());
        return object;
    }

    public String toString(){
        return toJSONObject().toJSONString();
    }

    public static BloodSugarLevel createRecord(ResultSet r) throws SQLException {
        if (r == null) return null;
        BloodSugarLevel bloodSugarLevel = new BloodSugarLevel();
        bloodSugarLevel.setTimestamp(r.getTimestamp(columns.timestamp.name()));
        bloodSugarLevel.setBloodSugarLevel(r.getInt(columns.blood_sugar_level.name()));
        bloodSugarLevel.setCurrentStatus(r.getString(columns.status.name()));
        return bloodSugarLevel;
    }
}
