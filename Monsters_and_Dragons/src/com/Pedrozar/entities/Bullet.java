package com.Pedrozar.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.Pedrozar.main.Game;
import com.Pedrozar.world.Camera;

public class Bullet extends Entity{

	
	private int dx, dy,  temp = 0, tempF = 35;
	public int life = 3;
	private double speed = 3;
	public Bullet(int x, int y, int width, int height, BufferedImage sprite, int dx, int dy) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		this.setMask(1, 1, 2, 2);
	}
	
	public void tick() {
		temp++;
		if(temp >= tempF) {
			Game.bullets.remove(this);
			return;
		}

		
		if ((isFree() && dx == 1)||(isFree() && dx ==-1)) {
			x+=dx * speed;	
		}else if ((isFree() && dy == -1)||(isFree()&& dy == 1 )) {
			y+=dy * speed;
		}else{
			Game.bullets.remove(this);
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(this.getX()-Camera.x,this.getY()-Camera.y, width, height);
		//super.render(g);
	}
	
	private boolean isFree() {
		
		for(int i = 0; i < Game.walls.size(); i++) {
			if(Game.walls.get(i).getX() >= Camera.x && 
			Game.walls.get(i).getX() <= Game.WIDTH+ Camera.x && 
			Game.walls.get(i).getY() >= Camera.y && 
			Game.walls.get(i).getY() <= Game.HEIGHT + Camera.y){
				if(Entity.isColliddingEntitieTile(this,Game.walls.get(i))) {
					return false;
				}/*
				if(dir == 1&&(Entity.isColliddingEntitieTile(this,Game.walls.get(i)))) {//RIGHT
					return false;
				}else if(dir == 3 && (Entity.isColliddingEntitieTile(this,Game.walls.get(i)))){//LEFT
					return false;
				}else if(dir == 2 && (Entity.isColliddingEntitieTile(this,Game.walls.get(i)))){//UP
					return false;
				}else if(dir == 4 && (Entity.isColliddingEntitieTile(this,Game.walls.get(i)))){//DOWN
					return false;
				}*/
			}
		}
		return true;
	}
	
}
