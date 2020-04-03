package com.example.mtask_mobile;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class CutomView extends ConstraintLayout {
    TextView mTextLeft;
    TextView mTextRight;
    ImageView mImgIcon;
    public CutomView(Context context) {
        super(context);
        initView(context);
    }

    public CutomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    public CutomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }

    //初始化UI，可根据业务需求设置默认值。
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.custom_layout, this, true);
        mTextLeft = (TextView) findViewById(R.id.view_order_item_left);
        mTextRight = (TextView) findViewById(R.id.view_order_item_right);
        mImgIcon = (ImageView) findViewById(R.id.go_in_img);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CombineTextReveal);
        String leftStr = mTypedArray.getString(R.styleable.CombineTextReveal_setLeftText);
        if (!TextUtils.isEmpty(leftStr)) {
            mTextLeft.setText(leftStr);
        }
        String rightStr = mTypedArray.getString(R.styleable.CombineTextReveal_setRightText);
        if (!TextUtils.isEmpty(rightStr)) {
            mTextRight.setText(rightStr);
        }
        boolean isVisible = mTypedArray.getBoolean(R.styleable.CombineTextReveal_setVisible ,true);
        if (isVisible) {
            mImgIcon.setVisibility(VISIBLE);
        } else {
            mImgIcon.setVisibility(GONE);
        }
        mTypedArray.recycle();
    }

    //设置标题文字的方法
    public void setmTextLeft(String str) {
        if (!TextUtils.isEmpty(str)) {
            mTextLeft.setText(str);
        }
    }

    public void setmTextRight(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTextRight.setText(title);
        }
    }
    //对左边按钮设置事件的方法
    public void setLeftListener(OnClickListener onClickListener) {
        mImgIcon.setOnClickListener(onClickListener);
    }
}
