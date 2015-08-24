package com.dc.smartcity.litenet.response;

import com.dc.smartcity.litenet.Config;

import java.util.HashMap;
import java.util.Map;

public class LiteRequest {
		public String version = Config.version2;
		public String url;
		public Map<String, Object> body;

		public LiteRequest(String url) {
			this.url = url;
			body = new HashMap<String, Object>();
		}

		public LiteRequest(String url, Map<String, Object> body) {
			this.url = url;
			this.body = body;
		}

		public LiteRequest() {
			body = new HashMap<String, Object>();
		}
}
