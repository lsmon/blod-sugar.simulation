package com.health.iq.controller;

import com.health.iq.model.BloodSugarLevel;
import com.health.iq.model.Input;

import java.util.Date;

/**
 * Created by lsmon on 11/3/16.
 */
public class Leveling {
    private static final int GLYCATION_THRESHOLD = 150;

    public static BloodSugarLevel currentBloodSugarLevel;

    /**
     * Blood sugar starts at 80 at the beginning of the day.
     * If neither food nor exercise is affecting your blood sugar (it has been more than 1 or 2 hours),
     * it will approach 80 linearly at a rate of 1 per minute.
     */
    public void normalization(Date endTimestamp) {
        if (currentBloodSugarLevel == null) {
            currentBloodSugarLevel = new BloodSugarLevel();
        }
        if (currentBloodSugarLevel.getBloodSugarLevel() > BloodSugarLevel.MIN_BLOOD_SUGAR_LEVEL) {
            int diffMinutes = (int)(endTimestamp.getTime() - currentBloodSugarLevel.getTimestamp().getTime()) / (60 * 1000) % 60;
            if (diffMinutes > 60) {
                double sugarBloodLevel = currentBloodSugarLevel.getBloodSugarLevel() - diffMinutes;
                currentBloodSugarLevel.setBloodSugarLevel((sugarBloodLevel<=BloodSugarLevel.MIN_BLOOD_SUGAR_LEVEL)?BloodSugarLevel.MIN_BLOOD_SUGAR_LEVEL:sugarBloodLevel);
                currentBloodSugarLevel.setTimestamp(endTimestamp);
                currentBloodSugarLevel.setCurrentStatus("normalization");
            }
        }
    }

    /**
     * In our model, eating food will increase blood sugar linearly for two hours.
     * The rate of increase depends on the food as defined in a database that we will provide.
     *
     * @param input
     */
    public void foodIngestion(Input input) {
        if (currentBloodSugarLevel == null) return;
        if (input.getType().equals("food")) {
            int diffMinutes = (int)(input.getTimestamp().getTime() - currentBloodSugarLevel.getTimestamp().getTime()) / (60 * 1000) % 60;
            if (diffMinutes < 120) {
                double rate = calculateLinearRate(input.getIndex(),120);
                double sugarBloodLevel = currentBloodSugarLevel.getBloodSugarLevel() + (diffMinutes * rate);
                currentBloodSugarLevel.setCurrentStatus("food");
                currentBloodSugarLevel.setBloodSugarLevel(sugarBloodLevel);
                currentBloodSugarLevel.setTimestamp(input.getTimestamp());
            }
        }
    }

    /**
     * Exercise decreases blood sugar linearly for one hour.
     *
     * @param input
     */
    public void exercise(Input input) {
        if (currentBloodSugarLevel == null) return;
        if (input.getType().equals("food")) {
            int diffMinutes = (int)(input.getTimestamp().getTime() - currentBloodSugarLevel.getTimestamp().getTime()) / (60 * 1000) % 60;
            if (diffMinutes < 60) {
                double rate = calculateLinearRate(input.getIndex(),120);
                double sugarBloodLevel = currentBloodSugarLevel.getBloodSugarLevel() - (diffMinutes * rate);
                currentBloodSugarLevel.setCurrentStatus("food");
                currentBloodSugarLevel.setBloodSugarLevel(sugarBloodLevel);
                currentBloodSugarLevel.setTimestamp(input.getTimestamp());
            }
        }
    }

    /**
     * For every minute your blood sugar stays above 150, increment “glycation” by 1.
     * This is a measure of how much crystallized sugar is accumulating in your blood stream which increases
     * heart disease risk.
     */
    public void glycation(Date endTimestamp) {
        if (currentBloodSugarLevel != null) {
            if (currentBloodSugarLevel.getBloodSugarLevel() > GLYCATION_THRESHOLD) {
                int diffMinutes = (int)(endTimestamp.getTime() - currentBloodSugarLevel.getTimestamp().getTime()) / (60 * 1000) % 60;
                currentBloodSugarLevel.setBloodSugarLevel(currentBloodSugarLevel.getBloodSugarLevel() + diffMinutes);
                currentBloodSugarLevel.setTimestamp(endTimestamp);
                currentBloodSugarLevel.setCurrentStatus("glycation");
            }
        }
    }

    private double calculateLinearRate(double sugarIndex, int minutesPeriod) {
        return sugarIndex / minutesPeriod;
    }

}
