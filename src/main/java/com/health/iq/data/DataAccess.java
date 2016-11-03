package com.health.iq.data;


import com.health.iq.model.Exercise;
import com.health.iq.model.GlycemicIndexByFood;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by lsmon on 11/2/16.
 */
public class DataAccess {
    public DataAccess() {
        try {
            ResultSet resultSet = ConnectionHandler.getConnection().createStatement().executeQuery("SELECT name FROM sqlite_master");
            boolean doesExerciseTableExists = false;
            boolean doesGlucemicIndexTableExists = false;

            while (resultSet.next()) {
                String tableName = resultSet.getString(1);
                if(tableName.equals(Exercise.TBL_EXERCISE)) doesExerciseTableExists = true;
                if(tableName.equals(GlycemicIndexByFood.TBL_FOOD_DB)) doesGlucemicIndexTableExists = true;
            }
            if (!doesExerciseTableExists) {
                if (!ConnectionHandler.getConnection().createStatement().execute(String.format("CREATE TABLE IF NOT EXISTS %s (\n" +
                        "  id integer PRIMARY KEY,\n" +
                        "  exercise string,\n" +
                        "  exercise_index integer\n" +
                        ")",Exercise.TBL_EXERCISE)))
                    System.out.println(Exercise.TBL_EXERCISE + " TABLE CREATED");
                else
                    System.err.println(Exercise.TBL_EXERCISE + " TABLE CREATION FAILED");
            } else {
                System.out.println(Exercise.TBL_EXERCISE + " TABLE ALREADY EXISTS");
            }
            if (!doesGlucemicIndexTableExists) {
                if (!ConnectionHandler.getConnection().createStatement().execute(String.format("CREATE TABLE IF NOT EXISTS %s (\n" +
                        "  id integer PRIMARY KEY,\n" +
                        "  name string,\n" +
                        "  glycemic_index integer\n" +
                        ")", GlycemicIndexByFood.TBL_FOOD_DB)))
                    System.out.println(GlycemicIndexByFood.TBL_FOOD_DB + " TABLE CREATED");
                else
                    System.err.println(GlycemicIndexByFood.TBL_FOOD_DB + " TABLE CREATION FAILED");
            } else {
                System.out.println(GlycemicIndexByFood.TBL_FOOD_DB + " TABLE ALREADY EXISTS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.closeConnection();
        }
    }

    public static List<Exercise> getExcerciseList() {
        List<Exercise> exerciseList = new LinkedList<>();
        try {
            ResultSet resultSet = ConnectionHandler.getConnection().createStatement().executeQuery(String.format("SELECT * FROM %s", Exercise.TBL_EXERCISE));
            while (resultSet.next())
                exerciseList.add(Exercise.createRecord(resultSet));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.closeConnection();
        }
        return exerciseList;
    }

    public static List<GlycemicIndexByFood> getGlycemicIndexByFoodList(){
        List<GlycemicIndexByFood> glycemicIndexByFoodList = new LinkedList<>();
        try{
            ResultSet resultSet = ConnectionHandler.getConnection().createStatement().executeQuery(String.format("SELECT * FROM %s", GlycemicIndexByFood.TBL_FOOD_DB));
            while (resultSet.next())
                glycemicIndexByFoodList.add(GlycemicIndexByFood.createRecord(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionHandler.closeConnection();
        }
        return glycemicIndexByFoodList;
    }
}
