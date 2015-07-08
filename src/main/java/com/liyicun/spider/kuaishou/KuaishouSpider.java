package com.liyicun.spider.kuaishou;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
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
				url = url.trim();
				File linkFile = new File("data/kuaishou/html/" + MD5Util.stringMD5(url));
				if (linkFile.exists()) {
					continue;
				}
				Document doc = Jsoup.connect(url).userAgent(HttpDownUtil.USERAGENT).timeout(HttpDownUtil.TIMEOUT).get();// Jsoup.parse(new
				FileUtil.writer(linkFile, doc.html());
				KuaishouSong song = new KuaishouSong();
				song.setDate(doc.getElementsByClass("span-3").select("span").text());
				song.setMp4(doc.getElementsByTag("video").attr("src"));
				song.setFullname(doc.getElementsByClass("span-5").text());
				song.setTitle(doc.getElementsByClass("span-7").text());
				song.setLink(url);
				song.setLinkMd5(MD5Util.stringMD5(url));
				song.setMd5(MD5Util.stringMD5(song.getMp4()));
				song.setMp4Length(HttpDownUtil.httpDownload(song.getMp4(), song.getPath(), song.getLink()));
				datas.add(song.getPath() + "|" + song.getDate() + "|" + song.getMp4Length() + "|" + song.getTitle() + "|" + song.getFullname() + "|" + song.getLink() + "|" + song.getLinkMd5());
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
		FileUtils.copyFile(new File(file), new File("data/kuaishou/log/" + file));
		FileUtils.moveDirectory(new File("data/kuaishou"), new File("data/kuaishou" + new SimpleDateFormat("yyyyMMdd").format(new Date())));
		System.out.println("end");
	}
}
