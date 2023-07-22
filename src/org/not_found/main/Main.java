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
	public static boolean isFullScreen = false;
	public static String tempDir = System.getProperty("java.io.tmpdir") + "not_found\\";
	static GamePanel gamePanel;
	public static void main(String[] args) throws IOException {	
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.requestFocus();
		window.setTitle("not_found");
		Image icon = Toolkit.getDefaultToolkit().getImage("/icon.png");
	    window.setIconImage(icon);
	    //window.setUndecorated(true);
		
	    JOptionPane.showConfirmDialog(window, "Just saying this will take a while depending on your connection or disk speeds.", "POP-UP", JOptionPane.PLAIN_MESSAGE);
	    
		String version = "alpha 0.1.3";

		File theDir = new File(tempDir);
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		
		File dir = new File(tempDir + "/res/");
		if(!dir.exists()) {
			download("https://chappie-webpages.werdimduly.repl.co/not_found/res.zip", tempDir + "/res.zip");
			UnzipUtility unzipper = new UnzipUtility();
			
			unzipper.unzip(tempDir + "/res.zip", tempDir);
		}
		System.out.println(version.replaceAll("[^0-9]", ""));
		try {
			File versionTXT = new File(tempDir + "version.txt");
			if (versionTXT.createNewFile()) {
				System.out.println("File created: " + versionTXT.getName());
				FileWriter myWriter = new FileWriter(tempDir + "version.txt");
				
				myWriter.write(version.replaceAll("[^0-9]", ""));
				myWriter.close();
				
				
			} else {
				System.out.println("File already exists.");
				
				BufferedReader brr = new BufferedReader(new FileReader(tempDir + "version.txt"));     
				String temp = brr.readLine();
				if (temp == null) {
					System.out.println("yeah");
					FileWriter myWriter = new FileWriter(tempDir + "version.txt");
					myWriter.write(version.replaceAll("[^0-9]", ""));
					myWriter.close();
				} else {
					if(Integer.parseInt(temp.replaceAll("[^0-9]", "")) == Integer.parseInt(version.replaceAll("[^0-9]", ""))) {}
					else {
						if(Integer.parseInt(temp.replaceAll("[^0-9]", "")) < Integer.parseInt(version.replaceAll("[^0-9]", ""))) {
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
							
							FileWriter myWriter = new FileWriter(tempDir + "version.txt");
							myWriter.write(version.replaceAll("[^0-9]", ""));
							myWriter.close();
						} else {
							JOptionPane.showMessageDialog(window, "This version is older than pre-recorded version :(\n(Redownload a newer version)", "ERROR", JOptionPane.ERROR_MESSAGE);
							window = null;
							System.err.println("[ERROR] Blud was running a prev version of not_found, recorded ver : " + Integer.parseInt(temp.replaceAll("[^0-9]", "")) + ", software ver : " + Integer.parseInt(version.replaceAll("[^0-9]", "")));
							System.exit(-1);
						}
					}
					
				}
				brr.close();
			}
			
		} catch (IOException e) {
			System.err.println("An error occurred.");
		}
		
		if(window != null) {
			gamePanel = new GamePanel();
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
		
    	
		
		
		
	}
	
	public static String removeCharacters(String sentence, char[] letters) {
	    String output = "";
	    boolean wasChanged = false;

	    for (int i = 0; i < sentence.length(); i++) {
	        char ch = sentence.charAt(i);

	        for (int j = 0; j < letters.length; j++)
	            if (ch == letters[j]) {
	                ch = '\0';
	                wasChanged = true;
	                break;
	            }

	        output += ch;
	    }

	    if (wasChanged)
	        return output;
	    else
	        return "No changes necessary";
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
