package com.lieying.comlib.utils;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by ${liyi} on 2018/1/27.
 */

public class ViewUtils {
    /**
     * 特殊情况下需要在 getViewTreeObserver().addOnGlobalLayoutListener里面调用
     *
     * @param tv
     * @param content
     * @param maxLine
     * @return
     */
    public static String getSubString(TextView tv, String content, float maxLine) {
        float width = tv.getPaint().measureText(content);
        float tvWidth = tv.getMeasuredWidth();
        if (width / tvWidth > maxLine) {
            int end = (int) ((float) content.length() * (maxLine - 0.8) / (width / tvWidth));
            return content.substring(0, end) + "...";
        }
        return content;
    }

    /***
     * 获取行数
     *
     * @param tv
     * @param content
     * @return
     */
    public static int getContentLine(TextView tv, String content) {
        float width = tv.getPaint().measureText(content);
        //这里只是为了方便，用屏幕宽度代替了textview控件宽度，如果需要精准控制，可以换成控件宽度
        float tvWidth = tv.getMeasuredWidth();
        return (int) (width / tvWidth + 1);
    }


    public static ViewTreeObserver.OnGlobalLayoutListener getKeyboardListener(final View root, final View btmView) {
        return getKeyboardListener(root, btmView, true);
    }

    public static ViewTreeObserver.OnGlobalLayoutListener getKeyboardListener(final View root, final View btmView, final boolean scrollBtm) {
        return getKeyboardListener(root, btmView, scrollBtm, null);
    }

    public static ViewTreeObserver.OnGlobalLayoutListener getKeyboardListener(final View root, final View btmView, final OnKeyBoardChangeListener listener) {
        return getKeyboardListener(root, btmView, true, listener);
    }

    /**
     * @param root      最外层布局，需要调整的布局
     * @param btmView   最底部的view
     * @param scrollBtm 是否把ScrollView的内容滑到底部
     * @param listener  键盘监听
     * @return
     */
    public static ViewTreeObserver.OnGlobalLayoutListener getKeyboardListener(final View root, final View btmView, final boolean scrollBtm, final OnKeyBoardChangeListener listener) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                root.getRootView().getWindowVisibleDisplayFrame(rect);
                //获取root在窗体的不可视区域高度(被其他View遮挡的区域高度)
                int rootInvisibleHeight = root.getRootView().getHeight() - rect.bottom;
                //若不可视区域高度大于 h/4，则键盘显示
                if (rootInvisibleHeight > root.getRootView().getHeight() / 4) {
                    int[] location;
                    if (btmView.getTag() == null) {//记录原始位置坐标
                        location = new int[2];
                        //获取scrollToView在窗体的坐标
                        btmView.getLocationInWindow(location);
                        btmView.setTag(location);
                    } else {
                        location = (int[]) btmView.getTag();
                    }

                    //计算root滚动高度，使scrollToView在可见区域
                    final int srollHeight = (location[1] + btmView.getHeight()) - rect.bottom;
                    if (srollHeight != 0) {
                        root.scrollTo(0, srollHeight);
                        if (scrollBtm) {
                            if (root instanceof ScrollView) {
                                root.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ((ScrollView) root).fullScroll(View.FOCUS_DOWN);
                                    }
                                });
                            }
                        }
                        if (listener != null) {
                            listener.onKeyBoardShow();
                        }
                    }
                } else {
                    //键盘隐藏
                    root.scrollTo(0, 0);
                    if (listener != null) {
                        listener.onKeyBoardHide();
                    }
                }
            }
        };
    }

    //移除布局监听
    public static void removeGlobalLayoutListener(View root, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (root != null && listener != null) {
            //最低api 16
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                root.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
            } else {
                root.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
            }
        }
    }

    public interface OnKeyBoardChangeListener {
        public void onKeyBoardShow();

        public void onKeyBoardHide();
    }
}
