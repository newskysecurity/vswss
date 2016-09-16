package org.w3c.connectedcars;

import com.google.gson.Gson;

public class RequestResponseParser {
	private static Gson gson = new Gson();
	
	public static Request fromJsonToRequest(String json) {
		Request request = (Request) gson.fromJson(json, Request.class);
		return request;
	}
	
	public static Response fromJsonToResponse(String json) {
		Response response = (Response) gson.fromJson(json, Response.class);
		return response;
	}
	
	public static String toJson(Response response) {
		String json = gson.toJson(response);
		return json;
	}

	public static String toJson(Request request) {
		String json = gson.toJson(request);
		return json;
	}
}
