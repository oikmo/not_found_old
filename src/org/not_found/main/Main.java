package org.not_found.main;

import java.awt.*;
import java.io.*;

import javax.swing.*;

import org.not_found.toolbox.Discord;

public class Main {
	public static void main(String[] args) throws IOException {	
		//args = new String[10];
		JFrame window = new JFrame();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.requestFocus();
		window.setTitle("not_found");
		Image icon = Toolkit.getDefaultToolkit().getImage("/icon.png");
	    window.setIconImage(icon);
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.debug = false;
		gamePanel.fps = false;
		gamePanel.version = "alpha 0.1.1";
		
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
		
		Discord.details = "i cant anymo";
		Discord.state = "game";
		Discord.main(args);
	}

}
