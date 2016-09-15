package org.w3c.connectedcars;

import com.google.gson.Gson;

public class RequestResponseParser {
	private static Gson gson = new Gson();
	
	public static Request fromJson(String json) {
		Request request = gson.fromJson(json, Request.class);
		return request;
	}
	
	public static String toJson(Response response) {
		String json = gson.toJson(response);
		return json;
	}
}
