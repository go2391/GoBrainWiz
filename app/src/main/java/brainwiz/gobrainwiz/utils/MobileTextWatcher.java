package brainwiz.gobrainwiz.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/**
 */

public class MobileTextWatcher implements TextWatcher {

    Context context;
    EditText editText;

    public MobileTextWatcher(Context context, EditText editText) {
        this.context = context;
        this.editText = editText;
    }

    public boolean flag;
    public String a;
    public int keyDel;
    public int mAfter;
    public boolean mFormatting;

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mAfter = i2;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int i1, int i2) {

        if (flag) {
            editText.setOnKeyListener(new View.OnKeyListener() {

                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (keyCode == KeyEvent.KEYCODE_DEL)
                        keyDel = 1;
                    return false;
                }
            });

            flag = false;

            if (keyDel == 1) {
                keyDel = 0;
                editText.setText(charSequence);
                editText.setSelection(charSequence.length());
            } else {

                String s = editText.getText().toString();
                s = StringUtills.formatMobileNo(s);
//                        if (str.length() > 6) {
//                            s = s + "-"+str.substring(6,str.length());
//                        }
                editText.setText(s);
                editText.setSelection(s.length());
            }

        }
    }


    @Override
    public void afterTextChanged(Editable editable) {
        flag = true;
    }
}

