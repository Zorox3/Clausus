package main.level;

import main.Game;
import main.level.blocks.Tile;

public class BlockPhysic {

	private Level level;

	private int lWidth;
	private int lHeight;

	private int i = 0;

	public BlockPhysic(Level level) {
		this.level = level;

		this.lWidth = level.lWidth;
		this.lHeight = level.lHeight;
	}

	public void tick() {

		for (int x = 0; x < lWidth; x++) {

			y: for (int y = 0; y < lHeight - 1; y++) {

				if ((level.chunk.get(i)[x][y].id == Tile.gravel || level.chunk
						.get(i)[x][y].id == Tile.sand)) {

					if (level.chunk.get(i)[x][y + 1].id == Tile.air || level.chunk.get(i)[x][y + 1].id == Tile.water || level.chunk.get(i)[x][y + 1].id == Tile.water_half) {
						Game.updatesPerTick++;
						level.chunk.get(i)[x][y + 1].id = level.chunk.get(i)[x][y].id == Tile.gravel ? Tile.gravel
								: Tile.sand;
						
						
						if(level.chunk.get(i)[x][y + 1].id == Tile.water)
							level.chunk.get(i)[x][y].id = Tile.water;
						else if(level.chunk.get(i)[x][y + 1].id == Tile.water_half)
							level.chunk.get(i)[x][y].id = Tile.water_half;
						else
							level.chunk.get(i)[x][y].id = Tile.air;
						break y;
					}

				} else
					continue;
			}
		}
		// System.err.println(i);
		i++;
		if (i > Game.player.playerChunk + 2) {
			i = Game.player.playerChunk - 2;
		}

	}
}
