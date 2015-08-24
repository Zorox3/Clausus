package main.level;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main.Game;
import main.gfx.ShadowRenderer;
import main.inventory.Inventory;
import main.level.bioms.Biom;
import main.level.bioms.types.Desert;
import main.level.bioms.types.GrasHills;
import main.level.bioms.types.GrasLand;
import main.level.bioms.types.Swamp;
import main.level.blocks.Block;
import main.level.blocks.Tile;
import main.level.worldGeneration.WorldGeneration;

public class Level implements Runnable {

	public static final int MINIMUM_CHUNKS_RENDERED = 3;
	public static int CHUNKS_RENDERED = MINIMUM_CHUNKS_RENDERED;
	public int lWidth = 32, lHeight = 64;
	public List<Block[][]> chunk = new ArrayList<Block[][]>();
	public List<String> chunkNames = new ArrayList<>();
	public WorldGeneration wg;
	public int maxChunks = 1000;

	private Random randomBiom = new Random();

	private List<Biom> bioms = new ArrayList<>();

	public Biom biom;
	private Biom tempBiom;
	private int biomTimes = 0;
	private int biomBorderTimes = 0;
	private int camX = Game.sX, camY = Game.sY;

	public void init() {
		bioms.add(new GrasLand());
		bioms.add(new GrasLand());
		bioms.add(new Desert());
		bioms.add(new GrasHills());
		bioms.add(new Swamp());

	}

	public String getBiomName() {
		return chunkNames.get(Game.player.playerChunk);
	}

	private void setBorderBiom(Biom biom) {
		if (biom.borderBiom != null) {
			this.tempBiom = biom;
			this.biom = biom.borderBiom;
			this.biomBorderTimes = biom.borderBiomTime;
		}
	}

	public int generateChunk() {

		if (biomTimes <= 0) {

			biom = bioms.get(randomBiom.nextInt(bioms.size()));

			Game.water.waterTexture = Tile.water;
			Game.water.waterTexture_half = Tile.water_half;

			biomTimes = randomBiom.nextInt(biom.toWidth - biom.fromWidth)
					+ biom.fromWidth;

			setBorderBiom(biom);
		}

		chunk.add(wg.generateLevel(createEmptyChunk(), biom));

		chunkNames.add(biom.getName());
		// System.out.println(chunk.size() + " - CHUNK");

		if (tempBiom == null) {
			biomTimes--;
		} else {
			if (biomBorderTimes <= 0) {
				biom = tempBiom;
				tempBiom = null;
			} else
				biomBorderTimes--;

		}

		return chunk.size();
	}

	private Block[][] createEmptyChunk() {
		Block[][] blockChunks = new Block[lWidth][lHeight];

		for (int x = 0; x < lWidth; x++) {

			for (int y = 0; y < lHeight; y++) {
				Game.updatesPerTick++;
				blockChunks[x][y] = new Block(new Rectangle(
						(x * Tile.TILE_SIZE)
								+ (chunk.size() == 0 ? 0 : chunk.get(chunk
										.size() - 1)[lWidth - 1][lHeight - 1].x
										+ Tile.TILE_SIZE), y * Tile.TILE_SIZE,
						Tile.TILE_SIZE, Tile.TILE_SIZE), Tile.air);
			}
		}

		return blockChunks;
	}

	public void tick() {

		camX = Game.sX;
		camY = Game.sY;

		if (Game.player.playerChunk + 1 == chunk.size()) {
			generateChunk();
		}
	}

	public static BufferedImage shadowMap;

	public void render(Graphics g) {
		int counterX = 0;
		int counterY = 0;
		//ShadowRenderer.updateShadow();
		//Graphics2D image = (Graphics2D) shadowMap.getGraphics();
		try {
			for (int i = Game.player.playerChunk == 0 ? 0
					: Game.player.playerChunk - 1; i < (Game.player.playerChunk == 0 ? 0
					: Game.player.playerChunk - 1)
					+ CHUNKS_RENDERED; i++) {
				for (int x = 0; x < lWidth; x++) {
					counterX++;
					for (int y = 0; y < lHeight; y++) {
						counterY++;

						if (x >= 0 && y >= 0
								&& x < chunk.get(i).length + (i * lWidth)
								&& y < chunk.get(i)[0].length) {

							if (chunk.get(i)[x][y].id != Tile.air) {
								double c = 0;
								if(y-4 >= 0){
									if(chunk.get(i)[x][y-4].id != Tile.air){
										double nx =  (chunk.get(i)[x][y].x - Game.player.x);
										double ny = (chunk.get(i)[x][y].y - Game.player.y);
										c =  Math.sqrt((nx * nx) + (ny * ny)) / Tile.TILE_SIZE; 
									}
								}
								
								if (Game.gameinfo && Game.debugRendering == 1) {
									
									chunk.get(i)[x][y].debugRenderer(g, c);

								} else {
									chunk.get(i)[x][y].render(g, c);
								}

								if (!Inventory.isOpen) {
									if (chunk.get(i)[x][y].contains(Game.mouse)) {
										g.setColor(new Color(0, 0, 0));
										g.drawRect(chunk.get(i)[x][y].x - camX,
												chunk.get(i)[x][y].y - camY,
												chunk.get(i)[x][y].width - 1,
												chunk.get(i)[x][y].height - 1);
									}
								}
							}
							if (Game.gameinfo && Game.debugRendering == 2) {
								if (counterX >= lWidth) {
									chunk.get(i)[x][y].drawChunkLine(g);
								}
							}
						}
						if (counterY >= lHeight) {

						}
					}
				}
				counterX = 0;

			}
		} catch (ArrayIndexOutOfBoundsException ab) {
			Game.lastError = "Render Error (Chunks)";
			System.err.println("Chunk Render Fehler" + "\n\t"
					+ "Array out of Bounds");
			Game.player.resetPlayer();
			ab.printStackTrace();
		} catch (IndexOutOfBoundsException ie) {
			Game.lastError = "Render Error (Chunks)";

			System.err.println("Chunk Render Fehler" + "\n\t"
					+ "Index out of Bounds");
			ie.printStackTrace();
		}
	}

	public List<Block[][]> getChunk() {
		return chunk;
	}

	@Override
	public void run() {
		System.out.println("Thread Level gestartet");
	}
}
