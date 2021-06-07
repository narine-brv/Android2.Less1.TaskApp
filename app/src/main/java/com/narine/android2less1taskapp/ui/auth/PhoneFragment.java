package com.narine.android2less1taskapp.ui.auth;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.narine.android2less1taskapp.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


public class PhoneFragment extends Fragment {

    private EditText editPhone;
    String verificationId;
    String sms;
    //    CustomCodeDialog dialog = new CustomCodeDialog();
    private EditText editText;
    private AlertDialog dialog;
    private FirebaseAuth mAuth;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            Log.e("Phone", "onVerificationCompleted" + phoneAuthCredential.getSmsCode());
            sms = phoneAuthCredential.getSmsCode();
//                dialog.setCode(verificationId,sms);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.e("Phone", "onVerificationFailed()" + e.getMessage());
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            startTimer();
            Toast.makeText(getContext(),"Code send",Toast.LENGTH_LONG).show();
        }
    };

    private void startTimer() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                Toast.makeText(getContext(),"seconds remaining: " + millisUntilFinished / 1000,Toast.LENGTH_SHORT).show();
            }

            public void onFinish() {
                dialog.dismiss();
            }
        }.start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        return inflater.inflate(R.layout.fragment_phone, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editPhone = view.findViewById(R.id.et_phone);
        editText = new EditText(requireContext());
        initCallbacks();
        view.findViewById(R.id.btn_phone_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSMS();
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setView(editText);
                builder.setMessage("Введите код");
                builder.setPositiveButton("Продолжить", null);
                builder.setNegativeButton(android.R.string.cancel, null);
                dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialogInterface) {

                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                sms = editText.getText().toString();
                                verifyCode(sms);

                                //Dismiss once everything is OK.
//                                dialog.dismiss();
                            }
                        });
                    }
                });

                dialog.show();
//                dialog.showDialog(requireActivity());
//                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, sms);
//                signinWithPhoneAuthCredential(credential);
            }
        });

    }

    private void verifyCode(String code) {
        if (verificationId  != null){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signinWithPhoneAuthCredential(credential);
        }
    }

    private void initCallbacks() {
//        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//            @Override
//            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                Log.e("Phone", "onVerificationCompleted" + phoneAuthCredential.getSmsCode());
//                sms = phoneAuthCredential.getSmsCode();
////                dialog.setCode(verificationId,sms);
//            }
//
//            @Override
//            public void onVerificationFailed(@NonNull FirebaseException e) {
//                Log.e("Phone", "onVerificationFailed()" + e.getMessage());
//            }
//
//            @Override
//            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                super.onCodeSent(s, forceResendingToken);
//                verificationId = s;
//                Toast.makeText(getContext(),"Code send",Toast.LENGTH_LONG);
//                Log.d("ERRORCODE","code send");
//            }
//        };
    }

    @SuppressLint("ShowToast")
    private void signinWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(getActivity(), task -> {
            if (task.isSuccessful()) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_home);
                dialog.dismiss();
                Log.e("TAG", "onComplete: ");
            } else {
                Log.e("TAG", "onFail: ");
                Toast.makeText(getActivity(),"Fail Code",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void requestSMS() {
        String phone = editPhone.getText().toString();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder()
                        .setPhoneNumber(phone)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // сколько мы должны ждать 60с
                        .setActivity(requireActivity())                 // Activity (for callback binding)
                        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


}