package com.health.iq.data;

import com.health.iq.controller.Leveling;
import com.health.iq.model.Exercise;
import com.health.iq.model.GlycemicIndexByFood;
import com.health.iq.model.Input;

import javax.xml.crypto.Data;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lsmon on 11/2/16.
 */
public class LoadData {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static DateFormat getDateFormat() {
        return dateFormat;
    }

    private List<Input> randomRunSchedule() throws ParseException {
        Random random = new Random(); // Ideally just create one instance globally


        List<Input> todaySchedule = new LinkedList<>();
        Input startDate = new Input();
        startDate.setIndex(80.0);
        startDate.setTimestamp(dateFormat.parse("2016-11-01 07:00:00"));
        startDate.setType("normalize");
        startDate.setId(0);
        startDate.setName("normalize");
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
        ingestion.setName(food.getName());
        todaySchedule.add(ingestion);

        Exercise exercise = exerciseList.get(random.nextInt(exerciseList.size() - 1));
        Input workout = new Input();
        workout.setId(exercise.getId());
        workout.setIndex(exercise.getExerciseIndex());
        workout.setType("exercise");
        workout.setTimestamp(dateFormat.parse("2016-11-01 08:00:00"));
        workout.setName(exercise.getExercise());
        todaySchedule.add(workout);

        food = glycemicIndexByFoodList.get(random.nextInt(glycemicIndexByFoodList.size() - 1));
        ingestion = new Input();
        ingestion.setId(food.getId());
        ingestion.setIndex(food.getGlycemicIndex());
        ingestion.setType("food");
        ingestion.setTimestamp(dateFormat.parse("2016-11-01 12:30:00"));
        ingestion.setName(food.getName());
        todaySchedule.add(ingestion);

        exercise = exerciseList.get(random.nextInt(exerciseList.size() - 1));
        workout = new Input();
        workout.setId(exercise.getId());
        workout.setIndex(exercise.getExerciseIndex());
        workout.setType("exercise");
        workout.setTimestamp(dateFormat.parse("2016-11-01 13:30:00"));
        workout.setName(exercise.getExercise());
        todaySchedule.add(workout);

        food = glycemicIndexByFoodList.get(random.nextInt(glycemicIndexByFoodList.size() - 1));
        ingestion = new Input();
        ingestion.setId(food.getId());
        ingestion.setIndex(food.getGlycemicIndex());
        ingestion.setType("food");
        ingestion.setTimestamp(dateFormat.parse("2016-11-01 16:00:00"));
        ingestion.setName(food.getName());
        todaySchedule.add(ingestion);

        food = glycemicIndexByFoodList.get(random.nextInt(glycemicIndexByFoodList.size() - 1));
        ingestion = new Input();
        ingestion.setId(food.getId());
        ingestion.setIndex(food.getGlycemicIndex());
        ingestion.setType("food");
        ingestion.setTimestamp(dateFormat.parse("2016-11-01 19:30:00"));
        ingestion.setName(food.getName());
        todaySchedule.add(ingestion);

        exercise = exerciseList.get(random.nextInt(exerciseList.size() - 1));
        workout = new Input();
        workout.setId(exercise.getId());
        workout.setIndex(exercise.getExerciseIndex());
        workout.setType("exercise");
        workout.setTimestamp(dateFormat.parse("2016-11-01 21:30:00"));
        workout.setName(exercise.getExercise());
        todaySchedule.add(workout);

        return todaySchedule;
    }

    private List<Input> fromInputFile(String inputFilename) throws IOException, ParseException {
        InputStream inputStream = new FileInputStream(inputFilename);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<Input> inputList = new LinkedList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            Input input = new Input();
            input.setTimestamp(dateFormat.parse(data[0]));
            input.setIndex(Double.parseDouble(data[1]));
            input.setType(data[2]);
            input.setId(Integer.parseInt(data[3]));
            input.setName(data[4]);
            inputList.add(input);
        }
        return inputList;
    }

    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                System.err.println("arguments needed:");
                System.err.println("blood.sugar.simulator-1.0-SNAPSHOT-jar-with-dependencies.jar random");
                System.err.println("or");
                System.err.println("blood.sugar.simulator-1.0-SNAPSHOT-jar-with-dependencies.jar <path_to_input_file>");
                System.exit(1);
            }
            LoadData loadData = new LoadData();
            Leveling leveling = new Leveling();
            if (args[0].equals("random")) {
                leveling.simulateBloodSugarLevels(loadData.randomRunSchedule());
            } else if (args[0].equals("list")) {
                System.out.println(DataAccess.selectAllExcerciseList());
                System.out.println(DataAccess.selectAllGlycemicIndexByFoodList());
            } else if (args[0].equals("list_to_csv")) {
                PrintWriter exercisePrintWriter = new PrintWriter("./exercise.csv","UTF-8");
                exercisePrintWriter.println(String.format("%s,%s,%s",Exercise.columns.id.name(),Exercise.columns.exercise.name(), Exercise.columns.exercise_index.name()));
                DataAccess.selectAllExcerciseList().forEach(exercise -> {
                    exercisePrintWriter.println(String.format("%s,%s,%s",exercise.getId(),exercise.getExercise(),exercise.getExerciseIndex()));
                });
                exercisePrintWriter.close();

                PrintWriter foodPrintWriter = new PrintWriter("./food_db.csv","UTF-8");
                foodPrintWriter.println(String.format("%s,%s,%s",GlycemicIndexByFood.columns.id.name(),GlycemicIndexByFood.columns.name.name(), GlycemicIndexByFood.columns.glycemic_index.name()));
                DataAccess.selectAllGlycemicIndexByFoodList().forEach(food -> {
                    foodPrintWriter.println(String.format("%s,%s,%s",food.getId(),food.getName(),food.getGlycemicIndex()));
                });
                foodPrintWriter.close();
            } else {
                leveling.simulateBloodSugarLevels(loadData.fromInputFile(args[0]));
            }
            if (!leveling.getBloodSugarLevel().isEmpty() || leveling.getBloodSugarLevel() != null) {
                String outputFilename =
                        (args[0].equals("random"))
                        ? "./output.csv"
                        : args[0].substring(0, args[0].lastIndexOf("/")) + "/output.csv";
                PrintWriter printWriter = new PrintWriter(outputFilename,"UTF-8");
                Map<Date, Double> treeMap = new TreeMap<>((o1, o2) -> o1.compareTo(o2));
                treeMap.putAll(leveling.getBloodSugarLevel());
                treeMap.forEach((date, aDouble) -> {
                    printWriter.println(dateFormat.format(date) + "," + aDouble);
                    DataAccess.insert(date,aDouble);
                });
                printWriter.close();
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
