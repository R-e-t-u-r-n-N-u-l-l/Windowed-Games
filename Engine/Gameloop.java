package engine;

public class Gameloop {
	
	private boolean running 	 	= false;
	private boolean displayFPS		= false;
	
	private double ns;
	private double delta = 0;
	
	private static long timer 		= System.currentTimeMillis();
	private static long lastTime 	= System.nanoTime();
	
	private static int updates 		 = 0;
	private static int frames 		= 0;
	
	
	private Runnable callback;
	private KeyInput input;
	
	
	public Gameloop(double maxFPS, Runnable callback, KeyInput input) {
		this.ns = Math.pow(10, 9) / maxFPS;
		
		this.callback 	= callback;
		this.input 		= input;
	}
	
	
	public void startLoop() { running = true;  runLoop(); }
	public void pauseLoop() { running = false; runLoop(); }
	
	
	private void runLoop() {
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				tick();
				render();
				frames++;
				updates++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				
				if(displayFPS)
					System.out.println("FPS: " + frames + " TICKS: " + updates);
				
				frames = 0;
				updates = 0;
			}
		}
	}

	
	private void tick() {
		this.input.handleInput();
		this.callback.run();
	}
	
	
	private void render() {

	}
	
}

