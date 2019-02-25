package com.projects.andreafranco.workforcetracking.ui.component;

import android.view.animation.Interpolator;

public class SpringInterpolator implements Interpolator {

    @Override
    public float getInterpolation(float input) {
        /*
                sin(x * pi) e⁻ˣ
         */
        return  (float)(-Math.sin(Math.PI * (8*input))*Math.pow(Math.PI, -(2*input))*1.2);

    }
}
