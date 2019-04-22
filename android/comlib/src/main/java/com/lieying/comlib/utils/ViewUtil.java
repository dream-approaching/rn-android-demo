package com.lieying.comlib.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by ${liyi} on 2018/1/30.
 */

public class ViewUtil {
    /**
     * 输入框聚焦监听
     * 获取焦点时显示 textView ,反之
     *
     * @param editText
     * @param textView
     */
    public static void setEditTextFocusListener(final EditText editText, final TextView textView, final View.OnFocusChangeListener listener) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                textView.setVisibility(hasFocus ? View.VISIBLE : View.INVISIBLE);
                editText.setHint(hasFocus ? "" : textView.getText().toString());
                if (listener != null) {
                    listener.onFocusChange(v, hasFocus);
                }
            }
        });
    }


    /**
     * 内容隐藏或显示监听
     *
     * @param editText
     * @param checkBox
     * @param listener
     */
    public static void setEditTextPwdStateListener(final EditText editText, CheckBox checkBox,
                                                   final CompoundButton.OnCheckedChangeListener listener) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editText.setTransformationMethod(isChecked ? HideReturnsTransformationMethod.getInstance()
                        : PasswordTransformationMethod.getInstance());
                editText.setSelection(editText.getText().toString().length());
                if (listener != null) {
                    listener.onCheckedChanged(buttonView, isChecked);
                }
            }
        });
    }

    //拦截回车键
    public static void interceptEnter(final EditText... editTexts) {
        for (EditText editText : editTexts) {
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    return (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
                }
            });
        }
    }

    /**
     * 事件按钮和输入框关联事件,达到指定长度才能点击
     */
    public static class WatchEnable implements TextWatcher {
        private View view;
        private TextWatcher textWatcher;
        private EnableListener enableListener;
        private EditText[] editTexts;
        private int[] lengths;

        public WatchEnable(View clickView) {
            this.view = clickView;
        }

        public void setEditTexts(EditText... ets) {
            editTexts = ets;
        }

        public void setLengths(int... lengths) {
            this.lengths = lengths;
        }

        public void setWatcher(TextWatcher watcher) {
            textWatcher = watcher;
        }

        public void setEnableListener(EnableListener listener) {
            enableListener = listener;
        }

        public void start() {
            for (EditText et : editTexts) {
                et.addTextChangedListener(this);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (textWatcher != null) {
                textWatcher.beforeTextChanged(s, start, count, after);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean enable = true;
            for (EditText et : editTexts) {
                if (et.getText().toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    StringBuilder sb = new StringBuilder();
                    for (String aStr : str) {
                        sb.append(aStr);
                    }
                    et.setText(sb.toString());
                    et.setSelection(start);

                }
            }

            if (lengths.length != editTexts.length) {
                for (EditText et : editTexts) {
                    if (et.length() < lengths[0]) {
                        enable = false;
                        break;
                    }
                }
            } else {
                for (int index = 0; index < editTexts.length; index++) {
                    if (editTexts[index].length() < lengths[index]) {
                        enable = false;
                        break;
                    }
                }
            }
            if (view.isEnabled() != enable) {
                view.setEnabled(enable);
                if (enableListener != null) {
                    enableListener.onEnable(enable);
                }
            }
            if (textWatcher != null) {
                textWatcher.onTextChanged(s, start, before, count);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (textWatcher != null) {
                textWatcher.afterTextChanged(s);
            }
        }
    }

    public interface EnableListener {
        void onEnable(boolean enable);
    }
}
