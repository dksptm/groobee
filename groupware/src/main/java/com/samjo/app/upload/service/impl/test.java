package com.samjo.app.upload.service.impl;

import java.util.Arrays;

public class test {
	public static void main(String[] args) {
		byte[] byteArray = {0x66, 0x67, 0x68, 0x69};
		String str = Arrays.toString(byteArray);
		System.out.println(str); // Result: [102, 103, 104, 105]

		System.out.println(str.charAt(0)); // [
		System.out.println(str.charAt(1)); // 1
		System.out.println(str.charAt(2)); // 0
		System.out.println(str.charAt(3)); // 2
	}
}
