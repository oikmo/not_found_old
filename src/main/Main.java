package main;
import java.awt.*;
import javax.swing.*;

public class Main {
	public static void main(String[] args) {		
		JFrame window = new JFrame();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);
		window.requestFocus();
		window.setTitle("not_found");
		Image icon = Toolkit.getDefaultToolkit().getImage("icon.png");
	    window.setIconImage(icon);
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
		
		
	}

}
