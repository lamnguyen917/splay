package com.splay.libs.payment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by LAM on 10/27/2015.
 */
public class UnityController {
    private Activity activity;
    private static UnityController instance;
    private static String gameName = "SPLAY";
    private String objectName = "SplayPaymentObject";

//    public static UnityController() {
//        instance = this;
//    }

    public static UnityController getInstance() {
        if (instance == null) {
            instance = new UnityController();
        }
        return instance;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public static Activity getActivity(){
        return instance.activity;
    }

    public String getObjectName() {
        return objectName;
    }
    public void setObjectName(String objectName){
        this.objectName = objectName;
    };

    public void setGameName(String name){
        this.gameName = name;
    }

    public static String getGameName(){
        return gameName;
    }

    public void showGui() {
        this.activity.startActivity(new Intent(this.activity, GuiActivity.class));
    }

    public void showMessage(String message) {
        Toast.makeText(this.activity.getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

//    public void ShowAlert(String title, String content){
//
//    }
}
