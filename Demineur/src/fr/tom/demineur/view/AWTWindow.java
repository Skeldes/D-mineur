package fr.tom.demineur.view;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import fr.tom.demineur.controleur.AWTKeyboard;
import fr.tom.demineur.controleur.AWTMouse;

public class AWTWindow extends Frame{
	private final int windowWidth = 800;
	private final int windowHeight = 960;
	
	private Canvas canvas;
	private int canvasWidth;
	private int canvasHeight;
	
	private boolean closingRequested;
	
	private BufferStrategy bs;
	
	private AWTMouse mouse;
	private AWTKeyboard keyboard;
	
	public void init(String title) {
		setTitle(title);
		setSize(windowWidth, windowHeight);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closingRequested = true;
			}
		});
		closingRequested = false;
	}
	
	public void createCanvas(int width, int height) {
		canvas = new Canvas();
		canvasWidth = width;
		canvasHeight = height;
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		add(canvas);
		
		mouse = new AWTMouse();
		keyboard = new AWTKeyboard();
		canvas.addMouseListener(mouse);
		canvas.addMouseMotionListener(mouse);
		canvas.addKeyListener(keyboard);
		pack();
	}
	
	public Graphics createGraphics() {
		bs = canvas.getBufferStrategy();
		if(bs == null) {
			canvas.createBufferStrategy(2);
			return null;
		}
		return bs.getDrawGraphics();
	}
	
	public boolean isClosingRequested() {
		return closingRequested;
	}
	
	public void setClosingRequested(boolean closingRequested) {
		this.closingRequested = closingRequested;
	}
	
	public void switchBuffers() {
		if(bs != null) {
			bs.show();
		}
	}
	
	public int getCanvasWidth() {
		return canvasWidth;
	}
	
	public int getCanvasHeigth() {
		return canvasHeight;
	}
	
	public AWTMouse getMouse() {
		return mouse;
	}
	
	public AWTKeyboard getKeyboard() {
		return keyboard;
	}
	
	public void setClosingRequested() {
		this.closingRequested = true;
	}
}
