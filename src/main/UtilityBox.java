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
	
	public String[] getDialogueFromTXT(String filepath) throws IOException {
		List<String> listOfStrings = new ArrayList<String>();
		BufferedReader bf = new BufferedReader(new FileReader("res" + filepath +".txt"));
		String line = bf.readLine();
		while (line != null) {listOfStrings.add(line);line = bf.readLine();}
		bf.close();
		String[] array= listOfStrings.toArray(new String[0]);
		return array;
	}
	
}
