package logic;

public abstract class Tank {
	
	protected float health;
	protected int damage;
	protected int move;
	protected int size;
	
	
	/*Getterek és setterek*/
	
	public float getHealth() {
		return health;
	}
	public void setHealth(float health) {
		this.health = health;
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}
}
