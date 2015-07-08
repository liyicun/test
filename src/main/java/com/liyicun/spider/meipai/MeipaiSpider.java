package com.liyicun.spider.meipai;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liyicun.spider.util.FileUtil;
import com.liyicun.spider.util.HttpDownUtil;
import com.liyicun.spider.util.MD5Util;

public class MeipaiSpider {

	static String url = "http://www.meipai.com/users/user_timeline?page=%d&count=12&tid=%s&category=0";

	public static String buildURL(int page, String uid) {
		return String.format(url, page, uid);
	}

	private static CloseableHttpClient httpclient;

	private static final int POOL_SIZE = 120;

	private int TIMEOUT_SECONDS = 5;

	public void initApacheHttpClient() {
		// Create global request configuration
		RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(TIMEOUT_SECONDS * 1000).setConnectTimeout(TIMEOUT_SECONDS * 1000).build();
		// Create an HttpClient with the given custom dependencies and
		// configuration.
		httpclient = HttpClients.custom().setUserAgent(HttpDownUtil.USERAGENT).setMaxConnTotal(POOL_SIZE).setMaxConnPerRoute(POOL_SIZE).setDefaultRequestConfig(defaultRequestConfig).build();
	}

	public void destroyApacheHttpClient() {
		try {
			httpclient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		MeipaiSpider meipaiSpider = new MeipaiSpider();
		meipaiSpider.initApacheHttpClient();
		String file = "meipai.txt";
		// http://www.meipai.com/user/37766057
		List<String> list = FileUtil.reader(file);
		List<String> datas = new ArrayList<String>();
		for (String url : list) {
			URL userURL = new URL(url);
			String path = userURL.getPath();
			String uid = path.substring(path.lastIndexOf("/") + 1);
			System.out.println(path + "----------" + uid);
			for (int page = 1;; page++) {
				String spiderUrl = buildURL(page, uid);
				System.out.println(url + "\t" + spiderUrl);
				String result = meipaiSpider.fetchContent(spiderUrl, url);
				JSONObject root = JSON.parseObject(result);
				JSONArray medias = root.getJSONArray("medias");
				if (medias == null || medias.isEmpty()) {
					break;
				}
				try {
					for (Object object : medias) {
						JSONObject data = (JSONObject) object;
						MeipaiSong song = new MeipaiSong();
						song.setDate(data.getString("created_at"));
						song.setTitle(data.getString("caption_origin"));
						song.setVideo(data.getString("video"));
						song.setLink(data.getString("url"));
						song.setMd5(MD5Util.stringMD5(song.getVideo()));
						try {
							song.setNameAll(data.getJSONObject("user").getString("screen_name_origin"));
						} catch (Exception e) {}
						song.setFileLength(HttpDownUtil.httpDownload(song.getVideo(), song.getPath(), song.getLink()));
						datas.add(song.getMd5() + "|" + song.getPath() + "|" + song.getDate() + "|" + song.getFileLength() + "|" + song.getLink() + "|" + song.getTitle() + "|" + song.getNameAll() + "|" + song.getVideo());
						try {
							// Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println("----------" + data.getString("url"));
					}
					if (medias.isEmpty()) {
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					// Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			FileUtil.writer(datas, "data/meipai/log/" + uid + ".ok.log");
			datas.clear();
		}
		meipaiSpider.destroyApacheHttpClient();
		FileUtils.copyFile(new File(file), new File("data/meipai/log/" + file));
		FileUtils.moveDirectory(new File("data/meipai"), new File("data/meipai" + new SimpleDateFormat("yyyyMMdd").format(new Date())));
		System.out.println("end");
	}

	public String fetchContent(String imageUrl, String referer) throws Exception {
		HttpGet httpget = new HttpGet(imageUrl);
		if (referer != null) {
			httpget.setHeader("Referer", referer);
		}
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(entity);
			}
		} finally {
			response.close();
		}
		return "";
	}

	public static String unicodeToString(String str) {
		if (str == null)
			return "";
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4,5}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = str.replace(matcher.group(1), ch + "");
		}
		return str;
	}
}
