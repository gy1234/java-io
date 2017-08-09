
package com.gy.netty.util;

import java.util.Arrays;

public class DataConvertUtils {
	// 从低位到高位一次存放int每个字节对应的byte值
	public static byte[] intToBytes (int n) {
		/**
		 *  return (memory[index]   & 0xff) << 24 |
               (memory[index + 1] & 0xff) << 16 |
               (memory[index + 2] & 0xff) <<  8 |
                memory[index + 3] & 0xff;
		 */
      byte[] b = new byte[4];  
//      b[0] = (byte) (n & 0xff);  
//      b[1] = (byte) (n >> 8 & 0xff);
//      b[2] = (byte) (n >> 16 & 0xff);
//      b[3] = (byte) (n >> 24 & 0xff);
      b[0] = (byte) (n >>> 24);  
      b[1] = (byte) (n >>> 16);
      b[2] = (byte) (n >>> 8);
      b[3] = (byte) (n);
		return b;
	}
	// byte字节数组转成int
	public static int bytes2Int (byte[] byteVal) {
		int result = 0;
		for (int i = 0; i < byteVal.length; i++) {
			int tmpVal = (byteVal[i] << (8 * (3 - i)));
			switch (i) {
			case 0:
				tmpVal = tmpVal & 0xFF000000;
				break;
			case 1:
				tmpVal = tmpVal & 0x00FF0000;
				break;
			case 2:
				tmpVal = tmpVal & 0x0000FF00;
				break;
			case 3:
				tmpVal = tmpVal & 0x000000FF;
				break;
			}
			result = result | tmpVal;
		}
		return result;
	}

	/** @param args */
	public static void main (String[] args) {
		int i = 255;
		System.out.println(Arrays.toString((intToBytes(i))));
//		System.out.println(Integer.toBinaryString(bytes2Int(intToBytes(i))));
//		
//		byte[] b = {115, 117, 101, 115, 115};
		
		
		
		
		
	}

}
