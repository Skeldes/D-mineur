package fr.tom.demineur.controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class AWTMouse implements Mouse, MouseListener, MouseMotionListener {

	private boolean[] buttons;
	private int x;
	private int y;

	public AWTMouse() {
		buttons = new boolean[4];
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() <= 3){
			buttons[e.getButton()] = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() <= 3)
			buttons[e.getButton()] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public boolean isButtonPressed(int button) {
		if(button >= buttons.length)
			return false;
		return buttons[button];
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

}
