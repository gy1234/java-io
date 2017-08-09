package com.gy.protobuf.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import com.google.protobuf.ByteString;

public class ByteStringUtils {
	private static final int BYTE_LEN = Byte.SIZE;

	/**
	 * Title: checkLength <br/>
	 * Description: 检查字节数据的长度是否满足要求 <br/>
	 * 
	 * @return: boolean true=符合长度要求
	 */
	private static boolean checkLength(byte[] bytes, int len) {
		return len >= (null == bytes ? 0 : bytes.length);
	}

	/**
	 * Title: boolean2bytes <br/>
	 * Description: 字节数组转boolean <br/>
	 * 
	 * @return: byte[]
	 */
	public static byte[] boolean2bytes(boolean val) {
		return val ? new byte[] { 1 } : new byte[] { 0 };
	}

	/**
	 * Title: bytes2boolean <br/>
	 * Description: 字节数组转boolean <br/>
	 * 
	 * @return: short
	 */
	public static boolean bytes2boolean(byte[] bytes) {
		int byteLen = 1;
		boolean result = false;
		if (checkLength(bytes, byteLen) && 1 == bytes[0]) {
			result = true;
		}
		return result;
	}

	/**
	 * Title: short2bytes <br/>
	 * Description: 字节数组转short <br/>
	 * 
	 * @return: byte[]
	 */
	public static byte[] short2bytes(short val) {
		int byteLen = Short.SIZE / BYTE_LEN;
		byte[] bytes = new byte[byteLen];
		for (int i = 0; i < byteLen; i++) {
			bytes[i] = (byte) ((val >> (BYTE_LEN * (byteLen - 1 - i))) & 0xFF);
		}
		return bytes;
	}

	/**
	 * Title: bytes2short <br/>
	 * Description: 字节数组转short <br/>
	 * 
	 * @return: short
	 */
	public static short bytes2short(byte[] bytes) {
		short result = 0;
		int byteLen = Short.SIZE / BYTE_LEN;
		if (checkLength(bytes, byteLen)) {
			byte b = 0;
			for (int i = 0; i < byteLen; i++) {
				b = bytes[i];
				result |= ((short) (b & 0xFF)) << (BYTE_LEN * (byteLen - 1 - i));
			}
		}
		return result;
	}

	/**
	 * Title: char2bytes <br/>
	 * Description: 字节数组转char <br/>
	 * 
	 * @return: byte[]
	 */
	public static byte[] char2bytes(char val) {
		int byteLen = Character.SIZE / BYTE_LEN;
		byte[] bytes = new byte[byteLen];
		for (int i = 0; i < byteLen; i++) {
			bytes[i] = (byte) ((val >> (BYTE_LEN * (byteLen - 1 - i))) & 0xFF);
		}
		return bytes;
	}

	/**
	 * Title: bytes2char <br/>
	 * Description: 字节数组转char <br/>
	 * 
	 * @return: char
	 */
	public static char bytes2char(byte[] bytes) {
		char result = 0;
		int byteLen = Character.SIZE / BYTE_LEN;
		if (checkLength(bytes, byteLen)) {
			byte b = 0;
			for (int i = 0; i < byteLen; i++) {
				b = bytes[i];
				result |= ((char) (b & 0xFF)) << (BYTE_LEN * (byteLen - 1 - i));
			}
		}
		return result;
	}

	/**
	 * Title: int2bytes <br/>
	 * Description: 字节数组转int <br/>
	 * 
	 * @return: byte[]
	 */
	public static byte[] int2bytes(int val) {
		int byteLen = Integer.SIZE / BYTE_LEN;
		byte[] bytes = new byte[byteLen];
		for (int i = 0; i < byteLen; i++) {
			bytes[i] = (byte) ((val >> (BYTE_LEN * (byteLen - 1 - i))) & 0xFF);
		}
		return bytes;
	}

