package com.projects.andreafranco.workforcetracking.ui.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class CircleInfoView extends View {

    private static final int RED = 0xFFD72D77;
    private static final int WHITE = 0xFFFFFFFF;

    private Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mCirclePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mTextPaintBaseLine;
    private Rect mTargetRect;

    private int mMaxCircleSize;

    private Bitmap mTempBitmap;
    private Canvas mTempCanvas;

    private String mMessage = "1";

    private Paint mTextPaint;

    public CircleInfoView(Context context) {
        this(context, null);
    }

    public CircleInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init () {
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(RED);

        mCirclePaint2.setStyle(Paint.Style.STROKE);
        mCirclePaint2.setStrokeWidth(2);
        mCirclePaint2.setColor(WHITE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaxCircleSize = w / 2;
        mTempBitmap = Bitmap.createBitmap(getWidth(), getWidth(), Bitmap.Config.ARGB_8888);
        mTempCanvas = new Canvas(mTempBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTempCanvas.drawCircle(mMaxCircleSize, mMaxCircleSize, mMaxCircleSize - 4, mCirclePaint);


        mTempCanvas.drawCircle(mMaxCircleSize, mMaxCircleSize, mMaxCircleSize - 4, mCirclePaint2);

        mTextPaint = new TextPaint();
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(WHITE);
        mTextPaint.setTextSize(mMaxCircleSize *8/5);
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        mTargetRect = new Rect(0, 0, 2* mMaxCircleSize,2* mMaxCircleSize);
        mTextPaintBaseLine = (mTargetRect.bottom + mTargetRect.top - fontMetricsInt.bottom - fontMetricsInt.top) / 2;
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTempCanvas.drawText(""+ mMessage, mTargetRect.centerX(), mTextPaintBaseLine, mTextPaint);

        canvas.drawBitmap(mTempBitmap, 0, 0, null);

    }

    public void setMessageNum (int messageNum) {
        if (messageNum > 0 && messageNum < 10) {
            mMessage = messageNum+"";
        } else {
            mMessage = "∵";
        }
        invalidate();
    }
}
