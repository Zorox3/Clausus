package main.level.bioms.trees;

import main.Game;
import main.level.blocks.Block;
import main.level.blocks.Tile;

public class Tree {

	private int height;
	private int width;

	private int x;
	private int y;

	private int top;

	private Block[][] block;

	private int[] m1;
	private int[] m2;

	public Tree(Block[][] block) {

		this.block = block;

	}

	public Block[][] returnBlock() {
		return this.block;
	}

	public void placeTree(int height, int width, int x, int[] m1, int[] m2) {
		this.height = height;
		this.width = width;

		this.x = x;
		this.y = getY();

		this.m1 = m1;
		this.m2 = m2;

		if (this.y == 0) {
			return;
		}
		

		this.top = this.y - this.height;

		for (int y = top; y < this.y; y++) {
			block[x][y].id = m1;
		}

		placeLeaves(width, top-1);
	}

	private void placeLeaves(int width, int top) {
		int r = Game.globalRandom.nextInt(2) - Game.globalRandom.nextInt(2);
		for (int xl = this.x - width + 1; xl < this.x + width; xl++) {
			if (xl < block.length && xl > 0) {
				if (top == this.top - 1 && xl == this.x + r) {
					block[xl][top].id = Tile.wood;
				} else {
					block[xl][top].id = Tile.leaves;
				}
			}
		}

		if (width > 0) {
			placeLeaves(width - 1, top - 1);
		}

	}

	private int getY() {
		for (int y = 0; y <= Game.level.lHeight; y++) {
			if (block[x][y].id != Tile.air && block[x][y].id != Tile.water
					&& block[x][y].id != Tile.water_half
							&& block[x][y].id != Tile.leaves&& block[x][y].id != Tile.wood) {
				
				if(block[x][y+1].id == Tile.gras || block[x][y+1].id == Tile.dirt_gras){
					block[x][y+1].id = Tile.dirt;
				}
				
				return y;
			}
		}
		return 0;
	}
}
