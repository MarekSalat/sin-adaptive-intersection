package cz.fit.sin.model.world;

public class Position {
	private int x;
	private int y;
	
	public Position(int x, int y) {		
		this.x = x;
		this.y = y;
	}
	
	public int getx() {return this.x;}
	public int gety() {return this.y;}
	public void setx(int x) {this.x=x;}
	public void sety(int y) {this.y=y;}
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
