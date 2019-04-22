package com.lieying.socialappstore.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lieying.comlib.utils.ScreenUtils;
import com.lieying.socialappstore.R;

/**
 * Created by liyi
 *
 * @see <com.comlib.ui.view.TitleView
 * android:id="@+id/view_title"
 * android:layout_width="match_parent"
 * android:layout_height="@dimen/dp_44"
 * app:back_text="@string/app_back"
 * app:back_text_color="@color/black_66"
 * app:back_text_size="@dimen/dp_14"
 * app:line_bg_color="@color/line_bg_main"
 * app:title="@string/app_rs_pwd"
 * app:title_color="@color/black_33"
 * app:title_size="@dimen/dp_18" />
 */

public class TitleView extends LinearLayout {
    private Context mContext;
    private View mView;
    private ImageView mIvBack;
    private TextView mTvBack;
    private TextView mTvTitle;
    private TextView mTvRight;

    public TitleView(Context context) {
        super(context);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttr(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        mContext = context;
        mView = LayoutInflater.from(mContext).inflate(R.layout.view_common_back_title, null);
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mView, params);

        mIvBack = findViewById(R.id.iv_back);
        mTvBack = findViewById(R.id.tv_back);
        mTvTitle = findViewById(R.id.tv_title);
        mTvRight = findViewById(R.id.tv_right);
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleView, defStyleAttr, 0);
        int value = typedArray.getResourceId(R.styleable.TitleView_back_image, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            setBackImage(value);
        }

        //===============左边字体 start=============
        String content = typedArray.getString(R.styleable.TitleView_back_text);
        if (!TextUtils.isEmpty(content)) {
            setBackContent(content);
        }

        value = typedArray.getColor(R.styleable.TitleView_back_text_color, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            mTvBack.setTextColor(value);
        }

        value = typedArray.getDimensionPixelSize(R.styleable.TitleView_back_text_size, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            mTvBack.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ScreenUtils.dimensionDp2Dip(mContext, value));
        }
        //===============左边字体 end=============

        //===============中间标题 start=============
        content = typedArray.getString(R.styleable.TitleView_title);
        if (!TextUtils.isEmpty(content)) {
            setTitle(content);
        }

        value = typedArray.getColor(R.styleable.TitleView_title_color, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            mTvTitle.setTextColor(value);
        }

        value = typedArray.getDimensionPixelSize(R.styleable.TitleView_title_size, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ScreenUtils.dimensionDp2Dip(mContext, value));
        }
        //===============中间标题 end=============

        //===============右边字体 start=============
        content = typedArray.getString(R.styleable.TitleView_right_text);
        if (!TextUtils.isEmpty(content)) {
            setRightContent(content);
        }

        value = typedArray.getColor(R.styleable.TitleView_right_text_color, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            mTvRight.setTextColor(value);
        }

        value = typedArray.getDimensionPixelSize(R.styleable.TitleView_right_text_size, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ScreenUtils.dimensionDp2Dip(mContext, value));
        }
        //===============右边字体 end=============

        value = typedArray.getColor(R.styleable.TitleView_line_bg_color, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            setLineBgColor(value);
        }

        RelativeLayout topRl = mView.findViewById(R.id.rl_top);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) topRl.getLayoutParams();
        value = typedArray.getDimensionPixelSize(R.styleable.TitleView_left_spacing, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            params.leftMargin = value;
        }
        value = typedArray.getDimensionPixelSize(R.styleable.TitleView_right_spacing, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            params.rightMargin = value;
        }
        topRl.setLayoutParams(params);
        typedArray.recycle();
    }


    public void setBackImage(int resId) {
        mIvBack.setImageResource(resId);
        mIvBack.setVisibility(VISIBLE);
    }

    public void setTitle(CharSequence title) {
        mTvTitle.setText(title);
    }

    public void setRightContent(CharSequence title) {
        mTvRight.setText(title);
    }

    public void setBackContent(CharSequence tip) {
        mTvBack.setText(tip);
    }

    /**
     * @param enable
     * @param colorId
     */
    public void setRightEnable(boolean enable, int colorId) {
        mTvRight.setEnabled(enable);
        mTvRight.setTextColor(ContextCompat.getColor(mContext, colorId));
    }

    public View getRightView() {
        return mTvRight;
    }
    public View getLeftView() {
        return mIvBack;
    }
    //右边点击事件 R.id.tv_right
    public void setRightOnClickListener(OnClickListener listener) {
        mTvRight.setOnClickListener(listener);
    }

    //返回事件 事件区域 --> 返回图标 R.id.iv_back +返回提示 R.id.tv_back
    public void setBackClickListener(OnClickListener listener) {
        findViewById(R.id.iv_back).setOnClickListener(listener);
        findViewById(R.id.tv_back).setOnClickListener(listener);
    }

    public void setClickBackFinish(final Activity activity) {
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        };
        setBackClickListener(listener);
    }

    private void setLineBgColor(int color) {
        findViewById(R.id.view_line).setBackgroundColor(color);
        findViewById(R.id.view_line).setVisibility(VISIBLE);
    }

}
