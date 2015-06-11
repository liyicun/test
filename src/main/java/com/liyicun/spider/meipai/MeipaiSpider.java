package com.liyicun.spider.meipai;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.liyicun.spider.kuaishou.KuaishouSong;
import com.liyicun.spider.util.FileUtil;
import com.liyicun.spider.util.HttpDownUtil;
import com.liyicun.spider.util.MD5Util;

public class MeipaiSpider {

	public static void main(String[] args) throws MalformedURLException, IOException {
		String file = "meipai.txt";
		List<String> list = FileUtil.reader(file);
		List<String> datas = new ArrayList<String>();
		for (String url : list) {
			try {
				Document doc = Jsoup.connect(url).userAgent(HttpDownUtil.USERAGENT).timeout(HttpDownUtil.TIMEOUT).get();// Jsoup.parse(new
				KuaishouSong song = new KuaishouSong();
				song.setDate(doc.getElementsByClass("span-3").select("span").text());
				song.setMp4(doc.getElementsByTag("video").attr("src"));
				song.setTitle(doc.getElementsByClass("span-5").text());
				song.setLink(url);
				song.setMd5(MD5Util.stringMD5(song.getLink()));
				song.setMp4Length(HttpDownUtil.httpDownload(song.getMp4(), song.getPath(), song.getLink()));
				datas.add(song.getPath() + "|" + song.getDate() + "|" + song.getMp4Length() + "|" + song.getLink() + "|" + song.getTitle());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		FileUtil.writer(datas, file + ".ok.txt");
	}
}
