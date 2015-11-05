package main.level.worldGeneration;

import main.Game;
import main.level.bioms.Biom;
import main.level.bioms.trees.Tree;
import main.level.blocks.Block;
import main.level.blocks.Tile;

public class WorldGeneration {
	
	private int lHeight = Game.level.lHeight;
	private int lWidth = Game.level.lWidth;

	private double STEP_MAX;
	private double STEP_CHANGE;
	private int HEIGHT_MAX;
	private int HEIGHT_MIN;
	private double slope = (Game.globalRandom.nextDouble() * STEP_MAX) * 2 - STEP_MAX;
	private double height = Game.globalRandom.nextDouble() * HEIGHT_MAX;

	

	public Block[][] generateLevel(Block[][] block, Biom biom) {

		this.STEP_MAX = biom.STEP_MAX;
		this.STEP_CHANGE = biom.STEP_CHANGE;
		this.HEIGHT_MAX = biom.HEIGHT_MAX;
		this.HEIGHT_MIN = biom.HEIGHT_MIN;

		float[][] whitenosie = GenerateWhiteNoise(lWidth, lHeight);
		float[][] perlinnoise = GeneratePerlinNoise(whitenosie, 4);

		int[] heightMap = generateHeightMap();

		for (int y = 0; y < lHeight; y++) {
			for (int x = 0; x < lWidth; x++) {

				// Dirt GROUND
				if (heightMap[x] < y) {
					block[x][y].id = biom.sMainMaterial;
				}

				// Stone GROUND
				if (y > heightMap[x]
						+ (biom.sMainMaterialHeight + Game.globalRandom
								.nextInt(biom.sMainMeterialHeightRandom))) {
					block[x][y].id = biom.mainMaterial;
				}

				if (y > heightMap[x] + 2 + Game.globalRandom.nextInt(2)) {
					if (perlinnoise[x][y] >= 4f && perlinnoise[x][y] <= 8f) {
						block[x][y].id = biom.mainMaterial;
					} else if (perlinnoise[x][y] >= 0f
							&& perlinnoise[x][y] <= 3f) {
						block[x][y].id = biom.sMainMaterial;
					} else if (perlinnoise[x][y] >= 9f
							&& perlinnoise[x][y] <= 10f) {
						block[x][y].id = biom.sOverlayMaterial;
					} else {
						int randomOre = Game.globalRandom.nextInt(100);

						// KOHLE
						if (randomOre >= biom.fCoal && randomOre <= biom.tCoal) {
							placeBlockCross(block, x, y, Tile.coal);
						}

						// KUPFER
						else if (randomOre >= biom.fCopper
								&& randomOre <= biom.tCopper) {
							placeBlockCross(block, x, y, Tile.copper);

						}

						// EISEN
						else if (randomOre >= biom.fIron
								&& randomOre <= biom.tIron && y > biom.hIron) {
							placeBlockCross(block, x, y, Tile.iron);

						}

						// GOLD
						else if (randomOre >= biom.fGold
								&& randomOre <= biom.tGold && y > biom.hGold) {
							placeBlockCross(block, x, y, Tile.gold);

						}

						// DIAMANT
						else if (randomOre >= biom.fDia
								&& randomOre <= biom.tDia && y > biom.hDia) {
							placeBlockCross(block, x, y, Tile.diamond);

						}

						// KIES
						else if (randomOre >= biom.fGravel
								&& randomOre <= biom.tGravel) {
							placeBlockCross(block, x, y, Tile.gravel);
						}

						// FILLER
						else {
							int[] defaultTile = Game.globalRandom.nextInt(100) <= biom.randomChance ? biom.filler1
									: biom.filler2;
							placeBlockCross(block, x, y, defaultTile);
						}
					}
				}

				// GRASS PLACEMENT
				if (y > 1)
					if (block[x][y].id != Tile.air
							&& block[x][y - 1].id == Tile.air) {
						if (Game.globalRandom.nextInt(100) < biom.overlayDif) {
							block[x][y].id = biom.sOverlayMaterial;
						} else {
							block[x][y].id = biom.overlayMaterial;
						}
					}
				if (y > 45 && block[x][y].id == Tile.air)
					block[x][y].id = biom.water;
			}
		}

		Tree t = new Tree(block);

		

		for (int i = 0; i < Game.globalRandom.nextInt(biom.treeCount); i++) {
			t.placeTree(Game.globalRandom.nextInt(biom.maxTreeWidth)+1, Game.globalRandom.nextInt(biom.maxTreeHeight)+1, Game.globalRandom.nextInt(Game.level.lWidth), Tile.wood,
					Tile.leaves);
		}
		block = t.returnBlock();
		
		return block;
	}

