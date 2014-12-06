package com.gandalf1209.main;

public class Ball {

	private int x;
	private int y;
	private int w;
	private int h;
	
	public int xspeed = 10;
	public int yspeed = 11;
	public int dx = -xspeed;
	public int dy = yspeed;
	
	public boolean player = true;
	
	public Ball(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void movementHandler() {
		setX(getX() + dx);
		setY(getY() + dy);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return w;
	}

	public void setWidth(int w) {
		this.w = w;
	}

	public int getHeight() {
		return h;
	}

	public void setHeight(int h) {
		this.h = h;
	}
	
}