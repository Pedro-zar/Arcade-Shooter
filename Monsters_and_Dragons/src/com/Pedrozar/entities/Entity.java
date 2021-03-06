package com.Pedrozar.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.Pedrozar.main.Game;
import com.Pedrozar.world.Camera;
import com.Pedrozar.world.Tile;

public class Entity{

	protected double x, y; 
	private int maskX, maskY,maskW, maskH;
	protected int width, height;
	public BufferedImage sprite;
	
	//sprites
	public static BufferedImage GUN_LEFT = Game.spritesheet.getSprite(9*Game.stdBits, 32, Game.stdBits, Game.stdBits);
	public static BufferedImage GUN_RIGHT = Game.spritesheet.getSprite(8*Game.stdBits, 32, Game.stdBits, Game.stdBits);
	public static BufferedImage GUN_UP = Game.spritesheet.getSprite(6*Game.stdBits, 48, Game.stdBits, Game.stdBits);
	public static BufferedImage GUN_DOWN = Game.spritesheet.getSprite(7*Game.stdBits, 48, Game.stdBits, Game.stdBits);
	public static BufferedImage AMMULET_EN = Game.spritesheet.getSprite(8*Game.stdBits, 48, Game.stdBits, Game.stdBits);
	public static BufferedImage ammoUI = Game.spritesheet.getSprite(8*Game.stdBits, Game.stdBits, Game.stdBits, Game.stdBits);
	public static BufferedImage ammoSpaceUI = Game.spritesheet.getSprite(9*Game.stdBits, Game.stdBits, Game.stdBits, Game.stdBits);
	public static BufferedImage HP_POTION_EN = Game.spritesheet.getSprite(6*Game.stdBits, 0, Game.stdBits, Game.stdBits);
	public static BufferedImage MP_POTION_EN = Game.spritesheet.getSprite(8*Game.stdBits, 0, Game.stdBits, Game.stdBits);
	public static BufferedImage GUN_EN = Game.spritesheet.getSprite(6*Game.stdBits, 32, Game.stdBits, Game.stdBits);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(6*Game.stdBits, Game.stdBits, Game.stdBits, Game.stdBits);
	public static BufferedImage LITTLE_DEMON_EN = Game.spritesheet.getSprite(6*Game.stdBits, Game.stdBits*4, Game.stdBits, Game.stdBits);
	
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		maskX = 0;
		maskY = 0;
		setMaskW(this.width);
		setMaskH(this.height);
	}

	public void setMask(int maskX, int maskY, int maskW, int maskH) {
		this.maskX = maskX;
		this.maskY = maskY;
		this.setMaskH(maskH);
		this.setMaskW(maskW);
	}
	
	public int getX(){
		return (int)this.x;
	}
	
	public void setX(int newX ){
		this.x = newX;
	}
	
	public void setY(int newY ){
		this.y = newY;
	}
	
	public void setWidth(int newWidth ){
		this.width = newWidth;
	}
	
	public void setHeight(int newHeight){
		this.height = newHeight;
	}
	
	public int getY(){
		return (int)this.y;
	}
	
	public int getWidth(){
		return this.width;
	}

	public int getHeight(){
		return this.height;
	}
	
	public int getMaskW() {
		return maskW;
	}

	public void setMaskW(int maskW) {
		this.maskW = maskW;
	}

	public int getMaskH() {
		return maskH;
	}

	public void setMaskH(int maskH) {
		this.maskH = maskH;
	}

	public static boolean isColliddingEntities(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.maskX +e1.getX(), e1.maskY + e1.getY(), e1.getMaskW(), e1.getMaskH());
		Rectangle e2Mask = new Rectangle(e2.maskX + e2.getX(), e2.maskY + e2.getY(), e2.getMaskW(), e2.getMaskH());
		return e1Mask.intersects(e2Mask);
	}
	
	public static boolean isColliddingEntitieTile(Entity e1, Tile t1) {
		Rectangle e1Mask = new Rectangle(e1.maskX +e1.getX(), e1.maskY-1 + e1.getY(), e1.getMaskW(), e1.getMaskH()+1);
		Rectangle e2Mask = new Rectangle(t1.getMaskX()+ t1.getX(), t1.getMaskY() + t1.getY(), t1.getMaskW(), t1.getMaskH());
		return e1Mask.intersects(e2Mask);
	}
	
	public void tick() {
		
	}
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY()-Camera.y, null);
		/*	
		  	g.setColor(Color.black);
			g.fillRect(this.getX() + maskX - Camera.x,this.getY() + maskY- Camera.y, maskW, maskH);
		*/
		}
}
