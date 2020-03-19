package fr.tom.demineur.controleur;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AWTKeyboard implements Keyboard, KeyListener {

	private boolean[] keys;
	
	public AWTKeyboard() {
		keys = new boolean[0x10000];
	}
	
	@Override
	public boolean isKeyPressed(int keyCode) {
		if(keyCode >= keys.length)
			return false;
		return keys[keyCode];
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < keys.length)
			keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() < keys.length)
			keys[e.getKeyCode()] = false;
	}

}
