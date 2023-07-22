package org.not_found.main;

import java.awt.*;
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
	public static String tempDir = System.getProperty("java.io.tmpdir") + "not_found";
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
    	
		GamePanel gamePanel = new GamePanel();
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

		gamePanel.setupGame();
		gamePanel.startGameThread();
		
		
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
