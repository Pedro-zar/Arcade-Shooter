package com.DrozarStudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Menu {
	
	private String[] options = { "CONTINUAR JOGO","NOVO JOGO", "SAIR"};
	private int currentOption = 0, maxOption = options.length-1;
	private boolean down = false, up = false, enter = false, gameStarted = false;

	private boolean getDown() {
		return this.down;
	}	
	
	private boolean getUp() {
		return this.up;
	}	

	public void setDown(boolean down) {
		this.down = down;
	}		

	public void setUp(boolean up) {
		this.up = up;
	}	
	
	public void tick() {
		if(getUp()) {
			currentOption--;
			setUp(false);
			if(currentOption < 0) {
				currentOption = maxOption;
			}
		}else if(getDown()){
			currentOption++;
			setDown(false);
			if(currentOption > maxOption) {
				currentOption = 0;
			}
		}else if(getEnter()) {	
			if(options[currentOption] == "NOVO JOGO") {
				Game.gameState = "NORMAL";
				currentOption = 0;
				if(gameStarted = true) {
					Game.inicialization(1);
				}
				gameStarted = true;
			}else if(options[currentOption] == "CONTINUAR JOGO" && gameStarted == true) {
				Game.gameState = "NORMAL";
			}else if(options[currentOption] == "SAIR") {
				System.exit(1);
			}
			setEnter(false);
		}
	}
	
	private boolean getEnter() {
		return this.enter;
	}
	public void setEnter(boolean enter) {
		this.enter = enter;
	}

	
	
	public void render(Graphics g) {
		//title
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0,0,0,100));
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD,36));
		g.drawString("> Demons and Pistols <", Game.WIDTH*Game.SCALE/4, Game.HEIGHT*Game.SCALE/5);
		//option
		g.setFont(new Font("arial", Font.BOLD,24));
		for(int i = 0; i <= maxOption; i++) {
			g.drawString(">", Game.WIDTH*Game.SCALE/5/3, Game.HEIGHT*Game.SCALE/5*(currentOption+2));
			g.drawString(options[i], Game.WIDTH*Game.SCALE/10, Game.HEIGHT*Game.SCALE/5*(i+2));
		}
	}
}
