package com.gandalf1209.main;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;

import com.gandalf1209.yge2.audio.Sound;
import com.gandalf1209.yge2.engine.Game;
import com.gandalf1209.yge2.engine.Mathf;
import com.gandalf1209.yge2.engine.Vector2;
import com.gandalf1209.yge2.graphics.Display;
import com.gandalf1209.yge2.graphics.GraphicsX;
import com.gandalf1209.yge2.input.Keys;
import com.gandalf1209.yge2.util.FontHandler;
import com.gandalf1209.yge2.util.Source;

public class MainGame implements Game {

	public static final MainGame game = new MainGame();
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static int WIDTHX;
	public static int HEIGHTX;
	
	public boolean fresh = false;
	public boolean ready = false;
	public boolean slow = false;
	public int slowTime = 0;
	
	private Display d;
	private Player p;
	public Computer c;
	public Ball b;
	
	private int lastSeconds = (int) System.currentTimeMillis();
	private int seconds = (int) System.currentTimeMillis();
	private int realSeconds = 0;
	
	public static void main(String[] args) {
		game.init();
	}
	
	public void init() {
		fresh = isNew();
		
		d = new Display("Advanced Pong - LD31", WIDTH, HEIGHT, this);
		p = new Player(30, 250, 20, 100);
		c = new Computer(750, 250, 20, 100, 10);
		b = new Ball(400, 300, 20, 20);
		
		Textures.init();
		Powerup.init();
		Sound.setDefaultLoadingDirectory("/audio/");
		
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
				if (key == KeyEvent.VK_SPACE) {
					if (ready && p.power != null) {
						p.usePowerup();
					}
				}
				if (key == Keys.ENTER) {
					if (fresh) {
						fresh = false;
					}
				}
				if (key == Keys.S) {
					Powerup.use(p, Powerup.slowmo);
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
				if (ready && !p.aimbot) {
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
		
		for (int i = 0; i < Powerup.active.size(); i++) {
			Powerup p = Powerup.active.get(i);
			g.addImage(p.getTexture(), new Vector2(p.getX(), p.getY()), 30, 30);
		}
		
		g.setColor(g.hex("#D4D4D4"));
		if (!b.invisible) {
			g.fillOval(b.getX(), b.getY(), b.getWidth(), b.getHeight());
		}
		
		g.addImage(Textures.paddle, p.getX(), p.getY(), p.getWidth(), p.getHeight());
		g.addImage(Textures.paddle, c.getX(), c.getY(), c.getWidth(), c.getHeight());
		
		g.setFont(new Font("Code New Roman", Font.PLAIN, 30));
		g.setColor(g.hex("#FFFFFF"));
		g.drawString("You: " + p.score, 15, 30);
		g.drawString("CPU: " + c.score, 15, 60);
		
		if (p.power != null) {
			g.addImage(p.power.getTexture(), 375, 15, 50, 50);
			g.drawString(p.power.getName(), 15, (HEIGHTX) - 15);
		}
		
		if (!ready) {
			g.drawString("Press ESCAPE to continue!", 200, 300);
		}
		
		if (fresh) {
			g.setColor(g.hex("#828282"));
			g.fillRect(100, 100, WIDTHX - 200, HEIGHTX - 200);
			g.setColor(g.hex("#4A4A4A"));
			g.drawRect(100, 100, WIDTHX - 200, HEIGHTX - 200);
			g.setColor(g.hex("#000"));
			g.drawString("Welcome!", 115, 130);
			g.setFont(new Font("Code New Roman", Font.PLAIN, 24));
			g.drawString("Welcome to Advanced Pong!", 115, 170);
			g.drawString("Made by Gandalf1209 for Ludum Dare 31", 115, 200);
			g.drawString("Press Escape to start and to pause", 115, 230);
			g.drawString("Press Space to use your powerups!", 115, 260);
			g.drawString("Have a good time playing!", 115, 290);
			g.drawString("Press ENTER to continue", 115, 320);
		}
	}

	@Override
	public void update() {
		
		if (ready) {
			// Player Logic
			p.setSpeed(Math.abs(p.getY() - p.getLastY()));
			p.setLastY(p.getY());
			if (p.aimbot) {
				p.aimTime++;
				p.setY(b.getY());
				if (p.aimTime == 30) {
					p.aimbot = false;
					p.aimTime = 0;
				}
			}
			
			// Ball Handler
			b.movementHandler();
			if (b.getY() < (HEIGHTX - HEIGHT) + b.getHeight()) {
				b.dy = b.yspeed;
				if (b.dx > 1) {
					b.dx -= 1;
				}
			}
			if (b.getY() > HEIGHTX - b.getHeight()) {
				b.dy = -b.yspeed;
				if (b.dx < -1) {
					b.dx -= 1;
				}
			}
			if (b.getX() >= p.getX() - b.getWidth() 
					&& b.getX() <= p.getX() + p.getWidth()
					&& b.getY() + b.getHeight() > p.getY()
					&& b.getY() < p.getY() + p.getHeight()) {
				if (b.getY() + b.getHeight() > p.getY() + (p.getHeight() / 2)) {
					b.dy = b.yspeed;
				} else {
					b.dy = -b.yspeed;
				}
				if (p.getSpeed() > 10) {
					b.dx = p.getSpeed() / 2;
				} else {
					b.dx = b.xspeed;
				}
				b.player = true;
				if (!slow) {
					Sound.play("Hit.wav");
				} else {
					Sound.play("PaddleSlow.wav");
				}
			}
			if (b.getX() >= c.getX() - b.getWidth() 
					&& b.getX() <= c.getX() + c.getWidth()
					&& b.getY() + b.getHeight() > c.getY()
					&& b.getY() < c.getY() + c.getHeight()) {
				if (b.getY() + b.getHeight() > c.getY() + (c.getHeight() / 2)) {
					b.dy = b.yspeed;
				} else {
					b.dy = -b.yspeed;
				}
				b.dx = -b.xspeed;
				b.player = false;
				if (!slow) {
					Sound.play("Hit.wav");
				} else {
					Sound.play("PaddleSlow.wav");
				}
			}
			if (b.getX() < 0) {
				c.score += c.bonus;
				if (slow) {
					slowTime = 99;
				}
				if (b.invisible) {
					b.invisiTime = 49;
				}
				resetGame();
			}
			if (b.getX() > WIDTH) {
				p.score += p.bonus;
				if (p.bonus > 1) {
					p.bonus = 1;
				}
				if (slow) {
					slowTime = 119;
				}
				if (b.invisible) {
					b.invisiTime = 49;
				}
				resetGame();
			}
			for (int i = 0; i < Powerup.active.size(); i++) {
				Powerup p = Powerup.active.get(i);
				if (b.getX() + b.getWidth() > p.getX() &&
						b.getX() - b.getWidth() < p.getX() + 30 &&
						b.getY() + b.getHeight() > p.getY() &&
						b.getY() - b.getHeight() < p.getY() + 30) {
					if (b.player) {
						this.p.power = p;
					}
					Powerup.despawn(p.getIteration());
					Sound.play("Powerup.wav");
				}
			}
			if (b.invisible) {
				b.invisiTime++;
				if (b.invisiTime == 50) {
					b.invisiTime = 0;
					b.invisible = false;
				}
			}
			
			// Computer AI
			if (!c.frozen) {
				if (!b.invisible) {
					if (c.getY() < b.getY()) {
						c.setY(c.getY() + c.getSpeed());
					}
					if (c.getY() > b.getY()) {
						c.setY(c.getY() - c.getSpeed());
					}
					if (b.xspeed > 20) {
						c.setSpeed(c.getSpeed() * 3);
					}
				} else {
					if (b.seenX < 0) {
						c.setY(c.getY() + c.getSpeed());
					} else {
						c.setY(c.getY() - c.getSpeed());
					}
				}
			}
			if (c.frozen) {
				c.freezeTime++;
				if (c.freezeTime == 15) {
					c.frozen = false;
					c.freezeTime = 0;
				}
			}
			
			// Other Handlers
			if (slow) {
				slowTime++;
				b.xspeed = 1;
				b.yspeed = 1;
				if (b.dx < 0) {
					b.dx = -b.xspeed;
				} else {
					b.dx = b.xspeed;
				}
				if (b.dy < 0) {
					b.dy = -b.yspeed;
				} else {
					b.dy = b.yspeed;
				}
				c.setSpeed(1);
				if (slowTime == 120) {
					b.xspeed = 10;
					b.yspeed = 11;
					if (b.dx < 0) {
						b.dx = -b.xspeed;
					} else {
						b.dx = b.xspeed;
					}
					if (b.dy < 0) {
						b.dy = -b.yspeed;
					} else {
						b.dy = b.yspeed;
					}
					c.setSpeed(10);
					slowTime = 0;
					slow = false;
				}
			}
			
			spawnTimer();
		}
		
	}
	
	public boolean isNew() {
		boolean isNew = true;
		String os = (String) Source.run("system os");
		String username = (String) Source.run("system user");
		String loc = "/";
		if (os.contains("Windows")) {
			loc = "C:\\Users\\" + username + "\\AppData\\Roaming\\AdvPong";
		} else if (os.contains("Mac")) {
			loc = "/Users/" + username + "/Library/AdvPong";
		} else if (os.contains("Linux")) {
			loc = "/home/" + username + "/.advpong";
		} else {
			System.out.println("Unable to save information due to operating system");
			return true;
		}
		File dir = new File(loc);
		if (!dir.exists()) {
			dir.mkdir();
			isNew = true;
		} else {
			// TODO Fix vvv
			//isNew = false;
		}
		return isNew;
	}
	
	public void spawnTimer() {
		seconds = (int)System.currentTimeMillis();
		if (lastSeconds + 1000 < seconds) {
			realSeconds++;
			lastSeconds = seconds;
		}
		if (realSeconds == 5) {
			int r = Mathf.random(0, 100);
			if (r >= 65) {
				Powerup.spawn();
			}
			realSeconds = 0;
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