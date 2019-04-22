package com.lieying.socialappstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lieying.comlib.bean.ExploreBean;
import com.lieying.socialappstore.MainApplication;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseViewHolder;
import com.lieying.socialappstore.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.VH>{
    List<ExploreBean> list = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    public ExploreAdapter(Context context , List<ExploreBean> list){
        mLayoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @NonNull
    @Override
    public ExploreAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VH(mLayoutInflater.inflate(R.layout.item_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreAdapter.VH vh, int i) {
        vh.setData(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class VH extends BaseViewHolder {
        ImageView iv_background;
        TextView tv_text;
        public VH(@NonNull View itemView) {
            super(itemView);
            iv_background = itemView.findViewById(R.id.iv_background);
            tv_text = itemView.findViewById(R.id.tv_text);
        }

        @Override
        public <T> void setData(T t) {
            GlideUtils.loadImageForUrl(MainApplication.getInstance().getApplicationContext() , iv_background , ((ExploreBean)t).getImgpath());
            tv_text.setText(((ExploreBean)t).getContent());
        }
    }

}
