package com.gandalf1209.main;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import com.gandalf1209.yge2.engine.Game;
import com.gandalf1209.yge2.graphics.Display;
import com.gandalf1209.yge2.graphics.GraphicsX;
import com.gandalf1209.yge2.input.Keys;
import com.gandalf1209.yge2.util.FontHandler;

public class MainGame implements Game {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static int WIDTHX;
	public static int HEIGHTX;
	
	public boolean ready = false;
	
	private Display d;
	private Player p;
	private Computer c;
	private Ball b;
	
	public static void main(String[] args) {
		new MainGame().init();
	}
	
	public void init() {
		d = new Display("Advanced Pong - LD31", WIDTH, HEIGHT, this);
		p = new Player(30, 250, 20, 100);
		c = new Computer(750, 250, 20, 100, 10);
		b = new Ball(400, 300, 20, 20);
		
		Textures.init();
		FontHandler fh = new FontHandler();
		fh.setDefaultLoadingDirectory("/fonts/");
		fh.loadFont("Code New Roman.otf");
		
		d.keyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
				if (key == Keys.ESCAPE) {
					if (!ready) {
						ready = true;
					} else {
						ready = false;
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
		});
		
		d.mouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if (ready) {
					p.setY(e.getY() - (50 + d.getWindow().getInsets().top));
				}
			}
		});
		
		d.getWindow().setResizable(false);
		d.getWindow().setVisible(true);
		
		WIDTHX = WIDTH - d.getWindow().getInsets().right;
		HEIGHTX = HEIGHT - d.getWindow().getInsets().top;
		
		d.initTime(30);
	}

	@Override
	public void render(GraphicsX g) {
		g.setBGImage(Textures.bg);
		
		g.setColor(g.hex("#D4D4D4"));
		g.fillOval(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		
		g.addImage(Textures.paddle, p.getX(), p.getY(), p.getWidth(), p.getHeight());
		g.addImage(Textures.paddle, c.getX(), c.getY(), c.getWidth(), c.getHeight());
		
		g.setFont(new Font("Code New Roman", Font.PLAIN, 30));
		g.setColor(g.hex("#FFFFFF"));
		if (!ready) {
			g.drawString("Press ESCAPE to continue!", 200, 300);
		}
		g.drawString("You: " + p.score, 15, 30);
		g.drawString("CPU: " + c.score, 15, 60);
	}

	@Override
	public void update() {
		
		if (ready) {
			// Ball Handler
			b.movementHandler();
			if (b.getY() < (HEIGHTX - HEIGHT) + b.getHeight()) {
				b.dy = b.yspeed;
			}
			if (b.getY() > HEIGHTX - b.getHeight()) {
				b.dy = -b.yspeed;
			}
			if (b.getX() == p.getX() && b.getY() + b.getHeight() > p.getY()
					&& b.getY() < p.getY() + p.getHeight()) {
				if (b.getY() + b.getHeight() > p.getY() + (p.getHeight() / 2)) {
					b.dy = b.yspeed;
				} else {
					b.dy = -b.yspeed;
				}
				b.dx = b.xspeed;
			}
			if (b.getX() == c.getX() && b.getY() + b.getHeight() > c.getY()
					&& b.getY() < c.getY() + c.getHeight()) {
				if (b.getY() + b.getHeight() > c.getY() + (c.getHeight() / 2)) {
					b.dy = b.yspeed;
				} else {
					b.dy = -b.yspeed;
				}
				b.dx = -b.xspeed;
			}
			if (b.getX() < 0) {
				c.score += c.bonus;
				resetGame();
			}
			if (b.getX() > WIDTH) {
				p.score += p.bonus;
				resetGame();
			}
			
			
			// Computer AI
			if (c.getY() < b.getY()) {
				c.setY(c.getY() + c.getSpeed());
			}
			if (c.getY() > b.getY()) {
				c.setY(c.getY() - c.getSpeed());
			}
		}
		
	}
	
	public void resetGame() {
		ready = false;
		p.setY(250);
		c.setY(250);
		b.setX(400);
		b.setY(300);
		b.dx = -b.xspeed;
		b.dy = b.yspeed;
	}
	
}