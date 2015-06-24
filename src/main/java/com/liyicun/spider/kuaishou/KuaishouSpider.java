package com.liyicun.spider.kuaishou;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.liyicun.spider.util.FileUtil;
import com.liyicun.spider.util.HttpDownUtil;
import com.liyicun.spider.util.MD5Util;

public class KuaishouSpider {

	public static void main(String[] args) throws Exception {
		String file = "kuaishou.txt";
		List<String> list = FileUtil.reader(file);
		List<String> datas = new ArrayList<String>();
		int num = 1;
		for (String url : list) {
			System.out.println(list.size() + "\t" + (num++));
			if (url == null || url.trim().length() == 0)
				continue;
			try {
				Document doc = Jsoup.connect(url).userAgent(HttpDownUtil.USERAGENT).timeout(HttpDownUtil.TIMEOUT).get();// Jsoup.parse(new
				KuaishouSong song = new KuaishouSong();
				song.setDate(doc.getElementsByClass("span-3").select("span").text());
				song.setMp4(doc.getElementsByTag("video").attr("src"));
				song.setFullname(doc.getElementsByClass("span-5").text());
				song.setTitle(doc.getElementsByClass("span-7").text());
				song.setLink(url);
				song.setMd5(MD5Util.stringMD5(song.getMp4()));
				song.setMp4Length(HttpDownUtil.httpDownload(song.getMp4(), song.getPath(), song.getLink()));
				datas.add(song.getPath() + "|" + song.getDate() + "|" + song.getMp4Length() + "|" + song.getTitle() + "|" + song.getFullname() + "|" + song.getLink());
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		FileUtil.writer(datas, "data/kuaishou/log/" + file + ".log");
		System.out.println("end");
	}
}
