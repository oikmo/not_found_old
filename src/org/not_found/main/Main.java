package org.not_found.main;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.channels.*;
import java.nio.file.*;
import java.util.Comparator;
import javax.swing.*;

import org.not_found.os.EnumOS;
import org.not_found.os.EnumOSMappingHelper;
import org.not_found.toolbox.UnzipUtility;

public class Main {
	public static JFrame window = new JFrame();
	private static int PrevX, PrevY, PrevWidth, PrevHeight;
	static String schoolDrive = "F:/";
	public static boolean isSchool = false;
	public static boolean isFullScreen = false;
	public static String gameDir = getNotFoundDir().getPath() + "/";
	static GamePanel gamePanel;
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	public static void main(String[] args) throws IOException {	
		
		
		//System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		device.setFullScreenWindow(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.requestFocus();
		window.setTitle("not_found");
		Image icon = Toolkit.getDefaultToolkit().getImage("/icon.png");
	    window.setIconImage(icon);
	    window.setUndecorated(false);
	    window.setAlwaysOnTop(false);
	    
	    int options = JOptionPane.showConfirmDialog(window, "Just saying this will take a while depending on your connection or disk speeds.", "POP-UP", JOptionPane.PLAIN_MESSAGE);
	    
	    if(options == -1) {
	    	System.exit(0);
	    }
	    
		String version = "alpha 0.1.6";

		File theDir = new File(gameDir);
		if (!theDir.exists()){
		    theDir.mkdirs();
		}
		
		if(!isSchool) {
			File dir = new File(gameDir + "/res/");
			if(!dir.exists()) {
				download("https://chappie-webpages.werdimduly.repl.co/not_found/res.zip", gameDir + "/res.zip");
				UnzipUtility unzipper = new UnzipUtility();
				
				unzipper.unzip(gameDir + "/res.zip", gameDir);
			}
			
			try {
				File versionTXT = new File(gameDir + "version.txt");
				if (versionTXT.createNewFile()) {
					System.out.println("File created: " + versionTXT.getName());
					FileWriter myWriter = new FileWriter(gameDir + "version.txt");
					
					myWriter.write(version.replaceAll("[^0-9]", ""));
					myWriter.close();
					
					
				} else {
					System.out.println("File already exists.");
					
					BufferedReader brr = new BufferedReader(new FileReader(gameDir + "version.txt"));     
					String temp = brr.readLine();
					if (temp == null) {
						FileWriter myWriter = new FileWriter(gameDir + "version.txt");
						myWriter.write(version.replaceAll("[^0-9]", ""));
						myWriter.close();
					} else {
						if(Integer.parseInt(temp.replaceAll("[^0-9]", "")) == Integer.parseInt(version.replaceAll("[^0-9]", ""))) {}
						else {
							if(Integer.parseInt(temp.replaceAll("[^0-9]", "")) < Integer.parseInt(version.replaceAll("[^0-9]", ""))) {
								if(dir.exists()) {
									Files.walk(Paths.get(gameDir + "/res/")).sorted(Comparator.reverseOrder()) .forEach(path -> {
					                    try {
					                        Files.delete(path);  //delete each file or directory
					                    } catch (IOException e) {
					                        e.printStackTrace();
					                    }
					                });
								}
								
								download("https://chappie-webpages.werdimduly.repl.co/not_found/res.zip", gameDir + "/res.zip");
								UnzipUtility unzipper = new UnzipUtility();
								unzipper.unzip(gameDir + "/res.zip", gameDir);
								
								FileWriter myWriter = new FileWriter(gameDir + "version.txt");
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
				System.err.println("[ERROR] Version could NOT be verified!");
			}
			
			File zipFile = new File(gameDir + "res.zip");
			if(zipFile.exists()) {
				zipFile.delete();
			}
		} else {
			gameDir = schoolDrive;
		}
		
		
		if(window != null) {
			gamePanel = new GamePanel();
			window.add(gamePanel);
			window.pack();
			window.setLocationRelativeTo(null);
			window.setVisible(true);
			
			gamePanel.config.loadConfig();
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
			setFullScreen();
			gamePanel.startGameThread();
		}	
	}
	
	public static void setFullScreen() {
		
		if (!isFullScreen) {
            PrevX = window.getX();
            PrevY = window.getY();
            PrevWidth = window.getWidth();
            PrevHeight = window.getHeight();

            window.dispose();
            window.setUndecorated(true);

            window.setBounds(0, 0, window.getToolkit().getScreenSize().width, window.getToolkit().getScreenSize().height);

            window.setVisible(true);
            isFullScreen = true;
        } else {
        	window.dispose();
        	window.setUndecorated(false);

        	window.setBounds(PrevX, PrevY, PrevWidth, PrevHeight);

        	window.setVisible(true);
        	isFullScreen = false;
        }
		
	}
	
	public static File getNotFoundDir() {
		File notFoundDir = getAppDir("not_found");
		return notFoundDir;
	}

	public static File getAppDir(String var0) {
		String var1 = System.getProperty("user.home", ".");
		File var2;
		switch(EnumOSMappingHelper.os[getOs().ordinal()]) {
		case 1:
		case 2:
			var2 = new File(var1, '.' + var0 + '/');
			break;
		case 3:
			String var3 = System.getenv("APPDATA");
			if(var3 != null) {
				var2 = new File(var3, "." + var0 + '/');
			} else {
				var2 = new File(var1, '.' + var0 + '/');
			}
			break;
		case 4:
			var2 = new File(var1, "Library/Application Support/" + var0);
			break;
		default:
			var2 = new File(var1, var0 + '/');
		}

		if(!var2.exists() && !var2.mkdirs()) {
			throw new RuntimeException("The working directory could not be created: " + var2);
		} else {
			return var2;
		}
	}

	private static EnumOS getOs() {
		String var0 = System.getProperty("os.name").toLowerCase();
		return var0.contains("win") ? EnumOS.windows : (var0.contains("mac") ? EnumOS.linux : (var0.contains("solaris") ? EnumOS.solaris : (var0.contains("sunos") ? EnumOS.unknown : (var0.contains("linux") ? EnumOS.linux : (var0.contains("unix") ? EnumOS.linux : EnumOS.unknown)))));
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
