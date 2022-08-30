package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtilityBox  {
	
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		return scaledImage;
	}
	
	public String[] array(String filepath) throws IOException {
		// list that holds strings of a file
		List<String> listOfStrings = new ArrayList<String>();
			
		// load data from file
		BufferedReader bf = new BufferedReader(new FileReader("res" + filepath +".txt"));
			
		// read entire line as string 
		String line = bf.readLine();
			
		// checking for end of file
		while (line != null) {
			listOfStrings.add(line);
			line = bf.readLine();
		}
			
		bf.close();
			
		// storing the data in arraylist to array
		String[] array= listOfStrings.toArray(new String[0]);
				
		//return array;
		return array;
	}
	
}
