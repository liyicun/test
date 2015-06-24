package com.liyicun.spider.meipai;

public class MeipaiSong {

	private String title;

	private String video;

	private String link;

	private String nameAll;

	public String getNameAll() {
		return nameAll;
	}

	public void setNameAll(String nameAll) {
		this.nameAll = nameAll;
	}

	private String playerLink;

	private String path;

	private String date;

	private String md5;

	private String suffix;

	private long fileLength;

	public String getPath() {
		path = "data/meipai/file/" + md5 + suffix;
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public String getPlayerLink() {
		return playerLink;
	}

	public void setPlayerLink(String playerLink) {
		this.playerLink = playerLink;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	public String toString() {
		return "MeipaiSong [title=" + title + ", video=" + video + ", link=" + link + ", playerLink=" + playerLink + ", path=" + path + ", date=" + date + ", md5=" + md5 + ", suffix=" + suffix + ", fileLength=" + fileLength + "]";
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

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		int a = video.lastIndexOf(".");
		if (a != -1) {
			suffix = video.substring(a);
		}
		this.video = video;
	}
}
