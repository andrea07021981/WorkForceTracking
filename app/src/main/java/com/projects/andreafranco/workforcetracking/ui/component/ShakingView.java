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

    private CircleInfoView mCircleView;
    ImageView mImageView;
    Animation mMasterAnimation;
    ValueAnimator mInfoAnimation;

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
        mImageView = (ImageView) findViewById(R.id.shake_imageview);
        mCircleView = (CircleInfoView) findViewById(R.id.info_circleinfoview);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ShakingView, defStyleAttr, 0);

        mImageView.setImageDrawable(context.getDrawable(a.getResourceId(R.styleable.ShakingView_ski_src, -1)));

        mMasterAnimation = new RotateAnimation(0, -20, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.3f);
        mMasterAnimation.setInterpolator(new SpringInterpolator());
        mMasterAnimation.setDuration(500);

        mInfoAnimation = ValueAnimator.ofFloat(1);
        mInfoAnimation.setDuration(300);
        mInfoAnimation.setInterpolator(new OvershootInterpolator());
        mInfoAnimation.setStartDelay(300);
        mInfoAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCircleView.setScaleX((Float) animation.getAnimatedValue());
                mCircleView.setScaleY((Float) animation.getAnimatedValue());
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void shake (int i) {
        mImageView.startAnimation(mMasterAnimation);
        mCircleView.setMessageNum(i);
        mInfoAnimation.start();
    }
}
