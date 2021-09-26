package com.mintegral.detailroi.common.base.utils;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SameBase64Tool {
	private static final String TAG = SameBase64Tool.class.getSimpleName();

	private static Map<Character, Character> Base64MapEncoder = null;

	private static Map<Character, Character> Base64MapDecoder = null;

	private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

	private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
			5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26,
			27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
			-1, -1, -1 };

	static {
		Base64MapDecoder = new HashMap<Character, Character>();
		Base64MapDecoder.put('v', 'A');
		Base64MapDecoder.put('S', 'B');
		Base64MapDecoder.put('o', 'C');
		Base64MapDecoder.put('a', 'D');
		Base64MapDecoder.put('j', 'E');
		Base64MapDecoder.put('c', 'F');
		Base64MapDecoder.put('7', 'G');
		Base64MapDecoder.put('d', 'H');
		Base64MapDecoder.put('R', 'I');
		Base64MapDecoder.put('z', 'J');
		Base64MapDecoder.put('p', 'K');
		Base64MapDecoder.put('W', 'L');
		Base64MapDecoder.put('i', 'M');
		Base64MapDecoder.put('f', 'N');
		Base64MapDecoder.put('G', 'O');
		Base64MapDecoder.put('y', 'P');
		Base64MapDecoder.put('N', 'Q');
		Base64MapDecoder.put('x', 'R');
		Base64MapDecoder.put('Z', 'S');
		Base64MapDecoder.put('n', 'T');
		Base64MapDecoder.put('V', 'U');
		Base64MapDecoder.put('5', 'V');
		Base64MapDecoder.put('k', 'W');
		Base64MapDecoder.put('+', 'X');
		Base64MapDecoder.put('D', 'Y');
		Base64MapDecoder.put('H', 'Z');
		Base64MapDecoder.put('L', 'a');
		Base64MapDecoder.put('Y', 'b');
		Base64MapDecoder.put('h', 'c');
		Base64MapDecoder.put('J', 'd');
		Base64MapDecoder.put('4', 'e');
		Base64MapDecoder.put('6', 'f');
		Base64MapDecoder.put('l', 'g');
		Base64MapDecoder.put('t', 'h');
		Base64MapDecoder.put('0', 'i');
		Base64MapDecoder.put('U', 'j');
		Base64MapDecoder.put('3', 'k');
		Base64MapDecoder.put('Q', 'l');
		Base64MapDecoder.put('r', 'm');
		Base64MapDecoder.put('g', 'n');
		Base64MapDecoder.put('E', 'o');
		Base64MapDecoder.put('u', 'p');
		Base64MapDecoder.put('q', 'q');
		Base64MapDecoder.put('8', 'r');
		Base64MapDecoder.put('s', 's');
		Base64MapDecoder.put('w', 't');
		Base64MapDecoder.put('/', 'u');
		Base64MapDecoder.put('X', 'v');
		Base64MapDecoder.put('M', 'w');
		Base64MapDecoder.put('e', 'x');
		Base64MapDecoder.put('B', 'y');
		Base64MapDecoder.put('A', 'z');
		Base64MapDecoder.put('T', '0');
		Base64MapDecoder.put('2', '1');
		Base64MapDecoder.put('F', '2');
		Base64MapDecoder.put('b', '3');
		Base64MapDecoder.put('9', '4');
		Base64MapDecoder.put('P', '5');
		Base64MapDecoder.put('1', '6');
		Base64MapDecoder.put('O', '7');
		Base64MapDecoder.put('I', '8');
		Base64MapDecoder.put('K', '9');
		Base64MapDecoder.put('m', '+');
		Base64MapDecoder.put('C', '/');

		Base64MapEncoder = new HashMap<Character, Character>();
		Base64MapEncoder.put('A', 'v');
		Base64MapEncoder.put('B', 'S');
		Base64MapEncoder.put('C', 'o');
		Base64MapEncoder.put('D', 'a');
		Base64MapEncoder.put('E', 'j');
		Base64MapEncoder.put('F', 'c');
		Base64MapEncoder.put('G', '7');
		Base64MapEncoder.put('H', 'd');
		Base64MapEncoder.put('I', 'R');
		Base64MapEncoder.put('J', 'z');
		Base64MapEncoder.put('K', 'p');
		Base64MapEncoder.put('L', 'W');
		Base64MapEncoder.put('M', 'i');
		Base64MapEncoder.put('N', 'f');
		Base64MapEncoder.put('O', 'G');
		Base64MapEncoder.put('P', 'y');
		Base64MapEncoder.put('Q', 'N');
		Base64MapEncoder.put('R', 'x');
		Base64MapEncoder.put('S', 'Z');
		Base64MapEncoder.put('T', 'n');
		Base64MapEncoder.put('U', 'V');
		Base64MapEncoder.put('V', '5');
		Base64MapEncoder.put('W', 'k');
		Base64MapEncoder.put('X', '+');
		Base64MapEncoder.put('Y', 'D');
		Base64MapEncoder.put('Z', 'H');
		Base64MapEncoder.put('a', 'L');
		Base64MapEncoder.put('b', 'Y');
		Base64MapEncoder.put('c', 'h');
		Base64MapEncoder.put('d', 'J');
		Base64MapEncoder.put('e', '4');
		Base64MapEncoder.put('f', '6');
		Base64MapEncoder.put('g', 'l');
		Base64MapEncoder.put('h', 't');
		Base64MapEncoder.put('i', '0');
		Base64MapEncoder.put('j', 'U');
		Base64MapEncoder.put('k', '3');
		Base64MapEncoder.put('l', 'Q');
		Base64MapEncoder.put('m', 'r');
		Base64MapEncoder.put('n', 'g');
		Base64MapEncoder.put('o', 'E');
		Base64MapEncoder.put('p', 'u');
		Base64MapEncoder.put('q', 'q');
		Base64MapEncoder.put('r', '8');
		Base64MapEncoder.put('s', 's');
		Base64MapEncoder.put('t', 'w');
		Base64MapEncoder.put('u', '/');
		Base64MapEncoder.put('v', 'X');
		Base64MapEncoder.put('w', 'M');
		Base64MapEncoder.put('x', 'e');
		Base64MapEncoder.put('y', 'B');
		Base64MapEncoder.put('z', 'A');
		Base64MapEncoder.put('0', 'T');
		Base64MapEncoder.put('1', '2');
		Base64MapEncoder.put('2', 'F');
		Base64MapEncoder.put('3', 'b');
		Base64MapEncoder.put('4', '9');
		Base64MapEncoder.put('5', 'P');
		Base64MapEncoder.put('6', '1');
		Base64MapEncoder.put('7', 'O');
		Base64MapEncoder.put('8', 'I');
		Base64MapEncoder.put('9', 'K');
		Base64MapEncoder.put('+', 'm');
		Base64MapEncoder.put('/', 'C');
	}

	private SameBase64Tool() {
	}

	public static String encode(byte[] data) {
		StringBuffer sb = new StringBuffer();
		int len = data.length;
		int i = 0;
		int b1, b2, b3;

		while (i < len) {
			b1 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
				sb.append("==");
				break;
			}
			b2 = data[i++] & 0xff;
			if (i == len) {
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
				sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
				sb.append("=");
				break;
			}
			b3 = data[i++] & 0xff;
			sb.append(base64EncodeChars[b1 >>> 2]);
			sb.append(base64EncodeChars[((b1 & 0x03) << 4) | ((b2 & 0xf0) >>> 4)]);
			sb.append(base64EncodeChars[((b2 & 0x0f) << 2) | ((b3 & 0xc0) >>> 6)]);
			sb.append(base64EncodeChars[b3 & 0x3f]);
		}
		return sb.toString();
	}

	public static byte[] decode(String str) {
		byte[] data = str.getBytes();
		int len = data.length;
		ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
		int i = 0;
		int b1, b2, b3, b4;

		while (i < len) {

			/* b1 */
			do {
				b1 = base64DecodeChars[data[i++]];
			} while (i < len && b1 == -1);
			if (b1 == -1) {
				break;
			}

			/* b2 */
			do {
				b2 = base64DecodeChars[data[i++]];
			} while (i < len && b2 == -1);
			if (b2 == -1) {
				break;
			}

			buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

			/* b3 */
			do {
				b3 = data[i++];
				if (b3 == 61) {
					return buf.toByteArray();
				}
				b3 = base64DecodeChars[b3];
			} while (i < len && b3 == -1);
			if (b3 == -1) {
				break;
			}
			buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

			/* b4 */
			do {
				b4 = data[i++];
				if (b4 == 61) {
					return buf.toByteArray();
				}
				b4 = base64DecodeChars[b4];
			} while (i < len && b4 == -1);
			if (b4 == -1) {
				break;
			}
			buf.write((int) (((b3 & 0x03) << 6) | b4));
		}
		return buf.toByteArray();
	}



	public static String newBase64Encode(String source) {
		//如果source是空，就返回空字符串
		if(TextUtils.isEmpty(source)){
			return "";
		}
		return SameMVEncoder.encodeStr(source);
//		String finalSring = "";
//		try {
//			// byte[] bytes = Base64.encode(source.getBytes(),
//			// Base64.NO_WRAP|Base64.URL_SAFE);
//			// String str = new String(bytes);
//			if (!TextUtils.isEmpty(source)) {
//				String str = encode(source.getBytes());
//				// SameLogTool.i(TAG, "source base64 = " + str);
//				char[] arrays = str.toCharArray();
//				char[] newArrays;
//				if (arrays != null && arrays.length > 0) {
//					newArrays = new char[arrays.length];
//					for (int i = 0; i < arrays.length; i++) {
//						newArrays[i] = getBaseEncoder(arrays[i]);
//					}
//					finalSring = new String(newArrays);
//				}
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return finalSring;
	}

	public static String newBase64Decode(String source) {
		return SameMVEncoder.decodeStr(source);
//		String finalSring = "";
//		try {
//			// byte[] bytes = Base64.encode(source.getBytes(),
//			// Base64.NO_WRAP|Base64.URL_SAFE);
//			// String str = new String(bytes);
//			if (!TextUtils.isEmpty(source)) {
//				char[] charArray = source.toCharArray();
//				char[] newArrays;
//				if (charArray != null && charArray.length > 0) {
//					newArrays = new char[charArray.length];
//					for (int i = 0; i < charArray.length; i++) {
//						newArrays[i] = getBaseDecoder(charArray[i]);
//					}
//					finalSring = new String(newArrays);
//				}
//				finalSring = new String(decode(finalSring));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return finalSring;
	}
}