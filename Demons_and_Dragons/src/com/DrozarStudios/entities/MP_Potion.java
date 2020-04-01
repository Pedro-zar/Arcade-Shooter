package com.DrozarStudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.DrozarStudios.main.Game;
import com.DrozarStudios.world.Camera;

public class MP_Potion extends Entity{
	
	private int frame = 0, maxFrames = 65, index = 0, maxIndex = 1;
	
	public MP_Potion(int x, int y, int width, int height, BufferedImage sprite) {
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
			Entity.MP_POTION_EN = Game.spritesheet.getSprite((8+index)*Game.stdBits, 0, Game.stdBits, Game.stdBits);
		}
	}
	
	public void render(Graphics g) {;
		g.drawImage(Entity.MP_POTION_EN, this.getX() - Camera.x, this.getY() - Camera.y, null);
		super.render(g);
	}

}
