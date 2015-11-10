package main.level;

import main.Game;
import main.level.blocks.Tile;

public class Water {
	private int lWidth = Game.level.lWidth;
	private int lHeight = Game.level.lHeight;

	public int[] waterTexture;
	public int[] waterTexture_half;

	private Level level;

	public Water(Level level) {
		this.level = level;
	}

	private int i = Level.maxChunks / 2;

	private boolean jumpOverRight = false;
	private boolean jumpOverLeft = false;

	boolean secondTick = false;

	public void tick() {

		if (secondTick) {

			x: for (int x = 0; x < lWidth; x++) {

				y: for (int y = 0; y < lHeight; y++) {

					if ((level.chunk.get(i)[x][y].id == waterTexture || level.chunk
							.get(i)[x][y].id == waterTexture_half)) {

						// DOWN MOVEMENT
						boolean movedDown = false;

						if (y + 1 >= lHeight) {
							continue y;
						}

						if (level.chunk.get(i)[x][y + 1].id == Tile.air
								|| level.chunk.get(i)[x][y + 1].id == waterTexture_half) {
							level.chunk.get(i)[x][y].id = Tile.air;
							level.chunk.get(i)[x][y + 1].id = waterTexture;
							movedDown = true;

							Game.updatesPerTick++;

							continue x;
						}

						int tx = x + 1;
						int tnx = x - 1;
						int ti = i;
						int tni = i;

						if (tx >= lWidth) {
							tx = 0;
							ti += 1;
						}
						if (tnx < 0) {
							tnx = lWidth - 1;
							tni -= 1;
						}

						// Waterblock to HalfWater - left/right
						if (!movedDown
								&& level.chunk.get(ti)[tx][y].id == Tile.air
								&& level.chunk.get(tni)[tnx][y].id == Tile.air) {

							level.chunk.get(ti)[tx][y].id = waterTexture_half;
							level.chunk.get(i)[x][y].id = Tile.air;
							level.chunk.get(tni)[tnx][y].id = waterTexture_half;

							Game.updatesPerTick++;

							continue y;
						}

						// waterflow right
						else if (!movedDown
								&& level.chunk.get(ti)[tx][y].id == Tile.air
								&& !jumpOverRight) {

							if (level.chunk.get(i)[x][y].id == waterTexture) {

								level.chunk.get(ti)[tx][y].id = waterTexture_half;
								level.chunk.get(i)[x][y].id = waterTexture_half;

							} else {
								level.chunk.get(ti)[tx][y].id = waterTexture;
								level.chunk.get(i)[x][y].id = Tile.air;

							}

							Game.updatesPerTick++;

						}

						// waterflow left
						else if (!movedDown
								&& level.chunk.get(tni)[tnx][y].id == Tile.air
								&& !jumpOverLeft) {

							if (level.chunk.get(i)[x][y].id == waterTexture) {
								level.chunk.get(i)[x][y].id = waterTexture_half;
								level.chunk.get(tni)[tnx][y].id = waterTexture;
							} else {
								level.chunk.get(i)[x][y].id = Tile.air;
								level.chunk.get(tni)[tnx][y].id = waterTexture;
							}

							Game.updatesPerTick++;
						}

						// waterblock to halfwater convertion
						if (level.chunk.get(i)[x][y].id == waterTexture
								&& (level.chunk.get(ti)[tx][y].id == Tile.water_half && level.chunk
										.get(tni)[tnx][y].id == Tile.water_half)) {

							level.chunk.get(i)[x][y].id = waterTexture_half;

							Game.updatesPerTick++;

							break x;

						}

					} else
						continue;
				}
			}
			i++;
			if (i > Game.player.playerChunk + 2) {
				i = Game.player.playerChunk - 2;
			}

		}
		secondTick = !secondTick;
	}
}
