package Astar;
public class Nodo {
	
	private int g;
	private int h;
	private int x;
	private int y;
	
	private Nodo parent;
	
	public Nodo(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getF() {
		return g + h;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}
	

	public Nodo getParent() {
		return this.parent;
	}

	public void setParent(Nodo parent) {
		this.parent = parent;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Object obj) {
		Nodo other = (Nodo)obj;
		
		return this.x == other.getX() && this.y == other.getY();
	}
	
	public String toString() {
		return "[" + x + ", " + y + "]" ;//+ " G:"+this.g+" H:"+this.h+" -> Parent:"+ this.parent;
	}
}
