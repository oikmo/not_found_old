package org.not_found.toolbox;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.not_found.main.Main;

public class UtilityBox  {
	
	public static BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = toCompatibleImage(new BufferedImage(width, height, original.getType()));
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		return scaledImage;
	}
	
	public static String[] getDialogueFromTXT(String filepath) throws IOException {
		List<String> listOfStrings = new ArrayList<String>();
		BufferedReader bf = new BufferedReader(new InputStreamReader(Main.class.getResourceAsStream("/res/" + filepath + ".txt")));
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

	    /*
	     * if image is already compatible and optimized for current system 
	     * settings, simply return it
	     */
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
}
