package org.not_found.main;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.Comparator;

import javax.swing.*;

import org.not_found.toolbox.UnzipUtility;

public class Main {
	public static JFrame window = new JFrame();
	
	String userDir = System.getProperty("user.dir");
	public static boolean isFullScreen = false;
	public static String tempDir = System.getProperty("java.io.tmpdir") + "not_found";
	static GamePanel gamePanel = new GamePanel();
	public static void main(String[] args) throws IOException {	
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.requestFocus();
		window.setTitle("not_found");
		Image icon = Toolkit.getDefaultToolkit().getImage("/icon.png");
	    window.setIconImage(icon);
	    //window.setUndecorated(true);
		
	    JOptionPane.showConfirmDialog(window, "Just saying this will take a while depending on your connection or disk speeds.", "POP-UP", JOptionPane.PLAIN_MESSAGE);
	    
		String version = "alpha 0.1.2";
		
		File theDir = new File(tempDir);
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		
		download("https://chappie-webpages.werdimduly.repl.co/not_found/version.txt", tempDir + "/version.txt");
		BufferedReader br = new BufferedReader(new FileReader(new File(tempDir + "/version.txt")));
		String st;
		File dir = new File(tempDir + "/res/");
		System.out.println();
    	while ((st = br.readLine()) != null) {
    		if(!st.contains(version) || !dir.exists()) {
    			System.out.println(st + " " + version);
    			
    			if(dir.exists()) {
    				Files.walk(Paths.get(tempDir + "/res/")).sorted(Comparator.reverseOrder()) .forEach(path -> {
                        try {
                            //System.out.println("Deleting: " + path);
                            Files.delete(path);  //delete each file or directory
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
    			}
    			
    			download("https://chappie-webpages.werdimduly.repl.co/not_found/res.zip", tempDir + "/res.zip");
    			//unzip(tempDir + "/0.1.zip", tempDir+"/res/");
    			UnzipUtility unzipper = new UnzipUtility();
    			
    			unzipper.unzip(tempDir + "/res.zip", tempDir);
    		}
    	}
    	
		
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.debug = false;
		gamePanel.fps = false;
		gamePanel.version = version;
		
		if(args.length != 0) {
			for(int i=0;i<args.length;i++) {
				if(args[i].contains("-debug")) {
					gamePanel.debug = true;
				} if(args[i].contains("-fps")) {
					gamePanel.fps = true;
				}
			}
		}
		toggleFullScreen();
		gamePanel.setupGame();
		gamePanel.startGameThread();
		
		
	}
	
	// Method to toggle between full-screen and windowed mode
	public static void toggleFullScreen() {
		updateTempScreenAndGraphics();
	    if (isFullScreen) {
	        // Switch back to windowed mode
	        window.dispose();
	        window.setUndecorated(false);
	        window.setSize(new Dimension(gamePanel.screenWidth, gamePanel.screenHeight));
	        window.setLocationRelativeTo(null);
	        window.setVisible(true);
	    } else {
	        // Switch to full-screen
	        window.dispose();
	        window.setUndecorated(true);
	        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
	        window.setVisible(true);
	    }
	    updateTempScreenAndGraphics();
	    // Update the full-screen flag
	    isFullScreen = !isFullScreen;
	}
	
	private static void updateTempScreenAndGraphics() {
		System.out.println("MY GOD");
	    int windowWidth = window.getWidth();
	    int windowHeight = window.getHeight();

	    double windowAspectRatio = (double) windowWidth / (double) windowHeight;

	    int tempScreenWidth;
	    int tempScreenHeight;

	    if (windowAspectRatio < gamePanel.aspectRatio) {
	        // The window is taller than the original aspect ratio
	        tempScreenWidth = (int) (windowHeight * gamePanel.aspectRatio);
	        tempScreenHeight = windowHeight;
	    } else {
	        // The window is wider than the original aspect ratio
	        tempScreenWidth =  (int) (windowHeight * gamePanel.aspectRatio);
	        tempScreenHeight = windowHeight;
	    }

	    // Update the size of the tempScreen and g2
	    gamePanel.screenWidth2 = tempScreenWidth;
	    gamePanel.screenHeight2 = tempScreenHeight;
	    gamePanel.tempScreen = new BufferedImage(gamePanel.screenWidth2, tempScreenHeight, BufferedImage.TYPE_INT_ARGB);
	    gamePanel.g2 = (Graphics2D) gamePanel.tempScreen.getGraphics();
	}


	
	private static void download(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

}
