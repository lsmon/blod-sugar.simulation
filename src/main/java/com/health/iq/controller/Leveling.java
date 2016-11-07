package com.health.iq.controller;

import com.health.iq.data.DataAccess;
import com.health.iq.data.LoadData;
import com.health.iq.model.Input;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Created by lsmon on 11/3/16.
 */
public class Leveling {
    private double calculateLinearRate(double sugarIndex, int minutesPeriod) {
        return sugarIndex / minutesPeriod;
    }

    public Map<Date, Double> getBloodSugarLevel() {
        return bloodSugarLevel;
    }

    public void simulateBloodSugarLevels(List<Input> inputList) {
        for (Input input : inputList) {
            System.out.println(input.toString());
            DataAccess.insert(input);
            if (input.getType().equals("normalize")){
                normalize(input);
            } else if (input.getType().equals("exercise")) {
                doWorkout(input);
            } else if (input.getType().equals("food")) {
                eat(input);
            }
        }
    }

    private Map<Date,Double> bloodSugarLevel = new Hashtable<>();

    private long getEndTimestamp(Input input, int minutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(input.getTimestamp());
        c.add(Calendar.MINUTE, minutes);
        return c.getTime().getTime();
    }

    /**
     * Blood sugar starts at 80 at the beginning of the day.
     * If neither food nor exercise is affecting your blood sugar (it has been more than 1 or 2 hours),
     * it will approach 80 linearly at a rate of 1 per minute.
     */
    private void normalize(Input input) {
        long endTimestamp = getEndTimestamp(input, 60);
        for (long timestamp = input.getTimestamp().getTime(); timestamp < endTimestamp; timestamp += (60*1000)){
            Date current = new Date(timestamp);
            if (bloodSugarLevel.isEmpty()){
                bloodSugarLevel.put(current, 80.0);
                print(current,80.0);
            } else {
                Date prevKey = new Date(timestamp - 60*1000);
                double sugarLevel = bloodSugarLevel.containsKey(prevKey)?bloodSugarLevel.get(new Date(timestamp - 60*1000)):getLastCalculatedBloodSugarLevel(prevKey);
                if (sugarLevel < 80.0) {
                    bloodSugarLevel.put(current,sugarLevel + 1);
                    print(current,sugarLevel + 1);
                } else if (sugarLevel > 80.0) {
                    bloodSugarLevel.put(current,sugarLevel - 1);
                    print(current,sugarLevel - 1);
                } else {
                    bloodSugarLevel.put(current,input.getIndex());
                    print(current,input.getIndex());
                }
            }
        }
    }

    /**
     * Exercise decreases blood sugar linearly for one hour.
     *
     * @param input
     */
    private void doWorkout(Input input) {
        long endTimestamp = getEndTimestamp(input, 60);
        double rate = calculateLinearRate(input.getIndex(), (int) ((endTimestamp - input.getTimestamp().getTime()) / (60*1000)));
        for (long timestamp = input.getTimestamp().getTime(); timestamp < endTimestamp; timestamp += (60*1000)) {
            Date current = new Date(timestamp);
            decreaseBloodSugar(rate, timestamp, current);
        }
    }

    /**
     * In our model, eating food will increase blood sugar linearly for two hours.
     * The rate of increase depends on the food as defined in a database that we will provide.
     *
     * @param input
     */
    private void eat(Input input) {
        long endTimestamp = getEndTimestamp(input, 120);
        double rate = calculateLinearRate(input.getIndex(), (int) ((endTimestamp - input.getTimestamp().getTime()) / (60*1000)));
        for (long timestamp = input.getTimestamp().getTime(); timestamp < endTimestamp; timestamp += (60*1000)) {
            Date current = new Date(timestamp);
            increaseBloodSugar(rate, timestamp, current);
        }
    }

    private void decreaseBloodSugar(double rate, long timestamp, Date current) {
        if (bloodSugarLevel.isEmpty()){
            bloodSugarLevel.put(current, 80.0);
            print(current,80.0);
        } else {
            Date prevKey = new Date(timestamp - 60*1000);
            double sugarLevel = bloodSugarLevel.containsKey(prevKey)?bloodSugarLevel.get(new Date(timestamp - 60*1000)):getLastCalculatedBloodSugarLevel(prevKey);
            bloodSugarLevel.put(current,sugarLevel - rate);
            print(current,sugarLevel - rate);
        }
    }

    private void increaseBloodSugar(double rate, long timestamp, Date current) {
        if (bloodSugarLevel.isEmpty()){
            bloodSugarLevel.put(current, 80.0);
        } else {
            Date prevKey = new Date(timestamp - 60*1000);
            double sugarLevel = bloodSugarLevel.containsKey(prevKey)?bloodSugarLevel.get(new Date(timestamp - 60*1000)):getLastCalculatedBloodSugarLevel(prevKey);
            bloodSugarLevel.put(current,sugarLevel + rate);
            print(current,sugarLevel + rate);
        }
    }

    /**
     * Extra normalization and glycation
     * For every minute your blood sugar stays above 150, increment “glycation” by 1.
     * This is a measure of how much crystallized sugar is accumulating in your blood stream which increases
     * heart disease risk.
     */
    private double getLastCalculatedBloodSugarLevel(Date limitKey) {
        Map<Date, Double> treeMap = new TreeMap<>((o1, o2) -> o2.compareTo(o1));
        treeMap.putAll(bloodSugarLevel);
        double lastBloodSugarLevel = 0.0;
        Date lastKey = limitKey;
        for (Date ts: treeMap.keySet()) {
            lastBloodSugarLevel = treeMap.get(ts);
            lastKey = ts;
            break;
        }
        int diffInHours = (int)(limitKey.getTime() - lastKey.getTime()) / (60*60*1000) % 60;
        boolean needNormalize = (diffInHours >= 1 || diffInHours <= 2)? true:false;

        for (long timestamp = lastKey.getTime(); timestamp < limitKey.getTime(); timestamp += (60*1000)) {
            if (needNormalize) {
                if (lastBloodSugarLevel < 80.0) {
                    bloodSugarLevel.put(new Date(timestamp),++lastBloodSugarLevel);
                    print(new Date(timestamp),lastBloodSugarLevel);
                } else if (lastBloodSugarLevel > 80.0) {
                    bloodSugarLevel.put(new Date(timestamp),--lastBloodSugarLevel);
                    print(new Date(timestamp),lastBloodSugarLevel);
                }
            } else if (lastBloodSugarLevel > 150) {
                bloodSugarLevel.put(new Date(timestamp), lastBloodSugarLevel++);
                print(new Date(timestamp),lastBloodSugarLevel);
            }
        }
        return lastBloodSugarLevel;
    }

    private void print(Date current, double sugarLevel) {
        JSONObject object = new JSONObject();
        object.put(LoadData.getDateFormat().format(current),sugarLevel);
        System.out.println(object.toJSONString());
    }
}
