package org.not_found.main;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.*;

import org.not_found.entity.*;
import org.not_found.event.EventHandler;
import org.not_found.tile.TileManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	//SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3;
	public String fpsCount = "60";

	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 17;
	public final int maxScreenRow = 13;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	//WORLD SETTINGS
	public final int maxWorldCol = 32;
	public final int maxWorldRow = 32;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	boolean music1, music2;
	public String version;
	
	//DEBUG BOOLEANS
	public boolean debug;
	public boolean fps;
	
	Image image;
	
	//SYSTEM
	int FPSLock = 60;
	Sound Tmusic = new Sound();
	Sound Pmusic = new Sound();
	Sound music = new Sound();
	Sound se = new Sound();
	
	public Thread gameThread;
	
	//ENTITIES AND OBJECTS
	ArrayList<Entity> entityList = new ArrayList<>();
	public Player player = new Player(this);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[5];
	public Entity monster[] = new Entity[20];
	
	public KeyHandler keyH = new KeyHandler(this);
	public AssetSetter aSetter = new AssetSetter(this);
	TileManager tileM = new TileManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	
	//GAMESTATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.blue);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		this.addMouseListener(keyH);
	}
	
	public void setupGame() throws IOException {
		//set objs, npcs, mons, gamestate etc
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		player.setDefaultValues();
		gameState = titleState;
		if(!Tmusic.isPlaying()) { Tmusic.setFile(0);} else { Tmusic.stop(); Tmusic.setFile(0); }
		if(!music.isPlaying()) { music.setFile(1); } else { music.stop(); music.setFile(1);}
		if(!Pmusic.isPlaying()) { Pmusic.setFile(2);} else { Pmusic.stop(); Pmusic.setFile(2); }
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() { //gameloop
		double drawInterval = 1000000000 / FPSLock;
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
		
		if(gameState == playState) {
			Tmusic.stop();
			if(!music1) {
				music2 = false;
				Pmusic.stop();
				music.play();
				music.loop();
				Tmusic.stop();
				music1 = true;
			} 
			
			
			player.update();
			for(int i=0;i<npc.length;i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
			for(int i=0;i<monster.length;i++) {
				if(monster[i] != null) {
					if(monster[i].alive) {
						monster[i].update();
					}
					else {
						monster[i] = null;
					}
					
				}
			}
		}
		//music stuff
		if(gameState == pauseState) {
			if(!music2) {
				Tmusic.stop();
				music.stop();
				Pmusic.play();
				music1 = false;
				music2 = true;
			}
		}
		if(gameState == titleState) {
			music1 = false;
			music2 = false;
			Tmusic.loop();
			music.stop();
			Pmusic.stop();
			Tmusic.play();
			
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		
		//title screen
		if(gameState == titleState) {
			ui.draw(g2);
		} else {
			tileM.draw(g2);

			entityList.add(player);
			
			for(int i=0; i<obj.length; i++) { if(obj[i] != null) { entityList.add(obj[i]); } }
			for(int i=0; i<npc.length; i++) { if(npc[i] != null) { entityList.add(npc[i]);} }
			
			for(int i=0; i<monster.length; i++) { if(monster[i] != null) { entityList.add(monster[i]); } }
			
			Collections.sort(entityList, new Comparator<Entity>() { @Override public int compare(Entity o1, Entity o2) { int result = Integer.compare(o1.worldX, o2.worldY); return result; } });
			
			for(int i =0; i<entityList.size(); i++) { entityList.get(i).draw(g2); }
			entityList.clear();
			ui.draw(g2);
		}
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	public void playTMusic(int i) {
		Tmusic.setFile(i);
		Tmusic.play();
		Tmusic.loop();
	}
	public void stopTMusic() {
		music.stop();
	}
}



