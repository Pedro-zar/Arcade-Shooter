package com.Pedrozar.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.Pedrozar.main.Game;

public class Tile {

	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, Game.stdBits, Game.stdBits);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, Game.stdBits, Game.stdBits);
	
	private BufferedImage sprite;
	private int x,y;
	private final int maskW = Game.stdBits, maskH = Game.stdBits, maskX = 0, maskY = 0;
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getMaskX() {
		return this.maskX;
	}
	
	public int getMaskY() {
		return this.maskY;
	}
	
	public int getMaskH() {
		return this.maskH;
	}
	
	public int getMaskW() {
		return this.maskW;
	}
	
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
}
