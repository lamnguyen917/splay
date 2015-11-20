package com.splay.libs.payment;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by LAM on 10/26/2015.
 */
public class SDialog extends android.app.Dialog {

    public SDialog(Context context) {
        super(context);
        setContentView(R.layout.payment_dialog_landscape);
    }

    public void Show(String content, View.OnClickListener evtOkButtonClick){
        TextView message = (TextView)findViewById(R.id.txv_message);
        message.setText(content);
        Button buttonOk = (Button)findViewById(R.id.btn_ok);
        buttonOk.setOnClickListener(evtOkButtonClick);
        show();
    }

    public void Show(String content){
        Show(content, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
