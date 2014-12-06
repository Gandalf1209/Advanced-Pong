package com.gandalf1209.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.gandalf1209.yge2.audio.Sound;
import com.gandalf1209.yge2.engine.Mathf;

public class Powerup {

	public static Powerup bonus;
	public static Powerup freeze;
	public static Powerup aimbot;
	public static Powerup slowmo;
	public static Powerup bonus2;
	public static Powerup invisi;
	
	private BufferedImage texture;
	private String name;
	private int x;
	private int y;
	private int it = 0;
	
	public static List<Powerup> list = new ArrayList<Powerup>();
	public static List<Powerup> active = new ArrayList<Powerup>();
	
	public Powerup(BufferedImage texture, String name) {
		this.texture = texture;
		this.name = name;
		list.add(this);
	}
	
	public Powerup(Powerup p) {
		this.texture = p.getTexture();
		this.name = p.getName();
	}
	
	public static void use(Player player, Powerup p) {
		if (p.getName().equalsIgnoreCase("bonus")) {
			player.bonus++;
		}
		if (p.getName().equalsIgnoreCase("freeze")) {
			MainGame.game.c.frozen = true;
		}
		if (p.getName().equalsIgnoreCase("aimbot")) {
			player.aimbot = true;
		}
		if (p.getName().equalsIgnoreCase("slowmo")) {
			MainGame.game.slow = true;
			Sound.play("Slow.wav");
		}
		if (p.getName().equalsIgnoreCase("bonus2")) {
			player.bonus += 2;
		}
		if (p.getName().equalsIgnoreCase("invisiball")) {
			MainGame.game.b.invisible = true;
		}
		player.power = null;
	}
	
	public static void despawn(int v) {
		for (int i = 0; i < active.size(); i++) {
			if (active.get(i).getIteration() == v) {
				active.remove(i);
			}
		}
	}
	
	public static void spawn() {
		int r = Mathf.random(0, list.size());
		Powerup p = new Powerup(list.get(r));
		p.setX(Mathf.random(100, MainGame.WIDTH - 100));
		p.setY(Mathf.random(100, MainGame.HEIGHT - 100));
		p.setIteration(active.size());
		active.add(p);
	}
	
	public static void init() {
		bonus = new Powerup(Textures.bonus, "Bonus");
		freeze = new Powerup(Textures.freeze, "Freeze");
		aimbot = new Powerup(Textures.aimbot, "AimBot");
		slowmo = new Powerup(Textures.slowmo, "SlowMo");
		bonus2 = new Powerup(Textures.bonus2, "Bonus2");
		invisi = new Powerup(Textures.invisi, "Invisiball");
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getIteration() {
		return it;
	}

	public void setIteration(int it) {
		this.it = it;
	}
	
}