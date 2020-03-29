package com.DrozarStudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.DrozarStudios.entities.*;
import com.DrozarStudios.main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth()*map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth()*map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for (int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy <map.getHeight(); yy++) {
					int pixelAtual= pixels[xx + (yy * map.getWidth())];
					//Tiles
					//chão/preto
					tiles[xx+(yy*WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR); 
					if(pixelAtual == 0xFFFFFFFF) {
						//branco/parede
						tiles[xx+(yy*WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_WALL);
						Game.walls.add((WallTile) tiles[xx+(yy*WIDTH)]);
					}
					/*************/
					//entidades
					else if(pixelAtual == 0xFFFFFF00) {
						//amarelo/munição
						Ammo ammo = new Ammo(xx*16,yy*16,16,16,Entity.BULLET_EN);
						ammo.setMask(4, 6, 6, 10);
						Game.entities.add(ammo);
					}else if (pixelAtual == 0xFF00FF00) {
						//verde/vida
						HP_Potion hpPotion = new HP_Potion(xx*16,yy*16,16,16,Entity.HP_POTION_EN);
						hpPotion.setMask(3, 5, 8, 10);
						Game.entities.add(hpPotion);
					}else if (pixelAtual == 0xFF00FFFF) {
						//ciano/mana
						MP_Potion mpPotion = new MP_Potion(xx*16,yy*16,16,16,Entity.MP_POTION_EN);
						mpPotion.setMask(3, 5, 8, 10);
						Game.entities.add(mpPotion);
					}else if (pixelAtual == 0xFFF0F0F0) {
						//cinza/arma
						Gun gun = new Gun(xx*16,yy*16,16,16,Entity.GUN_EN);
						gun.setMask(4,6,9,9);
						Game.entities.add(gun);
					}else if (pixelAtual == 0xFFFF0000) {
						//vermelho/inimigo
						Enemy en = new Enemy(xx*16,yy*16,16,16,Entity.LITTLE_DEMON_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					}else if (pixelAtual == 0xFFFF00FF) {
						//amuleto de fogo
						Amulet amulet = new Amulet(xx*16, yy*16, 16, 16, Entity.AMMULET_EN);
						Game.entities.add(amulet);
					}else if (pixelAtual == 0xFF80FFFF) {
						//ciano/inimigoBuffado
						Enemy en = new Enemy(xx*16,yy*16,16,16,Entity.LITTLE_DEMON_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
						en.maxLife*=2;
						en.life = en.maxLife - 1;
					}
					/***********/
					// player
					else if (pixelAtual == 0xFF0000FF) {
						//azul/player
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
					}
					/****/
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xNext, int yNext) {
		
		int x1 = xNext / TILE_SIZE;
		int y1 = yNext / TILE_SIZE;
		int x2 = (xNext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		int x3 = xNext/ TILE_SIZE;
		int y3 = (yNext+TILE_SIZE-1) / TILE_SIZE;
		int x4 = (xNext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (yNext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile) );
	}
	
	
	
	public void render(Graphics g) {
		int xStart = Camera.x>>4;
		int yStart = Camera.y>>4;
		int xFinal = xStart + (Game.WIDTH>>4);
		int yFinal = yStart + (Game.HEIGHT>>4);
		for(int xx = xStart; xx <= xFinal; xx++) {
			for(int yy = yStart; yy<= yFinal; yy++) {
				if(xx < 0 || yy < 0 || xx>= WIDTH || yy>=HEIGHT) 
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);	
			}
		}
	}
}
