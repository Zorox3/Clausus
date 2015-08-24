package main.level.blocks;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

public class Tile {

	public static final int TILE_SIZE = 32;

	public static int[] air = { 0, 0 };
	public static int[] wood = { 0, 1 };
	public static int[] leaves = { 1, 1 };
	public static int[] stone = { 1, 0 };
	public static int[] dirt = { 3, 0 };
	public static int[] gras = { 2, 0 };
	public static int[] dirt_gras = { 4, 0 };
	public static int[] coal = { 5, 0 };
	public static int[] copper = { 6, 0 };
	public static int[] iron = { 7, 0 };
	public static int[] gold = { 8, 0 };
	public static int[] diamond = { 9, 0 };
	public static int[] gravel = { 10, 0 };
	public static int[] sand = { 11, 0 };
	public static int[] sandstone = { 11, 1 };
	public static int[] water = { 12, 0 };
	public static int[] water_half = { 13, 0 };
	public static int[] water_quater = { 14, 0 };
	public static int[] water_swamp = { 12, 1 };
	public static int[] water_half_swamp = { 13, 0 };
	
	
	public static Map<int[], BufferedImage> texture = new HashMap<>();
	public static Map<int[], BufferedImage> textureSmall = new HashMap<>();
	
	// PLAYER
	public static int[] player_left = { 0, 28 };
	public static int[] player_right = { 0, 30 };
	public static int[] player_stand = { 0, 26 };

	// SCHWERTKÄMPFER
	public static int[] mob1_left = { 0, 24 };
	public static int[] mob1_right = { 0, 22 };

	public static BufferedImage sprite;
	public static BufferedImage invCell;
	public static BufferedImage invCellSelected;

	public Tile() {
		try {
			sprite = ImageIO.read(Tile.class.getResource("/sprite.png"));
			invCell = ImageIO.read(Tile.class.getResource("/inv_cell.png"));
			invCellSelected = ImageIO.read(Tile.class
					.getResource("/inv_cell_selected.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		texture.put(air, sprite.getSubimage(air[0]*TILE_SIZE, air[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(wood, sprite.getSubimage(wood[0]*TILE_SIZE, wood[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(leaves, sprite.getSubimage(leaves[0]*TILE_SIZE, leaves[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(stone, sprite.getSubimage(stone[0]*TILE_SIZE, stone[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(dirt, sprite.getSubimage(dirt[0]*TILE_SIZE, dirt[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(gras, sprite.getSubimage(gras[0]*TILE_SIZE, gras[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(dirt_gras, sprite.getSubimage(dirt_gras[0]*TILE_SIZE, dirt_gras[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(coal, sprite.getSubimage(coal[0]*TILE_SIZE, coal[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(copper, sprite.getSubimage(copper[0]*TILE_SIZE, copper[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(iron, sprite.getSubimage(iron[0]*TILE_SIZE, iron[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(gold, sprite.getSubimage(gold[0]*TILE_SIZE, gold[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(diamond, sprite.getSubimage(diamond[0]*TILE_SIZE, diamond[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(gravel, sprite.getSubimage(gravel[0]*TILE_SIZE, gravel[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(sand, sprite.getSubimage(sand[0]*TILE_SIZE, sand[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(sandstone, sprite.getSubimage(sandstone[0]*TILE_SIZE, sandstone[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(water, sprite.getSubimage(water[0]*TILE_SIZE, water[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		texture.put(water_half, sprite.getSubimage(water_half[0]*TILE_SIZE, water_half[1]*TILE_SIZE, TILE_SIZE, TILE_SIZE));
		
		
		
		for(Entry<int[], BufferedImage> entry : texture.entrySet()) {
		    int[] key = entry.getKey();
		    textureSmall.put(key, resize(sprite.getSubimage(key[0]*TILE_SIZE, key[1] * TILE_SIZE, TILE_SIZE, TILE_SIZE), TILE_SIZE-10, TILE_SIZE-10));   
		}
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	} 
}
