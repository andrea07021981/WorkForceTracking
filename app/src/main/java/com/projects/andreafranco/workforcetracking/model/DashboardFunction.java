package com.projects.andreafranco.workforcetracking.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DashboardFunction {

    private final int drawable;
    @FunctionType private final int functionType;

    public int getFunctionType() {
        return functionType;
    }

    public int getDrawable() {
        return drawable;
    }

    @Retention(RetentionPolicy.SOURCE)
    // Enumerate valid values for this interface
    @IntDef({FUNCTION_TEAM, FUNCTION_CALENDAR, FUNCTION_MATERIAL})
    // Create an interface for validating int types
    public @interface FunctionType {}

    // Declare the constants
    public static final int FUNCTION_TEAM = 1;
    public static final int FUNCTION_CALENDAR = 2;
    public static final int FUNCTION_MATERIAL = 3;

    public DashboardFunction(int drawable, @FunctionType int functionType) {
        this.drawable = drawable;
        this.functionType = functionType;
    }
}
