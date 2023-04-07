package org.not_found.toolbox;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

import org.apache.commons.io.IOUtils;

public class TextLoader {
	public static String GetValueFromFile(String file, String condition)
	{
		String returnalString = "";
		List<String> list = new ArrayList<String>(); 
	    File _file = new File("res/" + file + ".txt");
	    if(_file.exists()){
	        try { 
	            list = Files.readAllLines(_file.toPath(),Charset.defaultCharset());
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	      if(list.isEmpty())
	          return null;
	    }
	    for(String line : list){
	    	if(line.contains(condition))
	    	{
	    		String [] res = line.split(":");
	    		returnalString = res[1];
	    	}
	    }
	    return returnalString;
	}
	
	public static void SetValueToFile(String file, String condition, String input) throws IOException
	{
		List<String> list = new ArrayList<String>(); 
	    File _file = new File("res/" + file + ".txt");
	    if(_file.exists()){
	        try { 
	            list = Files.readAllLines(_file.toPath(), Charset.defaultCharset());
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	      if(list.isEmpty())
	          return;
	    }
	    for(int i = 0; i<list.size(); i++){
	    	if(list.get(i).contains(condition))
	    	{
	    		String temp = list.get(i); 
	    		String[] tempList = temp.split(":");
	    		tempList[1] = input;
	    		list.set(i, tempList[0] + ":" + tempList[1]);
	    		
	    		ReplaceFileString("res/" + file + ".txt", Files.readAllLines(_file.toPath(), Charset.defaultCharset()).get(i), list.get(i));
	    	}
	    }
	    
	    
	}
	
	public static void ReplaceFileString(String fileName, String old, String NEW) throws IOException {
	    FileInputStream fis = new FileInputStream(fileName);
	    String content = IOUtils.toString(fis, Charset.defaultCharset());
	    content = content.replaceAll(old, NEW);
	    FileOutputStream fos = new FileOutputStream(fileName);
	    IOUtils.write(content, new FileOutputStream(fileName), Charset.defaultCharset());
	    fis.close();
	    fos.close();
	}
	
	public static String[] GetListFromFile(String file) throws ArrayIndexOutOfBoundsException
	{
		
		List<String> list = new ArrayList<String>(); 
	    File _file = new File("res/" + file + ".txt");
	    if(_file.exists()){
	        try { 
	            list = Files.readAllLines(_file.toPath(),Charset.defaultCharset());
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	      if(list.isEmpty())
	          return null;
	    }
	    String[] returnalString = new String[list.size()];
	    return list.toArray(returnalString);
	}
	
}
