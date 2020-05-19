package com.videocomm.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.videocomm.customview.R;

/**
 * @author[wengCJ]
 * @version[创建日期，2020/5/13 0013]
 * @function[功能简介]
 **/
public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        int color = typedArray.getColor(R.styleable.FlowLayout_cu_background, 0xffffff);
        typedArray.recycle();

        setBackgroundColor(color);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取布局的宽高模式和大小(根据xml宽高转换来)
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;//控件需要的宽度
        int height = 0;//控件需要的高度
        int lineHeight = 0;//记录行高
        int lineWidth = 0;//记录行宽

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //测量 测量后 getMeasureWidth getMeasuredHeight 才有值
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            //获取margin值 并计算
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childHeight = child.getMeasuredHeight() + params.topMargin + params.bottomMargin;

            //计算控件需要的 宽 和 高
            if (lineWidth + childWidth > widthSize) {
                //需要换行
                height += lineHeight;
                width = Math.max(width, lineWidth);
                lineWidth = childWidth;
            } else {
                //不需要
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }


            //处理最后一行 因为最后一行不会超过这一行的宽度
            if (i == getChildCount() - 1) {
                height += lineHeight;
            }
        }

        //设置尺寸
        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;
        int left = 0;
        int lineWidth = 0;
        int measuredWidth = getMeasuredWidth();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();
            childWidth = childWidth + params.leftMargin + params.rightMargin;
            childHeight = childHeight + params.topMargin + params.bottomMargin;
            //当前行的宽度 + 当前子孩子的宽度 > 控件的宽度
            if (lineWidth + childWidth > measuredWidth) {
                //换行
                top += childHeight;
                left = 0;
                lineWidth = childWidth;
            } else {
                //不需要换行
                lineWidth += childWidth;
            }

            int lc = left + params.leftMargin;
            int tc = top + params.topMargin;
            int rc = lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();

            child.layout(lc, tc, rc, bc);
            left += childWidth;
        }
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
