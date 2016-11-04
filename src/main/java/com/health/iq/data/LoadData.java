package com.health.iq.data;

import com.health.iq.model.BloodSugarLevel;
import com.health.iq.model.Input;

import java.util.Date;

/**
 * Created by lsmon on 11/2/16.
 */
public class LoadData {

    public static void main(String[] args) {
        DataAccess access = DataAccess.getInstance();
        System.out.println(access.selectAllExcerciseList());
        System.out.println(access.selectAllGlycemicIndexByFoodList());
        Input input = new Input();
        input.setTimestamp(new Date());
        input.setType("exercise");
        input.setId(2);
        System.out.println("insert input: " + access.insert(input));
        BloodSugarLevel bloodSugarLevel = new BloodSugarLevel();
        bloodSugarLevel.setTimestamp(new Date());
        bloodSugarLevel.setBloodSugarLevel(80);
        System.out.println("insert blood_sugar_level: " + access.insert(bloodSugarLevel));
        System.out.println(access.selectAllBloodSugarLevel());
        System.out.println(access.selectAllInputs());
    }
}
