package main.level.bioms;

import main.Game;
import main.level.blocks.Tile;

public abstract class Biom {
	
	
	protected String biomName = "BIOM NAME";

	public int lHeight = Game.level.lHeight;
	public int lWidth = Game.level.lWidth;
	
	
	public double STEP_MAX = 1.5;
	public double STEP_CHANGE = 1.0;
	public int HEIGHT_MAX = lHeight - 15;
	public int HEIGHT_MIN = 50;

	public Biom borderBiom = null;
	public int borderBiomTime = 2;
	
	public int fromWidth = 3;
	public int toWidth = 5;
	
	public int treeCount = 6;
	public int maxTreeWidth = 4;
	public int maxTreeHeight = 5;
	
	//MAIN MATERIALS
	public int[] mainMaterial = Tile.stone;
	public int[] sMainMaterial = Tile.dirt; 
	public int sMainMaterialHeight = 10;
	public int sMainMeterialHeightRandom = 5;
	public int[] overlayMaterial = Tile.gras;
	public int[] sOverlayMaterial = Tile.dirt_gras;
	
	public int overlayDif = 15;
	
	//KOHLE
	public int fCoal = 0;
	public int tCoal = 15;
	
	//KUPFER
	public int fCopper = 16;
	public int tCopper = 25;
	
	//EISEN
	public int fIron = 26;
	public int tIron = 40;
	public int hIron = 40;
	
	//GOLD
	public int fGold = 35;
	public int tGold = 41;
	public int hGold = 20;
	
	//DIAMANT
	public int fDia = 42;
	public int tDia = 45;
	public int hDia = lHeight-8;
	
	//KIES
	public int fGravel = 46;
	public int tGravel = 70;
	
	//SAND/DIRT - FILLER
	public int[] filler1 = Tile.sand;
	public int[] filler2 = Tile.dirt;
	public int randomChance = 50;
	
	
	//WATER TEXTURE
	public int[] water = Tile.water;
	public int[] water_half = Tile.water_half;
	
	
	public String getName(){
		return this.biomName;
	}
}
