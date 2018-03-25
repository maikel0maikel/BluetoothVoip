package com.maikel.blutoothvoip.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.maikel.blutoothvoip.R;

/**
 * Created by maikel on 2018/3/25.
 */

public class SwitchButton extends View implements View.OnTouchListener {

    private Paint mPaint;//
    private Matrix matrix;
    private Bitmap mOFFBitmap;
    private Bitmap mONBitmap;
    private Bitmap mSplitBitmap;

    private Rect mSplitOFFRect;
    private Rect mSplictONRect;

    private boolean isEnable = true;

    private int mToggleX;
    private int mDownX;

    private boolean mSwitchState;

    private boolean isSmooth;

    private OnSwitchChangeListener listener;

    public SwitchButton(Context context) {
        super(context);
        init();
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init() {
        mPaint = new Paint();
        matrix = new Matrix();
        mOFFBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_switch_off);
        mONBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_switch_on);
        mSplitBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_switch_split);
        mSplictONRect = new Rect(0, 0, mONBitmap.getWidth(), mSplitBitmap.getHeight());
        mSplitOFFRect = new Rect(mOFFBitmap.getWidth() - mSplitBitmap.getWidth(), 0, mOFFBitmap.getWidth(), mSplitBitmap.getHeight());
        setOnTouchListener(this);
    }

    public void setChecked(boolean checked) {
        mSwitchState = checked;
        if (mSwitchState) {
            mToggleX = mONBitmap.getWidth();
        } else {
            mToggleX = 0;
        }

    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void setSwitchChangeListener(OnSwitchChangeListener l){
        listener = l;
    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int paddingTop = getPaddingTop();
//        int paddingLeft = getPaddingLeft();
//        int paddingRight = getPaddingRight();
//        int paddingBottom = getPaddingBottom();
//
//        int mesureW = getMeasuredWidth();
//        int mesureH = getMeasuredHeight();
//
//        setMeasuredDimension(mesureW,mesureH);
//
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mToggleX < mONBitmap.getWidth() / 2) {
            canvas.drawBitmap(mOFFBitmap, matrix, mPaint);
        } else {
            canvas.drawBitmap(mONBitmap, matrix, mPaint);
        }
        float x;
        if (isSmooth) {
            if (mToggleX > mONBitmap.getWidth()) {
                x = mONBitmap.getWidth() - mSplitBitmap.getWidth() / 2;
            } else {
                x = mToggleX - mSplitBitmap.getWidth() / 2;
            }
        } else {
            x = mSwitchState ? mSplitOFFRect.left : mSplictONRect.left;
        }

        if (x < 0) {
            x = 0;
        } else if (x > (mONBitmap.getWidth() - mSplitBitmap.getWidth() / 2)) {
            x = mONBitmap.getWidth() - mSplitBitmap.getWidth() / 2;
        }
        canvas.drawBitmap(mSplitBitmap, x, 0, mPaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!isEnable) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() > mONBitmap.getWidth() || event.getY() > mONBitmap.getHeight()) {
                    return false;
                }
                mDownX = (int) event.getX();
                mToggleX = mDownX;
                isSmooth = true;
                break;
            case MotionEvent.ACTION_MOVE:
                mToggleX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isSmooth = false;
                boolean lastSwitchState = mSwitchState;
                mSwitchState = event.getX() > mONBitmap.getWidth() / 2;
                if (listener != null && lastSwitchState != mSwitchState) {
                    listener.onSwitchChange("", mSwitchState);
                }
                break;
        }
        invalidate();
        return true;
    }

    public static interface OnSwitchChangeListener {
        void onSwitchChange(String name, boolean switched);
    }
}
