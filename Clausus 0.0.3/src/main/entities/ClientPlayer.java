package main.entities;

import java.awt.Graphics;
import java.awt.Point;

import main.Game;
import main.extentions.DoubleRectangle;
import main.level.blocks.Tile;

public class ClientPlayer extends DoubleRectangle {

	public double fallingSpeed = 6;
	public double movingSpeed = 3;
	public double jumpingSpeed = 4;
	public int jumpingHeight = 24, jumpingCount = 0;

	public boolean isJumping = false;
	public boolean inWater = false;

	public int positionX;
	public int positionY;

	public int[] animation = { 0 };
	public int[] animationFrame = { 0 };
	public int[] animationTime = { 7 };
	public int[] animationDir = { 1 };

	public int playerChunk = Game.level.maxChunks - 1;

	public ClientPlayer(int width, int height) {
		setBounds((Game.pixel.width / 2) - (width / 2), (Game.pixel.height / 2)
				- (height / 2), width, height);


	}

	

	public void render(Graphics g) {
		if (!Game.isMoving) {
			g.drawImage(Tile.sprite, (int) x - Game.sX, (int) y - Game.sY,
					(int) x + (int) width - Game.sX, (int) y + (int) height
							- Game.sY, Tile.player_stand[0] * Tile.TILE_SIZE,
					Tile.player_stand[1] * Tile.TILE_SIZE, Tile.player_stand[0]
							* Tile.TILE_SIZE + (int) width,
					Tile.player_stand[1] * Tile.TILE_SIZE + (int) height, null);
		} else {

			if (Game.dir < 0) {
				g.drawImage(
						Tile.sprite,
						(int) x - Game.sX,
						(int) y - Game.sY,
						(int) x + (int) width - Game.sX,
						(int) y + (int) height - Game.sY,
						Tile.player_left[0] * Tile.TILE_SIZE
								+ (Tile.TILE_SIZE * animation[0]),
						Tile.player_left[1] * Tile.TILE_SIZE,
						Tile.player_left[0] * Tile.TILE_SIZE
								+ (Tile.TILE_SIZE * animation[0]) + (int) width,
						Tile.player_left[1] * Tile.TILE_SIZE + (int) height,
						null);
			} else {
				g.drawImage(
						Tile.sprite,
						(int) x - Game.sX,
						(int) y - Game.sY,
						(int) x + (int) width - Game.sX,
						(int) y + (int) height - Game.sY,
						Tile.player_right[0] * Tile.TILE_SIZE
								+ (Tile.TILE_SIZE * animation[0]),
						Tile.player_right[1] * Tile.TILE_SIZE,
						Tile.player_right[0] * Tile.TILE_SIZE
								+ (Tile.TILE_SIZE * animation[0]) + (int) width,
						Tile.player_right[1] * Tile.TILE_SIZE + (int) height,
						null);
			}
		}
	}

	

}

