package com.a7codes.menu.Classes;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class AlertHelper {

    Context context;

    public AlertHelper(Context context) {
        this.context = context;
    }

    public void MakeAlert(String Title, String Message, String btnTxt){
        AlertDialog alertDialog = new AlertDialog.Builder(context)
//set icon
//                .setIcon(android.R.mipmap.)
//set title
                .setTitle(Title)
//set message
                .setMessage(Message)
//set positive button
                .setPositiveButton(btnTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked

                    }
                })

                .show();
    }
}
