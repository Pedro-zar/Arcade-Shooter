package com.DrozarStudios.entities;


import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.DrozarStudios.main.Game;
import com.DrozarStudios.world.Camera;
import com.DrozarStudios.world.World;

public class Enemy extends Entity {

	private int frames = 0, maxFrames = 15, index = 0, maxIndex = 3, minIndex=0;
	private int maskX = 10, maskY = 10, maskW = 8, maskH = 8;
	private BufferedImage[] sprites;
	public int  maxLife=2, life = maxLife, damage = 20;
	public double speed = 0.6;
	private boolean vertical = false;
	private int vertF = 0, maxVertF = 30;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[8];
		sprites[0] = Game.spritesheet.getSprite(6*16, 16*4, 16,16);
		sprites[1] = Game.spritesheet.getSprite(7*16, 16*4, 16,16);
		sprites[2] = Game.spritesheet.getSprite(8*16, 16*4, 16,16);
		sprites[3] = Game.spritesheet.getSprite(9*16, 16*4, 16,16);
		sprites[4] = Game.spritesheet.getSprite(6*16, 16*5, 16,16);
		sprites[5] = Game.spritesheet.getSprite(7*16, 16*5, 16,16);
		sprites[6] = Game.spritesheet.getSprite(8*16, 16*5, 16,16);
		sprites[7] = Game.spritesheet.getSprite(9*16, 16*5, 16,16);
	}
	
	public void tick() {
		if (this.isColliddingPlayer() == false) {
			if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY()) && !isCollidding((int)(x-speed), this.getY()) && vertical == true && (Game.rand.nextInt(100)<90)) {
				x-=speed;
			}else if((int)x < Game.player.getX() && World.isFree((int)(x+speed), this.getY())&& !isCollidding((int)(x+speed), this.getY()) && vertical == true && (Game.rand.nextInt(100)<90)) {
				x+=speed;
			}else if ((int)y < Game.player.getY()&& World.isFree(this.getX(), (int)(y+speed))&& !isCollidding(this.getX(), (int)(y+speed)) && vertical == false && (Game.rand.nextInt(100)<90)) {
				y+=speed;
			}else if ((int)y > Game.player.getY()&& World.isFree(this.getX(), (int)(y-speed))&& !isCollidding(this.getX(), (int)(y-speed)) && vertical == false && (Game.rand.nextInt(100)<90)) {
				y-=speed;
			}
			vertF++;
			if (vertF>= maxVertF) {
				vertical = !vertical;
				vertF = 0;
			}
			frames++;
			if(frames == maxFrames) {
				frames=0;
				index++;
				if (index>maxIndex) {
					index=minIndex;
				}
			}
		}else {
			//atacar player
			attack();
		}
		
		isColliddingBullet();
		
		
	}
	
	private void attack() {
		if (Game.rand.nextInt(100) <= 20) 
			Game.player.playerDamage(Game.rand.nextInt(damage)+1);
	}

	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY()-Camera.y, null);
	}

	public boolean isColliddingPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() +maskX, this.getY()+maskY, maskW, maskH);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(),16,16);
		return enemyCurrent.intersects(player);
	}
	public boolean isCollidding(int xNext, int yNext) {
		Rectangle enemyCurrent = new Rectangle(xNext +maskX, yNext+maskY, maskW, maskH);
		for(int i = 0; i < Game.enemies.size();i++) {
			Enemy e = Game.enemies.get(i);
			if (e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX()+maskX, e.getY()+maskY, maskW,maskH);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}	
		return false;
	}
	
	public void isColliddingBullet() {
		for(int i= 0; i < Game.bullets.size(); i++) {
			Bullet e = Game.bullets.get(i);
			if(Entity.isColliddingEntities(this, e)) {
				int damage = e.life;
				e.life-=life;
				if(e.life <= 0) {
					Game.bullets.remove(i);
				}
				life-=damage;
				if(this.life<=0) {
					Game.entities.remove(this);
					Game.enemies.remove(this);
				}
				if(life!=maxLife && life >0) {
					index = 4;
					maxIndex = 7;
					minIndex=4;
					speed*=3;
					this.damage*=2.5;
				}
				
				return;
			}
		}
	}
}
