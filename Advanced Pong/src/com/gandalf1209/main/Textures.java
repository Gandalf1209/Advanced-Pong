package com.gandalf1209.main;

import java.awt.image.BufferedImage;

import com.gandalf1209.yge2.graphics.GraphicsLoader;

public class Textures {

	public static BufferedImage bg;
	public static BufferedImage paddle;
	public static BufferedImage ptemplate;
	
	public static void init() {
		GraphicsLoader gl = new GraphicsLoader();
		gl.setDefaultLoadingDirectory("/textures/");
		bg = gl.loadGraphic("BG.png");
		paddle = gl.loadGraphic("Paddle.png");
		ptemplate = gl.loadGraphic("PTemplate.png");
	}
	
}