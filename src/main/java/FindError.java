import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindError {

	public static void main(String[] args) throws IOException {
		String fn = "c:/error.txt";
		String ip = null;
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				String key = args[i];
				if ("-f".equals(key)) {
					fn = args[i + 1];
				} else if ("-ip".equals(key)) {
					ip = args[i + 1];
				}
			}
		}
		File file = new File(fn);
		if (!file.exists()) {
			System.err.println("fileName no find:" + file.getAbsolutePath());
			return;
		}
		FileReader reader = new FileReader(file);
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		List<dataBo> list = new ArrayList<dataBo>();
		dataBo bo = null;
		while ((line = br.readLine()) != null) {
			if (line.indexOf("	at ") != 0) {
				if (line.startsWith("[ERROR]")) {
					if (bo != null) {
						list.add(bo);
					}
					bo = new dataBo();
					bo.setDate(line.substring(8, 18));
				}
				if (bo != null) {
					bo.addError(line);
				}
			}
		}
		if (bo != null) {
			list.add(bo);
		}
		Map<String, Integer> dateError = new HashMap<String, Integer>();
		Map<String, Integer> dateOutTimeError = new HashMap<String, Integer>();
		Map<String, Integer> dateEntError = new HashMap<String, Integer>();
		/* sid=147,cid=713 */
		for (dataBo error : list) {
			String key = "date" + error.getDate();
			Integer count = dateError.get(key);
			if (count == null)
				count = 0;
			count++;
			dateError.put(key, count);
			if (error.isReadTimedOut()) {
				Integer outtimeCount = dateOutTimeError.get(key);
				if (outtimeCount == null)
					outtimeCount = 0;
				outtimeCount++;
				dateOutTimeError.put(key, outtimeCount);
			}
			if (error.isEnt()) {
				key = error.getDate() + "_sid:" + error.getSid() + "_cid:" + error.getCid();
				Integer entCount = dateEntError.get(key);
				if (entCount == null)
					entCount = 0;
				entCount++;
				dateEntError.put(key, entCount);
			}
		}
		System.out.println("ip\tkey\tvalue\tdateOutTimeError\tvalue2");
		for (Entry<String, Integer> data : dateError.entrySet()) {
			System.out.println(ip + "\t" + data.getKey() + "\t" + data.getValue() + "\tdateOutTimeError\t" + dateOutTimeError.get(data.getKey()));
		}
		for (Entry<String, Integer> data : dateEntError.entrySet()) {
			System.out.println(ip + "\t" + data.getKey() + "\t" + data.getValue());
		}
		br.close();
		reader.close();
	}
}

class dataBo {

	String date;

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
		return "dataBo [date=" + date + ", type=" + type + ", isReadTimedOut=" + isReadTimedOut + ", isEnt=" + isEnt + ", sid=" + sid + ", cid=" + cid + ", error=" + error + "]";
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
