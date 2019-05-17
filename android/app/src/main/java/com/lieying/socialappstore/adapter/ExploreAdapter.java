package com.lieying.socialappstore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lieying.comlib.bean.ExploreBean;
import com.lieying.comlib.constant.Constants;
import com.lieying.socialappstore.MainApplication;
import com.lieying.socialappstore.R;
import com.lieying.socialappstore.activity.CommonReactActivity;
import com.lieying.socialappstore.activity.LoginActivity;
import com.lieying.socialappstore.base.BaseViewHolder;
import com.lieying.socialappstore.bean.ReactParamsJson;
import com.lieying.socialappstore.manager.UserManager;
import com.lieying.socialappstore.network.BaseObserver;
import com.lieying.socialappstore.network.ReqBody;
import com.lieying.socialappstore.network.ResponseData;
import com.lieying.socialappstore.network.RetrofitUtils;
import com.lieying.socialappstore.utils.GlideUtils;
import com.lieying.socialappstore.utils.GsonUtil;
import com.lieying.socialappstore.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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
        public void setData(int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (list.get(position).getType()){
                        case Constants.INDEX_ARITCLE_TYPE_TOPIC:
                            CommonReactActivity.startActivity(mContext , "MyReactNativeAppthree" ,"detailChat" , new ReactParamsJson.Builder().setContentID(list.get(position).getId()).setContentType(Constants.COMMENT_TYPE_APP).getRNParams());
                            break;
                        case Constants.INDEX_ARITCLE_TYPE_SHUZI:
                            ToastUtil.showToast("暂时不支持打开数字生活研究所"+list.get(position).getTitle());
                            break;
                        case Constants.INDEX_ARITCLE_TYPE_APP:
                            ToastUtil.showToast("暂时不支持打开数字生活研究所"+list.get(position).getTitle());
                            break;
                    }
                }
            });
            GlideUtils.loadImageForUrl(MainApplication.getInstance().getApplicationContext(), iv_background, list.get(position).getImg());
            mIVCollection.setImageResource(list.get(position).isIs_favorites() ? R.drawable.ic_card_index_collection_s : R.drawable.ic_card_index_collection);
            mTvItemContent.setText(list.get(position).getTitle());
            mTvItemTitle.setText(getTitle(list.get(position).getType()));
            mIVback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardClickListener.back();
                }
            });
            mIVComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardClickListener.comments(list.get(position));
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
                    collection(list.get(position).getType(), list.get(position).isIs_favorites() ?"del" : "add", position);
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

        /**
         * @Description: 获取首页卡片数据
         * @Params: collection   add代表收藏，del代表取消收藏
         * @Author: liyi
         */
        private void collection(String type, String collection, int position) {
            HashMap<String, String> map = new HashMap<>();
            map.put("type", type);
            map.put("opt", collection);
            map.put("info_id", list.get(position).getId());
            map.put("mobilephone", UserManager.getCurrentUser().getPhone());
            RetrofitUtils.getInstance(mContext).sendRequset(new Function<String, ObservableSource<ResponseData<Object>>>() {
                @Override
                public ObservableSource<ResponseData<Object>> apply(String s) throws Exception {
                    return RetrofitUtils.getInstance(mContext).getApiService().collectionContent(ReqBody.getReqString(map));
                }
            }, new BaseObserver<ResponseData<Object>>() {
                @Override
                protected void onSuccees(ResponseData<Object> objectResponseData) {
                    if (objectResponseData.getStatus() == 0) {
                        if (collection.equals("add")) {
                            ToastUtil.showToast("搜藏成功");
                            list.get(position).setIs_favorites(true);
                            mIVCollection.setImageResource(list.get(position).isIs_favorites() ? R.drawable.ic_card_index_collection_s : R.drawable.ic_card_index_collection);
                        } else {
                            ToastUtil.showToast("取消收藏成功");
                            list.get(position).setIs_favorites(false);
                            mIVCollection.setImageResource(list.get(position).isIs_favorites() ? R.drawable.ic_card_index_collection_s : R.drawable.ic_card_index_collection);
                        }
                    }else {
                        ToastUtil.showToast("操作失败"+objectResponseData.getMsg());
                    }
                }

                @Override
                protected void onFailure(Throwable e, boolean isNetWorkError) {
                    if (isNetWorkError) {
                        ToastUtil.showToast("请求失败");
                    }
                }
            });
        }
    }

    public interface OnCardClickListener {
        public void back();

        public void comments(ExploreBean exploreBean);

        public void collection();

        public void moveEnd();
    }

}
