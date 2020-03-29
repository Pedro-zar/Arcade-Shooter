package com.DrozarStudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.DrozarStudios.main.Game;
import com.DrozarStudios.world.Camera;

public class Ammo extends Entity{

	
	private int frame = 0, maxFrames = 40, index = 0, maxIndex = 1;
	
	
	public Ammo(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		this.frame++;
	
		if(this.frame == this.maxFrames) {
			this.frame = 0;
			this.index++;
			if(this.index > this.maxIndex) {
				this.index = 0;
			}
			Entity.BULLET_EN = Game.spritesheet.getSprite((6+this.index)*16, 16, 16, 16);
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Entity.BULLET_EN, this.getX() - Camera.x, this.getY() - Camera.y, null);
		super.render(g);
	}
}
