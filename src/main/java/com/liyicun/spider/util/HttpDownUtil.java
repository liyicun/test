package com.liyicun.spider.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpDownUtil {

	public static final int TIMEOUT = 5 * 1000;

	public static final String USERAGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36";

	public static long httpDownload(String httpUrl, String saveFile, String baseUrl) {
		return httpDownload(httpUrl, saveFile, null, baseUrl);
	}

	public static long httpDownload(String httpUrl, String saveFile) {
		return httpDownload(httpUrl, saveFile, null, null);
	}

	public static long httpDownload(String httpUrl, String saveFile, Integer timeout, String baseUrl) {
		File sf = new File(saveFile);
		if (sf.exists())
			return sf.length();
		if (sf.getParentFile() != null && !sf.getParentFile().exists()) {
			sf.getParentFile().mkdirs();
		}
		// 下载网络文件
		long bytesum = 0;
		int byteread = 0;
		URL url = null;
		try {
			url = new URL(httpUrl);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(httpUrl);
			return 0l;
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.addRequestProperty("User-Agent", USERAGENT);
			if (baseUrl != null)
				conn.addRequestProperty("Referer", baseUrl);
			if (timeout != null && timeout > 0) {
				conn.setConnectTimeout(timeout);
				conn.setReadTimeout(timeout);
			}
			InputStream inStream = conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(sf);
			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
			}
			fs.close();
			return bytesum;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 0l;
		} catch (IOException e) {
			e.printStackTrace();
			return 0l;
		}
	}
}
