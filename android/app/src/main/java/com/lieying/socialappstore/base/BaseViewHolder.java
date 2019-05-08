package com.lieying.socialappstore.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    protected View mItemView;
    protected Context mContext;


    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mItemView = itemView;
        mItemView.setClickable(true);
    }

    public abstract  void setData(int position);

    public interface OnHolderCreatedListener {
        void onHolderCreated(BaseViewHolder holder);
    }

}
