using UnityEngine;
using System.Collections;

public class SplayPayment : MonoBehaviour
{
    private static AndroidJavaObject paymentObject;
    private static AndroidJavaObject playerContext;

    public static SplayPayment instance;

    //private GameObject obj;

    public int smsAmmount;
    public string money;

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
                    paymentObject.Call("setObjectName", gameObject.name);
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
        Debug.Log(ammount);
        ShowToastMessage("Bạn đã nạp thành công " + ammount + " " + money);
    }

    public void OnCardError(string error)
    {
        Debug.Log("OnCardError____" + error);
        ShowToastMessage("Lỗi: " + error);
    }

    public void OnSMSSuccess(string message)
    {
        ShowToastMessage("Bạn đã nạp thành công " + smsAmmount + " " + money);
        //MotoState.dola += smsAmmount;
    }

    public void OnSMSFailed(string error)
    {
        ShowToastMessage("Lỗi: " + error);
    }

}
