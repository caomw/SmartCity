package com.dc.smartcity.litenet;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.dc.smartcity.litenet.HttpUtils.HttpResponseData;
import com.dc.smartcity.litenet.interf.IResPonseListener;
import com.dc.smartcity.litenet.response.DCResponse;
import com.dc.smartcity.litenet.response.LiteRequest;
import com.dc.smartcity.litenet.response.ResponHead;
import com.dc.smartcity.util.Utils;

public class Api {

	public void makeHttpPost(LiteRequest request,
			final IResPonseListener listener) {

		final HashMap<String, Object> req = new HashMap<String, Object>();
		StringEntity entity = null;
		try {
			Map<String, Object> head = new HashMap<String, Object>();
			// Map<String, Object> body = new HashMap<String, Object>();

			/*
			 * SHA1 sha1=new SHA1(); String
			 * password=sha1.getDigestOfString("111111".getBytes());
			 */

			// for (int i = 0; i < nameValues.length; i += 2) {
			// body.put(nameValues[i], nameValues[i + 1]);
			// }
			// appid,数据库配死，手机端写死
			head.put("appid", Config.APP_ID);
			// 第三个参数appkey数据库配死，手机端写死
			head.put("sign", DataUtil.signRequest(Config.APP_ID, request.body,Config.APP_KEY));
			// 1.0后台不做验签，大于2.0以及以上做验签
			head.put("version", request.version);

			if (!TextUtils.isEmpty(Utils.getAccessTicket())) {
				head.put("accessTicket", Utils.getAccessTicket());
			}
			// body.put("siteId", "320581");

			req.put("head", head);

			req.put("body", request.body);

			entity = new StringEntity(JSON.toJSONString(req), "UTF-8");
			entity.setContentType("application/json;charset=utf8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// try { entity = new UrlEncodedFormEntity(params, "UTF-8");
		// }catch(Exception e){}
		Log.e("Api:", "entity:" + JSON.toJSONString(req));
		makeHttpPost(request.url, entity, listener);
	}

	@SuppressLint("NewApi")
	private void makeHttpPost(final String url, final HttpEntity entity,
			final IResPonseListener listener) {
		new AsyncTask<HttpEntity, Void, HttpResponseData>() {
			@Override
			protected HttpResponseData doInBackground(HttpEntity... params) {
				HttpUtils http = new HttpUtils("", -1, -1);
				HttpResponseData r;
				String realUrl;
				if (url.startsWith("http")) {
					realUrl = url;
				} else if (url.contains("/")) {
					realUrl = Config.BASE_URL + url;
				} else {
					realUrl = Config.SERVER_URL + url;
				}

				r = http.doHttpPost(realUrl, null, params[0], true);
				r.getDataString();
				return r;
			}

			@Override
			protected void onPostExecute(HttpResponseData result) {
				if (listener != null) {
					boolean success = result.getCode() == 200;
					Log.e("test", "response:" + result.getDataString());
					Log.e("test", "response:" + result.getCode());
					String respBody = "";
					String msg = null;
					if (success) {
						DCResponse resp = JSON.parseObject(
								result.getDataString(), DCResponse.class);
						ResponHead head = JSON.parseObject(resp.getHead(),
								ResponHead.class);

						success = ResponHead.CODE_SUCCES.equals(head
								.getRtnCode());
						respBody = resp.getBody();
						msg = head.getRtnMsg();

						if (success) {
							listener.onSuccess(msg, respBody);
						} else {
							if (TextUtils.isEmpty(msg)) {
								msg = "当前服务不可用，请稍后重试";
							}
							listener.onError(head.getRtnCode(), msg);
						}
					} else {
						msg = "当前服务不可用，请稍后重试";
						listener.onError(String.valueOf(result.getCode()), msg);
					}

				}
			}
		}.execute(entity);
	}

}
