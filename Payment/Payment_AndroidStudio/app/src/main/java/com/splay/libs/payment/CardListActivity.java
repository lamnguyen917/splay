package com.splay.libs.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by LAM on 24/10/2015.
 */
public class CardListActivity extends Activity {
    private String TAG = "CardListActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResources().getIdentifier("payment_card_list_landscape", "layout", getPackageName()));

        Log.d(TAG, "overridePendingTransition");
        overridePendingTransition(getResources().getIdentifier("scale_towards_corner", "anim", getPackageName()),
                getResources().getIdentifier("scale_from_corner", "anim", getPackageName()));

        LinearLayout buttonMCCard = (LinearLayout)findViewById(getResources().getIdentifier("btn_mc", "id", getPackageName()));
        LinearLayout buttonMobi = (LinearLayout)findViewById(getResources().getIdentifier("btn_mobi", "id", getPackageName()));
        LinearLayout buttonVina = (LinearLayout)findViewById(getResources().getIdentifier("btn_vina", "id", getPackageName()));
        LinearLayout buttonVtc = (LinearLayout)findViewById(getResources().getIdentifier("btn_vtc", "id", getPackageName()));
        LinearLayout buttonViettel = (LinearLayout)findViewById(getResources().getIdentifier("btn_viettel", "id", getPackageName()));
        LinearLayout buttonFpt = (LinearLayout)findViewById(getResources().getIdentifier("btn_fpt", "id", getPackageName()));
        buttonMCCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCardDialog(getString(getResources().getIdentifier("payment_mc", "string", getPackageName())), CardTypes.MOBAY);
            }
        });
        buttonMobi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCardDialog(getString(getResources().getIdentifier("payment_mobi", "string", getPackageName())), CardTypes.MOBI);
            }
        });
        buttonVina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCardDialog(getString(getResources().getIdentifier("payment_vina", "string", getPackageName())), CardTypes.VINA);
            }
        });
        buttonVtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCardDialog(getString(getResources().getIdentifier("payment_vtc", "string", getPackageName())), CardTypes.VTC);
            }
        });
        buttonViettel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCardDialog(getString(getResources().getIdentifier("payment_viettel", "string", getPackageName())), CardTypes.VIETTEL);
            }
        });
        buttonFpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCardDialog(getString(getResources().getIdentifier("payment_fpt", "string", getPackageName())), CardTypes.GATE);
            }
        });

        (findViewById(getResources().getIdentifier("btn_close", "id", getPackageName()))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openCardDialog(String title, CardTypes type){
        Intent intent = new Intent(this, CardActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}