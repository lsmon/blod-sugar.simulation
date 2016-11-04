package com.health.iq.controller;

import com.health.iq.model.BloodSugarLevel;
import com.health.iq.model.Input;

/**
 * Created by lsmon on 11/3/16.
 */
public interface ILeveling {
    BloodSugarLevel normilize();
    BloodSugarLevel foodIngestion(Input input);
    BloodSugarLevel exercise(Input input);
    BloodSugarLevel glycation();
}
