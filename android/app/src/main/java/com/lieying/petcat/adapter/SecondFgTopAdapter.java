package com.lieying.petcat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.lieying.comlib.bean.SecondFgBean;
import com.lieying.petcat.R;
import com.lieying.petcat.base.BaseViewHolder;
import com.lieying.petcat.utils.GlideUtils;
import com.lieying.petcat.widget.NiceImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/6/14 0014 15:40
 */
public class SecondFgTopAdapter extends RecyclerView.Adapter<SecondFgTopAdapter.VH> {
    List<SecondFgBean.LikeBean> list = new ArrayList<>();
    private LayoutInflater mLayoutInflater;

    public SecondFgTopAdapter(Context context, List<SecondFgBean.LikeBean> list) {
        mLayoutInflater = LayoutInflater.from(context);
        this.list = list;
    }


    @NonNull
    @Override
    public SecondFgTopAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SecondFgTopAdapter.VH(mLayoutInflater.inflate(R.layout.item_second_fg_top_item_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.setData(i);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    class VH extends BaseViewHolder {
        NiceImageView mIvHead;
        TextView mTvNickName;
        TextView mTvBrief;
        TextView mTvFollow;
        public VH(@NonNull View itemView) {
            super(itemView);
            mIvHead = itemView.findViewById(R.id.iv_second_fg_top_item_head);
            mTvNickName = itemView.findViewById(R.id.tv_second_fg_top_item_nick);
            mTvBrief = itemView.findViewById(R.id.tv_second_fg_top_item_brief);
            mTvFollow = itemView.findViewById(R.id.tv_second_fg_top_item_follow);
        }

        @Override
        public void setData(int position) {
            GlideUtils.loadImageForUrl(mContext , mIvHead , list.get(position).getHead_image());
            mTvNickName.setText(list.get(position).getNick_name());
            mTvBrief.setText(list.get(position).getProfile());
            mTvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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

}
