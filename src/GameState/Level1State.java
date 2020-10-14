package GameState;

import Main.GamePanel;
import TileMap.*;
import Entity.*;
import Entity.Enemies.*;
import Audio.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;


public class Level1State extends GameState {
	
	private TileMap tileMap;
	private Background bg;
	
	private Player player;
	private BadGuy badGuy;
	
	private ArrayList<Enemy> enemies;
	private ArrayList<Explosion> explosions;
	private ArrayList<Diamond> diamonds;
	
	
	private HUD hud;
	
	private AudioPlayer bgMusic;
	
	private int iPos = 100;
	
	public Level1State(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	public void init() {
		bgMusic = new AudioPlayer("/Music/level1.mp3");
		bgMusic.play();
		
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/grasstileset.gif");
		tileMap.loadMap("/Maps/level1-1.map");
		tileMap.setPosition(0, 0);
		tileMap.setTween(1);
		
		bg = new Background("/Backgrounds/grassbg1.gif", 0.1);
	
		player = new Player(tileMap);
		player.setPosition(100, 100);
		
		badGuy = new BadGuy(tileMap);
		badGuy.setPosition(3168, 100);

		populateEnemies();
		
		locateDiamonds();
		
		explosions = new ArrayList<Explosion>();
		
		hud = new HUD(player);
		
		
		
		
	}
	
	
	private void populateEnemies() {

		enemies = new ArrayList<Enemy>();
		
		Slugger s;
		Point[] points = new Point[] {
			new Point(200, 100),
			new Point(860, 200),
			new Point(1525, 200),
			new Point(1680, 200),
			new Point(1800, 200)
		};
		for(int i = 0; i < points.length; i++) {
			s = new Slugger(tileMap);
			s.setPosition(points[i].x, points[i].y);
			enemies.add(s);
		}
		
	}
	
	private void locateDiamonds() {
		
		diamonds = new ArrayList<Diamond>();
		
		Diamond s;
		Point[] points = new Point[] {
			new Point(200, 110),
			new Point(330, 70),
			new Point(1801, 50)
		};
		for(int i = 0; i < points.length; i++) {
			s = new Diamond(tileMap);
			s.setPosition(points[i].x, points[i].y);
			diamonds.add(s);
		}
		
	}
	
	public void update() {
		
		if(badGuy.isDead()) {
			
			gsm.setState(GameStateManager.LEVEL2INTRO);
			bgMusic.close();
			
			}
		
		// update player
		player.update();
		
		badGuy.update();
		
		tileMap.setPosition(
			GamePanel.WIDTH / 2 - player.getx(),
			GamePanel.HEIGHT / 2 - player.gety()
		);
		
		// set background
		bg.setPosition(tileMap.getx(), tileMap.gety());
		
		// attack enemies
		player.checkAttack(enemies);
		
		// attack BadGuy
		player.checkAttackBadGuy(badGuy);
		
		// attack BadGuy to player
		badGuy.checkAttack(player);
		
		// update all enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(
					new Explosion(e.getx(), e.gety()));
			}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		// update diamonds
		for(int i = 0; i < diamonds.size(); i++) {
			Diamond d = diamonds.get(i);
			d.update();
			if( player.getx()+ (d.getWeidth()/2) >= d.getx() && 
				player.gety() <= d.gety()+ d.getHeight()&&
				player.getx() <= d.getx() + d.getWeidth()+10 &&
				player.gety() - player.getHeight() <= d.gety() + d.getHeight()
				
				) 
			  {
				player.setDiamond();
				d.setDrawIt(false);
				diamonds.remove(i);
				i--;
				
			 }
			
			
			
		}
		
		
	}
	
	public void draw(Graphics2D g) {
		
		
				
		// draw bg
		bg.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		// draw badGuy
		if(!badGuy.isDead()) {
			badGuy.draw(g);
		}
		if(badGuy.isDead()) {
			badGuy.sety(0);
			badGuy.setx(0);
		}
		
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			
			enemies.get(i).draw(g);
		}
		
		// draw diamonds
		for(int i = 0; i < diamonds.size(); i++) {
			diamonds.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).setMapPosition(
				(int)tileMap.getx(), (int)tileMap.gety());
			explosions.get(i).draw(g);
		}
		
		// draw hud
		hud.draw(g);
		
		if((int)badGuy.gety()==iPos) {
			moveBadGuy();
			
			
			if(badGuy.getx()-player.getx()<200) {
				setFire();
			}
			
		}
	
	
	}
	
	public void moveBadGuy() {
		
		Random r = new Random();
		iPos = r.nextInt(((badGuy.getMaxY()) - badGuy.getMinY()) + 1) + badGuy.getMinY();
		
		if((badGuy.gety() == iPos)) {
			if(iPos == badGuy.getMaxY()) {
			iPos -= 2;
			}else if(iPos == badGuy.getMinY()){
			iPos += 2;	
			}else {
			iPos--;	
			}
			
		}
		
		if(badGuy.gety() > iPos) {
		badGuy.setDown(false);	
		badGuy.setUp(true);
		}
		if(badGuy.gety()< iPos) {
	    badGuy.setUp(false);
		badGuy.setDown(true);
		}
		
	}
	
	public void setFire() {
		badGuy.setFiring();
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(true);
		if(k == KeyEvent.VK_RIGHT) player.setRight(true);
		if(k == KeyEvent.VK_UP) player.setUp(true);
		if(k == KeyEvent.VK_DOWN) player.setDown(true);
		if(k == KeyEvent.VK_W) player.setJumping(true);
		if(k == KeyEvent.VK_E) player.setGliding(true);
		if(k == KeyEvent.VK_R) player.setScratching();
		if(k == KeyEvent.VK_F) player.setFiring();
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_LEFT) player.setLeft(false);
		if(k == KeyEvent.VK_RIGHT) player.setRight(false);
		if(k == KeyEvent.VK_UP) player.setUp(false);
		if(k == KeyEvent.VK_DOWN) player.setDown(false);
		if(k == KeyEvent.VK_W) player.setJumping(false);
		if(k == KeyEvent.VK_E) player.setGliding(false);
	}
	
}












