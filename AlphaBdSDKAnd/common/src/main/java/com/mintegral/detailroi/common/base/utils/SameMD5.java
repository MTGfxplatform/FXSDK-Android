package com.mintegral.detailroi.common.base.utils;

import android.text.TextUtils;

import com.mintegral.detailroi.common.base.NoProguard;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;


/**
 * MD5工具类，注意此处算法算出的MD5值为小写
 * 
 * @author TonyZhou
 * 
 */
public class SameMD5 implements NoProguard {

	public static final String TAG = "MD5";

	/**
	 * 获取MD5值注意此处算法算出的Md5值为小写32位
	 * 
	 * @param val
	 * @return
	 */
	public static String getMD5(String val) {
		if(TextUtils.isEmpty(val)){
			return val;
		}
		try {
			SameLogTool.d(TAG, val);
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(val.getBytes());
			return HexEncode(md5.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取MD5值注意此处算法算出的Md5值为大写32位
	 * 
	 * @param val
	 * @return
	 */
	public static String getUPMD5(String val) {
		try {
			SameLogTool.d(TAG, val);
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.reset();
			md5.update(val.getBytes());
			return UpHexEncode(md5.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 获取MD5，兼容JS端，需要中文MD5值的请使用此方法
	 * */
	public static String getQftJSMD5(String val) {
		try {
			byte[] source = getQftJSMD5Bytes(val);
			char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
					'9', 'a', 'b', 'c', 'd', 'e', 'f' };
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(source);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

	/**
	 * 兼容服务端的js中区字符的bug，服务端js中是低位优先输出，并且只取了二进制的一半位数
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getQftJSMD5Bytes(String str)
			throws UnsupportedEncodingException {

		int len = str.length();
		byte[] b = str.getBytes("UTF-16LE");// 低位输出
		SameLogTool.i("----", "b = " + ByteArrayToHexString(b));
		byte[] source = new byte[len];
		for (int i = 0, pos = 0; i < b.length; i = i + 2)// 只取一半
		{
			if (b[i] == -1 || b[i] == -2)
				continue;
			source[pos] = b[i];
			pos++;
			if (pos == len)
				break;
		}
		SameLogTool.i("----", "source = " + ByteArrayToHexString(source));
		return source;
	}

	public static String ByteArrayToHexString(byte d[]) {
		if (d == null)
			return "";
		if (d.length == 0)
			return "";
		int len = d.length * 2;
		byte strData[] = new byte[len];
		for (int i = 0; i < strData.length; i++)
			strData[i] = 48;

		byte data[] = new byte[d.length + 1];
		data[0] = 0;
		System.arraycopy(d, 0, data, 1, d.length);
		BigInteger bi = new BigInteger(data);
		byte src[] = bi.toString(16).getBytes();
		int offset = strData.length - src.length;
		len = src.length;
		System.arraycopy(src, 0, strData, offset, len);
		return new String(strData);
	}

	private static String HexEncode(byte[] toencode) {
		StringBuilder sb = new StringBuilder(toencode.length * 2);
		for (byte b : toencode) {
			sb.append(Integer.toHexString((b & 0xf0) >>> 4));
			sb.append(Integer.toHexString(b & 0x0f));
		}
		return sb.toString().toLowerCase(Locale.US);
	}
	
	private static String UpHexEncode(byte[] toencode) {
		StringBuilder sb = new StringBuilder(toencode.length * 2);
		for (byte b : toencode) {
			sb.append(Integer.toHexString((b & 0xf0) >>> 4));
			sb.append(Integer.toHexString(b & 0x0f));
		}
		return sb.toString().toUpperCase(Locale.US);
	}
}
