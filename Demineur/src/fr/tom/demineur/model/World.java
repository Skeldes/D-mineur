package fr.tom.demineur.model;

import java.util.Random;

public class World {

	private Case[][] world;

	private int worldWidth;
	private int worldHeight;

	private int nbBombe;
	

	public World(int worldWidth, int worldHeight, int nbBombe) {
		world = new Case[worldHeight][worldWidth];
		this.nbBombe = nbBombe;
		this.worldHeight = worldHeight;
		this.worldWidth = worldWidth;
		firstInit();
	}
	
	public void reset() {
		firstInit();
	}

	private void firstInit() {
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				Case c = new Case(0, CaseStateEnum.UNREVEALED);
				c.setX(j);
				c.setY(i);
				world[i][j] = c;
			}
		}
	}

	public void init(int x, int y) {
		Random rd = new Random();
		int countBombe = nbBombe;
		while (countBombe > 0) {
			int randX = rd.nextInt(worldWidth);
			int randY = rd.nextInt(worldHeight);
			if (!(x+1 == randX && y+1 == randY)&&
				!(x+1 == randX && y == randY) &&
				!(x+1 == randX && y-1 == randY) &&
				!(x == randX && y+1 == randY) &&
				!(x == randX && y == randY) &&
				!(x == randX && y-1 == randY) &&
				!(x-1 == randX && y+1 == randY) &&
				!(x-1 == randX && y == randY) &&
				!(x-1 == randX && y-1 == randY) && world[randY][randX].getContent() != -1) {
				world[randY][randX].setContent(-1);
				countBombe--;
			}
		}
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				if(world[i][j].getContent() == -1) {
					incrementContent(j-1,i-1);
					incrementContent(j-1,i);
					incrementContent(j-1,i+1);
					incrementContent(j,i-1);
					incrementContent(j,i+1);
					incrementContent(j+1,i-1);
					incrementContent(j+1,i);
					incrementContent(j+1,i+1);
				}
			}
		}
	}
	
	public void reveal(int x, int y) {
		System.out.println(x + " reveal "+ y);
		if(caseValid(x,y)) {
			if(world[y][x].getState() == CaseStateEnum.UNREVEALED) {
				if(world[y][x].getContent()==0) {
					world[y][x].setState(CaseStateEnum.REVEALED);
					reveal(x-1,y-1);
					reveal(x-1,y);
					reveal(x-1,y+1);
					reveal(x,y-1);
					reveal(x,y+1);
					reveal(x+1,y-1);
					reveal(x+1,y);
					reveal(x+1,y+1);
				}
				else {
					world[y][x].setState(CaseStateEnum.REVEALED);
				}
			}
		}
	}
	
	public void revealZone(int x, int y) {
		if(checkFlag(x,y)) {
			for(int i = -1; i<2; i++) {
				for(int j = -1;j<2;j++) {
					if(caseValid(x-j,y-i)) {
						reveal(x-j,y-i);
					}
				}
			}
		}
	}
	
	public void revealAll() {
		for(int i = 0; i< world.length; i++) {
			for(int j = 0; j<world[i].length; j++) {
				world[i][j].setState(CaseStateEnum.REVEALED);
			}
		}
	}
	
	private boolean checkFlag(int x, int y) {
		if(world[y][x].getContent()!=-1) {
			int nbFlag = world[y][x].getContent();
			int count = 0;
			for(int i = -1; i<2; i++) {
				for(int j = -1; j<2; j++) {
					if(caseValid(x-j,y-i)) {
						if(world[y-i][x-j].getState() == CaseStateEnum.FLAGED)
							count++;
					}
				}
			}
			return nbFlag==count;
		}
		return true;
	}
	
	
	private boolean caseValid(int x, int y) {
		return (x<worldWidth && x>=0 && y<worldHeight && y>=0);
	}
	
	
	private void incrementContent(int x, int y) {
		if(x>= 0 && x < worldWidth && y>= 0 && y<worldHeight) {
			if(world[y][x].getContent() != -1)
				world[y][x].setContent(world[y][x].getContent()+1);
		}
	}

	public Case[][] getWorld() {
		return world;
	}

	public void setWorld(Case[][] world) {
		this.world = world;
	}

}
