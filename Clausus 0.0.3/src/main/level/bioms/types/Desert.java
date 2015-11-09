package main.level.bioms.types;

import main.level.bioms.Biom;
import main.level.blocks.Tile;

public class Desert extends Biom{
	public Desert() {
		this.biomName = "Desert";
		
		this.fromWidth = 2;
		this.toWidth = 6;
		
		this.borderBiom = new GrasDesert();
		this.borderBiomTime = 1;
		
		this.STEP_MAX = 0.6;
		this.STEP_CHANGE = 0.2;
		this.HEIGHT_MAX = lHeight - 25;
		this.HEIGHT_MIN = 60;
		
		
		//this.mainMaterial = Tile.sand;
		this.sMainMaterial = Tile.sandstone;
		this.sMainMaterialHeight = 50;
		this.overlayMaterial = Tile.sand;
		this.sOverlayMaterial = Tile.sand;
	}
}