	/**
	 * Title: bytes2int <br/>
	 * Description: 字节数组转int <br/>
	 * 
	 * @return: int
	 */
	public static int bytes2int(byte[] bytes) {
		int result = 0;
		int byteLen = Integer.SIZE / BYTE_LEN;
		if (checkLength(bytes, byteLen)) {
			byte b = 0;
			for (int i = 0; i < byteLen; i++) {
				b = bytes[i];
				result |= ((int) (b & 0xFF)) << (BYTE_LEN * (byteLen - 1 - i));
			}
		}
		return result;
	}

	/**
	 * Title: long2bytes <br/>
	 * Description: 字节数组转long <br/>
	 * 
	 * @return: byte[]
	 */
	public static byte[] long2bytes(long val) {
		int byteLen = Long.SIZE / BYTE_LEN;
		byte[] bytes = new byte[byteLen];
		for (int i = 0; i < byteLen; i++) {
			bytes[i] = (byte) ((val >> (BYTE_LEN * (byteLen - 1 - i))) & 0xFF);
		}
		return bytes;
	}

	/**
	 * Title: bytes2long <br/>
	 * Description: 字节数组转long <br/>
	 * 
	 * @return: long
	 */
	public static long bytes2long(byte[] bytes) {
		long result = 0;
		int byteLen = Long.SIZE / BYTE_LEN;
		if (checkLength(bytes, byteLen)) {
			byte b = 0;
			for (int i = 0; i < byteLen; i++) {
				b = bytes[i];
				result |= ((long) (b & 0xFF)) << (BYTE_LEN * (byteLen - 1 - i));
			}
		}
		return result;
	}

	/**
	 * Title: float2bytes <br/>
	 * Description: 字节数组转float <br/>
	 * 
	 * @return: byte[]
	 */
	public static byte[] float2bytes(float val) {
		return int2bytes(Float.floatToIntBits(val));
	}

	/**
	 * Title: bytes2float <br/>
	 * Description: 字节数组转float <br/>
	 * 
	 * @return: float
	 */
	public static float bytes2float(byte[] bytes) {
		return Float.intBitsToFloat(bytes2int(bytes));
	}

	/**
	 * Title: double2bytes <br/>
	 * Description: 字节数组转double <br/>
	 * 
	 * @return: byte[]
	 */
	public static byte[] double2bytes(double val) {
		return long2bytes(Double.doubleToLongBits(val));
	}

	/**
	 * Title: bytes2double <br/>
	 * Description: 字节数组转double <br/>
	 * 
	 * @return: double
	 */
	public static double bytes2double(byte[] bytes) {
		return Double.longBitsToDouble(bytes2long(bytes));
	}

	/**
	 * Title: string2bytes <br/>
	 * Description: 字节数组转String <br/>
	 * 
	 * @return: byte[]
	 */
	public static byte[] string2bytes(String val) {
		return null == val || val.isEmpty() ? new byte[0] : val.getBytes();
	}

	/**
	 * Title: bytes2string <br/>
	 * Description: 字节数组转String <br/>
	 * 
	 * @return: double
	 */
	public static String bytes2string(byte[] bytes) {
		return null == bytes || bytes.length == 0 ? "" : new String(bytes);
	}

