package engine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JFrame;

public class Window {
	
	private int x, y, width, height;
	private float velx = 0f, vely = 0f;
	private String title = "";
	
	private JFrame frame;
	
	public Window(int x, int y, int width, int height) {
		this.x 		= x;
		this.y 		= y;
		this.setWidth(width);
		this.setHeight(height);
		this.setTitle(title);
		
		initFrame(false);
	}
	
	public Window(int x, int y, int width, int height, boolean undecorated) {
		this.x 		= x;
		this.y 		= y;
		this.setWidth(width);
		this.setHeight(height);
		this.setTitle(title);
		
		initFrame(undecorated);
	}
	
	public Window(int x, int y, int width, int height, KeyInput input) {
		this.x 		= x;
		this.y 		= y;
		this.setWidth(width);
		this.setHeight(height);
		this.setTitle(title);
		
		initFrame(input, false);
	}
	
	public Window(int x, int y, int width, int height, KeyInput input, boolean undecorated) {
		this.x 		= x;
		this.y 		= y;
		this.setWidth(width);
		this.setHeight(height);
		this.setTitle(title);
		
		initFrame(input, undecorated);
	}
	
	private void initFrame(boolean undecorated) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(this.x, this.y);
		frame.setSize(this.width, this.height);
		frame.setUndecorated(undecorated);
		frame.setVisible(true);
	}
	
	private void initFrame(KeyInput input, boolean undecorated) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(this.x, this.y);
		frame.setSize(this.width, this.height);
		frame.addKeyListener(input);
		frame.setUndecorated(undecorated);
		frame.setVisible(true);
	}
	
	public boolean intersect(Window window) {
		if( this.x 					< window.getX() + window.getWidth() 	&&
			this.x + this.width 	> window.getX() 						&&
			this.y					< window.getY() + window.getHeight() 	&&
			this.height + this.y 	> window.getY())
			return true;
		
		return false;
	}
	
	public int getIntersectingSide(Window window) {
		if(!intersect(window))
			return -1;
		
		// https://gamedev.stackexchange.com/questions/29786/a-simple-2d-rectangle-collision-algorithm-that-also-determines-which-sides-that
		float w = 0.5f * (width + window.getWidth());
		float h = 0.5f * (height +window.getHeight());
		float dx = getCenterX() - window.getCenterX();
		float dy = getCenterY() - window.getCenterY();

		 float wy = w * dy;
		 float hx = h * dx;

		 if (wy > hx)
			 if (wy > -hx)
				 return 0;	// top
		     else
		    	 return 1;	// left
		 else
		     if (wy > -hx)
		    	 return 3;	// right
		     else
		    	 return 2;	// bottom

	}
	
	public void move() {
		this.x += velx;
		this.y += vely;
		frame.setLocation(x, y);
	}
	
	public void setFocus() {
		frame.setVisible(false);
		frame.setVisible(true);
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		frame.setLocation(x, y);
	}
	
	public void setColor(Color color) {
		frame.getContentPane().setBackground(color);
	}
	public void setColor(int r, int g, int b) {
		frame.getContentPane().setBackground(new Color(r, g, b));
	}
	public Color getColor() {
		return frame.getBackground();
	}
	
	
	public float getCenterX() {
		return x + width * 0.5f;
	}
	public float getCenterY() {
		return y + height * 0.5f;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		frame.setLocation(x, y);
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		frame.setLocation(x, y);
	}
	
	
	public float getVelX() {
		return velx;
	}
	public void setVelX(float x) {
		this.velx = x;
	}
	public float getVelY() {
		return vely;
	}
	public void setVelY(float y) {
		this.vely = y;
	}
	

	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		if(frame != null)
			frame.setTitle(this.title);
	}
	public void setTitle(String title, boolean centered) {
		this.title = title;
		if(frame != null && centered) {
			
			frame.setFont(new Font("System", Font.PLAIN, 14));
			Font f = frame.getFont();
			FontMetrics fm = frame.getFontMetrics(f);
			
			int textLength 	= fm.stringWidth(title);
			int spaceLength = fm.stringWidth(" ");
			
			int toCenter 	= frame.getWidth() / 2 - (textLength / 2);
			int amtOfSpaces = toCenter / spaceLength;
			
			String padding = String.format("%" + amtOfSpaces + "s", "");
			frame.setTitle(padding + title);
		}
	}
	
	

}
