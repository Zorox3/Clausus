package main.level;

import main.Game;
import main.level.blocks.Tile;

public class Water {
	private int lWidth = Game.level.lWidth;
	private int lHeight = Game.level.lHeight;

	private int waterTimer = 0;

	public int[] waterTexture;
	public int[] waterTexture_half;

	private Level level;

	public Water(Level level) {
		this.level = level;
	}

	public void tickO() {

		waterTimer++;

		if (waterTimer >= 5) {

			int i = 0;
			for (int j = 0; j < level.chunk.size(); j++) {
				i++;
				int chunkSwitch = 0;
				for (int x = 0; x < lWidth; x++) {

					y: for (int y = 0; y < lHeight; y++) {

						if (i > j) {
							i = j;
						}
						if ((level.chunk.get(i)[x][y].id == waterTexture || level.chunk
								.get(i)[x][y].id == waterTexture_half)) {

							boolean movedDown = false;
							if (level.chunk.get(i)[x][y + 1].id == Tile.air
									|| level.chunk.get(i)[x][y + 1].id == waterTexture_half) {
								level.chunk.get(i)[x][y].id = Tile.air;
								level.chunk.get(i)[x][y + 1].id = waterTexture;
								movedDown = true;
								Game.updatesPerTick++;

								break;
							}
							if (x + 1 >= lWidth) {
								chunkSwitch = lWidth;
								i++;
							}
							if (x + 1 < lWidth && x - 1 > 0) {
								if (!movedDown
										&& (level.chunk.get(i)[x - 1
												- chunkSwitch][y].id == Tile.air
												&& level.chunk.get(i)[x][y].id == waterTexture && (level.chunk
												.get(i)[x][y - 1].id != waterTexture))) {
									level.chunk.get(i)[x][y].id = Tile.air;
									level.chunk.get(i)[x - 1 - chunkSwitch][y].id = waterTexture;
									Game.updatesPerTick++;

								}
							}
							if (x + 1 < lWidth && x - 1 > 0) {
								if (level.chunk.get(i)[x - 1 - chunkSwitch][y].id == Tile.air
										&& level.chunk.get(i)[x + 1
												- chunkSwitch][y].id != Tile.air) {
									level.chunk.get(i)[x][y].id = Tile.air;
									level.chunk.get(i)[x - 1 - chunkSwitch][y].id = waterTexture_half;
									Game.updatesPerTick++;

								} else if (level.chunk.get(i)[x - 1
										- chunkSwitch][y].id != Tile.air
										&& level.chunk.get(i)[x + 1
												- chunkSwitch][y].id == Tile.air) {
									level.chunk.get(i)[x][y].id = waterTexture_half;
									level.chunk.get(i)[x + 1 - chunkSwitch][y].id = waterTexture_half;
									Game.updatesPerTick++;
								}
							}
						} else
							continue;
					}
				}
			}
			waterTimer = 0;
		}

	}

	private int i = 0;

	public void tick() {

		waterTimer++;

		if (waterTimer > 1) {

			int chunkSwitch = 0;
			for (int x = 0; x < lWidth; x++) {

				y: for (int y = 0; y < lHeight; y++) {

					if ((level.chunk.get(i)[x][y].id == waterTexture || level.chunk
							.get(i)[x][y].id == waterTexture_half)) {

						
						//DOWN MOVEMENT
						boolean movedDown = false;
						if (level.chunk.get(i)[x][y + 1].id == Tile.air
								|| level.chunk.get(i)[x][y + 1].id == waterTexture_half) {
							level.chunk.get(i)[x][y].id = Tile.air;
							level.chunk.get(i)[x][y + 1].id = waterTexture;
							movedDown = true;

							Game.updatesPerTick++;

							break y;
						}

						if (x + 1 < lWidth && x - 1 >= 0) {
							if (!movedDown
									&& (level.chunk.get(i)[x - 1 - chunkSwitch][y].id == Tile.air
											&& level.chunk.get(i)[x][y].id == waterTexture && (level.chunk
											.get(i)[x][y - 1].id != waterTexture))) {
								level.chunk.get(i)[x][y].id = Tile.air;
								level.chunk.get(i)[x - 1 - chunkSwitch][y].id = waterTexture;

								Game.updatesPerTick++;
							}
						}

						if (x + 1 < lWidth && x - 1 >= 0) {
							if (level.chunk.get(i)[x - 1 - chunkSwitch][y].id == Tile.air
									&& level.chunk.get(i)[x + 1 - chunkSwitch][y].id != Tile.air) {
								level.chunk.get(i)[x][y].id = Tile.air;
								level.chunk.get(i)[x - 1 - chunkSwitch][y].id = waterTexture_half;

								Game.updatesPerTick++;

							} else if (level.chunk.get(i)[x - 1 - chunkSwitch][y].id != Tile.air
									&& level.chunk.get(i)[x + 1 - chunkSwitch][y].id == Tile.air) {
								level.chunk.get(i)[x][y].id = waterTexture_half;
								level.chunk.get(i)[x + 1 - chunkSwitch][y].id = waterTexture_half;

								Game.updatesPerTick++;
							}

						}
					} else
						continue;
				}
			}

			waterTimer = 0;
			// System.err.println(i);
			i++;
			if (i > Game.player.playerChunk + 2) {
				i = Game.player.playerChunk - 2;
			}
		}
	}
}
