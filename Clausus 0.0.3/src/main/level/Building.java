package main.level;

import main.Game;
import main.inventory.Inventory;
import main.level.blocks.Tile;

public class Building {

	private int lWidth = Game.level.lWidth;
	private int lHeight = Game.level.lHeight;

	private int delay = 20;

	public void tick() {
		if (delay <= 0) {
			delay = 10;
			if (!Inventory.isOpen) {
				if (Game.isLeftMousePressed) {
					pickUpBlock();
				} else if (Game.isRightMousePressed) {
					placeBlock();
				}
			}
		}

		delay--;
	}

	private void placeBlock() {
		for (int i = Game.player.playerChunk == 0 ? 0
				: Game.player.playerChunk - 1; i < (Game.player.playerChunk == 0 ? 0
				: Game.player.playerChunk - 1)
				+ Level.CHUNKS_RENDERED; i++) {
			for (int x = 0; x < lWidth; x++) {
				for (int y = 0; y < lHeight; y++) {

					double nx = (Game.level.chunk.get(i)[x][y].x - Game.player.x);
					double ny = (Game.level.chunk.get(i)[x][y].y - Game.player.y);
					double c = Math.sqrt((nx * nx) + (ny * ny))
							/ Tile.TILE_SIZE;

					if (c <= 4) {

						if (x >= 0 && y >= 0 && x < lWidth && y < lHeight) {
							if (Game.level.chunk.get(i)[x][y]
									.contains(Game.mouse)) {
								int sb[] = Inventory.cells
										.get(Inventory.selected).id;

								if (!sb.equals(Tile.air)
										&& Game.level.chunk.get(i)[x][y].id == Tile.air) {
									if (Inventory.decrease(sb)) {
										Game.level.chunk.get(i)[x][y].id = sb;
										if (Game.isClient || Game.isServer) {
											Game.client.addMessage("block", i
													+ " " + x + " " + y + " "
													+ sb[0] + " " + sb[1]);
										}
									}
									break;

								}
								if (y + 1 < lHeight)
									if (!sb.equals(Tile.air)
											&& Game.level.chunk.get(i)[x][y + 1].id == Tile.gras
											|| Game.level.chunk.get(i)[x][y + 1].id == Tile.dirt_gras) {
										Game.level.chunk.get(i)[x][y + 1].id = Tile.dirt;
									}
								break;
							}
						}
					}
				}
			}
		}
	}

	private void pickUpBlock() {
		for (int i = Game.player.playerChunk == 0 ? 0
				: Game.player.playerChunk - 1; i < (Game.player.playerChunk == 0 ? 0
				: Game.player.playerChunk - 1)
				+ Level.CHUNKS_RENDERED; i++) {

			for (int x = 0; x < lWidth; x++) {
				for (int y = 0; y < lHeight; y++) {

					double nx = (Game.level.chunk.get(i)[x][y].x - Game.player.x);
					double ny = (Game.level.chunk.get(i)[x][y].y - Game.player.y);
					double c = Math.sqrt((nx * nx) + (ny * ny))
							/ Tile.TILE_SIZE;

					if (c <= 4) {
						if (x >= 0 && y >= 0 && x < lWidth && y < lHeight) {

							if (Game.level.chunk.get(i)[x][y]
									.contains(Game.mouse)) {

								if (Inventory
										.increase(Game.level.chunk.get(i)[x][y].id)) {
									Game.level.chunk.get(i)[x][y].id = Tile.air;
									if (Game.isClient || Game.isServer) {
										if (Game.isClient || Game.isServer) {
											Game.client.addMessage("block", i
													+ " " + x + " " + y + " "
													+ "0 0");
										}
									}
									break;
								}
							}
						}
					}
				}
			}
		}
	}
}
