package main.level.bioms.types;

import main.level.bioms.Biom;
import main.level.blocks.Tile;

public class GrasDesert extends Biom{

	public GrasDesert(){
		this.biomName ="Gras Desert";
		
		this.STEP_MAX = 0.6;
		this.STEP_CHANGE = 0.4;
		this.HEIGHT_MAX = lHeight - 25;
		this.HEIGHT_MIN = 30;
		
		this.treeCount = 2;
		
		this.overlayMaterial = Tile.dirt_gras;
	}
	
}
