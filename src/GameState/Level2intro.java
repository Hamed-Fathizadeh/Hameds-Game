package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import TileMap.Background;


import java.util.Timer;
import java.util.TimerTask;

import Audio.AudioPlayer;

public class Level2intro extends GameState{
	private Background bg;
	AudioPlayer sfx1 = new AudioPlayer("/SFX/menuselect.mp3");
	public Level2intro(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	@Override
	public void init() {
		sfx1.play();
		bg = new Background("/Backgrounds/Intro/intro2.png", 0.1);
		
		Timer move = new Timer();
		move.schedule(new TimerTask() {

			@Override
			public void run() {
			gsm.setState(GameStateManager.LEVEL2STATE);
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

