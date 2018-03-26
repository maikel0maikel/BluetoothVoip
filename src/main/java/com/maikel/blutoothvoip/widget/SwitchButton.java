package com.maikel.blutoothvoip.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

    public void setSwitchChangeListener(OnSwitchChangeListener l) {
        listener = l;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec)-paddingLeft-paddingRight;
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec)-paddingTop-paddingBottom;

        if (widthMeasureMode == MeasureSpec.AT_MOST && heightMeasureMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mOFFBitmap.getWidth(),mOFFBitmap.getHeight());
        }else if (widthMeasureMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(mOFFBitmap.getWidth(),heightSize);
        }else if (heightMeasureMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,mOFFBitmap.getHeight());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        if (mToggleX < mONBitmap.getWidth() / 2) {
            canvas.drawBitmap(mOFFBitmap,paddingLeft,paddingTop, mPaint);
        } else {
            canvas.drawBitmap(mONBitmap,paddingLeft, paddingTop, mPaint);
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
        canvas.drawBitmap(mSplitBitmap, x,paddingTop, mPaint);
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
