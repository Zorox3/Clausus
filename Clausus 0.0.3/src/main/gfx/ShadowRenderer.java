package main.gfx;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.Game;
import main.level.Level;
import main.level.blocks.Block;
import main.level.blocks.Tile;

public class ShadowRenderer {

	private BufferedImage i;
	private int x, y, width, height, luminosity;

	public ShadowRenderer(int x, int y, int width, int height, int luminosity) {

		this.setX(x);
		this.setY(y);
		this.width = width;
		this.height = height;
		this.luminosity = luminosity;

		int step = 8;
		int step2 = 20;
		int numSteps = height / step;

		i = new BufferedImage(width * 2, height * 2,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D image = (Graphics2D) i.getGraphics();
		image.setColor(new Color(0, 0, 0, 0));
		image.fillRect(0, 0, width * 2, height * 2);
		image.setColor(new Color(0, 0, 0, luminosity));
		//image.setComposite(AlphaComposite.DstOut);
		for (int i = 0; i < numSteps; i++) {
			image.fillOval((width / 2 - i * step2) + width / 2, (height / 2 - i
					* step)
					+ height / 2, i * step2 * 2, i * step * 2);
		}

		image.dispose();

		Level.shadowMap = new BufferedImage(Level.CHUNKS_RENDERED
				* Game.level.lWidth * Tile.TILE_SIZE, Game.level.lHeight
				* Tile.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);

	}

	private int time = 0;
	private int timer = 10;

	public void render(Graphics g) {
		if (time >= timer) {
			//updateShadow();

			time = 0;
		} else {
			time++;
		}

//		Graphics2D image = (Graphics2D) i.getGraphics();
//		image.setComposite(AlphaComposite.DstOut);
//		if (Level.shadowMap != null){
//			image.drawImage(Level.shadowMap, x, y,
//					null);
//		}
		g.drawImage(i, 0, Game.pixel.height / 2 + 100, null);
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

	public static void updateShadow() {
		Level.shadowMap = new BufferedImage(Level.CHUNKS_RENDERED
				* Game.level.lWidth * Tile.TILE_SIZE, Game.level.lHeight
				* Tile.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
	}

}
