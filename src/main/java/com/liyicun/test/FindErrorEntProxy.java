package com.liyicun.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindErrorEntProxy {

	public static void main(String[] args) throws IOException {
		// ip:221.228.83.202,local port:52713, ip:61.147.186.31,port:3770 ,head
		// : sid=164,cid=200, content : [65564904, 0, 102400, {}]
		String path = "C:\\Users\\Administrator\\Downloads\\";
		File root = new File(path);
		int n = 0;
		for (File file1 : root.listFiles()) {
			n+=1000;
			if (file1.isDirectory())
				for (File file : file1.listFiles()) {
					List<dataEntProxy> list = new ArrayList<dataEntProxy>();
					if (!file.isFile()) {
						continue;
					}
					FileReader reader = new FileReader(file);
					BufferedReader br = new BufferedReader(reader);
					System.out.println(file);
					dataEntProxy bo = null;
					String line = null;
					while ((line = br.readLine()) != null) {
						if (line.indexOf("	at ") != 0) {
							if (line.startsWith("[ERROR]")) {
								if (bo != null) {
									list.add(bo);
								}
								bo = new dataEntProxy();
								bo.setDate(line.substring(8, 18));
								bo.setDateTime(line.substring(8, 27));
							}
							if (bo != null) {
								bo.addError(line);
							}
						}
					}
					if (bo != null) {
						list.add(bo);
					}
					br.close();
					reader.close();
					File writename = new File("/" + (++n) + "_output.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件
					writename.createNewFile(); // 创建新文件
					BufferedWriter out = new BufferedWriter(new FileWriter(writename));
					for (dataEntProxy data : list) {
						if (data.isEnt() && "164".equals(data.getSid()) && "200".equals(data.getCid())) {
							// ip:221.228.83.202,local port:52713,
							// ip:61.147.186.31,port:3770 ,head :
							// sid=164,cid=200, content : [65564904, 0, 102400,
							// {}]
							List<String> lst = data.getError();
							String uid = null;
							String ip = null;
							String port = null;
							for (String str : lst) {
								Matcher m1 = Pattern.compile("ip:([0-9\\.]+),port:([0-9]+)\\D+sid=([0-9]+),cid=([0-9]+)\\D+([0-9]+),").matcher(str);
								// [a-z= :,]+countent : \\[([0-9]+), 0, 102400,
								// \\{\\}\\]
								while (m1.find()) {
									ip = m1.group(1);
									port = m1.group(2);
									uid = m1.group(5);
								}
							}
							if (uid == null || uid.length() == 0) {
								continue;
							}
							out.write(data.getDate() + "|" + data.getDateTime());
							out.write("|" + uid);
							out.write("|" + ip);
							out.write("|" + port);
							out.write("\r\n");
						}
					}
					list.clear();
					out.flush(); // 把缓存区内容压入文件
					out.close();
				}
		}
		if (1 == 1)
			return;
		Map<String, Integer> dateError = new HashMap<String, Integer>();
		Map<String, Integer> dateOutTimeError = new HashMap<String, Integer>();
		Map<String, Integer> dateEntError = new HashMap<String, Integer>();
		/* sid=147,cid=713 */
		// for (dataEntProxy error : list) {
		// String key = "date" + error.getDate();
		// Integer count = dateError.get(key);
		// if (count == null)
		// count = 0;
		// count++;
		// dateError.put(key, count);
		// if (error.isReadTimedOut()) {
		// Integer outtimeCount = dateOutTimeError.get(key);
		// if (outtimeCount == null)
		// outtimeCount = 0;
		// outtimeCount++;
		// dateOutTimeError.put(key, outtimeCount);
		// }
		// if (error.isEnt()) {
		// key = error.getDate() + "_sid:" + error.getSid() + "_cid:" +
		// error.getCid();
		// Integer entCount = dateEntError.get(key);
		// if (entCount == null)
		// entCount = 0;
		// entCount++;
		// dateEntError.put(key, entCount);
		// }
		// }
		System.out.println("ip\tkey\tvalue\tdateOutTimeError\tvalue2");
	}
}

class dataEntProxy {

	String date;

	String dateTime;

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	String type;

	boolean isReadTimedOut = false;

	boolean isEnt = false;

	String sid;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	String cid;

	public boolean isEnt() {
		return isEnt;
	}

	public void setEnt(boolean isEnt) {
		this.isEnt = isEnt;
	}

	public boolean isReadTimedOut() {
		return isReadTimedOut;
	}

	public void setReadTimedOut(boolean isReadTimedOut) {
		this.isReadTimedOut = isReadTimedOut;
	}

	List<String> error = new ArrayList<String>();

	@Override
	public String toString() {
		return "dataEntProxy [date=" + date + ", dateTime=" + dateTime + ", type=" + type + ", isReadTimedOut=" + isReadTimedOut + ", isEnt=" + isEnt + ", sid=" + sid + ", cid=" + cid + ", error=" + error + "]";
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getError() {
		return error;
	}

	public void setError(List<String> error) {
		this.error = error;
	}

	public void addError(String error) {
		if (error.indexOf("Read timed out") != -1)
			isReadTimedOut = true;
		if (!isEnt) {
			Matcher m = Pattern.compile("sid=([0-9]+),cid=([0-9]+)").matcher(error);
			while (m.find()) {
				if (m.groupCount() == 2) {
					sid = m.group(1);
					cid = m.group(2);
					isEnt = true;
				}
			}
		}
		int sid = error.indexOf("sid=");
		if (sid != -1) {
			if (error.indexOf("cid=", sid) != -1) {
				isEnt = true;
			}
		}
		this.error.add(error);
	}
}
