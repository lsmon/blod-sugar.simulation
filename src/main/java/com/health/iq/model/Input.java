package com.health.iq.model;

import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by lsmon on 11/4/16.
 */
public class Input {
    public static final String TBL_INPUTS = "inputs";

    enum columns {
        timestamp,
        type,
        id,
        index
    }

    private Date timestamp;
    private String type;
    private int id;
    private double index;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    public JSONObject toJSONObject(){
        JSONObject object = new JSONObject();
        object.put(columns.timestamp.name(), getTimestamp().toString());
        object.put(columns.type.name(), getType());
        object.put(columns.id.name(), getId());
        object.put(columns.index.name(), getIndex());
        return object;
    }

    public String toString(){
        return toJSONObject().toJSONString();
    }

    public static Input createRecord(ResultSet r) throws SQLException {
        if (r == null) return null;
        Input input = new Input();
        input.setTimestamp(r.getTimestamp(columns.timestamp.name()));
        input.setType(r.getString(columns.type.name()));
        input.setId(r.getInt(columns.id.name()));
        input.setIndex(r.getDouble(columns.index.name()));
        return input;
    }
}
