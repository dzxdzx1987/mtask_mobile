package com.example.mtask_mobile.util;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogUtil {

    private ProgressDialog progressDialog;
    private static DialogUtil instance;

    private DialogUtil() {
    }

    public static DialogUtil getInstance() {
        if (instance == null) {
            return new DialogUtil();
        } else {
            return  instance;
        }
    }

    public void showProgressDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("loading...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    public void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
