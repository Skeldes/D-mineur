package fr.tom.demineur.view;

import fr.tom.demineur.controleur.Keyboard;
import fr.tom.demineur.controleur.Mouse;

public interface IGUIFacade {

	public void createWindow(int width, int height, String title);
	
	public boolean isClosingRequested();
	
	public void setClosingRequested();
	
	public void dispose();
	
	public boolean beginPaint();
	
	public void endPaint();
	
	public ILayer createLayer();
	
	public void drawLayer(ILayer layer);
	
	public Mouse getMouse();
	
	public Keyboard getKeybpard();
}
