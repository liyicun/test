package com.liyicun.spider.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FileUtil {

	public static List<String> reader(String file) {
		List<String> datas = new ArrayList<String>();
		try {
			File fl = new File(file);
			if (!fl.exists())
				throw new RuntimeException("文件不存在!" + fl);
			InputStream is = new FileInputStream(fl);
			Reader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);
			String data = null;
			while ((data = br.readLine()) != null) {
				datas.add(data);
			}
			br.close();
			reader.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	public static void writer(List<String> datas, String file) {
		try {
			File fl = new File(file);
			File pfile = fl.getParentFile();
			if (pfile != null && !pfile.exists()) {
				pfile.mkdirs();
			}
			OutputStream out = new FileOutputStream(file);
			Writer writer = new OutputStreamWriter(out);
			// FileWriter writer = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(writer);
			for (String str : datas) {
				bw.write(str);
				bw.newLine();
			}
			bw.close();
			writer.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean writer(File file, String con) {
		try {
			File pfile = file.getParentFile();
			if (pfile != null && !pfile.exists()) {
				pfile.mkdirs();
			}
			FileWriter writer = new FileWriter(file);
			writer.write(con);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static void main(String[] args) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
		//
		System.out.println(formatter.format(new Date()));
		//writer(new ArrayList<String>(), "ok.txt");
	}
}
