package com.lieying.socialappstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lieying.comlib.bean.ExploreBean;
import com.lieying.socialappstore.MainApplication;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.base.BaseViewHolder;
import com.lieying.socialappstore.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.VH> {
    List<ExploreBean> list = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private OnCardClickListener cardClickListener;

    public ExploreAdapter(Context context, List<ExploreBean> list, OnCardClickListener cardClickListener) {
        this.cardClickListener = cardClickListener;
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
        TextView mTvItemTitle;
        TextView mTvItemContent;
        ImageView mIVback;
        ImageView mIVComment;
        ImageView mIVCollection;

        public VH(@NonNull View itemView) {
            super(itemView);
            iv_background = itemView.findViewById(R.id.iv_item_card_background);
            mTvItemContent = itemView.findViewById(R.id.tv_item_card_content);
            mTvItemTitle = itemView.findViewById(R.id.tv_item_card_title);
            mIVback = itemView.findViewById(R.id.iv_item_card_back);
            mIVComment = itemView.findViewById(R.id.iv_item_card_comments);
            mIVCollection = itemView.findViewById(R.id.iv_item_card_collection);
        }

        @Override
        public <T> void setData(T t) {
            ExploreBean exploreBean = (ExploreBean)t;
            GlideUtils.loadImageForUrl(MainApplication.getInstance().getApplicationContext(), iv_background, exploreBean.getImg());
            mTvItemContent.setText(exploreBean.getTitle());
            mTvItemTitle.setText(getTitle(exploreBean.getType()));
            mIVback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardClickListener.back();
                }
            });
            mIVComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardClickListener.comments();
                }
            });
            mIVCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardClickListener.collection();
                }
            });
        }

        private String getTitle(String type) {
            switch (type) {
                case "1":
                    return "互动话题";
                case "2":
                    return "数字生活研究所";
                case "3":
                    return "应用推荐";
                default:
                    return "互动话题";
            }
        }
    }

    public interface OnCardClickListener {
        public void back();

        public void comments();

        public void collection();
    }

}
