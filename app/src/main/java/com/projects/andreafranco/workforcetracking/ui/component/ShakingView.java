package com.projects.andreafranco.workforcetracking.ui.component;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.projects.andreafranco.workforcetracking.R;

public class ShakingView extends FrameLayout {

    private CircleInfoView circleView;
    ImageView imageView;
    Animation masterAnimation, animation2;
    ValueAnimator infoAnimation;

    public ShakingView(Context context) {
        this(context, null);
    }

    public ShakingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShakingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(getContext()).inflate(R.layout.shaking_view, this, true);
        imageView = (ImageView) findViewById(R.id.shake_imageview);
        circleView = (CircleInfoView) findViewById(R.id.info_circleinfoview);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShakingView, defStyleAttr, 0);

        imageView.setImageDrawable(context.getDrawable(a.getResourceId(R.styleable.ShakingView_ski_src, -1)));

        masterAnimation = new RotateAnimation(0, -20, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.3f);
        masterAnimation.setInterpolator(new SpringInterpolator());
        masterAnimation.setDuration(500);

        infoAnimation = ValueAnimator.ofFloat(1);
        infoAnimation.setDuration(300);
        infoAnimation.setInterpolator(new OvershootInterpolator());
        infoAnimation.setStartDelay(300);
        infoAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleView.setScaleX((Float) animation.getAnimatedValue());
                circleView.setScaleY((Float) animation.getAnimatedValue());
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void shake (int i) {
        imageView.startAnimation(masterAnimation);
        circleView.setMessageNum(i);
        infoAnimation.start();
    }
}
