package GameState;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	public static final int NUMGAMESTATES = 5;
	public static final int MENUSTATE = 0;
	public static final int LEVEL1INTRO = 1;
	public static final int LEVEL1STATE = 2;
	public static final int LEVEL2INTRO = 3;
	public static final int LEVEL2STATE = 4;
	
	public GameStateManager() {
		gameStates = new GameState[NUMGAMESTATES];
		currentState = MENUSTATE;
		loadState(currentState);	
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		if(state == LEVEL1INTRO)
			gameStates[state] = new Level1intro(this);
		if(state == LEVEL2INTRO)
			gameStates[state] = new Level2intro(this);
		if(state == LEVEL1STATE)
			gameStates[state] = new Level1State(this);
		if(state == LEVEL2STATE)
			gameStates[state] = new Level2State(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		update();
		//gameStates[currentState].init();
	}
	
	public GameState getState(int state) {
		return gameStates[state]; 
	}
	
	public void update() {
		try {
			gameStates[currentState].update();
		} catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try {
			gameStates[currentState].draw(g);
		} catch(Exception e) {}
	}
	
	public void keyPressed(int k) {
		
			gameStates[currentState].keyPressed(k);
		
		
	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
	
}









