package yh.util.repeat;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5的获取，用于文件路径命名
 * 
 * @author yh 创建时间：2012-09-19
 */
public class MD5 {

	private static MessageDigest md = null;
	private static int BUFF_SIZE = 1024 * 4;

	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取buff的md5
	 * 
	 * @param buff
	 * @return
	 */
	public synchronized static String digest(byte[] buff) {
		md.reset();
		md.update(buff);
		byte[] digest = md.digest();
		return bin2Hex(digest);
	}

	/**
	 * 获取文件内容的md5
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws Exception
	 */
	public synchronized static String digestFile(String filePath)
			throws Exception {
		md.reset();
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		byte[] buff = new byte[BUFF_SIZE];
		int length = in.read(buff);
		while (length > 0) {
			md.update(buff, 0, length);
			length = in.read(buff);
		}
		in.close();
		buff = md.digest();
		return bin2Hex(buff);
	}

	/**
	 * 获取字符串的md5
	 * 
	 * @param str
	 * @return
	 */
	public synchronized static String digestString(String str) {
		md.reset();
		md.update(str.getBytes());
		byte[] digest = md.digest();
		return bin2Hex(digest);
	}

	/**
	 * 将buf转为字符串
	 * 
	 * @param buff
	 * @return
	 */
	synchronized static String bin2Hex(byte[] buff) {
		StringBuilder sb = new StringBuilder();
		String temp;
		for (byte bt : buff) {
			temp = Integer.toHexString(bt & 0xff);
			sb.append(temp.length() == 1 ? ('0' + temp) : temp);
		}
		return sb.toString();
	}
}
