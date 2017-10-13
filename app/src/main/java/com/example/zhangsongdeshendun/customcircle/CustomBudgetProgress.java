package com.example.zhangsongdeshendun.customcircle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by zhangsongdeshendun on 17/10/13.
 */

public class CustomBudgetProgress extends ProgressBar {

    //自定义属性
    private static final int DEFAULT_TITLE_COLOR = 0xffffffff;
    private static final int DEFAULT_TITLE_SIZE = 10;//SP
    private static final int DEFAULT_SUB_COLOR = 0xffffffff;
    private static final int DEFAULT_SUB_SIZE = 14;//SP;
    private static final int DEFAULT_BACKGROUND_COLOR = 0XFFe84554;
    private static final int DEFAULT_BACKGROUND_RADIUS = 30;//DP;
    private static final int DEFAULT_BACKGROUND_DEFAULT_COLOR = 0xffd1cfcf;


    private int mTitleColor = DEFAULT_TITLE_COLOR;
    private int mTitleSize = sp2px(DEFAULT_TITLE_SIZE);
    private int mSubColor = DEFAULT_SUB_COLOR;
    private int mSubSize = sp2px(DEFAULT_SUB_SIZE);
    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
    private int mBackgroundRadius = dp2px(DEFAULT_BACKGROUND_RADIUS);
    private int mBackgrounDefaultColor = DEFAULT_BACKGROUND_DEFAULT_COLOR;


    private int mRealWidth;

    private Paint mPaint;

    private String mRestMonty = "3000";


    public String getmRestMonty() {
        return mRestMonty;
    }


    public void setmRestMonty(String mRestMonty) {
        this.mRestMonty = mRestMonty;

    }


    public CustomBudgetProgress(Context context) {
        this(context, null);
    }


    public CustomBudgetProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomBudgetProgress(Context context, AttributeSet attrs,
                                int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    private void init(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CustomBudgetProgress);
        mTitleColor = ta.getColor(R.styleable.CustomBudgetProgress_budget_title_color, mTitleColor);
        mTitleSize = ta.getDimensionPixelOffset(R.styleable.CustomBudgetProgress_budget_title_size, mTitleSize);
        mSubColor = ta.getColor(R.styleable.CustomBudgetProgress_budget_sub_color, mSubColor);
        mSubSize = ta.getDimensionPixelOffset(R.styleable.CustomBudgetProgress_budget_sub_size, mSubSize);
        mBackgroundColor = ta.getColor(R.styleable.CustomBudgetProgress_budget_background_color, mBackgroundColor);
        mBackgroundRadius = ta.getDimensionPixelOffset(R.styleable.CustomBudgetProgress_budget_background_radius, mBackgroundRadius);
        mBackgrounDefaultColor = ta.getDimensionPixelOffset(R.styleable.CustomBudgetProgress_budget_background_default_color, mBackgrounDefaultColor);

        ta.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Style.FILL);

    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expect = mBackgroundRadius * 2 + getPaddingLeft() + getPaddingRight();
        int hight = resolveSize(expect, heightMeasureSpec);
        int width = resolveSize(expect, widthMeasureSpec);
        mRealWidth = Math.min(hight, width);
        mBackgroundRadius = (mRealWidth - getPaddingLeft() - getPaddingRight()) / 2;
        setMeasuredDimension(mRealWidth, mRealWidth);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制最下面的圆
        mPaint.setColor(mBackgrounDefaultColor);
        canvas.drawCircle(mBackgroundRadius, mBackgroundRadius, mBackgroundRadius, mPaint);
//		绘制下面的扇形
        canvas.save();
        float percent = getProgress() * 1.0f / getMax();
        float angle = percent * 360;
        mPaint.setColor(mBackgroundColor);
        canvas.translate(mBackgroundRadius, mBackgroundRadius);
        canvas.rotate((180 - angle) / 2);
        canvas.drawArc(new RectF(-mBackgroundRadius, -mBackgroundRadius, mBackgroundRadius, mBackgroundRadius), 0, angle, false, mPaint);
        canvas.restore();
        //绘制本月剩余
        String text = "松的demo";
        mPaint.setTextSize(mTitleSize);
        mPaint.setColor(mTitleColor);
        int textWidth = (int) mPaint.measureText(text);
        int textHeiht = (int) ((mPaint.ascent() + mPaint.descent()) / 2);
        mPaint.setTextAlign(Align.CENTER);
        canvas.drawText(text, mBackgroundRadius, mBackgroundRadius - textHeiht - 7, mPaint);
        //绘制本月剩余金额
        String textSub = mRestMonty;
        mPaint.setTextSize(mSubSize);
        mPaint.setColor(mSubColor);
        int textSubWidth = (int) mPaint.measureText(textSub);
        int textSubHeiht = (int) ((mPaint.ascent() + mPaint.descent()) / 2);
        mPaint.setTextAlign(Align.CENTER);
        canvas.drawText(textSub, mBackgroundRadius, mBackgroundRadius - textSubHeiht * 4, mPaint);


    }

    //dp转换成px
    @SuppressLint("DrawAllocation")
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    //sp转换成px
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }
}
