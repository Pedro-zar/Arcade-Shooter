package com.DrozarStudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.DrozarStudios.entities.Ammo;
import com.DrozarStudios.entities.Amulet;
import com.DrozarStudios.entities.Bullet;
import com.DrozarStudios.entities.Enemy;
import com.DrozarStudios.entities.Entity;
import com.DrozarStudios.entities.Player;
import com.DrozarStudios.graphics.Spritesheet;
import com.DrozarStudios.graphics.UI;
import com.DrozarStudios.world.Camera;
import com.DrozarStudios.world.WallTile;
import com.DrozarStudios.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener{
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH= 240, SCALE = 3, HEIGHT = 160;
	private boolean running = true;
	public static JFrame frame;
	public static BufferedImage image;
	private Thread thread;
	public static Player player;
	public static Enemy enemy;
	public static Amulet amulet;
	public static Random rand;
	public UI ui;
	public static String gameState = "MENU";
	public static World world;
	public Ammo ammo;
	private int cur_Level = 1, maxLevel = 3;
	public static List<Bullet> bullets;
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	public static List<WallTile> walls;
	public static Spritesheet spritesheet;
	private boolean showMessageGameOver = true, restartGame = false;
	private int framesGameOver = 0;
	public Menu menu;
	public static void main(String[] args) {
		
		Game game = new Game();
		game.start();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0, ns = 1000000000/amountOfTicks, delta = 0/*, timer = System.currentTimeMillis()*/;
		requestFocus();
		//int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				delta = 0;
				//frames++;
			}
			
			//teste de quantidade de frames
			/*if(System.currentTimeMillis() - timer >= 1000) {
				System.out.println(frames);
				frames = 0;
				timer+=1000;
			}*/
		}
		stop();
	}
	
	public void tick() {
		if(gameState == "NORMAL") {
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			for(int i = 0; i < bullets.size(); i++) {
				Entity e = bullets.get(i);
				e.tick();
			}
			
			if(Game.enemies.size() == 0) {
				cur_Level++;
				if(cur_Level > maxLevel) {
					cur_Level = 1;
				}
				Game.inicialization(cur_Level);
			}
		}else if(gameState == "GAME_OVER") {
			framesGameOver++;
			if(framesGameOver == 15) {
				framesGameOver = 0;
				showMessageGameOver = !showMessageGameOver;
			}
			
			if(restartGame) {
				gameState = "NORMAL";
				restartGame = false;
				cur_Level = 1;
				Game.inicialization();
			}
		}else if(gameState == "MENU") {
			menu.tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		/*Game rendering*/
		world.render(g);
		
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		if(gameState != "GAME_OVER") {
			for(int i = 0; i < bullets.size(); i++) {
				Entity e = bullets.get(i);
				e.render(g);
			}
		}
		ui.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE,null);
		UI.writeUI(g);
		if (gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,150));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD,40*SCALE/3));
			g.drawString("GAME OVER", (WIDTH*SCALE)/2 -100, HEIGHT*SCALE/2-30);
			g.setFont(new Font("arial", Font.BOLD,30*SCALE/3));
			if(showMessageGameOver)
				g.drawString("> Pressione o espaço para reiniciar! <",  (WIDTH*SCALE)/2 -260, HEIGHT*SCALE/2+30);

		}else  if(gameState == "MENU") {
			menu.render(g);
		}
		bs.show();
	}
	
	public Game() {
		
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		frameStart();
		ui = new UI();
	
		//Inicializando
		inicialization();
		menu = new Menu();
		
		
	}
	
	public static void inicialization() {
		Game.image = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.bullets = new ArrayList<Bullet>();
		Game.walls = new ArrayList<WallTile>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.world = new World("/map_1.png");
	}
	
	public static void inicialization(int level) {
		Game.image = new BufferedImage(Game.WIDTH, Game.HEIGHT, BufferedImage.TYPE_INT_RGB);
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.bullets = new ArrayList<Bullet>();
		Game.walls = new ArrayList<WallTile>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		Game.player = new Player(0,0,16,16,Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.entities.add(Game.player);
		Game.world = new World("/map_"+level+".png");
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		running = true;
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void frameStart() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		frame = new JFrame("Demons and Pistols");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		
	}
	
	public void keyPressed(KeyEvent e) {
		switch(gameState) {
		case "Normal":
			if(e.getKeyCode() == KeyEvent.VK_D) {
				player.right = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_A) {
				player.left = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_W) {
				player.up = true;
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
				player.down = true;
			}
			if(Game.player.pickGun) {
				if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
					Game.player.shooting(3);
				}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
					Game.player.shooting(2);
				}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
					Game.player.shooting(1);
				}else if(e.getKeyCode() == KeyEvent.VK_UP) {
					Game.player.shooting(4);
				}
			}
			return;
		case "GAME_OVER":
			if(e.getKeyCode()== KeyEvent.VK_ENTER) {
				restartGame = true;
			}
			return;
		case "MENU":
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.setDown(true);
			}else if(e.getKeyCode() == KeyEvent.VK_UP) {
				menu.setUp(true);
			}
		}
		
		
		
		
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void mouseClicked(MouseEvent e) {
		double angle = Math.toDegrees(Math.atan2((int)(e.getY()/SCALE) - (Game.player.getY() +8- Camera.y), ((int)(e.getX()/SCALE) - (Game.player.getX() +8- Camera.x))));
		if(e.getButton() == MouseEvent.BUTTON1 && player.pickGun) {
			if(angle >= -135 && angle <= -45) {//cima
				Game.player.shooting(4);
			}else if(angle <= 135 && angle >= 45) {//baixo
				Game.player.shooting(1);
			}else if(angle > -45 && angle <45) {//direita
				Game.player.shooting(3);
			}else {//esquerda
				Game.player.shooting(2);				
			}
		}
	}

	
	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
}
