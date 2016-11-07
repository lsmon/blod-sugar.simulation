package com.health.iq.model;

import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by lsmon on 11/2/16.
 */
public class Exercise {
    public static final String TBL_EXERCISE = "exercise";
    public enum columns {
        id,
        exercise,
        exercise_index
    }

    private int id;
    private String exercise;
    private int exerciseIndex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public int getExerciseIndex() {
        return exerciseIndex;
    }

    public void setExerciseIndex(int exerciseIndex) {
        this.exerciseIndex = exerciseIndex;
    }

    public JSONObject toJSONOboject(){
        JSONObject object = new JSONObject();
        object.put(columns.id.name(),getId());
        object.put(columns.exercise.name(), getExercise());
        object.put(columns.exercise_index.name(), getExerciseIndex());
        return object;
    }

    public String toString(){
        return this.toJSONOboject().toJSONString();
    }

    public static Exercise createRecord(ResultSet r) throws SQLException {
        if (r == null) return null;
        Exercise exercise = new Exercise();
        exercise.setId(r.getInt(columns.id.name()));
        exercise.setExercise(r.getString(columns.exercise.name()));
        exercise.setExerciseIndex(r.getInt(columns.exercise_index.name()));
        return exercise;
    }
}
