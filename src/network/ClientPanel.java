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
	public BufferedImage tank;
	public BufferedImage destroyed;


	public ClientPanel(){

		try {
            grass = ImageIO.read(new File("resources/grass.png"));
		} catch (IOException exc) {
			//TODO: Handle exception.
        }

		try {
            tank = ImageIO.read(new File("resources/tank.png"));
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

		for(int i = 50; i < 350; i+= 30){
			for(int j = 10; j < 310; j+=30 ){
				System.out.println(Client.table[0][0]);
				if( Client.table[((i-50)/30)][(j-10)/30] == 0){
					g2d.drawImage(tank, i, j, this);
				}
				if( Client.table[(i-50)/30][(j-10)/30] == 1){
					g2d.drawImage(grass, i, j, this);
				}
				if( Client.table[(i-50)/30][(j-10)/30] == 2){
					g2d.drawImage(destroyed, i, j, this);
				}
				g2d.drawRect(i, j, 30, 30);
			}
		}

		for(int i = 450; i < 750; i+= 30){
			for(int j = 10; j < 310; j+=30 ){
				if( Client.table[(i-450)/30][(j-10)/30] == 0){
					g2d.drawImage(tank, i, j, this);
				}
				if( Client.table[(i-450)/30][(j-10)/30] == 1){
					g2d.drawImage(grass, i, j, this);
				}
				if( Client.table[(i-450)/30][(j-10)/30] == 2){
					g2d.drawImage(destroyed, i, j, this);
				}
				g2d.drawRect(i, j, 30, 30);
			}
		}
	}

	public void repaint(Graphics g){
        paint(g);
    }

	public String getIndex(int x, int y) {
		String tmp = "";

		if(y < 300){
			tmp =  Integer.toString(y/30) + ",";
		} else tmp = Integer.toString(y) + ",";

		if(x < 300){
			tmp = tmp + Integer.toString(x/30);
		} else tmp = tmp + Integer.toString(x);


		System.out.println(tmp);
		return tmp;
	}

	//ez nem kell...
	public boolean isValidString(int x, int y){
		return 5 < 6;
	}

}