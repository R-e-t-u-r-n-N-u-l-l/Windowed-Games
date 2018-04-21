package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


public class KeyInput implements KeyListener {
	
	private List<Integer> activeKeys = new ArrayList<Integer>();
	private Runnable callback;

	public KeyInput(Runnable callback) {
		this.callback = callback;
	}
	
	//////////////
	// KEY EVENTS
	@Override
	public void keyPressed(KeyEvent e) 	{ addKey(e.getKeyCode());    }

	@Override
	public void keyReleased(KeyEvent e) { removeKey(e.getKeyCode()); }

	@Override
	public void keyTyped(KeyEvent e) 	{}
	// END
	//////////////
	
	
	//////////////
	// UTILITIES
	private void addKey(int key) {
		if(!isDown(key))
			activeKeys.add(key);
	}
	private void removeKey(int key) {
		if(isDown(key))
			activeKeys.remove(activeKeys.indexOf(key));
	}
	
	public boolean isDown(int key) {
		if(activeKeys.contains(key))
			return true;
		return false;
	}
	// END
	//////////////
	
	
	//////////////
	// HANDLING
	public void handleInput() {
		this.callback.run();
	}
	// END
	//////////////

}