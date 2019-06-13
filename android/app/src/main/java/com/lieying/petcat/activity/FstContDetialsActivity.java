package com.lieying.petcat.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.dou361.ijkplayer.listener.OnShowThumbnailListener;
import com.dou361.ijkplayer.widget.IjkVideoView;
import com.dou361.ijkplayer.widget.PlayStateParams;
import com.dou361.ijkplayer.widget.PlayerView;
import com.gyf.immersionbar.ImmersionBar;
import com.lieying.petcat.R;
import com.lieying.petcat.base.BaseActivity;

public class FstContDetialsActivity extends BaseActivity {
    private static String KEY_FSTCONENT_PARAMS = "key_first_fg_pamars";
    IjkVideoView mPlayer;
    private String path;
    private PlayerView player;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, FstContDetialsActivity.class);
        intent.putExtra(KEY_FSTCONENT_PARAMS, url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fst_cont_detials);
        ImmersionBar.with(this).statusBarDarkFont(false).init();
    }

    @Override
    public void findView() {
        mPlayer = findViewById(R.id.video_view);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        path = getIntent().getStringExtra(KEY_FSTCONENT_PARAMS);
//        setContentView(rootView);
        player = new PlayerView(this)
                .setTitle("什么")
                .setScaleType(PlayStateParams.fitparent)
                .hideMenu(true)
                .hideAllUI()
                .forbidTouch(false)
                .showThumbnail(new OnShowThumbnailListener() {
                    @Override
                    public void onShowThumbnail(ImageView ivThumbnail) {
                        Glide.with(mContext)
                                .load("http://pic2.nipic.com/20090413/406638_125424003_2.jpg")
                                .into(ivThumbnail);
                    }
                })
                .setPlaySource(path)
                .startPlay();
    }

    @Override
    public void initListener() {

    }
}
