package com.example.mypc.stores.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.mypc.stores.R;
import com.example.mypc.stores.ui.register.RegisActivity;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.x;
import static java.lang.Math.ceil;

/**
 * Created by MyPC on 05/08/2017.
 */

public class UtilDatas {

    public static boolean isEmpty(EditText etText) {
        if (etText != null && etText.getText().toString().trim().length() > 0) {
            return true;
        } else {
            etText.requestFocus();
            etText.setError("Vui lòng điền thông tin!");
            return false;
        }
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        String expression = "[a-zA-Z0-9._-]+@[a-z]+(\\.+[a-z]+)+";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean checkInPutLogin(EditText edtAccName, EditText edtAccPass, Context context) {
        if (isEmpty(edtAccName) && isEmpty(edtAccPass)) {
            String acc = edtAccName.getText().toString().trim();
            String pass = edtAccPass.getText().toString().trim();
            if (acc.length() < 6) {
                edtAccName.requestFocus();
                edtAccName.setError(context.getResources().getString(R.string.accc_error));
                return false;
            } else {
                if (pass.length() < 6) {
                    edtAccPass.requestFocus();
                    edtAccPass.setError(context.getResources().getString(R.string.pass_error));
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkInPutRegis(EditText edtNameRegis, EditText edtNumberRegis,
                                          EditText edtPassRegis, RegisActivity regisActivity) {
        if (isEmpty(edtNameRegis) && isEmpty(edtNumberRegis) && isEmpty(edtPassRegis)) {
            String acc = edtNameRegis.getText().toString().trim();
            String pass = edtPassRegis.getText().toString().trim();
            String number = edtNumberRegis.getText().toString().trim();
            if (acc.length() < 6) {
                edtNameRegis.requestFocus();
                edtNameRegis.setError(regisActivity.getResources().getString(R.string.accc_error));
                return false;
            } else {
                if (pass.length() < 6) {
                    edtPassRegis.requestFocus();
                    edtPassRegis.setError(regisActivity.getResources().getString(R.string.pass_error));
                    return false;
                }else {
                    if (isNumber(number)) {
                        if (number.length() > 11 && number.length() < 8) {
                            edtPassRegis.requestFocus();
                            edtPassRegis.setError(regisActivity.getResources().getString(R.string.numberError));
                            return false;
                        }
                    }
                }

            }
            return true;
        } else {
            return false;
        }
    }


    private static boolean isNumber(String number) {

        try
        {

            double d = Double.parseDouble(number);
            return true;
        }
        catch(NumberFormatException nfe)
        {

            return false;
        }

    }
}
