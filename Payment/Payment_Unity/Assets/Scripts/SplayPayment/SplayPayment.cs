using UnityEngine;
using System.Collections;

public class SplayPayment : MonoBehaviour
{
    private static AndroidJavaObject paymentObject;
    private static AndroidJavaObject playerContext;

    public static SplayPayment instance;

    //private GameObject obj;

    public int smsAmmount = 15000;
    public string money = "dollar";

    public string GAMENAME = "MORDENWORLDWAR";

    void Start()
    {
        instance = this;
        init();
    }

    void init()
    {
        if (paymentObject == null)
        {
            using (var actClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer"))
            {
                playerContext = actClass.GetStatic<AndroidJavaObject>("currentActivity");
            }

            using (var pluginClass = new AndroidJavaClass("com.splay.libs.payment.UnityController"))
            {
                if (pluginClass != null)
                {
                    paymentObject = pluginClass.CallStatic<AndroidJavaObject>("getInstance");
                    paymentObject.Call("setActivity", playerContext);
                    paymentObject.Call("setObjectName", name);
                    paymentObject.Call("setGameName", GAMENAME);
                }
            }
        }
    }

    public void ShowGui()
    {
        instance.init();
        playerContext.Call("runOnUiThread", new AndroidJavaRunnable(() =>
            {
                paymentObject.Call("showGui");
            }));
    }

    void ShowToastMessage(string msg)
    {
        AndroidLog.showToast(msg);
    }

    public void OnCardSuccess(string ammount)
    {
        int coinAmmount = int.Parse(ammount);

        ShowToastMessage("Bạn đã nạp thành công " + coinAmmount + " " + money);
        //paymentObject.Call("ShowAlert", "Bạn đã nạp thành công " + ammount + " " + money);
        UserProfile.coin += coinAmmount;
    }

    public void OnCardError(string error)
    {
        Debug.Log("OnCardError____" + error);
        ShowToastMessage("Lỗi: " + error);
    }

    public void OnSMSSuccess(string message)
    {
        ShowToastMessage("Bạn đã nạp thành công " + smsAmmount + " " + money);
        UserProfile.coin += smsAmmount;
    }

    public void OnSMSFailed(string error)
    {
        ShowToastMessage("Lỗi: " + error);
    }

}
