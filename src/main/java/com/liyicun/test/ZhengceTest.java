package com.liyicun.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhengceTest {

	public static void main(String[] args) {
		String a = "send package local ip:14.152.36.50,local port:24675, ip:14.152.36.49,port:3770 ,head : sid=147,cid=713, content : [987940075, {}]";
		
	 	Matcher m = Pattern.compile("sid=([0-9]+),cid=([0-9]+)").matcher(a);
		System.out.println(m.matches());
		while (m.find()) { 
			System.out.println(m.groupCount()+" "+ m.group(0)+" "+m.group(1)+" "+m.group(2));
		}
		 
	}
}
