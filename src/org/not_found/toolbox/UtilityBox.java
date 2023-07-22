package org.not_found.toolbox;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

import org.not_found.main.Main;

public class UtilityBox  {
	
	/**
	 * Scales the image by its intened params
	 * @param
	 * 
	 **/
	public static BufferedImage scaleImage(BufferedImage originalImage, int width, int height) {
		if(originalImage == null) { System.err.println("[ERROR] Image could not be scaled!"); return null; }
		BufferedImage scaledImage = toCompatibleImage(new BufferedImage(width, height, originalImage.getType()));
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(originalImage, 0, 0, width, height, null);
		g2.dispose();
		return scaledImage;
	}
	
	public static String[] getDialogueFromTXT(String filepath) throws IOException {
		List<String> listOfStrings = new ArrayList<String>();
		BufferedReader bf = new BufferedReader(new FileReader(Main.tempDir + "/res/" + filepath + ".txt"));
		String line = bf.readLine();
		while (line != null) {listOfStrings.add(line);line = bf.readLine();}
		bf.close();
		String[] array= listOfStrings.toArray(new String[0]);
		return array;
	}
	
	private static BufferedImage toCompatibleImage(BufferedImage image)
	{
	    // obtain the current system graphical settings
	    GraphicsConfiguration gfxConfig = GraphicsEnvironment.
	        getLocalGraphicsEnvironment().getDefaultScreenDevice().
	        getDefaultConfiguration();
 
	    //if image is already compatible and optimized for current system settings, simply return it
	    if (image.getColorModel().equals(gfxConfig.getColorModel()))
	        return image;

	    // image is not optimized, so create a new image that is
	    BufferedImage newImage = gfxConfig.createCompatibleImage(
	            image.getWidth(), image.getHeight(), image.getTransparency());

	    // get the graphics context of the new image to draw the old image on
	    Graphics2D g2d = newImage.createGraphics();

	    // actually draw the image and dispose of context no longer needed
	    g2d.drawImage(image, 0, 0, null);
	    g2d.dispose();

	    // return the new optimized image
	    return newImage; 
	}
	
	public static BufferedImage[] fromSheet(BufferedImage image, int rows, int cols) {

		int chunks = rows * cols;

		int chunkWidth = 16; // determines the chunk width and height
		int chunkHeight = 16;
		System.out.println(chunkWidth + " " + chunkHeight);
		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				//Initialize the image array with image chunks

				int imageType = image.getType();
				if (imageType == 0) {
					imageType = 5;
				}

				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, imageType);
				Graphics2D g2 = imgs[count++].createGraphics();
				g2.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				g2.dispose();
			}
		}

		return imgs;

	}
	
	public static BufferedImage[] fromSheet(BufferedImage image, int rows, int cols, int width, int height) {

		int chunks = rows * cols;

		int chunkWidth = image.getWidth() / cols; //determines the chunk height
	    int chunkHeight = image.getHeight() / rows;
	    
		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks]; //Image array to hold image chunks
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				//Initialize the image array with image chunks
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
				Graphics2D g2 = imgs[count++].createGraphics();
				g2.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				g2.dispose();
			}
		}

		return imgs;

	}
	
	public static BufferedImage[][] fromSheet2D(BufferedImage image, int rows, int cols) {

		int chunks = rows * cols;

		int chunkWidth = 16; //determines the chunk height
	    int chunkHeight = 16;
	    
		BufferedImage imgs[][] = new BufferedImage[chunks][chunks]; //Image array to hold image chunks
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				//Initialize the image array with image chunks
				imgs[x][y] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
				Graphics2D g2 = imgs[x][y].createGraphics();
				g2.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				g2.dispose();
			}
		}

		return imgs;

	}
	
	public static BufferedImage[][] fromSheet2D(BufferedImage image, int rows, int cols, int width, int height) {

		int chunks = rows * cols;

		int chunkWidth = image.getWidth() / cols; //determines the chunk height
	    int chunkHeight = image.getHeight() / rows;
	    
		BufferedImage imgs[][] = new BufferedImage[chunks][chunks]; //Image array to hold image chunks
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				//Initialize the image array with image chunks
				imgs[x][y] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
				Graphics2D g2 = imgs[x][y].createGraphics();
				g2.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
				g2.dispose();
			}
		}

		return imgs;

	}
}
