package com.gjl.circleindictor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by guojilong on 16/1/21.
 */
public class CircleIndictor extends View {
    private Paint mPaint;
    private float mRadius = 50;
    private float mStrokeWidth = 10;
    private int mColor = Color.parseColor("#f5f5f5");
    private int mProgressColor = Color.parseColor("#bdfc22");
    private int mProgress = 0;
    private Paint mProgressPaint;
    private float mStartAngle = 90;
    private RectF mRectf;
    private int mPadding = 10;
    private int mStartColor = mProgressColor;
    private int mEndColor = mProgressColor;
    private int mStep = 10;
    private String mContent = "";
    private int mTextColot = Color.parseColor("#000000");
    private Paint mTextPaint;
    private float mTextSize = 15;

    public CircleIndictor(Context context) {
        super(context);
        init();

    }


    public CircleIndictor(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public CircleIndictor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);

        mProgressPaint = new Paint();
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mStrokeWidth);
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setAntiAlias(true);

        mTextSize = spToPx(getContext(), (int) mTextSize);
        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColot);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setAntiAlias(true);


    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        invalidate();
    }

    public void setStrokeWidth(int width) {

        mStrokeWidth = width;
        mPaint.setStrokeWidth(mStrokeWidth);
        mProgressPaint.setStrokeWidth(mStrokeWidth);
        invalidate();
    }

    public void setBackGroundCirclrColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    public void setCircleColor(int color) {
        mProgressPaint.setColor(color);
        invalidate();
    }


    public void setStartColor(int color) {

        this.mStartColor = color;
    }

    public void setEndColor(int color) {
        this.mEndColor = color;
    }

    public void setContent(String content) {
        this.mContent = content;
        invalidate();
    }

    public void setTextColor(int color) {
        this.mTextColot = color;
        mTextPaint.setColor(mTextColot);
        invalidate();
    }

    public void setTextSize(int size) {
        mTextSize = spToPx(getContext(), size);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        mRadius = (width > height ? height : width) / 2;
        mRectf = new RectF(mPadding, mPadding, width - mPadding, height - mPadding);
        canvas.drawArc(mRectf, mStartAngle, 360, false, mPaint);

        if (mProgress > 0) {

            int mProgressLength = (int) (360.0f / 100 * mProgress);

            int colors[] = new int[3];
            float positions[] = new float[3];


            colors[0] = mStartColor;
            positions[0] = 0;

            colors[1] = mEndColor;
            positions[1] = 1.0f;


            LinearGradient shader = new LinearGradient(
                    0, 0,
                    0, height,
                    colors,
                    positions,
                    Shader.TileMode.CLAMP);

            mProgressPaint.setShader(shader);

            canvas.drawArc(mRectf, mStartAngle - mProgressLength, mProgressLength, false, mProgressPaint);


        }

        if (!TextUtils.isEmpty(mContent)) {
            float startY = ((height - mTextPaint.ascent() + mTextPaint.descent()) / 2);
            float startX = (width - mTextPaint.measureText(mContent)) / 2;
            canvas.drawText(mContent, startX, startY, mTextPaint);

        }
    }

    public static float spToPx(Context context, int spValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, metrics);
    }
}
