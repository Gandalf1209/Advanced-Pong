package com.gandalf1209.main;

public class Player {

	private int x;
	private int y;
	private int w;
	private int h;
	
	private int lastY = 0;
	private int speed;
	
	public int score = 0;
	public int bonus = 1;
	
	public Powerup power;
	
	public boolean aimbot = false;
	public int aimTime = 0;
	
	public Player(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void usePowerup() {
		Powerup.use(this, power);
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getLastY() {
		return lastY;
	}

	public void setLastY(int lastY) {
		this.lastY = lastY;
	}
	
}