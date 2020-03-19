package fr.tom.demineur.model;

public class Case {
	private int content;
	private CaseStateEnum state;
	
	private int x;
	private int y;
	
	public Case(int content, CaseStateEnum state) {
		this.content = content;
		this.state = state;
	}

	public int getContent() {
		return content;
	}

	public CaseStateEnum getState() {
		return state;
	}

	public void setContent(int content) {
		this.content = content;
	}

	public void setState(CaseStateEnum state) {
		this.state = state;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
}
