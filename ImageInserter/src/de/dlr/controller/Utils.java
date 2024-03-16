package de.dlr.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class Utils {
	/**
	 * getPathPartRight
	 * split path (directory) from right and get part (without filename)
	 * @param path :String path to split
	 * @param position : int postion from right (start at 1)
	 * @param pathSeperator : String path seperator (split)
	 * @return
	 */
	public static String getPathPartRight(String path, int position, String pathSeperator) {
		if (path.length() == 0 || position <= 0 || pathSeperator.length() == 0)
			{return "";}
		String[] pathsplit = path.split(pathSeperator);
		int rightmaxid = pathsplit.length-1;
		int id = rightmaxid - position;
		if (id >= 0) return pathsplit[id];
		return "";
	}
	//------------------------------------------------------------------------------
    public static String substringAfterLast(final String str, final String separator) {
    	if (str.length() == 0  || separator.length() == 0)
			{return "";}
    	
        final int pos = str.lastIndexOf(separator);
        if (pos == -1 || pos == str.length() - separator.length()) {
            return "";
        }
        return str.substring(pos + separator.length());
    }
    
   //------------------------------------------------------------------------------
	
    public static String substringBeforeLast(final String str, final String separator) {
    	if (str.length() == 0  || separator.length() == 0) {
            return str;
        }
        final int pos = str.lastIndexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }
    
   //------------------------------------------------------------------------------
	public static String getFilename(String path,  String pathSeperator) {
		return substringAfterLast(path, pathSeperator);
	}
	//------------------------------------------------------------------------------
	public static String getPath(String path,  String pathSeperator) {
		return substringBeforeLast(path, pathSeperator);
	}
	//------------------------------------------------------------------------------
	public static List<String> readFileLines(String filename) {
		List<String> allLines = null;
		try {
			allLines = Files.readAllLines(Paths.get(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allLines;
	}
	//------------------------------------------------------------------------------
	public static void test() {
		String testpath = "C://test/ABC/symbols/B7/34x42/s123/bmp0.bmp";
		String testpath2 = "C://Users/Alex/eclipse-workspace/ImageInserter/src/res/symbols/A4/44x21/s0/data.ini";
		String s1 = getPathPartRight(testpath,1,"/");
		String s2 = getFilename(testpath,"/");
		String s3 = getPath(testpath,"/");
	
		System.out.println(s1);
		System.out.println(s2);
		System.out.println(s3);
		
		
		Properties p = new Properties();
	    try {
			p.load(new FileInputStream(testpath2));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    p.list(System.out);
	    System.out.println("");
	    System.out.println("masterId = " + p.getProperty("masterId"));
	    System.out.println("masterid = " + p.getProperty("masterid"));
	    
	}
	
	
}
