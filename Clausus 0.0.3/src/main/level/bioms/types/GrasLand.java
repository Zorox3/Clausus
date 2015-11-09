package main.level.bioms.types;

import main.level.bioms.Biom;
import main.level.blocks.Tile;

public class GrasLand extends Biom {
	public GrasLand() {
		this.biomName = "Gras Lands";
		
		this.STEP_MAX = 0.8;
		this.STEP_CHANGE = 0.2;
		this.HEIGHT_MAX = lHeight - 20;
		this.HEIGHT_MIN = 50;
		
		this.filler1 = Tile.dirt;
		this.filler2 = Tile.dirt;
	}
}
