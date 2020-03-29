package com.DrozarStudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.DrozarStudios.main.Game;
import com.DrozarStudios.world.Camera;

public class Amulet extends Entity{
	
	public int index = 0, maxIndex = 1;
	public int frames = 0, maxFrames = 30;
	
	public Amulet(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
	}
	
	public void tick() {
		super.tick();
		frames++;
		if(frames==maxFrames) {
			frames=0;
			index++;
			if(index>maxIndex) 
				index=0;
		}
		Entity.AMMULET_EN = Game.spritesheet.getSprite((8+index)*16, 48, 16, 16);
	}
	
	public void render(Graphics g) {
		g.drawImage(Entity.AMMULET_EN, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
