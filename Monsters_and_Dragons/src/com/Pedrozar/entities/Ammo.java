package com.Pedrozar.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.Pedrozar.main.Game;
import com.Pedrozar.world.Camera;

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
			Entity.BULLET_EN = Game.spritesheet.getSprite((6+this.index)*Game.stdBits, Game.stdBits, Game.stdBits, Game.stdBits);
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(Entity.BULLET_EN, this.getX() - Camera.x, this.getY() - Camera.y, null);
		super.render(g);
	}
}
