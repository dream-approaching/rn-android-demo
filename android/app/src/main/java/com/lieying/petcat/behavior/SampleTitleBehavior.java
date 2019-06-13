package com.lieying.petcat.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/5/30 0030 17:30
 */
public class SampleTitleBehavior extends CoordinatorLayout.Behavior<View> {
    // 列表顶部和title底部重合时，列表的滑动距离。
    private float deltaY;

    public SampleTitleBehavior() {
    }

    public SampleTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof ViewPager;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float dy = dependency.getY() - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        float y = -(dy / deltaY) * child.getHeight();
        if (deltaY == 0) {
            deltaY = dependency.getY() - child.getHeight();
        }
        child.setTranslationY(y);
        if (deltaY == 0) {
            deltaY = dependency.getY() - child.getHeight();
        }
        float alpha = 1 - (dy / deltaY);
        child.setAlpha(alpha);

        return true;
    }


}
