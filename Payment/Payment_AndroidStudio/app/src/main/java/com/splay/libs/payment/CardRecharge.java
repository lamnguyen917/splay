package com.splay.libs.payment;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardRecharge {
    static final String URL = "http://210.211.99.144:8087/gapi/v1/charge";
    static final String TAG = "CardRecharge";

    public static final String CARD_MOBAY = "MCCard";
    public static final String CARD_MOBI = "MobiCard";
    public static final String CARD_VINA = "VinaCard";
    public static final String CARD_VTC = "VtcCard";
    public static final String CARD_VIETTEL = "ViettelCard";
    public static final String CARD_GATE = "FptCard";

    private CardTypes mCardType = CardTypes.MOBAY;
    private String mPinCard = "";
    private String mSerial = "";
    private String mTarget = "";
    private String mGame = "";
    private Context mContext;

    Result result = new Result();

    private List<ISunnetEventListener> listeners = new ArrayList<ISunnetEventListener>();

    public CardRecharge(Context context, CardTypes type, String pin, String serial, String target, String game){
        mContext = context;
        mCardType = type;
        mPinCard = pin;
        mSerial = serial;
        mTarget = target;
        mGame = game;
    }

    public void addEventListener(ISunnetEventListener eventListener){
        listeners.add(eventListener);
    }

    public String getCardTypeStr(){
        return getCardType(mCardType);
    }

    public CardTypes getCardType(){
        return mCardType;
    }

    public String getCardType(CardTypes type){
        switch (type){
            case GATE:
                return CARD_GATE;
            case MOBAY:
                return CARD_MOBAY;
            case MOBI:
                return CARD_MOBI;
            case VIETTEL:
                return CARD_VIETTEL;
            case VINA:
                return CARD_VINA;
            case VTC:
                return CARD_VTC;
        }
        return null;
    }

    public void getRequest(){
        WebServiceTask wst = new WebServiceTask(mContext, WebServiceTask.POST_TASK, "Send Request...");
        wst.addNameValuePair("operation", getCardTypeStr());
        wst.addNameValuePair("pinCard", mPinCard);
        wst.addNameValuePair("serial", mSerial);
        wst.addNameValuePair("target", mTarget);
        wst.addNameValuePair("game", mGame);

        wst.addResponseListener(new IAsyncResponse() {
            @Override
            public void onProcessFinished(String resultString) {
                Log.d(TAG, "Responselistener");
                String xmlResult;
                try {
                    JSONObject jsonResult = new JSONObject(resultString);
                    result.transId = jsonResult.getString("transId");
                    result.status = jsonResult.getInt("status");
                    result.statusMsg = jsonResult.getString("statusMsg");
                    result.amount = jsonResult.getInt("amount");
                    result.respTime = jsonResult.getInt("respTime");
                    for(ISunnetEventListener listener:listeners){
                        listener.SunnetEventListener(result);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // the passed String is the URL we will POST to
        wst.execute(new String[]{URL});
    }
}
