package com.lieying.petcat.videorecord;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.lieying.petcat.R;


/**
 * Created by Administrator on 2016/9/26.
 */
public class ErrorDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Transparent)
                .setCancelable(true)
                .setTitle(getArguments().getString("errorMsg"))
                .setPositiveButton(getResources().getString(R.string.string_sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        getActivity().finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}
