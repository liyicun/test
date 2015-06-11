package com.liyicun.test;

import net.sf.json.JSONObject;

public class TestJson {
	public static final int SHENQU_SVID = 213;
	public static final int GOD_MUSIC_QUERY_BY_USER = 39 << 8 | SHENQU_SVID;

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("resid", "========");
		System.out.println(json);
		System.out.println(GOD_MUSIC_QUERY_BY_USER);
		System.out.println(39 << 8 | SHENQU_SVID);

	}
}
