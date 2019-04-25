package com.lieying.socialappstore.widget.cardlayout;


public interface OnCardLayoutListener {
    void onSwipe(float dx, float dy);

    void onStateChanged(CardLayoutHelper.State state);
}
