package com.springboot.maybank.demo.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonFactory {

	public static Gson create() {
		GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
		return builder.create();
	}

	public static String toJson(final Object obj) {
		return create().toJson(obj);
	}

	@SuppressWarnings("unchecked")
	public static Object fromJson(final String json, final Class cls) {
		return create().fromJson(json, cls);
	}
}
