package com.splay.libs.payment;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.unity3d.player.UnityPlayer;

public class SmsActivity extends Activity {
    public static final String TAG = "SmsActivity";
    private static Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();
        setContentView(getResources().getIdentifier("payment_confirm_landscape", "layout", getPackageName()));

        TextView message = (TextView)findViewById(getResources().getIdentifier("txv_message", "id", getPackageName()));
        message.setText(getResources().getIdentifier("buy_game_text_wap", "string", getPackageName()));

        int scale_towards_corner = getResources().getIdentifier("scale_towards_corner", "anim", getPackageName());
        int scale_from_corner = getResources().getIdentifier("scale_from_corner", "anim", getPackageName());
        overridePendingTransition(scale_towards_corner, scale_from_corner);

        Button buttonCancel = (Button)findViewById(getResources().getIdentifier("btn_cancel", "id", getPackageName()));
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button buttonOk = (Button)findViewById(getResources().getIdentifier("btn_ok", "id", getPackageName()));
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendSMS(getString(getResources().getIdentifier("payment_sms_number", "string", getPackageName())),
                        getString(getResources().getIdentifier("payment_sms_code_level_1", "string", getPackageName())) + " "
                                + getString(getResources().getIdentifier("payment_sms_code_level_2", "string", getPackageName())));
            }
        });

        (findViewById(getResources().getIdentifier("btn_close", "id", getPackageName()))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void SendSMS(String phoneNumber, String message){
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
        PendingIntent sentPI = PendingIntent.getBroadcast(mContext, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(mContext, 0, new Intent(DELIVERED), 0);
        //---when the SMS has been sent---
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                Log.d(TAG, "SENT onReceive: " + getResultCode());
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.d(TAG, "Send sms ok");
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Log.e(TAG, "RESULT_ERROR_GENERIC_FAILURE");
//                        UnityPlayer.UnitySendMessage("SunnetPayment", "OnSMSFailed", "RESULT_ERROR_GENERIC_FAILURE");
                        UnityPlayer.UnitySendMessage(UnityController.getInstance().getObjectName(), "OnSMSFailed", "");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Log.e(TAG, "RESULT_ERROR_NO_SERVICE");
//                        UnityPlayer.UnitySendMessage("SunnetPayment", "OnSMSFailed", "RESULT_ERROR_NO_SERVICE");
                        UnityPlayer.UnitySendMessage(UnityController.getInstance().getObjectName(), "OnSMSFailed", "");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Log.e(TAG, "RESULT_ERROR_NULL_PDU");
//                        UnityPlayer.UnitySendMessage("SunnetPayment", "OnSMSFailed", "RESULT_ERROR_NULL_PDU");
                        UnityPlayer.UnitySendMessage(UnityController.getInstance().getObjectName(), "OnSMSFailed", "");
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Log.e(TAG, "RESULT_ERROR_RADIO_OFF");
//                        UnityPlayer.UnitySendMessage("SunnetPayment", "OnSMSFailed", "RESULT_ERROR_RADIO_OFF");
                        UnityPlayer.UnitySendMessage(UnityController.getInstance().getObjectName(), "OnSMSFailed", "");
                        break;
                }
            }
        }, new IntentFilter(SENT));

        //---when the SMS has been delivered---
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                Log.d(TAG, "DELIVERED onReceive: " + getResultCode());
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Log.d(TAG, "Sent!!!!!!!!!");
                        UnityPlayer.UnitySendMessage(UnityController.getInstance().getObjectName(), "OnSMSSuccess", "");
                        break;
                    case Activity.RESULT_CANCELED:
                        UnityPlayer.UnitySendMessage(UnityController.getInstance().getObjectName(), "OnSMSFailed", "");
                        Log.d(TAG, "RESULT_CANCELED");
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }
}