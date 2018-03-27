package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;

public class FileReader {

	/**
	 * 按行读取指定路径文件
	 * @param path
	 * @return
	 */
	public static List<String> readByLine(String path) {
		List<String> lines = new LinkedList<String>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "UTF-8"));
			String line = null;
			while(null != (line = br.readLine())) {
				lines.add(line.trim());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lines;
	}

}
