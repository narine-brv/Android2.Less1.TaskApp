package com.narine.android2less1taskapp.ui.auth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.narine.android2less1taskapp.R;

public class CustomCodeDialog{

    private String id;
    private  String code;
    private EditText editText;
    private Dialog dialog;

    public void showDialog(Activity activity){
        dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog_code);

        editText = (EditText) dialog.findViewById(R.id.et_code);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_code_continue);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyCode();
                }
            });

            dialog.show();

    }

    private void verifyCode() {
        if (editText.getText().toString().equals(code)){
            dialog.dismiss();
            Log.e("TAG", "verifyCode: "+ code );
        } else {
            Log.e("tag","Ошибка");
        }

    }


    public void setCode(String id, String code) {
        this.id = id;
        this.code = code;
    }
}
