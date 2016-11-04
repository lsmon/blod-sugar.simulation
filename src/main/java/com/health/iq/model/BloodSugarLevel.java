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

    enum columns {
        timestamp,
        blood_sugar_level
    }

    private Date timestamp;
    private int bloodSugarLevel;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void setBloodSugarLevel(int bloodSugarLevel) {
        this.bloodSugarLevel = bloodSugarLevel;
    }

    public JSONObject toJSONObject(){
        JSONObject object = new JSONObject();
        object.put(columns.timestamp.name(),getTimestamp().toString());
        object.put(columns.blood_sugar_level.name(), getBloodSugarLevel());
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
        return bloodSugarLevel;
    }
}
