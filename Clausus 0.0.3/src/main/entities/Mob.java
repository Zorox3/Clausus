package main.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import main.Game;
import main.extentions.DoubleRectangle;
import main.level.Level;
import main.level.blocks.Tile;

public class Mob extends DoubleRectangle {

	public double fallingSpeed = 2.5;
	public double movingSpeed = 2.5;
	public double jumpingSpeed = 4;
	public int jumpingHeight = 14, jumpingCount = 0;

	public boolean isJumping = false;

	public int positionX;
	public int positionY;

	public int[] id_r;
	public int[] id_l;
	private int movementSpeed = 1;
	public int dir = -movementSpeed;
	public boolean isMoving = false;
	public int[] animation = { 0 };
	public int[] animationFrame = { 0 };
	public int[] animationTime = { 7 };
	public int[] animationDir = { 1 };

	public Mob(int x, int y, int width, int height, int[] id_l, int[] id_r) {
		setBounds(x, y, width, height);
		this.id_l = id_l;
		this.id_r = id_r;

	}

	public void tick() {

		positionX = (int) (x) / Tile.TILE_SIZE;
		positionY = (int) (y) / Tile.TILE_SIZE;

		if (!isJumping
				&& !isCollidingWithBlock(new Point((int) x + 6,
						(int) (y + height)), new Point((int) (x + width - 6),
						(int) (y + height)))) {
			y += fallingSpeed;

		} else {
			if (isJumping) {
				isJumping = true;
			}
		}
		if (isMoving) {

			boolean canMove = false;

			if (dir == movingSpeed) {
				canMove = isCollidingWithBlock(new Point((int) (x + width),
						(int) y), new Point((int) (x + width),
						(int) (y + (height - 6))));
			} else if (dir == -movingSpeed) {
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
				x += dir;
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
					jumpingCount++;
				}
			} else {
				isJumping = false;
				jumpingCount = 0;
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.drawLine((int) (x + width), (int) y, (int) (x + width),
				(int) (y + (height - 6)));
		g.drawLine((int) x, (int) y, (int) x, (int) (y + (height - 6)));

		g.drawLine((int) x + 6, (int) y, (int) (x + width - 6), (int) y);

		g.drawLine((int) x + 6, (int) (y + height), (int) (x + width - 6),
				(int) (y + height));

		if (dir < 0) {
			g.drawImage(Tile.sprite, (int) x, (int) y, (int) x + (int) width,
					(int) y + (int) height, id_l[0] * Tile.TILE_SIZE
							+ (Tile.TILE_SIZE * animation[0]), id_l[1]
							* Tile.TILE_SIZE, id_l[0] * Tile.TILE_SIZE
							+ (Tile.TILE_SIZE * animation[0]) + (int) width,
					id_l[1] * Tile.TILE_SIZE + (int) height, null);
		} else {
			g.drawImage(Tile.sprite, (int) x, (int) y, (int) x + (int) width,
					(int) y + (int) height, id_r[0] * Tile.TILE_SIZE
							+ (Tile.TILE_SIZE * animation[0]), id_r[1]
							* Tile.TILE_SIZE, id_r[0] * Tile.TILE_SIZE
							+ (Tile.TILE_SIZE * animation[0]) + (int) width,
					id_r[1] * Tile.TILE_SIZE + (int) height, null);
		}
	}

	public double realChunk = 0;
	private int temp = 0;

	public boolean isCollidingWithBlock(Point pt1, Point pt2) {
		boolean isColliding = false;
		int i;
		int chunkSwitch = 0;
		temp = i = (positionX) / Game.level.lWidth;

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
						System.err.println("Colliding Error. AI Reset");

					}
				}

				i = temp;
			}
		}

		return isColliding;
	}

}
