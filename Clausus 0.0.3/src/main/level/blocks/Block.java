package main.level.blocks;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;

public class Block extends Rectangle {

	private static final long serialVersionUID = 1L;

	public int[] id = { 0, 0 };

	public Block(Rectangle size, int[] id) {
		setBounds(size);
		this.id = id;
	}

	public void render(Graphics g, double c) {
		if (id != Tile.air) {
			if(c < 25 || !Game.showShadow)
				g.drawImage(Tile.texture.get(id), x - Game.sX, y - Game.sY, null);
			if (Game.showShadow)
				renderShadow(g, c);
		}

	}

	private void renderShadow(Graphics g, double c) {
		// if (id != Tile.wood && id != Tile.leaves && id != Tile.water
		// && id != Tile.water_half)
		if (c <= 8) {
			g.setColor(new Color(0, 0, 0, Game.sky.getDarkness()));
			g.fillRect(x - Game.sX, y - Game.sY, Tile.TILE_SIZE, Tile.TILE_SIZE);
		} else if (c > 8 && c <= 20) {
			int lum = 0;

			lum = Game.sky.getMaxDarkness() * (int) c;
			if (lum > 220 + Game.sky.getMaxDarkness()) {
				lum = 220 + Game.sky.getMaxDarkness();
			}

			g.setColor(new Color(0, 0, 0, lum));
			g.fillRect(x - Game.sX, y - Game.sY, Tile.TILE_SIZE, Tile.TILE_SIZE);

		}else if(c > 20 && c <= 25){
			g.setColor(new Color(0, 0, 0, 230));
			g.fillRect(x - Game.sX, y - Game.sY, Tile.TILE_SIZE, Tile.TILE_SIZE);
		}else{
			g.setColor(new Color(0, 0, 0, 255));
			g.fillRect(x - Game.sX, y - Game.sY, Tile.TILE_SIZE, Tile.TILE_SIZE);
		}
	
		//Game.updatesPerTick++;
	}

	public void debugRenderer(Graphics g, double c) {
		if (id != Tile.air) {
			g.drawImage(Tile.textureSmall.get(id), x - Game.sX + 5, y - Game.sY
					+ 5, null);
			if (Game.showShadow)
				renderShadow(g, c);
		}
	}

	public void drawChunkLine(Graphics g) {
		g.setColor(Color.RED);
		g.drawLine(x - Game.sX + width, y - Game.sY, x - Game.sX + width, y
				- Game.sY + width);
	}

	public void renderColor(Graphics g, Color c) {
		g.setColor(c);
		g.fillRect(x - Game.sX, y - Game.sY, width, height);
	}

	public void tick() {
	}
}
