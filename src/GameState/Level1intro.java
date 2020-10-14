package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;


import Audio.AudioPlayer;

import TileMap.Background;


import java.util.Timer;
import java.util.TimerTask;

public class Level1intro extends GameState{
	private Background bg;
	private AudioPlayer bgMusic;
	
	public Level1intro(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {

		bg = new Background("/Backgrounds/Intro/intro1.png", 0.1);
		
		Timer move = new Timer();
		move.schedule(new TimerTask() {
			@Override
			public void run() {
			gsm.setState(GameStateManager.LEVEL1STATE);
        }
			
		}, 1000);
    }
		
	

	@Override
	public void update() {
    bg.setPosition(0, 0);
	}
	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
	}

	@Override
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			
		};
		
	}
	
	public void keyReleased(int k) {
        if(k == KeyEvent.VK_ENTER) {
			
		};
	}

}
