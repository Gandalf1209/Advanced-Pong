package com.gandalf1209.main;

import java.awt.image.BufferedImage;

import com.gandalf1209.yge2.graphics.GraphicsLoader;

public class Textures {

	public static BufferedImage bg;
	public static BufferedImage paddle;
	public static BufferedImage ptemplate;
	public static BufferedImage bonus;
	public static BufferedImage freeze;
	public static BufferedImage aimbot;
	public static BufferedImage slowmo;
	
	public static void init() {
		GraphicsLoader gl = new GraphicsLoader();
		gl.setDefaultLoadingDirectory("/textures/");
		bg = gl.loadGraphic("BG.png");
		paddle = gl.loadGraphic("Paddle.png");
		ptemplate = gl.loadGraphic("PTemplate.png");
		bonus = gl.loadGraphic("Bonus.png");
		freeze = gl.loadGraphic("Freeze.png");
		aimbot = gl.loadGraphic("AimBot.png");
		slowmo = gl.loadGraphic("SlowMo.png");
	}
	
}