package com.DrozarStudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.DrozarStudios.main.Game;
import com.DrozarStudios.main.Sound;
import com.DrozarStudios.world.Camera;
import com.DrozarStudios.world.World;

public class Player extends Entity{
	
	/*Declarations*/
	//movement/control
	public boolean right, up, left, down;
	public int up_dir = 4, down_dir = 1, left_dir = 2, right_dir = 3,dir = down_dir,speed = 1, dire = 0;
	private BufferedImage[] downPlayer,upPlayer,rightPlayer, leftPlayer;
	private int frames = 0, maxFrames = 8, index = 0, maxIndex = 3, dirFrames = 0;
	private boolean moved = false;
	private BufferedImage damaged = Game.spritesheet.getSprite(144, 144, Game.stdBits, Game.stdBits);
	//damage
	private boolean isDamaged = false, continueIsDamage = false;
	private int  damagedF = 0, maxDamagedF = 6;
	private double damageSec = 0,maxDamageSec = 3.5;
	//status
	public double maxLife = 100, life = maxLife, maxMana = 200, mana = maxMana, maxAmmo = 6, ammo = maxAmmo;
	public boolean pickGun = false, pickAmulet = false;
	/****************************************************************************************************/
	
	/*Methods*/
	//Constructor
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		downPlayer = new BufferedImage[maxIndex+1];
		upPlayer = new BufferedImage[maxIndex+1];
		leftPlayer = new BufferedImage[maxIndex+1];
		rightPlayer = new BufferedImage[maxIndex+1];
		for(int i = 0; i < maxIndex+1; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32+(i*Game.stdBits), 0, width, height);
			upPlayer[i] = Game.spritesheet.getSprite(32+(i*Game.stdBits), Game.stdBits, width, height);
			rightPlayer[i] = Game.spritesheet.getSprite(32+(i*Game.stdBits), 32, width, height);
			leftPlayer[i] = Game.spritesheet.getSprite(32+(i*Game.stdBits), 48, width, height);		
		}
	}
	
	//1 per frame
	public void tick() {
		moved = false;
		if (right && World.isFree((int)(x+speed),(int)(y))) {
			moved = true;
			setX((int) (x+=speed));
			dir = right_dir;
		}
		if(left && World.isFree((int)(x-speed),(int)(y))) {
			moved = true;
			setX((int) (x-=speed));
			dir = left_dir;
		}
		if (up && World.isFree((int)(x),(int)((int)(y-speed)))) {
			moved = true;
			setY((int) (y-=speed));
			dir = up_dir;
		}
		if(down && World.isFree((int)(x),(int)(y+speed))) {
			moved = true;
			dir = down_dir;
			setY((int) (y+=speed));
		}
		
		if(Gun.gunFrames!=Gun.maxGunFrames) {
			Gun.gunFrames++;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames=0;
				index++;
				if (index>maxIndex) {
					index=0;
				}		
			}
		}
		checkItemCollision();
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),	0, World.WIDTH*Game.stdBits - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),	0, World.HEIGHT*Game.stdBits- Game.HEIGHT);
	}
	
	
	//1 per tick
	public void render(Graphics g) {
		if(!isDamaged) {
			if(dire != 0) {
				dir = dire;
				dirFrames++;
				if(dirFrames == 29) {
					dirFrames = 0;
					dire = 0;
				}
			}
			if(dir == up_dir) {
				if(pickGun)
					g.drawImage(Entity.GUN_UP, this.getX()-Camera.x -4, this.getY()-Camera.y, null);
				g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}else if(dir == down_dir) {
				g.drawImage(downPlayer[index], this.getX()- Camera.x, this.getY() - Camera.y, null);	
				if(pickGun)
					g.drawImage(Entity.GUN_DOWN, this.getX()-Camera.x+4, this.getY()-Camera.y+5, null);
			}else if(dir == right_dir) {
				if(pickGun)
					g.drawImage(Entity.GUN_RIGHT, this.getX()-Camera.x + 5, this.getY()-Camera.y, null);
				g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);	
			}else if(dir == left_dir) {
				g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);	
				if(pickGun)
					g.drawImage(Entity.GUN_LEFT, this.getX()-Camera.x-5, this.getY()-Camera.y, null);
			}
			if(continueIsDamage) {
				damagedF++;
				if(damagedF == maxDamagedF) {
					damagedF = 0;
					isDamaged = true;
				}
			}
		}else {
			g.drawImage(this.damaged,  this.getX() - Camera.x, this.getY() - Camera.y, null);
			damagedF++;
			if(damagedF>=maxDamagedF) {
				damageSec+=0.5;
				damagedF = 0;
				isDamaged = false;
				if(damageSec>=maxDamageSec) {
					continueIsDamage = false;
					damageSec=0;
				}
			}
		}
	}
	
	//Damage to player
	public void playerDamage(int damage) {	
		if(!continueIsDamage) {
			continueIsDamage = true;
			Game.player.life-= damage;
			Sound.hurtEffect.play();
			//System.out.println(Game.player.life);	
			isDamaged = true;
			if(Game.player.life<=0) {
				Game.gameState = "GAME_OVER";
				Game.player.life=0;
			}
		}
	}
	
	//Player collision with items
	public void checkItemCollision(){
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof HP_Potion) {
				if(Entity.isColliddingEntities(this, e) && life < maxLife) {
					life+= 5*(Game.rand.nextInt(3)+1);
					if(life > maxLife) 
						life = maxLife;
					Game.entities.remove(i);
					return;
				}
			}else if(e instanceof MP_Potion) {
				if(Entity.isColliddingEntities(this, e) && mana < 100) {
					mana+= 5*(Game.rand.nextInt(5)+1);
					if(mana > maxMana) 
						mana = maxMana;
					Game.entities.remove(i);
					return;
				}
			}else if(e instanceof Gun) {
				if(Entity.isColliddingEntities(this, e)) {
					pickGun = true;
					Game.entities.remove(i);
					return;
				}
			}else if(e instanceof Ammo && pickGun) {
				if(Entity.isColliddingEntities(this, e) && ammo < maxAmmo) {
					ammo+=2;
					Game.entities.remove(i);
					return;
				}
			}else if(e instanceof Amulet) {
				if(Entity.isColliddingEntities(this, e)) {
					pickAmulet = true;
					Game.entities.remove(e);
					return;
				}
			}
		}
 	}

	//Player shooting
	public void shooting(int dir) {
		if (ammo>0 && Gun.gunFrames == Gun.maxGunFrames) {
			this.dire = dir;
			int dx = 0, dy = 0, py = 0, px=0;
			if(dire == right_dir) {//right
				px = 14;
				py = 6;
				dx = 1;
			}else if(dire == left_dir) {//left
				px = -2;
				py = 6;
				dx = -1;
			}else if(dire == up_dir) {//up
				px = 1;
				py = 4;
				dy = -1;
			}else if(dire == down_dir) {//down
				dy = 1;
				px = 11;
				py = 12;
			}
			ammo-=0.5;
			Sound.shootEffect.play();
			Bullet bullet = new Bullet(this.getX()+px,this.getY()+py, 3, 3, null, dx, dy);
			Game.bullets.add(bullet);
			Gun.gunFrames = 0;
		}
	}
}
