package com.a7codes.menu.Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.a7codes.menu.R;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void StartLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loadingdialog, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void StopLoading(){
        dialog.dismiss();
    }

}
