package com.lieying.petcat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lieying.comlib.utils.ScreenUtils;
import com.lieying.petcat.R;

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

public class NoDataTipsLayout extends LinearLayout {
    private Context mContext;
    private TextView mTvTips;
    private TextView mTvBnt;
    private View mView;

    public NoDataTipsLayout(Context context) {
        super(context);
        init(context);
    }

    public NoDataTipsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public NoDataTipsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttr(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        mContext = context;
        mView = LayoutInflater.from(mContext).inflate(R.layout.view_comment_no_data, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mView, params);
        mTvTips = findViewById(R.id.tv_no_data_tip);
        mTvBnt = findViewById(R.id.tv_no_data_bnt);
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NoDataTips, defStyleAttr, 0);

        //===============提示文字=============
        String content = typedArray.getString(R.styleable.NoDataTips_tip_text);
        if (!TextUtils.isEmpty(content)) {
            setTipsContent(content);
        }
        int value = typedArray.getColor(R.styleable.NoDataTips_tip_text_color, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            mTvTips.setTextColor(value);
        }
        value = typedArray.getDimensionPixelSize(R.styleable.NoDataTips_tip_text_size, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            mTvTips.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ScreenUtils.dimensionDp2Dip(mContext, value));
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mTvTips.getLayoutParams();
        value = typedArray.getDimensionPixelSize(R.styleable.NoDataTips_tip_text_margin_top, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            params.topMargin = value;
        }
        mTvTips.setLayoutParams(params);

        //===============中间标题 start=============
        content = typedArray.getString(R.styleable.NoDataTips_bnt_text);
        if (!TextUtils.isEmpty(content)) {
            setBtnContent(content);
        }else{
            mTvBnt.setVisibility(GONE);
        }

        value = typedArray.getColor(R.styleable.NoDataTips_bnt_text_color, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            mTvBnt.setTextColor(value);
        }

        value = typedArray.getDimensionPixelSize(R.styleable.NoDataTips_bnt_text_size, Integer.MIN_VALUE);
        if (value != Integer.MIN_VALUE) {
            mTvBnt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ScreenUtils.dimensionDp2Dip(mContext, value));
        }
        //===============中间标题 end=============

        typedArray.recycle();
    }



    public void setBtnContent(CharSequence title) {
        mTvBnt.setText(title);
    }


    public void setTipsContent(CharSequence tip) {
        mTvTips.setText(tip);
    }





}
