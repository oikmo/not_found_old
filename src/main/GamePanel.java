package main;

import java.awt.*;

import javax.swing.*;

import entity.*;
import object.SuperObject;
import tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	//SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3;
	public String fpsCount = "120";

	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	//WORLD SETTINGS
	public final int maxWorldCol = 32;
	public final int maxWorldRow = 32;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	int FPS = 120;
	
	Image image;
	
	
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10];
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.blue);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.addMouseListener(keyH);
	}
	
	public void setupGame() {
		aSetter.setObject();
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override

	public void run() { //gameloop
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime = 0;
		int drawCount = 0;
		long timer = 0;

		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			
			if (timer >= 1000000000) {
				fpsCount = Integer.toString(drawCount);
				//drawCount = drawCount;
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		tileM.draw(g2);
		for(int i=0;i<obj.length;i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}
		player.draw(g2);
		g2.dispose();
		
	}
	
}

//public void run() { // game loop
//
//	double drawInterval = 1000000000 / FPS;
//	double nextDrawTime = System.nanoTime() + drawInterval;
//
//	while (gameThread != null) {
//		// long currentTime = System.nanoTime();
//		// System.out.println("current time : " + currentTime);
//		// System.out.println("the game loop is loop");
//		update();
//
//		repaint();
//		try {
//			double remainingTime = nextDrawTime - System.nanoTime();
//			remainingTime = remainingTime / 1000000;
//
//			if (remainingTime < 0) {
//				remainingTime = 0;
//			}
//
//			Thread.sleep((long) remainingTime);
//
//			nextDrawTime += drawInterval;
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//}

