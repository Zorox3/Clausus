package main.entities;

import java.awt.Graphics;
import java.awt.Point;

import main.Game;
import main.extentions.DoubleRectangle;
import main.level.blocks.Tile;

public class Player extends DoubleRectangle {

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

	public Player(int width, int height) {
		setBounds((Game.WIDTH / 2) - (width / 2), (Game.HEIGHT / 2)
				- (height / 2), width, height);

		int posX = ((Game.level.maxChunks - 1) * Tile.TILE_SIZE * Game.level.lWidth) / 2;

		x += posX;
		Game.sX += posX;
	}

	public void tick() {

		positionX = (int) (x) / Tile.TILE_SIZE;
		positionY = (int) (y) / Tile.TILE_SIZE;

		if (!isJumping
				&& !isCollidingWithBlock(new Point((int) x + 6,
						(int) (y + height)), new Point((int) (x + width - 6),
						(int) (y + height)))) {
			y += fallingSpeed;
			Game.sY += fallingSpeed;
		} else {
			if (Game.isJumping) {
				isJumping = true;
			}
		}
		if (Game.isMoving) {

			boolean canMove = false;

			if (Game.dir == movingSpeed) {
				canMove = isCollidingWithBlock(new Point((int) (x + width),
						(int) y), new Point((int) (x + width),
						(int) (y + (height - 6))));
			} else if (Game.dir == -movingSpeed) {
				canMove = isCollidingWithBlock(new Point((int) x, (int) y),
						new Point((int) x, (int) (y + (height - 6))));
			}

			if (animationFrame[0] >= animationTime[0]) {
				if (animation[0] >= 4) {
					animationDir[0] = -1;
				} else if (animation[0] == 0) {
					animationDir[0] = 1;
				}
				animation[0] += animationDir[0];
				animationFrame[0] = 0;
			} else {
				animationFrame[0]++;
			}

			if (!canMove) {
				x += Game.dir;
				Game.sX += Game.dir;
			}
		}

		if (isJumping) {
			if (!isCollidingWithBlock(new Point((int) x + 6, (int) y),
					new Point((int) (x + width - 6), (int) y))) {
				if (jumpingCount >= jumpingHeight) {

					jumpingCount = 0;
					isJumping = false;
				} else {
					y -= jumpingSpeed;
					Game.sY -= jumpingSpeed;
					jumpingCount++;
				}
			} else {
				isJumping = false;
				jumpingCount = 0;
			}
		}
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

	

	public boolean isCollidingWithBlock(Point pt1, Point pt2) {
		boolean isColliding = false;
		int i;
		int chunkSwitch = 0;

		playerChunk = i = (positionX) / Game.level.lWidth;

		for (int x = positionX - (Game.level.lWidth * i); x < (int) ((positionX + 3))
				- (Game.level.lWidth * i); x++) {

			for (int y = (int) positionY; y < positionY + 3; y++) {

				// System.out.println(x + " " + y);

				if (x >= Game.level.lWidth) {
					chunkSwitch = Game.level.lWidth;
					i++;
				}

				if (y < Game.level.lHeight) {
					try {
						if (Game.level.chunk.get(i)[x - chunkSwitch][y].id != Tile.air
								&& Game.level.chunk.get(i)[x - chunkSwitch][y].id != Tile.water
								&& Game.level.chunk.get(i)[x - chunkSwitch][y].id != Tile.water_half
								&& Game.level.chunk.get(i)[x - chunkSwitch][y].id != Tile.wood
								&& Game.level.chunk.get(i)[x - chunkSwitch][y].id != Tile.leaves) {

							if (Game.level.chunk.get(i)[x - chunkSwitch][y]
									.contains(pt1)
									|| Game.level.chunk.get(i)[x - chunkSwitch][y]
											.contains(pt2)) {

								isColliding = true;
								break;
							}
						}
					} catch (Exception npe) {
						Game.lastError = "Colliding Error";
						System.err.println("Colliding Error. Player Reset");
						resetPlayer();
					}
				}

				i = playerChunk;
			}
		}

		return isColliding;
	}

	public void resetPlayer() {
		if (Game.dir < 0) {
			x += width;
			Game.sX += (int) width;
		} else if (Game.dir > 0) {
			x -= width;
			Game.sX -= (int) width;
		}
		y -= height;
		Game.sY -= (int) height;
	}

}