	/**
	 * Title: string2bytes <br/>
	 * Description: 字节数组转String <br/>
	 * 
	 * @return: byte[]
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] stringUTF82bytes(String val) {
		byte[] result = new byte[0];
		if (null == val || val.isEmpty()) {
			return result;
		}
		try {
			result = val.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Title: bytes2string <br/>
	 * Description: 字节数组转String <br/>
	 * 
	 * @return: double
	 * @throws UnsupportedEncodingException
	 */
	public static String bytes2stringUTF8(byte[] bytes) {
		String result = "";
		if (null == bytes || bytes.length == 0) {
			return result;
		}
		try {
			result = new String(bytes, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static ByteString buildBytes(byte[] bytes) {
		return ByteString.copyFrom(bytes);
	}

	public static ByteString buildBoolean(boolean val) {
		return buildBytes(boolean2bytes(val));
	}

	public static ByteString buildChar(char val) {
		return buildBytes(char2bytes(val));
	}

	public static ByteString buildShort(short val) {
		return buildBytes(short2bytes(val));
	}

	public static ByteString buildInt(int val) {
		return buildBytes(int2bytes(val));
	}

	public static ByteString buildLong(long val) {
		return buildBytes(long2bytes(val));
	}

	public static ByteString buildFloat(float val) {
		return buildBytes(float2bytes(val));
	}

	public static ByteString buildDouble(double val) {
		return buildBytes(double2bytes(val));
	}

	public static ByteString buildString(String val) {
		return buildBytes(stringUTF82bytes(val));
	}

	/**
	 * Title: main <br/>
	 * Description: TODO <br/>
	 * 
	 * @return: void
	 */
	public static void main(String[] args) {
		byte[] bytes;
		// 测试boolean
		boolean b = Boolean.TRUE;
		bytes = boolean2bytes(b);
		System.out.println("boolean 2 bytes, boolean=" + b + ", bytes="
				+ Arrays.toString(bytes));
		b = bytes2boolean(bytes);
		System.out.println("bytes 2 boolean, bytes=" + Arrays.toString(bytes)
				+ ", boolean=" + b);
		// 测试short
		short s = Short.MAX_VALUE - 1;
		bytes = short2bytes(s);
		System.out.println("short 2 bytes, short=" + s + ", bytes="
				+ Arrays.toString(bytes));
		s = bytes2short(bytes);
		System.out.println("bytes 2 short, bytes=" + Arrays.toString(bytes)
				+ ", short=" + s);
		// 测试char
		char c = 'A';
		bytes = char2bytes(c);
		System.out.println("char 2 bytes, char=" + c + ", bytes="
				+ Arrays.toString(bytes));
		c = bytes2char(bytes);
		System.out.println("bytes 2 char, bytes=" + Arrays.toString(bytes)
				+ ", char=" + c);
		// 测试int
		int i = Integer.MAX_VALUE - 1;
		bytes = int2bytes(i);
		System.out.println("int 2 bytes, int=" + i + ", bytes="
				+ Arrays.toString(bytes));
		i = bytes2int(bytes);
		System.out.println("bytes 2 int, bytes=" + Arrays.toString(bytes)
				+ ", int=" + i);
		// 测试long
		long l = Long.MAX_VALUE - 1;
		bytes = long2bytes(l);
		System.out.println("long 2 bytes, long=" + l + ", bytes="
				+ Arrays.toString(bytes));
		l = bytes2long(bytes);
		System.out.println("bytes 2 long, bytes=" + Arrays.toString(bytes)
				+ ", long=" + l);
		// 测试float
		float f = new Float(123.123123F).floatValue();
		bytes = float2bytes(f);
		System.out.println("float 2 bytes, float=" + f + ", bytes="
				+ Arrays.toString(bytes));
		f = bytes2float(bytes);
		System.out.println("bytes 2 float, bytes=" + Arrays.toString(bytes)
				+ ", float=" + f);
		// 测试double
		double d = new Double(987.123123456456D).doubleValue();
		bytes = double2bytes(d);
		System.out.println("double 2 bytes, double=" + d + ", bytes="
				+ Arrays.toString(bytes));
		d = bytes2double(bytes);
		System.out.println("bytes 2 double, bytes=" + Arrays.toString(bytes)
				+ ", double=" + d);
		// 测试string
		String str = "测试中文";
		bytes = string2bytes(str);
		System.out.println("String 2 bytes, String=" + str + ", bytes="
				+ Arrays.toString(bytes));
		str = bytes2string(bytes);
		System.out.println("bytes 2 String, bytes=" + Arrays.toString(bytes)
				+ ", String=" + str);
		ByteString bs = buildString(str);
		bs = ByteString.copyFromUtf8(str);
		System.out.println(bs);

	}

}
