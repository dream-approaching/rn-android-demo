package com.lieying.petcat.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lieying.comlib.bean.ExploreBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.petcat.MainApplication;
import com.lieying.petcat.R;
import com.lieying.petcat.activity.CommonReactActivity;
import com.lieying.petcat.activity.LoginActivity;
import com.lieying.petcat.base.BaseViewHolder;
import com.lieying.petcat.bean.ReactParamsJson;
import com.lieying.petcat.manager.UserManager;
import com.lieying.petcat.utils.ClickEventUtils;
import com.lieying.petcat.utils.GlideUtils;
import com.lieying.petcat.utils.ToastUtil;

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
        ImageView iv_background;
        TextView mTvItemTitle;
        TextView mTvItemContent;
        ImageView mIVback;
        ImageView mIVComment;
        ImageView mIVCollection;
        TextView mTvCommentNum;
        public VH(@NonNull View itemView) {
            super(itemView);
            iv_background = itemView.findViewById(R.id.iv_item_card_background);
            mTvItemContent = itemView.findViewById(R.id.tv_item_card_content);
            mTvItemTitle = itemView.findViewById(R.id.tv_item_card_title);
            mIVback = itemView.findViewById(R.id.iv_item_card_back);
            mIVComment = itemView.findViewById(R.id.iv_item_card_comments);
            mIVCollection = itemView.findViewById(R.id.iv_item_card_collection);
            mTvCommentNum = itemView.findViewById(R.id.tv_item_card_comments_num);
        }

        @Override
        public void setData(int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!ClickEventUtils.doubleEventView(v.getId())) {
                        switch (list.get(position).getType()){
                            case Constants.INDEX_ARITCLE_TYPE_TOPIC:
                                CommonReactActivity.startActivity(mContext , "MyReactNativeAppthree" ,"detailChat" , new ReactParamsJson.Builder().setContentID(list.get(position).getId()).setContentType(Constants.COMMENT_TYPE_APP).setPosition(position+"").getRNParams());
                                break;
                            case Constants.INDEX_ARITCLE_TYPE_SHUZI:
                                CommonReactActivity.startActivity(mContext , "MyReactNativeAppthree" , "detailWebview" ,new ReactParamsJson.Builder().setWebUrl(list.get(position).getUrl_content()).setPosition(position+"").getRNParams());
                                break;
                            case Constants.INDEX_ARITCLE_TYPE_APP:
                                CommonReactActivity.startActivity(mContext , "MyReactNativeAppthree" , "detailWebview" ,new ReactParamsJson.Builder().setWebUrl(list.get(position).getUrl_content()).setPosition(position+"").getRNParams());
                                break;
                        }
                    }
                }
            });
            GlideUtils.loadImageForUrl(MainApplication.getInstance().getApplicationContext(), iv_background, list.get(position).getImg());
            mIVCollection.setImageResource(list.get(position).isIs_favorites() ? R.drawable.ic_card_index_collection_s : R.drawable.ic_card_index_collection);
            mTvItemContent.setText(list.get(position).getTitle());
            mTvItemTitle.setText(getTitle(list.get(position).getType()));
            mTvCommentNum.setText(list.get(position).getComment_num());
                mIVback.setImageResource(R.drawable.ic_card_index_back);
                mIVback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cardClickListener.back();
                    }
                });
            mIVComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardClickListener.comments(list.get(position) , position);
                }
            });
            mIVCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(UserManager.getCurrentUser().getUserinfo() == null){
                        LoginActivity.startActivity(mContext);
                        ToastUtil.showToast("请先登陆账号");
                        return;
                    }
                    String collection = list.get(position).isIs_favorites() ? Constants.OPT_TO_USER_CANCLE_FOLLOW : Constants.OPT_TO_USER_FOLLOW;

                    if (collection.equals(Constants.OPT_TO_USER_FOLLOW)) {
                        list.get(position).setIs_favorites(true);
                        mIVCollection.setImageResource(list.get(position).isIs_favorites() ? R.drawable.ic_card_index_collection_s : R.drawable.ic_card_index_collection);
                    } else {
                        list.get(position).setIs_favorites(false);
                        mIVCollection.setImageResource(list.get(position).isIs_favorites() ? R.drawable.ic_card_index_collection_s : R.drawable.ic_card_index_collection);
                    }
                    cardClickListener.collection(list.get(position).getType(), collection, list.get(position).getId());
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

        public void comments(ExploreBean exploreBean , int position);

        public void collection(String type, String collection, String id);

        public void moveEnd();
    }

}
