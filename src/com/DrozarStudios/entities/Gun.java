package com.DrozarStudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.DrozarStudios.main.Game;
import com.DrozarStudios.world.Camera;

public class Gun extends Entity {

	public static int gunFrames = 30, maxGunFrames = 30;

	
	private int frame = 0, maxFrames = 65, index = 0, maxIndex = 1;
	public Gun(int x, int y, int width, int height, BufferedImage sprite) {
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
			Entity.GUN_EN = Game.spritesheet.getSprite((6+index)*16, 32, 16, 16);
		}
		
		
	}
	
	public void render(Graphics g) {
		g.drawImage(Entity.GUN_EN, this.getX() - Camera.x, this.getY() - Camera.y, null);
		super.render(g);
	}

}
