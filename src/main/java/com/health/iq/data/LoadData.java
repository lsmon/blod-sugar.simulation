package com.health.iq.data;

import com.health.iq.controller.Leveling;
import com.health.iq.model.Exercise;
import com.health.iq.model.GlycemicIndexByFood;
import com.health.iq.model.Input;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lsmon on 11/2/16.
 */
public class LoadData {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private static List<Input> randomRunSchedule() throws ParseException {
        Random random = new Random(); // Ideally just create one instance globally


        List<Input> todaySchedule = new LinkedList<>();
        Input startDate = new Input();
        startDate.setIndex(80.0);
        startDate.setTimestamp(dateFormat.parse("2016-11-01 07:00:00"));
        startDate.setType("normalize");
        startDate.setId(0);
        todaySchedule.add(startDate);

        DataAccess access = DataAccess.getInstance();

        List<Exercise> exerciseList = access.selectAllExcerciseList();
        List<GlycemicIndexByFood> glycemicIndexByFoodList = access.selectAllGlycemicIndexByFoodList();

        GlycemicIndexByFood food = glycemicIndexByFoodList.get(random.nextInt(glycemicIndexByFoodList.size() - 1));
        Input ingestion = new Input();
        ingestion.setId(food.getId());
        ingestion.setIndex(food.getGlycemicIndex());
        ingestion.setType("food");
        ingestion.setTimestamp(dateFormat.parse("2016-11-01 07:10:00"));
        todaySchedule.add(ingestion);

        Exercise exercise = exerciseList.get(random.nextInt(exerciseList.size() - 1));
        Input workout = new Input();
        workout.setId(exercise.getId());
        workout.setIndex(exercise.getExerciseIndex());
        workout.setType("exercise");
        workout.setTimestamp(dateFormat.parse("2016-11-01 08:00:00"));
        todaySchedule.add(workout);

        food = glycemicIndexByFoodList.get(random.nextInt(glycemicIndexByFoodList.size() - 1));
        ingestion = new Input();
        ingestion.setId(food.getId());
        ingestion.setIndex(food.getGlycemicIndex());
        ingestion.setType("food");
        ingestion.setTimestamp(dateFormat.parse("2016-11-01 12:30:00"));
        todaySchedule.add(ingestion);

        exercise = exerciseList.get(random.nextInt(exerciseList.size() - 1));
        workout = new Input();
        workout.setId(exercise.getId());
        workout.setIndex(exercise.getExerciseIndex());
        workout.setType("exercise");
        workout.setTimestamp(dateFormat.parse("2016-11-01 13:30:00"));
        todaySchedule.add(workout);

        food = glycemicIndexByFoodList.get(random.nextInt(glycemicIndexByFoodList.size() - 1));
        ingestion = new Input();
        ingestion.setId(food.getId());
        ingestion.setIndex(food.getGlycemicIndex());
        ingestion.setType("food");
        ingestion.setTimestamp(dateFormat.parse("2016-11-01 16:00:00"));
        todaySchedule.add(ingestion);

        food = glycemicIndexByFoodList.get(random.nextInt(glycemicIndexByFoodList.size() - 1));
        ingestion = new Input();
        ingestion.setId(food.getId());
        ingestion.setIndex(food.getGlycemicIndex());
        ingestion.setType("food");
        ingestion.setTimestamp(dateFormat.parse("2016-11-01 19:30:00"));
        todaySchedule.add(ingestion);

        exercise = exerciseList.get(random.nextInt(exerciseList.size() - 1));
        workout = new Input();
        workout.setId(exercise.getId());
        workout.setIndex(exercise.getExerciseIndex());
        workout.setType("exercise");
        workout.setTimestamp(dateFormat.parse("2016-11-01 21:30:00"));
        todaySchedule.add(workout);

        return todaySchedule;
    }

    public static void main(String[] args) {
        try {
            Leveling leveling = new Leveling();
            leveling.simulateBloodSugarLevels(randomRunSchedule());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
