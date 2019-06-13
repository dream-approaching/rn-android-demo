package com.lieying.petcat.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.WindowManager;
import com.lieying.petcat.R;

public abstract class BaseLoadingActivity extends BaseActivity {
    Dialog dialog;
    AnimationDrawable animationDrawable;
    protected void showLoading(){

        if(dialog == null) {
            dialog = new Dialog(this, R.style.Theme_Transparent);
            dialog.setContentView(R.layout.dialog_loading_prograss);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
            dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lp);
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(animationDrawable!=null){
                    animationDrawable.setCallback(null);
                    animationDrawable = null;
                }
            }
        });
    }

    protected void dissMissDialog(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dissMissDialog();
    }
}
