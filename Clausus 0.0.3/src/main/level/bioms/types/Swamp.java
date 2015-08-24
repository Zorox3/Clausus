package main.level.bioms.types;

import main.level.bioms.Biom;
import main.level.blocks.Tile;

public class Swamp extends Biom {
	public Swamp() {
		this.biomName = "Swamp";
	
		this.STEP_MAX = 0.8;
		this.STEP_CHANGE = 0.2;
		this.HEIGHT_MAX = lHeight - 20;
		this.HEIGHT_MIN = 30;
		
		this.water = Tile.water;
		this.water_half = Tile.water_half;
		
		this.sOverlayMaterial = Tile.water;
		this.overlayDif= 30;
	}
}
