package com.lieying.petcat.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lieying.comlib.bean.FstFgRcmdBean;
import com.lieying.petcat.R;
import com.lieying.petcat.activity.FstContDetialsActivity;
import com.lieying.petcat.utils.GlideUtils;
import com.lieying.comlib.pull.DefaultNoMoreViewHolder;
import com.lieying.petcat.widget.NiceImageView;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

/**
 * @Description: java类作用描述
 * @Author: liyi
 * @CreateDate: 2019/6/5 0005 16:51
 */
public class FirstFgFallsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int VIEW_TYPE_CONTENT = 1;
    private final static int VIEW_TYPE_EMPTY = -1;
    private boolean isExitsMore = true;//是否存在更多
    private Context mContext;
    private List<FstFgRcmdBean> list;
    RequestOptions options = new RequestOptions()
            .centerCrop()
            .priority(Priority.LOW);
    public FirstFgFallsAdapter(Context mContext, List<FstFgRcmdBean> list, OnCardClickListener onCardClickListener) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE_EMPTY) {
            return new DefaultNoMoreViewHolder(mContext);
        }
        return new VH(mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VH) {
            FstFgRcmdBean bean = list.get(position);
            ((VH) holder).initData(bean, position);
        } else if (holder instanceof DefaultNoMoreViewHolder) {
            if(list.size()<=0 ){
                ((DefaultNoMoreViewHolder) holder).setTip( "暂无数据");
            }else {
                ((DefaultNoMoreViewHolder) holder).setTip("已经到底啦");
            }
        }
    }


    @Override
    public int getItemCount() {
        return !isExitsMore() ? list.size() + 1 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!isExitsMore() && position == list.size()) {
            return VIEW_TYPE_EMPTY;
        } else {
            return VIEW_TYPE_CONTENT;
        }
    }

    public boolean isExitsMore() {
        return isExitsMore;
    }

    public void setExitsMore(boolean exitsMore) {
        if (isExitsMore != exitsMore) {
            isExitsMore = exitsMore;
            notifyItemChanged(list.size());
        }
    }

    private class VH extends RecyclerView.ViewHolder {
        GifImageView imageView;
        TextView mTvTitle;
        TextView mTvNick;
        TextView mTvPraise;
        NiceImageView mIvHead;
        private VH(Context context) {
            super(LayoutInflater.from(context).inflate(R.layout.item_first_fg_falls, null));
            imageView = itemView.findViewById(R.id.iv_first_fg_bg);
            mTvTitle = itemView.findViewById(R.id.tv_first_fg_title);
            mTvNick = itemView.findViewById(R.id.tv_first_fg_nick);
            mIvHead = itemView.findViewById(R.id.iv_first_fg_header);
            mTvPraise = itemView.findViewById(R.id.tv_first_fg_praise);
        }

        private void initData(final FstFgRcmdBean bean, final int position) {
            mTvTitle.setText(bean.getTitle());
            mTvNick.setText(bean.getCommit_user());
            mTvPraise.setText(bean.getFavorites());
            GlideUtils.loadImageForUrl(mContext , mIvHead , bean.getHead_image());
            Glide.with(mContext).load( bean.getFile())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FstContDetialsActivity.startActivity(mContext , bean.getContent());
                }
            });
        }
    }

    public interface OnCardClickListener{
        public void onItemClick();
    }
}
