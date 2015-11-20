using UnityEngine;
using System.Collections;

public class AndroidLog : MonoBehaviour
{
		public static bool isDebug = true;

		static AndroidJavaObject getUnityPlayerObject ()
		{
				AndroidJavaClass parentClass = new AndroidJavaClass ("com.unity3d.player.UnityPlayer");
				AndroidJavaObject activityObject = parentClass.GetStatic<AndroidJavaObject> ("currentActivity");
		
				return activityObject;
		}
	
		static AndroidJavaClass prepareLog ()
		{
				return new AndroidJavaClass ("vn.dev.util.lib.Log.LogManager");
		}
	
		public static void showToast (string message)
		{		
				if (isDebug == true) {
						prepareLog ().CallStatic ("showToast", getUnityPlayerObject (), message);
				}
		}

		public static void logError (string tag, string text)
		{
				if (isDebug == true) {
						prepareLog ().CallStatic ("logError", getUnityPlayerObject (), tag, text);
				}
		}

		public static void logDebug (string tag, string text)
		{
				if (isDebug == true) {
						prepareLog ().CallStatic ("logDebug", getUnityPlayerObject (), tag, text);
				}
		}

		public static void logWarning (string tag, string text)
		{
				if (isDebug == true) {
						prepareLog ().CallStatic ("logWarning", getUnityPlayerObject (), tag, text);
				}
		}

		public static void logInfo (string tag, string text)
		{
				if (isDebug == true) {
						prepareLog ().CallStatic ("logInfo", getUnityPlayerObject (), tag, text);
				}
		}

		public static void logVerbose (string tag, string text)
		{
				if (isDebug == true) {
						prepareLog ().CallStatic ("logVerbose", getUnityPlayerObject (), tag, text);
				}
		}
}
