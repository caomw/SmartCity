package com.dfh.plugin;

import java.util.HashMap;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;

import com.dc.smartcity.activity.LoginActivity;
import com.dc.smartcity.litenet.Config;
import com.dc.smartcity.util.Utils;

public class DFHPlugin extends CordovaPlugin {
	private static final String ACTION_ACCESSTOKEN = "ACCESSTOKEN";
	private static final String ACTION_LOGIN = "LOGIN";
	private static final String ACTION_USERINFO = "USERINFO";
	private static final String TAG = "DFHPlugin";
	private static final HashMap<String, CallbackContext> callbacks = new HashMap();

	public static CallbackContext retriveCallback(String paramString) {
		CallbackContext localCallbackContext = null;
		if (paramString == null) {

			localCallbackContext = null;
		} else {
			localCallbackContext = callbacks
					.remove(paramString);
		}
		return localCallbackContext;
	}

	private static String saveCallbackContext(
			CallbackContext paramCallbackContext) {
		String str = null;
		try {
			str = Long
					.toHexString((long) (System.currentTimeMillis() + 10000 * Math
							.random()));
			callbacks.put(str, paramCallbackContext);

		} catch (Exception e) {
		}
		return str;

	}

	public boolean execute(String paramString, CordovaArgs paramCordovaArgs,
			final CallbackContext paramCallbackContext) throws JSONException {
		if (TextUtils.isEmpty(paramString)) {
			return false;
		}

		if (paramString.equals(ACTION_LOGIN)) {
			Intent localIntent = new Intent(this.cordova.getActivity(),
					LoginActivity.class);
			localIntent.putExtra("CallbackContext",
					saveCallbackContext(paramCallbackContext));
			this.cordova.getActivity().startActivity(localIntent);
			return true;
		}

		if (paramString.equals(ACTION_ACCESSTOKEN)) {
			if (Utils.isLogon()) {
				paramCallbackContext.success(Utils.getAccessTicket());
				return true;
			} else {
				paramCallbackContext.error("Not logined!");
				return false;
			}
		}

		if (paramString.equals(ACTION_USERINFO)) {
			JSONObject localJSONObject5 = new JSONObject();
			if (Utils.user != null) {
				localJSONObject5.put("userid", Utils.user.userBase.userid);
				localJSONObject5.put("login", Utils.user.userBase.login);
				localJSONObject5.put("nickname", Utils.user.userBase.name);
				localJSONObject5.put("name", Utils.user.userBase.name);
				localJSONObject5.put("level", Utils.user.userBase.level);
				localJSONObject5.put("headphotourl",
						Utils.user.userBase.headphotourl);
				localJSONObject5
						.put("mobilenum", Utils.user.userAuth.mobilenum);
				localJSONObject5.put("sex", Utils.user.userBase.sex);
				localJSONObject5.put("birthday", Utils.user.userBase.birthday);
				localJSONObject5.put("email", Utils.user.userAuth.email);
				localJSONObject5.put("mobileisbound",
						Utils.user.userAuth.mobileisbound);
				localJSONObject5.put("emailisbound",
						Utils.user.userAuth.emailisbound);
				localJSONObject5.put("idcardcode",
						Utils.user.userAuth.idcardcode);
				localJSONObject5.put("siteid", Config.cityCode);

				PluginResult localPluginResult3 = new PluginResult(
						PluginResult.Status.OK, localJSONObject5);
				paramCallbackContext.sendPluginResult(localPluginResult3);
				return true;
			} else {
				localJSONObject5.put("siteid", Config.cityCode);
				PluginResult localPluginResult3 = new PluginResult(
						PluginResult.Status.ERROR, localJSONObject5);
				paramCallbackContext.sendPluginResult(localPluginResult3);
				return false;
			}
		}
		if (paramString.equals("ON_EVENT")) {
			// JSONObject localJSONObject4 = paramCordovaArgs.optJSONObject(0);
			// String str15 = localJSONObject4.optString("eventid");
			// String str16 = localJSONObject4.optString("eventlabel");
			// String str17 = localJSONObject4.optString("serviceid");
			// String str18 = localJSONObject4.optString("appversion");
			// String str19 = "";
			// if (Utils.isLogon()){
			//
			// str19 = Utils.user.userBase.userid;
			// }
			// String str20 = Config.cityCode;
			// UmsAgent.onEvent(this.cordova.getActivity(), str15);
			return true;
		}

		if (paramString.equals("DFH_LOG")) {
			JSONObject localJSONObject3 = paramCordovaArgs.optJSONObject(0);
			String str12 = localJSONObject3.optString("appid");
			String str13 = localJSONObject3.optString("appversion");
			String str14 = localJSONObject3.optString("content");
		} else if (paramString.equals("GET_HTTP_HEAD")) {
			JSONObject localJSONObject1 = new JSONObject();
			localJSONObject1.put("appkey", Config.APP_KEY);
			localJSONObject1.put("appid", Config.APP_ID);
			localJSONObject1.put("version", "2.0");
			PluginResult localPluginResult1 = new PluginResult(
					PluginResult.Status.OK, localJSONObject1);
			paramCallbackContext.sendPluginResult(localPluginResult1);
		}
		if (paramString.equals("LOGIN_OUT")) {
			this.cordova.getActivity().finish();
			paramCallbackContext.success();
		} else if (paramString.equals("ALL_INFO")) {
			JSONObject localJSONObject2 = new JSONObject();
			if (Utils.isLogon()) {
				localJSONObject2.put("accessTicket", Utils.getAccessTicket());
				localJSONObject2.put("logined", true);
				localJSONObject2.put("userid", Utils.user.userBase.userid);
				localJSONObject2.put("login", Utils.user.userBase.login);
				localJSONObject2.put("nickname", Utils.user.userBase.name);
				localJSONObject2.put("name", Utils.user.userBase.name);
				localJSONObject2.put("level", Utils.user.userBase.level);
				localJSONObject2.put("headphotourl",
						Utils.user.userBase.headphotourl);
				localJSONObject2
						.put("mobilenum", Utils.user.userAuth.mobilenum);
				localJSONObject2.put("sex", Utils.user.userBase.sex);
				localJSONObject2.put("birthday", Utils.user.userBase.birthday);
				localJSONObject2.put("email", Utils.user.userAuth.email);
				localJSONObject2.put("mobileisbound",
						Utils.user.userAuth.mobileisbound);
				localJSONObject2.put("emailisbound",
						Utils.user.userAuth.emailisbound);
				localJSONObject2.put("idcardcode",
						Utils.user.userAuth.idcardcode);
				localJSONObject2.put("siteid", Config.cityCode);
			} else {
				localJSONObject2.put("appkey", Config.APP_KEY);
				localJSONObject2.put("appid", Config.APP_ID);
				localJSONObject2.put("version", "2.0");
				localJSONObject2.put("logined", false);

			}
			PluginResult localPluginResult2 = new PluginResult(
					PluginResult.Status.OK, localJSONObject2);
			paramCallbackContext.sendPluginResult(localPluginResult2);
		}

		return true;
		// if (paramString.equals("ADD_FAVORITE"))
		// {
		// paramCallbackContext.disable();
		// String str9 =
		// paramCordovaArgs.optJSONObject(0).getString("serviceid");
		// String str10 = SpUtils.getStringToSp(this.cordova.getActivity(),
		// "selected_city_code");
		// String str11 = SpUtils.getStringToSp(this.cordova.getActivity(),
		// "current_access_ticket");
		// UserProxy.getInstance(this.cordova.getActivity()).collectionService(str9,
		// str10, str11, new UserProxy.VertifyLoginCallback()
		// {
		// public void onFailed(String paramAnonymousString)
		// {
		// paramCallbackContext.enable();
		// paramCallbackContext.success("999999");
		// }
		//
		// public void onSuccess(String paramAnonymousString)
		// {
		// paramCallbackContext.enable();
		// paramCallbackContext.success("000000");
		// }
		// });
		// }
		// else if (paramString.equals("REMOVE_FAVORITE"))
		// {
		// paramCallbackContext.disable();
		// String str5 =
		// paramCordovaArgs.optJSONObject(0).getString("serviceid");
		// String str6 = SpUtils.getStringToSp(this.cordova.getActivity(),
		// "selected_city_code");
		// String str7 = SpUtils.getStringToSp(this.cordova.getActivity(),
		// "current_access_ticket");
		// final String str8 = saveCallbackContext(paramCallbackContext);
		// UserProxy.getInstance(this.cordova.getActivity()).cancelCollection(str5,
		// str6, str7, new UserProxy.VertifyLoginCallback()
		// {
		// public void onFailed(String paramAnonymousString)
		// {
		// CallbackContext localCallbackContext =
		// DFHPlugin.retriveCallback(str8);
		// localCallbackContext.enable();
		// localCallbackContext.success("999999");
		// }
		//
		// public void onSuccess(String paramAnonymousString)
		// {
		// CallbackContext localCallbackContext =
		// DFHPlugin.retriveCallback(str8);
		// localCallbackContext.enable();
		// localCallbackContext.success("000000");
		// }
		// });
		// }
		// else if (paramString.equals("IS_FAVORITE"))
		// {
		// paramCallbackContext.disable();
		// String str1 =
		// paramCordovaArgs.optJSONObject(0).getString("serviceid");
		// String str2 = SpUtils.getStringToSp(this.cordova.getActivity(),
		// "selected_city_code");
		// String str3 = SpUtils.getStringToSp(this.cordova.getActivity(),
		// "current_access_ticket");
		// final String str4 = saveCallbackContext(paramCallbackContext);
		// UserProxy.getInstance(this.cordova.getActivity()).isServiceCollected(str1,
		// str2, str3, new UserProxy.VertifyLoginCallback()
		// {
		// public void onFailed(String paramAnonymousString)
		// {
		// JSONObject localJSONObject = new JSONObject();
		// try
		// {
		// localJSONObject.put("status", "999999");
		// CallbackContext localCallbackContext =
		// DFHPlugin.retriveCallback(str4);
		// localCallbackContext.enable();
		// localCallbackContext.success(localJSONObject);
		// return;
		// }
		// catch (JSONException localJSONException)
		// {
		// while (true)
		// {
		// Log.e("DFHPlugin", localJSONException.toString());
		// localJSONException.printStackTrace();
		// }
		// }
		// }

		// public void onSuccess(String paramAnonymousString)
		// {
		// JSONObject localJSONObject = new JSONObject();
		// try
		// {
		// localJSONObject.put("status", "000000");
		// localJSONObject.put("result", paramAnonymousString);
		// CallbackContext localCallbackContext =
		// DFHPlugin.retriveCallback(str4);
		// localCallbackContext.enable();
		// localCallbackContext.success(localJSONObject);
		// return;
		// }
		// catch (JSONException localJSONException)
		// {
		// while (true)
		// {
		// Log.e("DFHPlugin", localJSONException.toString());
		// localJSONException.printStackTrace();
		// }
		// }
		// }
		// });
	}
}
