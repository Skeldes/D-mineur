package fr.tom.demineur;

import fr.tom.demineur.view.AWTGUIFacade;
import fr.tom.demineur.view.IGUIFacade;

public class Main {

	private GameMode gm;
	private IGUIFacade gui;
	
	public synchronized void setGameMode(GameMode mode) {
		try {
			mode.setParent(this);
			mode.setGui(gui);
			mode.init();
			this.gm = mode;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setGUI(IGUIFacade gui) {
		this.gui = gui;
	}

	public void run() {
		int count = 0;
		long begin = System.nanoTime();
		while (! gui.isClosingRequested()) {
			synchronized (gm) {
				gm.handleInput();
				gm.update();
				gm.render();
			}
			count++;
		}
		gui.dispose();
		long end = System.nanoTime();
		double fps = count / ((end - begin) / 1_000_000_000.0);
		System.out.println(fps);
	}

	public static void main(String[] args) {
		Main main = new Main();
		main.setGUI(new AWTGUIFacade());
		main.setGameMode(new PlayGameMode());
		main.run();
	}
}
