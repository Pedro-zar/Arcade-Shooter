package com.Pedrozar.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.Pedrozar.main.Game;
import com.Pedrozar.world.Camera;

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
		Entity.AMMULET_EN = Game.spritesheet.getSprite((8+index)*Game.stdBits, 48, Game.stdBits, Game.stdBits);
	}
	
	public void render(Graphics g) {
		g.drawImage(Entity.AMMULET_EN, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
}
