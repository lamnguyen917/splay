package com.splay.libs.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by LAM on 24/10/2015.
 */
public class GuiActivity extends Activity {
    static GuiActivity INSTANCE = null;
    public static final String OBJECT_NAME = "SplayPayment";
    public static final String TAG = "GuiActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int payment_main_landscape_id = getResources().getIdentifier("payment_main_landscape", "layout", getPackageName());
        setContentView(payment_main_landscape_id);

        int btn_sms_id = getResources().getIdentifier("btn_sms", "id", getPackageName());
        LinearLayout btn_sms = (LinearLayout)findViewById(btn_sms_id);
        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSmsPanel();
            }
        });
        int btn_card_id = getResources().getIdentifier("btn_card", "id", getPackageName());
        int txt_sms_item_id = getResources().getIdentifier("txt_sms_item", "id", getPackageName());
        int txt_card_item_id = getResources().getIdentifier("txt_card_item", "id", getPackageName());

        int payment_sms_item_id = getResources().getIdentifier("payment_sms_item", "string", getPackageName());
        int payment_card_item_id = getResources().getIdentifier("payment_card_item", "string", getPackageName());
        LinearLayout btn_card = (LinearLayout) findViewById(btn_card_id);
        btn_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCardListPanel();
            }
        });
        TextView tvSms = (TextView) findViewById(txt_sms_item_id);
        tvSms.setText(Html.fromHtml(getString(payment_sms_item_id)));
        TextView tvCard = (TextView) findViewById(txt_card_item_id);
        tvCard.setText(Html.fromHtml(getString(payment_card_item_id)));

        int scale_towards_corner = getResources().getIdentifier("scale_towards_corner", "anim", getPackageName());
        int scale_from_corner = getResources().getIdentifier("scale_from_corner", "anim", getPackageName());
        overridePendingTransition(scale_towards_corner, scale_from_corner);

        (findViewById(getResources().getIdentifier("btn_close", "id", getPackageName()))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void openSmsPanel() {
        Intent smsIntent = new Intent(this, SmsActivity.class);
        startActivity(smsIntent);
    }

    private void openCardListPanel() {
        Intent cardListIntent = new Intent(this, CardListActivity.class);
        startActivity(cardListIntent);
    }
}