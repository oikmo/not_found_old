package org.not_found.main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.swing.JPanel;

import org.not_found.achievements.AchieveManager;
import org.not_found.entity.*;
import org.not_found.entity.monster.MONSTER;
import org.not_found.entity.npc.NPC;
import org.not_found.event.EventHandler;
import org.not_found.object.OBJ;
import org.not_found.projectile.Projectile;
import org.not_found.tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	//SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3;
	public String fpsCount = "NONE";

	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 17; //17
	public final int maxScreenRow = 13; //13
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	public final int maxMaps = 10;
	public int currentMap = 0;
	
	boolean music1, music2;
	public String version;
	
	//DEBUG BOOLEANS
	public boolean debug;
	public boolean fps;
	
	//SYSTEM
	int FPSLock = 60;
	Sound Tmusic = new Sound();
	Sound Pmusic = new Sound();
	Sound music = new Sound();
	Sound se = new Sound();
	
	public Thread gameThread;
	public Graphics2D g2;
	
	final static int maxEntities = 128;
	
	//ENTITIES AND OBJECTS
	public ArrayList<Entity> entityList = new ArrayList<>();
	public Player player = new Player(this);
	public OBJ obj[][] = new OBJ[maxMaps][maxEntities];
	public NPC npc[][] = new NPC[maxMaps][maxEntities];
	public MONSTER monster[][] = new MONSTER[maxMaps][maxEntities];
	public ArrayList<Projectile> projectiles = new ArrayList<>();
	public ArrayList<OBJ> walls = new ArrayList<>();
	
	public AssetSetter assetSetter = new AssetSetter(this);
	public KeyHandler keyH = new KeyHandler(this);
	TileManager tileM = new TileManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	public Config config = new Config(this);
	
	//GAMESTATE
	public int gameState;
	public final int loadingState = 0;
	public final int titleState = 1;
	public final int playState = 2;
	public final int pauseState = 3;
	public final int dialogueState = 4;
	public final int characterState = 5;
	public final int optionsState = 6;
	public final int gameOverState = 7;
	public long deltaTime = 0;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		//this.addMouseListener(keyH);
	}
	
	public void setupGame() throws IOException {
		AchieveManager.setAchievements(this);
		se.volumeScale = 2;
		//set objs, npcs, mons, gamestate etc
		assetSetter.setAll();
		if(gameThread != null) {
			gameState = titleState;
		} else {
			gameState = loadingState;
		}
		
		Tmusic.setFile(0);
		
		//System.out.println(SoundEnum.Espionage.ordinal());
		if(!Tmusic.isPlaying()) { Tmusic.setFile(SoundEnum.Espionage);} else { Tmusic.stop(); Tmusic.setFile(SoundEnum.Espionage); }
		if(!music.isPlaying()) { music.setFile(SoundEnum.JEALOUS); } else { music.stop(); music.setFile(SoundEnum.JEALOUS);}
		if(!Pmusic.isPlaying()) { Pmusic.setFile(SoundEnum.IntenseMoments);} else { Pmusic.stop(); Pmusic.setFile(SoundEnum.IntenseMoments); }
		this.setBackground(Color.blue);
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameState = titleState;
		gameThread.start();
	}
	
	public void retry() {
		player.setDefaultPositions();
		player.restoreLifeAndMana();
		try {
			assetSetter.setNPC();
			assetSetter.setMonster();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.invinceCounter = 0;
		player.isInvince = false;
	}
	
	public void restart() {
		player.setDefaultValues();
		player.setItems();
		try {
			assetSetter.setNPC();
			assetSetter.setMonster();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		player.invinceCounter = 0;
		player.isInvince = false;
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
			for(int i = 0; i < maxEntities; i++) {
				if(getNPC(i) != null) {
					getNPC(i).update();
				}
				
				if(getMONSTER(i) != null) {
					if(getMONSTER(i).alive && !getMONSTER(i).dying) {
						getMONSTER(i).update();
					}
					if(!getMONSTER(i).alive) {
						getMONSTER(i).checkDrop();
						monster[currentMap][i] = null;
					}
				}
			}
			for(int i = 0; i < projectiles.size(); i++) {
				if(projectiles.get(i) != null) {
					if(projectiles.get(i).alive) {
						projectiles.get(i).update();
					}
					else {
						projectiles.get(i).alive = false;
						projectiles.remove(i);
					}
				}
			}
			
			for(int i = 0; i < walls.size(); i++) {
				if(walls.get(i) != null) {
					if(walls.get(i).alive) {
						walls.get(i).update();
					}
					else {
						walls.remove(i);
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
		if(g2 != (Graphics2D)g) {
			g2 = (Graphics2D)g;
		}
		
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
	    g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); 
		
        //title screen
        if(gameState == titleState) {
        	ui.draw(g2);
        } 
        //literally every other state :(
        else {
        	tileM.draw(g2);

        	entityList.add(player);

        	for(int i=0; i<maxEntities; i++) {
        		if(getOBJ(i) != null) { entityList.add(getOBJ(i)); } 
        		if(getMONSTER(i) != null) { entityList.add(getMONSTER(i)); }
        		if(getNPC(i) != null) { entityList.add(getNPC(i)); }
        	}
        	for(int i = 0; i < projectiles.size(); i++) {
        		if(projectiles.get(i) != null) { entityList.add(projectiles.get(i)); }
        	}
        	for(int i = 0; i < walls.size(); i++) {
        		if(walls.get(i) != null) { entityList.add(walls.get(i)); }
        	}
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
	
	public void setOBJ(OBJ object, int i) {
		obj[currentMap][i] = object;
	}
	
	public OBJ getOBJ(int i) {
		return obj[currentMap][i];
	}
	
	public NPC getNPC(int i) {
		return npc[currentMap][i];
	}
	
	public void setNPC(NPC object, int i) {
		npc[currentMap][i] = object;
	}
	
	public MONSTER getMONSTER(int i) {
		return monster[currentMap][i];
	}
	
	public void setMONSTER(MONSTER object, int i) {
		monster[currentMap][i] = object;
	}
}



