package com.splay.libs.payment;

/**
 * Created by LAM on 10/26/2015.
 */
public class Result {
    public String transId;
    public int status;
    public String statusMsg;
    public int amount;
    public int respTime;

    @Override
    public String toString(){
        return String.format("transID: %s\ntransID: %s\nstatus: %d\nstatusMsg: %s\namount: %d\nrespTime: %d\n", transId, transId, status, statusMsg, amount, respTime);
    }
}