	private int[] generateHeightMap() {

		int[] heightMap = new int[lWidth];

		for (int x = 0; x < lWidth; x++) {
			height += slope;
			slope += (Game.globalRandom.nextDouble() * STEP_CHANGE) * 2 - STEP_CHANGE;

			if (slope > STEP_MAX) {
				slope = STEP_MAX;
			}

			if (slope < -STEP_MAX) {
				slope = -STEP_MAX;
			}

			if (height > HEIGHT_MAX) {
				height = HEIGHT_MAX;
				slope *= -1;
			}
			if (height < HEIGHT_MIN) {
				height = HEIGHT_MIN;
				slope *= -1;
			}
			heightMap[x] = (int) height;
		}
		return heightMap;
	}

	private Block[][] placeBlockCross(Block[][] block, int x, int y, int[] tile) {

		block[x][y].id = tile;

		if (x - 1 > 0)
			block[x - 1][y].id = tile;

		if (x + 1 < lWidth)
			block[x + 1][y].id = tile;

		if (y - 1 > 0)
			block[x][y - 1].id = tile;

		if (y + 1 < lHeight)
			block[x][y + 1].id = tile;

		return block;
	}

	private float[][] GenerateWhiteNoise(int width, int height) {
		 // Seed to 0 for testing
		float[][] noise = new float[width][height];

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				noise[i][j] = (float) Game.globalRandom.nextDouble() % 1;
			}
		}

		return noise;
	}

	private float[][] GenerateSmoothNoise(float[][] baseNoise, int octave) {
		int width = baseNoise.length;
		int height = baseNoise[0].length;

		float[][] smoothNoise = new float[width][height];

		int samplePeriod = 1 << octave; // calculates 2 ^ k
		float sampleFrequency = 1.0f / samplePeriod;

		for (int i = 0; i < width; i++) {
			// calculate the horizontal sampling indices
			int sample_i0 = (i / samplePeriod) * samplePeriod;
			int sample_i1 = (sample_i0 + samplePeriod) % width; // wrap around
			float horizontal_blend = (i - sample_i0) * sampleFrequency;

			for (int j = 0; j < height; j++) {
				// calculate the vertical sampling indices
				int sample_j0 = (j / samplePeriod) * samplePeriod;
				int sample_j1 = (sample_j0 + samplePeriod) % height; // wrap
																		// around
				float vertical_blend = (j - sample_j0) * sampleFrequency;

				// blend the top two corners
				float top = Interpolate(baseNoise[sample_i0][sample_j0],
						baseNoise[sample_i1][sample_j0], horizontal_blend);

				// blend the bottom two corners
				float bottom = Interpolate(baseNoise[sample_i0][sample_j1],
						baseNoise[sample_i1][sample_j1], horizontal_blend);

				// final blend
				smoothNoise[i][j] = Interpolate(top, bottom, vertical_blend);
			}
		}
		return smoothNoise;
	}

	private float Interpolate(float x0, float x1, float alpha) {
		return x0 * (1 - alpha) + alpha * x1;
	}

	private float[][] GeneratePerlinNoise(float[][] baseNoise, int octaveCount) {
		int width = baseNoise.length;
		int height = baseNoise[0].length;

		float[][][] smoothNoise = new float[octaveCount][][];

		float persistance = 0.5f;

		// generate smooth noise
		for (int i = 0; i < octaveCount; i++) {
			smoothNoise[i] = GenerateSmoothNoise(baseNoise, i);
		}

		float[][] perlinNoise = new float[width][height];
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;

		// blend noise together
		for (int octave = octaveCount - 1; octave >= 0; octave--) {
			amplitude *= persistance;
			totalAmplitude += amplitude;

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
				}
			}
		}
		// normalisation
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				perlinNoise[i][j] /= totalAmplitude / 10;
			}
		}
		return perlinNoise;
	}
}
