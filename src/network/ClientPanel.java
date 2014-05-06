package network;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;



public class ClientPanel extends JPanel{

	public BufferedImage grass;
	public BufferedImage tank_l;
	public BufferedImage tank_r;
	public BufferedImage destroyed;


	public ClientPanel(){
		/**
		 * Képek betöltése
		 */
		try {
            grass = ImageIO.read(new File("resources/grass.png"));
		} catch (IOException exc) {
			//TODO: Handle exception.
        }

		try {
            tank_l = ImageIO.read(new File("resources/tank_l.png"));
        } catch (IOException exc) {
            //TODO: Handle exception.
        }
		
		try {
            tank_r = ImageIO.read(new File("resources/tank_r.png"));
        } catch (IOException exc) {
            //TODO: Handle exception.
        }

		try {
            destroyed = ImageIO.read(new File("resources/destroyed.png"));
        } catch (IOException exc) {
            //TODO: Handle exception.
        }	
	}

	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.BLACK);

		/**
		 * A saját táblánk kirajzolása
		 */
		for(int i = 50; i < 350; i+= 30){
			for(int j = 10; j < 310; j+=30 ){
				g2d.drawImage(grass, i, j, this);
				if( Client.table[((i-50)/30)][(j-10)/30] == 0){
					g2d.drawImage(tank_l, i, j, this);
				}
				if( Client.table[(i-50)/30][(j-10)/30] == 2){
					g2d.drawImage(destroyed, i, j, this);
				}
				g2d.drawRect(i, j, 30, 30);
			}
		}

		/**
		 * Az ellenfél táblája
		 */
		for(int i = 450; i < 750; i+= 30){
			for(int j = 10; j < 310; j+=30 ){
				g2d.drawImage(grass, i, j, this);
				if( Client.shootTable[(i-450)/30][(j-10)/30] == 0){
					g2d.drawImage(tank_r, i, j, this);
				}
				if( Client.shootTable[(i-450)/30][(j-10)/30] == 2){
					g2d.drawImage(destroyed, i, j, this);
				}
				g2d.drawRect(i, j, 30, 30);
			}
		}
	}

	public void repaint(Graphics g){
        paint(g);
    }

	/**
	 * 
	 * @param x
	 * @param y
	 * Két szám alapján meghatározzuk, hogy a felhasználó hova kattintott a felületen
	 * @return string
	 */
	public String getIndex(int x, int y) {
		String tmp = " ";
		if(Client.State == 0){
			if(x >= 50 && x < 350 && y >= 10 && y < 310){
				//saját tábla
				tmp = tmp + Integer.toString((x-50)/30) + "," + Integer.toString((y-10)/30);
			}	
		} 
		else if(Client.State == 1){
			if(x >= 450 && x < 750 && y >= 10 && y < 310){
				//saját tábla
				tmp = tmp + Integer.toString((x-450)/30) + "," + Integer.toString((y-10)/30);
			}		
		}
		return tmp;
	}


}