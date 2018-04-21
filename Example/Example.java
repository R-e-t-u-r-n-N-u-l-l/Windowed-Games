package example;

import engine.Gameloop;
import engine.KeyInput;
import engine.Window;

public class Example {
	
	
	private Window 		window;
	private Gameloop 	gameloop;
	private KeyInput 	inputhandler;
	
	

	private Runnable loop = new Runnable() {
		@Override
		public void run() {
			// GAME-UPDATES //
			window.move();
		}
	};
	
	private Runnable handleInput = new Runnable() {
		@Override
		public void run() {
			// KEY-EVENTS //
		}
	};
	
	
	
	
	
	public Example() {
		inputhandler 	= new KeyInput(handleInput);					// new KeyInput(Runnable callback);
		window 			= new Window(0, 0, 135, 135, inputhandler);		// new Window(int x, int y, int width, int height, KeyInput input);
		gameloop 		= new Gameloop(60, loop, inputhandler);			// new Gameloop(int maxFPS, Runnable callback, KeyInput input);
		
		gameloop.startLoop();
	}
	
	public static void main(String args[]) {
		new Example();
	}

}
