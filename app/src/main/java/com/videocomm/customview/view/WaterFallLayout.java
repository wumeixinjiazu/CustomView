package com.videocomm.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author[wengCJ]
 * @version[创建日期，2020/5/19 0019]
 * @function[功能简介]
 **/
public class WaterFallLayout extends ViewGroup {

    int columns = 3;//每一行三列
    int hSpace = 20;//横向间隔
    int vSpace = 20;//竖向间隔
    int childWidth = 0;//子孩子的宽度
    int top[];

    public WaterFallLayout(Context context) {
        this(context, null);
    }

    public WaterFallLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterFallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        top = new int[columns];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(this.getClass().getSimpleName(), "onMeasure");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        childWidth = (getMeasuredWidth() - (columns - 1) * hSpace) / columns;

        //计算控件宽度
        int wrapWidth;
        int childCount = getChildCount();
        if (childCount <= columns) {
            wrapWidth = childCount * childWidth + childCount * hSpace;
        } else {
            wrapWidth = widthSize;
        }

        //计算控件高度
        clearTopArr();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
//            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            int childHeight = child.getMeasuredHeight() * childWidth / child.getMeasuredWidth();
            int column = getMinHeightColumn();
            top[column] += childHeight + vSpace;
        }
        int wrapHeight;
        wrapHeight = getMaxHeight();

        setMeasuredDimension(widthMode == MeasureSpec.AT_MOST  ?  wrapWidth : widthSize, wrapHeight);
    }

    /**
     * @return 返回Top数组最大的队列
     */
    private int getMaxHeight() {
        int maxHeight = 0;
        for (int i = 0; i < columns; i++) {
            if (top[i] > maxHeight) {
                maxHeight = top[i];
            }
        }
        return maxHeight;
    }

    /**
     * @return 返回最小的高度列
     */
    private int getMinHeightColumn() {
        int minColumn = 0;
        for (int i = 0; i < columns; i++) {
            if (top[i] < top[minColumn]) {
                minColumn = i;
            }
        }
        return minColumn;
    }

    /**
     * 清除Top数组
     */
    private void clearTopArr() {
        for (int i = 0; i < columns; i++) {
            top[i] = 0;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(this.getClass().getSimpleName(), "onLayout");
        int childCount = getChildCount();
        clearTopArr();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            int childHeight = child.getMeasuredHeight() * childWidth / child.getMeasuredWidth();
            int minColum = getMinHeightColumn();
            int tleft = minColum * (childWidth + hSpace);
            int ttop = top[minColum];
            int tright = tleft + childWidth;
            int tbottom = ttop + childHeight;
            top[minColum] += vSpace + childHeight;
            child.layout(tleft, ttop, tright, tbottom);
        }
    }

}
