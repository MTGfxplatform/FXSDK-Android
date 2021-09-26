/**
 * 
 */
package com.mintegral.detailroi.common.base.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * The implementation's mechanism is similar to BASE64, but encode table is not
 * same. So, it can't be decode by BASE64 decoder
 * 
 * @author chaocai 
 * Created at 2018.1.11
 * MBridge.com
 */
public class SameMVEncoder {
	private static Map<Character, Character> Base64MapEncoder;
	private static Map<Character, Character> Base64MapDecoder;
	private static final char[] S_BASE64_CHAR = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '+', '/' };
	private static char[] S_MV_ENCODER_CHAR;
//	private static char[] S_MV_DECODER_CHAR;

//	static {
//		Base64MapDecoder = new HashMap<Character, Character>();
//		Base64MapDecoder.put('v', 'A');
//		Base64MapDecoder.put('S', 'B');
//		Base64MapDecoder.put('o', 'C');
//		Base64MapDecoder.put('a', 'D');
//		Base64MapDecoder.put('j', 'E');
//		Base64MapDecoder.put('c', 'F');
//		Base64MapDecoder.put('7', 'G');
//		Base64MapDecoder.put('d', 'H');
//		Base64MapDecoder.put('R', 'I');
//		Base64MapDecoder.put('z', 'J');
//		Base64MapDecoder.put('p', 'K');
//		Base64MapDecoder.put('W', 'L');
//		Base64MapDecoder.put('i', 'M');
//		Base64MapDecoder.put('f', 'N');
//		Base64MapDecoder.put('G', 'O');
//		Base64MapDecoder.put('y', 'P');
//		Base64MapDecoder.put('N', 'Q');
//		Base64MapDecoder.put('x', 'R');
//		Base64MapDecoder.put('Z', 'S');
//		Base64MapDecoder.put('n', 'T');
//		Base64MapDecoder.put('V', 'U');
//		Base64MapDecoder.put('5', 'V');
//		Base64MapDecoder.put('k', 'W');
//		Base64MapDecoder.put('+', 'X');
//		Base64MapDecoder.put('D', 'Y');
//		Base64MapDecoder.put('H', 'Z');
//		Base64MapDecoder.put('L', 'a');
//		Base64MapDecoder.put('Y', 'b');
//		Base64MapDecoder.put('h', 'c');
//		Base64MapDecoder.put('J', 'd');
//		Base64MapDecoder.put('4', 'e');
//		Base64MapDecoder.put('6', 'f');
//		Base64MapDecoder.put('l', 'g');
//		Base64MapDecoder.put('t', 'h');
//		Base64MapDecoder.put('0', 'i');
//		Base64MapDecoder.put('U', 'j');
//		Base64MapDecoder.put('3', 'k');
//		Base64MapDecoder.put('Q', 'l');
//		Base64MapDecoder.put('r', 'm');
//		Base64MapDecoder.put('g', 'n');
//		Base64MapDecoder.put('E', 'o');
//		Base64MapDecoder.put('u', 'p');
//		Base64MapDecoder.put('q', 'q');
//		Base64MapDecoder.put('8', 'r');
//		Base64MapDecoder.put('s', 's');
//		Base64MapDecoder.put('w', 't');
//		Base64MapDecoder.put('/', 'u');
//		Base64MapDecoder.put('X', 'v');
//		Base64MapDecoder.put('M', 'w');
//		Base64MapDecoder.put('e', 'x');
//		Base64MapDecoder.put('B', 'y');
//		Base64MapDecoder.put('A', 'z');
//		Base64MapDecoder.put('T', '0');
//		Base64MapDecoder.put('2', '1');
//		Base64MapDecoder.put('F', '2');
//		Base64MapDecoder.put('b', '3');
//		Base64MapDecoder.put('9', '4');
//		Base64MapDecoder.put('P', '5');
//		Base64MapDecoder.put('1', '6');
//		Base64MapDecoder.put('O', '7');
//		Base64MapDecoder.put('I', '8');
//		Base64MapDecoder.put('K', '9');
//		Base64MapDecoder.put('m', '+');
//		Base64MapDecoder.put('C', '/');
//		S_MV_DECODER_CHAR = new char[S_BASE64_CHAR.length];
//		for (int i = 0; i < S_BASE64_CHAR.length; i++) {
//			S_MV_DECODER_CHAR[i] = Base64MapDecoder.get(S_BASE64_CHAR[i]);
//		}
//
//	}
	static {
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
		S_MV_ENCODER_CHAR = new char[S_BASE64_CHAR.length];
		for (int i = 0; i < S_BASE64_CHAR.length; i++) {
			S_MV_ENCODER_CHAR[i] = Base64MapEncoder.get(S_BASE64_CHAR[i]);
		}

	}

	private static final char S_PAD = '=';
	private static final byte[] S_DECODETABLE = new byte[128];
	static {
		for (int i = 0; i < S_DECODETABLE.length; i++) {
			S_DECODETABLE[i] = Byte.MAX_VALUE; // 127
		}
		for (int i = 0; i < S_MV_ENCODER_CHAR.length; i++) // 0 to 63
		{
			S_DECODETABLE[S_MV_ENCODER_CHAR[i]] = (byte) i;
		}
	}

	private static int decode0(char[] ibuf, byte[] obuf, int wp) {
		try {
			int outlen = 3;
			if (ibuf[3] == S_PAD) {
				outlen = 2;
			}
			if (ibuf[2] == S_PAD) {
				outlen = 1;
			}
			int b0 = S_DECODETABLE[ibuf[0]];
			int b1 = S_DECODETABLE[ibuf[1]];
			int b2 = S_DECODETABLE[ibuf[2]];
			int b3 = S_DECODETABLE[ibuf[3]];
			switch (outlen) {
				case 1:
					obuf[wp] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
					return 1;
				case 2:
					obuf[wp++] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
					obuf[wp] = (byte) (b1 << 4 & 0xf0 | b2 >> 2 & 0xf);
					return 2;
				case 3:
					obuf[wp++] = (byte) (b0 << 2 & 0xfc | b1 >> 4 & 0x3);
					obuf[wp++] = (byte) (b1 << 4 & 0xf0 | b2 >> 2 & 0xf);
					obuf[wp] = (byte) (b2 << 6 & 0xc0 | b3 & 0x3f);
					return 3;
				default:
					throw new RuntimeException("Internal Error");
			}
		}catch (Exception e){

		}
		return 0;
	}

	/**
	 * Decode the data.
	 * 
	 * @param data
	 *            The encoded data to be decoded
	 * @param off
	 *            The offset within the encoded data at which to start decoding
	 * @param len
	 *            The length of data to decode
	 * @return The decoded data
	 */
	public static byte[] decode(char[] data, int off, int len) {
		try {
			char[] ibuf = new char[4];
			int ibufcount = 0;
			byte[] obuf = new byte[(len >> 2) * 3 + 3];
			int obufcount = 0;
			for (int i = off; i < off + len; i++) {
				char ch = data[i];
				if (ch == S_PAD || ch < S_DECODETABLE.length && S_DECODETABLE[ch] != Byte.MAX_VALUE) {
					ibuf[ibufcount++] = ch;
					if (ibufcount == ibuf.length) {
						ibufcount = 0;
						obufcount += decode0(ibuf, obuf, obufcount);
					}
				}
			}
			if (obufcount == obuf.length) {
				return obuf;
			}
			byte[] ret = new byte[obufcount];
			System.arraycopy(obuf, 0, ret, 0, obufcount);
			return ret;
		}catch (Exception e){

		}
		return null;
	}

	public static final int BUF_SIZE = 256;

	/**
	 * Decode the data.
	 *
	 * @param data
	 *            The encoded data to be decoded
	 * @return The decoded data
	 */
	public static String decodeStr(String data) {
		byte[] bytes = decode(data);
		if(bytes != null && bytes.length > 0){
			return new String(bytes);
		}
		return null;
	}

	/**
	 * Decode the data.
	 * 
	 * @param data
	 *            The encoded data to be decoded
	 * @return The decoded data
	 */
	public static byte[] decode(String data) {
		try{
			int ibufcount = 0;
			int slen = data.length();
			char[] ibuf = new char[slen < BUF_SIZE + 3 ? slen : BUF_SIZE + 3];
			byte[] obuf = new byte[(slen >> 2) * 3 + 3];
			int obufcount = 0;
			int blen;

			for (int i = 0; i < slen; i += BUF_SIZE) {
				// buffer may contain unprocessed characters from previous step
				if (i + BUF_SIZE <= slen) {
					data.getChars(i, i + BUF_SIZE, ibuf, ibufcount);
					blen = BUF_SIZE + ibufcount;
				} else {
					data.getChars(i, slen, ibuf, ibufcount);
					blen = slen - i + ibufcount;
				}

				for (int j = ibufcount; j < blen; j++) {
					char ch = ibuf[j];
					if (ch == S_PAD || ch < S_DECODETABLE.length && S_DECODETABLE[ch] != Byte.MAX_VALUE) {
						ibuf[ibufcount++] = ch;
						// as soon as we have 4 chars process them
						if (ibufcount == 4) {
							ibufcount = 0;
							obufcount += decode0(ibuf, obuf, obufcount);
						}
					}
				}
			}
			if (obufcount == obuf.length) {
				return obuf;
			}
			byte[] ret = new byte[obufcount];
			System.arraycopy(obuf, 0, ret, 0, obufcount);
			return ret;
		}catch (Exception e){

		}
		return null;
	}

	/**
	 * Returns representation of specified byte array.
	 *
	 * @param data
	 *            The data to be encoded
	 * @return The encoded data
	 */
	public static String encodeStr(String data) {
		return encode(data.getBytes());
	}

	/**
	 * Returns representation of specified byte array.
	 * 
	 * @param data
	 *            The data to be encoded
	 * @return The encoded data
	 */
	public static String encode(byte[] data) {
		return encode(data, 0, data.length);
	}

	/**
	 * Returns base64 representation of specified byte array.
	 * 
	 * @param data
	 *            The data to be encoded
	 * @param off
	 *            The offset within the data at which to start encoding
	 * @param len
	 *            The length of the data to encode
	 * @return The base64 encoded data
	 */
	public static String encode(byte[] data, int off, int len) {
		try{
			if (len <= 0) {
				return "";
			}
			char[] out = new char[(len / 3 << 2) + 4];
			int rindex = off;
			int windex = 0;
			int rest = len;
			while (rest >= 3) {
				int i = ((data[rindex] & 0xff) << 16) + ((data[rindex + 1] & 0xff) << 8) + (data[rindex + 2] & 0xff);
				out[windex++] = S_MV_ENCODER_CHAR[i >> 18];
				out[windex++] = S_MV_ENCODER_CHAR[(i >> 12) & 0x3f];
				out[windex++] = S_MV_ENCODER_CHAR[(i >> 6) & 0x3f];
				out[windex++] = S_MV_ENCODER_CHAR[i & 0x3f];
				rindex += 3;
				rest -= 3;
			}
			if (rest == 1) {
				int i = data[rindex] & 0xff;
				out[windex++] = S_MV_ENCODER_CHAR[i >> 2];
				out[windex++] = S_MV_ENCODER_CHAR[(i << 4) & 0x3f];
				out[windex++] = S_PAD;
				out[windex++] = S_PAD;
			} else if (rest == 2) {
				int i = ((data[rindex] & 0xff) << 8) + (data[rindex + 1] & 0xff);
				out[windex++] = S_MV_ENCODER_CHAR[i >> 10];
				out[windex++] = S_MV_ENCODER_CHAR[(i >> 4) & 0x3f];
				out[windex++] = S_MV_ENCODER_CHAR[(i << 2) & 0x3f];
				out[windex++] = S_PAD;
			}
			return new String(out, 0, windex);
		}catch (Exception e){

		}
		return null;
	}

}
