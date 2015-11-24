package com.splay.libs.payment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.unity3d.player.UnityPlayer;

public class CardActivity extends Activity {
    public static final String TAG = "CardActivity";
    private Context mContext;

    private String title;
    private CardTypes type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = CardActivity.this;
        int layout_id = getResources().getIdentifier("payment_card_landscape", "layout", getPackageName());
        setContentView(layout_id);

        int scale_towards_corner = getResources().getIdentifier("scale_towards_corner", "anim", getPackageName());
        int scale_from_corner = getResources().getIdentifier("scale_from_corner", "anim", getPackageName());
        overridePendingTransition(scale_towards_corner, scale_from_corner);

        title = getIntent().getStringExtra("title");
        type = (CardTypes)getIntent().getSerializableExtra("type");

        int txt_title_id = getResources().getIdentifier("txt_title", "id", getPackageName());
        TextView txtTitle = (TextView)findViewById(txt_title_id);
        txtTitle.setText(title);
        int btn_clear_id = getResources().getIdentifier("btn_clear", "id", getPackageName());
        Button buttonClear = (Button)findViewById(btn_clear_id);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int edt_card_code_id = getResources().getIdentifier("edt_card_code", "id", getPackageName());
                EditText edtCode = (EditText)findViewById(edt_card_code_id);
                int edt_card_seri_id = getResources().getIdentifier("edt_card_seri", "id", getPackageName());
                EditText edtSeri = (EditText)findViewById(edt_card_seri_id);
                edtCode.setText("");
                edtSeri.setText("");
            }
        });

        int btn_topup_id = getResources().getIdentifier("btn_topup", "id", getPackageName());
        Button buttonTopup = (Button)findViewById(btn_topup_id);
        buttonTopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int edt_card_code = getResources().getIdentifier("edt_card_code", "id", getPackageName());
                EditText edtCode = (EditText)findViewById(edt_card_code);
                String code = edtCode.getText().toString();
                int edt_card_seri = getResources().getIdentifier("edt_card_seri", "id", getPackageName());
                EditText edtSeri = (EditText)findViewById(edt_card_seri);
                String seri = edtSeri.getText().toString();
                CallRESTfulRequest(type, code, seri, "mobay.vn", UnityController.getPackageName());
            }
        });


        (findViewById(getResources().getIdentifier("btn_close", "id", getPackageName()))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                close();
            }
        });
    }

    public void close(){
        finish();
    }

    public void closeAll(){
        Intent intent = new Intent(getApplicationContext(), UnityController.getActivity().getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void ShowAlert(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton(getString(getResources().getIdentifier("payment_btn_ok", "string", getPackageName())), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
                        closeAll();
                    }
                });
        builder.show();
    }

    public void CallRESTfulRequest(CardTypes type, String pin, String serial, String target, String game) {
        Log.d(TAG, "CallRESTfulRequest" + type + pin + serial + target + game);
        final CardRecharge sunnetRESTfulRecharge = new CardRecharge(mContext, type, pin, serial, target, game);
        sunnetRESTfulRecharge.addEventListener(new ISunnetEventListener() {
            @Override
            public void SunnetEventListener(Result result) {
                Log.d(TAG, "on request done!!!!!!!!!!!!!!");
                if (result.status == 0){
                    ShowAlert(result.statusMsg, getString(getResources().getIdentifier("payment_card_message", "string", getPackageName())));
                    Log.d(TAG, "SPLAY REQUEST SUCCESS    ");
                    UnityPlayer.UnitySendMessage(UnityController.getInstance().getObjectName(), "OnCardSuccess", String.valueOf(result.amount));
                } else {
                    ShowAlert(getString(getResources().getIdentifier("payment_alert_error", "string", getPackageName())) + " " + result.status + ":", result.statusMsg);
                    Log.d(TAG, "_____SPLAY PAYMENT ERROR   " + UnityController.getInstance().getObjectName());
//                    result.amount = 10000000;
//                    UnityPlayer.UnitySendMessage(UnityController.getInstance().getObjectName(), "OnCardSuccess", String.valueOf(result.amount));
                    UnityPlayer.UnitySendMessage(UnityController.getInstance().getObjectName(), "OnCardError", result.statusMsg);
                    Log.d(TAG, "on request error!!!!!!!!!!!!!!" + UnityController.getInstance().getObjectName());

                }
            }
        });
        sunnetRESTfulRecharge.getRequest();
    }
}