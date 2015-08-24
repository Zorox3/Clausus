package main.gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Light {

	private int x, y, width, height, luminosity;
	private Graphics2D image;
	private BufferedImage Bimage;
	public Light(int x, int y, int width, int height, int luminosity ){
		
		this.setX(x);
		this.setY(y);
		this.width = width;
		this.height = height;
		this.luminosity = luminosity;
		
		Bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		image = (Graphics2D)Bimage.getGraphics();
		
		int step = 8;
		int numSteps = height / step;
		
		image.setColor(new Color(0,0,0,luminosity));
		for(int i = 0; i < numSteps; i++){
			image.fillOval(height/2 - i * step, width/2 - i * step, i * step * 2, i * step * 2);
		}
		
		
		
	}
	
	public void render(Graphics2D g){
		g.drawImage(Bimage, getX(), getY(), null);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
