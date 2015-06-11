package com.liyicun.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		// java
		System.out.println("MAX_VALUE = " + Integer.MAX_VALUE);
		System.out.println("MIN_VALUE = " + Integer.MIN_VALUE);
		System.out.println("MAX_VALUE+1 = " + (Integer.MAX_VALUE + 1));
		System.out.println("MIN_VALUE-1 = " + (Integer.MIN_VALUE - 1));
		System.out.println("MAX_VALUE+2 = " + (Integer.MAX_VALUE + 2));
		System.out.println("MIN_VALUE-1 = " + (Integer.MIN_VALUE - 2));
	}

	public void testLong() {
		// java
		System.out.println("Long.MAX_VALUE = " + Long.MAX_VALUE);
		System.out.println("Long.MIN_VALUE = " + Long.MIN_VALUE);
		System.out.println("Long.MAX_VALUE+1 = " + (Long.MAX_VALUE + 1));
		System.out.println("Long.MIN_VALUE-1 = " + (Long.MIN_VALUE - 1));
		System.out.println("Long.MAX_VALUE+2 = " + (Long.MAX_VALUE + 2));
		System.out.println("Long.MIN_VALUE-1 = " + (Long.MIN_VALUE - 2));
	}

	public void testFloat() {
		// java
		System.out.println("Float.MAX_VALUE = " + Float.MAX_VALUE);
		System.out.println("Float.MIN_VALUE = " + Float.MIN_VALUE);
		System.out.println("Float.MAX_VALUE+1 = " + (Float.MAX_VALUE + 1));
		System.out.println("Float.MIN_VALUE-1 = " + (Float.MIN_VALUE - 1));
		System.out.println("Float.MAX_VALUE+2 = " + (Float.MAX_VALUE + 2));
		System.out.println("Float.MIN_VALUE-1 = " + (Float.MIN_VALUE - 2));
		Float a = 0.1f;
		Float b = 0.2f;
		System.out.println("a + b = " + (a + b));
		System.out.println("(a + b) == 0.3f " + ((a + b) == 0.3f));
	}
}
