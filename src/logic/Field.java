package logic;

public class Field {
	
	Tank tank;
	boolean isDestroyed;
	private int posX;
	private int posY;
	
	public Field(int x, int y){
		tank = null;
		isDestroyed = false;
		posX = x;
		posY = y;
	}
	
	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public Tank getTank(){
		return tank;
	}
		
	public void setTank(Tank tank) {
		this.tank = tank;
	}
	
	public boolean getIsDestroyed(){
		return isDestroyed;
	}
	
	public void setIsDestroyed(boolean destroy){
		isDestroyed = destroy;
	}
	@Override
	public String toString() {
		if(tank == null && !isDestroyed){
			return " 0 ";
		}else if(isDestroyed){
			return " -1 ";
		}else{
			return " 1 ";
		}
	}
	
}