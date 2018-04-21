package breakout;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import engine.Constants;
import engine.Gameloop;
import engine.KeyInput;
import engine.Utilities;
import engine.Window;

public class Breakout {
	
	// SETTINGS
	private final int borderWidth	= 20;
	private final int amtOfCols		= 10;
	private final int amtOfRows		= 6;
	private final int baseVelocity	= 5;
	
	private final int brickWidth 	= (Constants.screenwidth - borderWidth * 2) / amtOfCols;
	private final int brickHeight	= brickWidth / 3;
	
	private final Color[] colors = {
			new Color(200, 72, 72),
			new Color(198, 108, 58),
			new Color(180, 122, 48),
			new Color(162, 162, 42),
			new Color(72, 160, 72),
			new Color(66, 72, 200)};
	private final int[] points = { 7, 7, 4, 4, 1, 1 };
	private final float[] speeds = { 0.75f, 0.6f, 0.45f, 0.3f, 0.15f, 0f };
	
	
	private Window 		display;
	private Window		player;
	private Window		ball;
	private Gameloop 	gameloop;
	private KeyInput 	inputhandler;
	
	private int score = 0;
	private int lifes = 5;
	private float maxSpeedHor = 5;
	
	private ArrayList<Window> bricks = new ArrayList<Window>();
	
	

	private Runnable loop = new Runnable() {
		@Override
		public void run() {
			// GAME-UPDATES //
			ball.move();
			
			for(int i = bricks.size() - 1; i >= 0; i--) {
				if(ball.intersect(bricks.get(i))) {
					
					int side = bricks.get(i).getIntersectingSide(ball);
					switch(side) {
					case 0:
						ball.setY(bricks.get(i).getY() - ball.getHeight());
						ball.setVelY(ball.getVelY() * -1);
						break;
					case 1:
						ball.setX(bricks.get(i).getX() + brickWidth);
						ball.setVelX(ball.getVelX() * -1);
						break;
					case 2:
						ball.setY(bricks.get(i).getY() + brickHeight);
						ball.setVelY(ball.getVelY() * -1);
						break;
					case 3:
						ball.setX(bricks.get(i).getX() - ball.getWidth());
						ball.setVelX(ball.getVelX() * -1);
						break;
					}
					

					
					score += points[(bricks.get(i).getY() - Constants.screenheight / 10) / brickHeight];
					display.setTitle("SCORE: " + score + "   LIFES: " + lifes + "    ▁ ▂ ▄ ▅ ▆ ▇ █ BREAKOUT █ ▇ ▆ ▅ ▄ ▂ ▁");
					

					maxSpeedHor = baseVelocity + speeds[(bricks.get(i).getY() - Constants.screenheight / 10) / brickHeight];
					
					bricks.get(i).hide();
					bricks.remove(i);
					
					if(bricks.size() == 0) {
						gameloop.pauseLoop();
						display.setTitle("¸,ø¤º°`°º¤ø,¸¸,ø¤º° FINAL SCORE: " + score + " °º¤ø,¸¸,ø¤º°`°º¤ø,¸", true);
					}
				}
			}
			
			if(ball.getX() <= 0 || ball.getX() + ball.getWidth() >= Constants.screenwidth)
				ball.setVelX(ball.getVelX() * -1);
			
			if(ball.getY() <= 0)
				ball.setVelY(ball.getVelY() * -1);
			
			
			if(ball.getY() > Constants.screenheight) {				
				lifes--;
				display.setTitle("SCORE: " + score + "   LIFES: " + lifes + "    ▁ ▂ ▄ ▅ ▆ ▇ █ BREAKOUT █ ▇ ▆ ▅ ▄ ▂ ▁");
				
				if(lifes == 0) {
					gameloop.pauseLoop();
					display.setTitle("¸,ø¤º°`°º¤ø,¸¸,ø¤º° FINAL SCORE: " + score + " °º¤ø,¸¸,ø¤º°`°º¤ø,¸", true);
				}
				
				
				ball.setPosition(Constants.screenwidth_half / 2 - brickHeight / 4, Constants.screenheight / 6 * 5);
				player.setPosition(Constants.screenwidth_half - brickWidth, Constants.screenheight - brickHeight);
				
				ball.setVelX(Utilities.randFloat(-5, 5));
				ball.setVelY(-5);
			}
			
			
			if(ball.intersect(player) && ball.getY() + ball.getHeight() <= player.getY() + player.getHeight() / 2) {
				ball.setVelY(ball.getVelY() * -1);
				
				float distToCenter 		= ball.getCenterX() - player.getCenterX();
				float mappedDistance 	= Utilities.mapFloat(distToCenter, -(ball.getWidth() / 2 - player.getWidth() / 2), -(ball.getWidth() * 2 - player.getWidth() * 2), 1f, 1.5f);
				
				ball.setVelX(maxSpeedHor * (mappedDistance * (ball.getCenterX() < player.getCenterX() ? -1 : 1)));
			}
		}
	};
	
	private Runnable handleInput = new Runnable() {
		@Override
		public void run() {
			// KEY-EVENTS //
			if(inputhandler.isDown(KeyEvent.VK_A))
				player.setX(player.getX() - 6);
			if(inputhandler.isDown(KeyEvent.VK_D))
				player.setX(player.getX() + 6);
		}
	};
	
	
	private void addBrick(int gridX, int gridY, Color color) {
		Window window = new Window(
				borderWidth + gridX * brickWidth,
				Constants.screenheight / 10 + gridY * brickHeight,
				brickWidth,
				brickHeight,
				false);
		
		window.setColor(color);
		bricks.add(window);
	}
	
	
	
	
	
	public Breakout() {
		inputhandler 	= new KeyInput(handleInput);
		gameloop 		= new Gameloop(60, loop, inputhandler);
		display = new Window(Constants.screenwidth_half - Constants.screenwidth / 6, Constants.screenheight / 20, Constants.screenwidth / 3, 0, inputhandler);
		display.setTitle("...LOADING...", true);
		display.setColor(65, 65, 65);
		
		
		for(int i = 0; i < amtOfRows; i++) {
			for(int j = 0; j < amtOfCols; j++) {
				addBrick(j, i, colors[i]);
			}
		}	
		
		ball = new Window(Constants.screenwidth_half - brickHeight / 4, Constants.screenheight / 6 * 5, brickHeight / 2, brickHeight / 2, false);
		ball.setColor(colors[0]);
		
		player = new Window(Constants.screenwidth_half - brickWidth, Constants.screenheight - brickHeight, brickWidth * 2, brickHeight / 2, false);
		player.setColor(colors[0]);
		
		display.setFocus();
		display.setTitle("SCORE: " + score + "   LIFES: " + lifes + "    ▁ ▂ ▄ ▅ ▆ ▇ █ BREAKOUT █ ▇ ▆ ▅ ▄ ▂ ▁");
		
		ball.setVelX(baseVelocity / 2);
		ball.setVelY(-5);

		
		
		gameloop.startLoop();
	}
	
	public static void main(String args[]) {
		new Breakout();
	}

}
