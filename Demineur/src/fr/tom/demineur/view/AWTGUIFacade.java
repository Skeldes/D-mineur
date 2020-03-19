package fr.tom.demineur.view;

import java.awt.Graphics;

import fr.tom.demineur.controleur.Keyboard;
import fr.tom.demineur.controleur.Mouse;

public class AWTGUIFacade implements IGUIFacade {
	
	public AWTWindow window;
	public Graphics g;

	@Override
	public void createWindow(int width, int height, String title) {
		window = new AWTWindow();
		window.init(title);
		window.createCanvas(width, height);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	@Override
	public boolean isClosingRequested() {
		return window.isClosingRequested();
	}

	@Override
	public void setClosingRequested() {
		window.setClosingRequested();
	}

	@Override
	public void dispose() {
		window.dispose();
	}

	@Override
	public boolean beginPaint() {
		if(g!=null) {
			g.dispose();
		}
		g = window.createGraphics();
		if(g==null) {
			return false;
		}
		return true;
	}

	@Override
	public void endPaint() {
		if(g == null){
			return;
		}
		g.dispose();
		g = null;
		window.switchBuffers();
		
	}

	@Override
	public ILayer createLayer() {
		return new AWTLayer();
	}

	@Override
	public void drawLayer(ILayer layer) {
		if(g == null) {
			return;
		}
		if(layer == null) {
			throw new IllegalArgumentException("Layer null");
		}
		if(!(layer instanceof AWTLayer)) {
			throw new IllegalArgumentException("Type layer invalid");
		}
		AWTLayer awtLayer = (AWTLayer) layer;
		awtLayer.draw(g);
	}

	@Override
	public Mouse getMouse() {
		if(window == null) {
			throw new RuntimeException("Il faut d'abord créer une fenêtre");
		}
		return window.getMouse();
	}

	@Override
	public Keyboard getKeybpard() {
		if(window == null) {
			throw new RuntimeException("Il faut d'abord créer une fenêtre");
		}
		return window.getKeyboard();
	}

	

}
