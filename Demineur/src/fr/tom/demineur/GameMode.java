
package fr.tom.demineur;

import fr.tom.demineur.view.IGUIFacade;

/**
 * @author thoma
 *
 */
public abstract class GameMode {
	
	private Main main;
	protected IGUIFacade gui;
	
	public void setGameMode(GameMode mode) {
		main.setGameMode(mode);
	}
	
	public void setParent(Main main) {
		this.main = main;
	}
	
	public void setGui(IGUIFacade gui) {
		this.gui = gui;
	}
	
	
	public abstract void init();
	
	public abstract void update();
	
	public abstract void render();

	public abstract void handleInput();

}
