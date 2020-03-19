package fr.tom.demineur;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import fr.tom.demineur.controleur.Keyboard;
import fr.tom.demineur.controleur.Mouse;
import fr.tom.demineur.model.Case;
import fr.tom.demineur.model.CaseStateEnum;
import fr.tom.demineur.model.World;
import fr.tom.demineur.view.ILayer;

public class PlayGameMode extends GameMode {

	private boolean firstClick;
	private World world;

	private final int worldWidth = 25;
	private final int worldHeight = 30;

	private final int nbBombe = 100;

	private boolean endGame;
	private boolean bombeClicked;

	private ILayer mainLayer;

	private Mouse mouse;
	private Keyboard keyboard;

	@Override
	public void init() {
		firstClick = false;
		endGame = false;
		bombeClicked = false;

		world = new World(worldWidth, worldHeight, nbBombe);

		mainLayer = gui.createLayer();
		mainLayer.setSpriteCount(world.getWorld().length * world.getWorld()[0].length);
		mainLayer.setTileSize(32, 32);
		try {
			mainLayer.setTexture("tile.png");
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < world.getWorld().length; i++) {
			for (int j = 0; j < world.getWorld()[i].length; j++) {
				mainLayer.setSpriteTexture(i * worldWidth + j, 2, 0);
				mainLayer.setSpriteLocation(i * worldWidth + j, j * 32, i * 32);
			}
		}

		gui.createWindow(worldWidth * 32, worldHeight * 32, "demineur");
		mouse = gui.getMouse();
		keyboard = gui.getKeybpard();
	}

	private long lastUpdate;
	private long lastUpdate2;

	@Override
	public void update() {
		long now = System.nanoTime();
		if ((now - lastUpdate) >= 1_000_000_000 / 4) {
			lastUpdate = now;
			int count = 0;
			for(int i = 0; i<world.getWorld().length; i++) {
				for(int j = 0; j<world.getWorld()[i].length; j++) {
					if(world.getWorld()[i][j].getState() == CaseStateEnum.UNREVEALED && world.getWorld()[i][j].getContent() != -1)
						count++;
				}
			}
			if(count==0)
				endGame = true;
		}
		if ((now - lastUpdate2) >= 1_000_000_000 / 12) {
			for (int i = 0; i < world.getWorld().length; i++) {
				for (int j = 0; j < world.getWorld()[i].length; j++) {
					switch (world.getWorld()[i][j].getState()) {
					case FLAGED:
						mainLayer.setSpriteTexture(i * worldWidth + j, 3, 0);
						break;
					case UNREVEALED:
						mainLayer.setSpriteTexture(i * worldWidth + j, 2, 0);
						break;
					case REVEALED:
						switch (world.getWorld()[i][j].getContent()) {
						case -1:
							mainLayer.setSpriteTexture(i * worldWidth + j, 0, 1);
							break;
						case 0:
							mainLayer.setSpriteTexture(i * worldWidth + j, 4, 0);
							break;
						case 1:
							mainLayer.setSpriteTexture(i * worldWidth + j, 5, 0);
							break;
						case 2:
							mainLayer.setSpriteTexture(i * worldWidth + j, 6, 0);
							break;
						case 3:
							mainLayer.setSpriteTexture(i * worldWidth + j, 7, 0);
							break;
						case 4:
							mainLayer.setSpriteTexture(i * worldWidth + j, 8, 0);
							break;
						case 5:
							mainLayer.setSpriteTexture(i * worldWidth + j, 1, 1);
							break;
						case 6:
							mainLayer.setSpriteTexture(i * worldWidth + j, 2, 1);
							break;
						case 7:
							mainLayer.setSpriteTexture(i * worldWidth + j, 3, 1);
							break;
						case 8:
							mainLayer.setSpriteTexture(i * worldWidth + j, 4, 1);
							break;
						}
					}

				}
			}
		}
	}

	@Override
	public void render() {
		if (!gui.beginPaint()) {
			return;
		}
		try {
			gui.drawLayer(mainLayer);
		} finally {
			gui.endPaint();
		}
	}

	@Override
	public void handleInput() {
		int selectedTileX = mouse.getX()/32;
		int selectedTileY = mouse.getY()/32;		
		
		if(selectedTileX >= 0 && selectedTileX < worldWidth && selectedTileY >= 0 && selectedTileY < worldHeight) {
					
			if(mouse.isButtonPressed(MouseEvent.BUTTON1)) {
				if(!firstClick) {
					world.init(selectedTileX, selectedTileY);
					world.reveal(selectedTileX, selectedTileY);
					firstClick = true;
				}
				else {
					if(world.getWorld()[selectedTileY][selectedTileX].getState()==CaseStateEnum.UNREVEALED) {
						world.reveal(selectedTileX, selectedTileY);
						if(world.getWorld()[selectedTileY][selectedTileX].getContent()==-1) {
							endGame = true;
							bombeClicked = true;
						}
					}
					if(world.getWorld()[selectedTileY][selectedTileX].getState()==CaseStateEnum.REVEALED) {
						world.revealZone(selectedTileX, selectedTileY);
						for(int i = 0; i<world.getWorld().length; i++) {
							for(int j = 0; j<world.getWorld()[i].length; j++) {
								if(world.getWorld()[i][j].getState()==CaseStateEnum.REVEALED && world.getWorld()[i][j].getContent()==-1) {
									world.revealAll();
									endGame = true;
									bombeClicked = true;
								}
							}
						}
					}

				}
			}
			
			if(mouse.isButtonPressed(MouseEvent.BUTTON3)) {
				if(world.getWorld()[selectedTileY][selectedTileX].getState() == CaseStateEnum.UNREVEALED)
					world.getWorld()[selectedTileY][selectedTileX].setState(CaseStateEnum.FLAGED);
				else if(world.getWorld()[selectedTileY][selectedTileX].getState() == CaseStateEnum.FLAGED)
					world.getWorld()[selectedTileY][selectedTileX].setState(CaseStateEnum.UNREVEALED);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			if(keyboard.isKeyPressed(KeyEvent.VK_N)) {
				world.reset();
				endGame = false;
				bombeClicked = false;
				firstClick = false;
			}
				
			
		}

	}

}
