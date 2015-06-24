package com.liyicun.spider.kuaishou;

public class KuaishouSong {

	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	private String fullname;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	private String title;

	private String link;

	private String path;

	private String date;

	private String mp4;

	private String md5;

	private String suffix;

	private long mp4Length;

	public String getPath() {
		path = "data/kuaishou/file/" + md5 + suffix;
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getMp4Length() {
		return mp4Length;
	}

	public void setMp4Length(long mp4Length) {
		this.mp4Length = mp4Length;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String toString() {
		return "title|" + title + "|link|" + link + "|path|" + path + "|date|" + date + "|mp4|" + mp4 + "|md5|" + md5 + "|suffix|" + suffix + "|mp4Length|" + mp4Length;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMp4() {
		return mp4;
	}

	public void setMp4(String mp4) {
		int a = mp4.lastIndexOf(".");
		if (a != -1) {
			suffix = mp4.substring(a);
		}
		this.mp4 = mp4;
	}
}
