package com.health.iq.model;

import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lsmon on 11/2/16.
 */
public class GlycemicIndexByFood {
    public static final String TBL_FOOD_DB = "food_db";

    enum columns {
        id,
        name,
        glycemic_index
    }

    private int id;
    private String name;
    private int glycemicIndex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGlycemicIndex() {
        return glycemicIndex;
    }

    public void setGlycemicIndex(int glycemicIndex) {
        this.glycemicIndex = glycemicIndex;
    }

    public JSONObject toJSONObject(){
        JSONObject object = new JSONObject();
        object.put(columns.id.name(), getId());
        object.put(columns.name.name(), getName());
        object.put(columns.glycemic_index.name(), getGlycemicIndex());
        return object;
    }

    public String toString(){
        return this.toJSONObject().toJSONString();
    }

    public static GlycemicIndexByFood createRecord(ResultSet r) throws SQLException {
        if (r == null) return null;
        GlycemicIndexByFood glycemicIndexByFood = new GlycemicIndexByFood();
        glycemicIndexByFood.setId(r.getInt(columns.id.name()));
        glycemicIndexByFood.setName(r.getString(columns.name.name()));
        glycemicIndexByFood.setGlycemicIndex(r.getInt(columns.glycemic_index.name()));
        return glycemicIndexByFood;
    }
}
