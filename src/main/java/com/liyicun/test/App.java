package com.liyicun.test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws ParseException {
		long now = System.currentTimeMillis();
		System.out.println(new Date().getTime());
		long b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(
				"2015-03-04 19:11:33").getTime();
		System.out.println(new Date(now));
		System.out.println(new Date(b));
		long stime = now - b;
		System.out.println(now);
		System.out.println(b);
		System.out.println(3084052080l);
		System.out.println();
		System.out.println();
		System.out.println();
		String spaceTime = "";

		if ((stime / (365 * 24 * 3600 * 1000l)) > 1) {
			spaceTime = stime / (365 * 24 * 3600 * 1000l) + "年";
		} else if ((stime / (30 * 24 * 3600 * 1000l)) > 1) {
			spaceTime = stime / (30 * 24 * 3600 * 1000l) + "月";
		} else if ((stime / (24 * 3600 * 1000l)) > 1) {
			spaceTime = stime / (24 * 3600 * 1000l) + "天";
		} else if ((stime / (3600 * 1000l)) > 1) {
			spaceTime = stime / (3600 * 1000l) + "小时";
		} else {
			spaceTime = stime / (60 * 1000l) + "分钟";
		}
		System.out.println(stime);
		System.out.println(stime / (365 * 24 * 60 * 60 * 1000l));
		System.out.println(365 * 24 * 60 * 60 * 1000l);
		System.out.println(spaceTime);

		System.out.println(numToUpper("888880000"));
		System.out.println(numToUpper("555550000"));
		System.out.println(numToUpper("222220000"));
		System.out.println(numToUpper("88888"));
		System.out.println(numToUpper("55555"));
		System.out.println(numToUpper("22222"));
		System.out.println(numToUpper("333"));
	}

	public static String numToUpper(String shareCount) {
		try {
			BigDecimal d = new BigDecimal(shareCount);
			if (d.longValue() > 9999999l) {
				return d.divide(BigDecimal.valueOf(1000 * 10000l), 2,
						BigDecimal.ROUND_HALF_UP) + "千万";
			} else if (d.longValue() > 9999) {
				return d.divide(BigDecimal.valueOf(10000l), 1,
						BigDecimal.ROUND_HALF_UP) + "万";
			} else {
				return shareCount;
			}
		} catch (Exception e) {
			return shareCount;
		}
	}
}
