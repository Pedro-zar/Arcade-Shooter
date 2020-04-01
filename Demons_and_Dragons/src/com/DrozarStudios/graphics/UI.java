package com.DrozarStudios.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.DrozarStudios.entities.Entity;
import com.DrozarStudios.main.Game;

public class UI {
		
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 70, 8);
		g.fillRect(0, 8, 70, 7);
		g.setColor(Color.gray);
		g.fillRect(1, 1, 68, 6);
		g.fillRect(1, 8, 68, 6);
		g.setColor(Color.red);
		g.fillRect(1, 1, (int)((Game.player.life/Game.player.maxLife)*68), 6);
		g.setColor(Color.blue);
		g.fillRect(1, 8, (int)((Game.player.mana/Game.player.maxMana)*68), 6);
		/* Forma 1*/
		if(Game.player.pickGun) {
			g.drawImage(Entity.GUN_RIGHT, -3, 10, Game.stdBits, Game.stdBits, null);
			for(int i = 0; i<Game.player.maxAmmo; i++) {
				g.drawImage(Entity.ammoSpaceUI, 14 + (i * 7), Game.stdBits, Game.stdBits, Game.stdBits,null);	
			}
			for(int i = (int) Game.player.ammo - 1; i>=0; i--) {
				g.drawImage(Entity.ammoUI, 14 + (i * 7), Game.stdBits, Game.stdBits, Game.stdBits,null);	
			}
		}
		/*******/
		/*forma 3
		if(Game.player.pickGun) 
			g.drawImage(Entity.GUN_RIGHT, -3, 10, Game.stdBits, Game.stdBits, null);
		*/
	}
	
	public static void writeUI(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,15*Game.SCALE/3));
		g.drawString((int)Game.player.life+"/"+(int)Game.player.maxLife, 80*Game.SCALE/3, 17*Game.SCALE/3);
		g.drawString((int)Game.player.mana+"/"+(int)Game.player.maxMana, 80*Game.SCALE/3, 38*Game.SCALE/3);
		/*forma 3
		if(Game.player.pickGun) 
			g.drawString( " x " + (int)Game.player.ammo, 30*Game.SCALE/3, 60*Game.SCALE/3);
		*/
		/*forma 2 
		g.drawString( "Munições: " + (int)Game.player.ammo, 10*Game.SCALE/3, 60*Game.SCALE/3);
		*/
	}
}