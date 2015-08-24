package main.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import main.Game;
import main.level.blocks.Tile;

public class Inventory {

	// INVENTAR BAR
	public static final int INV_CELL_LENGHT = 8;
	public static final int INV_CELL_SIZE = 32;
	public static final int INV_CELL_SPACE = 4;
	public static final int INV_CELL_BORDER_SPACE = 4;

	// INVENTAR ( RUCKSACK )
	public static final int INV_BACKPACK_HEIGHT = 4;
	public static final int INV_BACKPACK_WIDTH = 8;
	public static final int INV_BACKPACK_SLOTS = INV_BACKPACK_HEIGHT
			* INV_BACKPACK_WIDTH;

	public static int selected = 0;
	public static int[] holdingId = Tile.air;
	public static int holdingCount = 0;

	public static ArrayList<Cell> cells = new ArrayList<Cell>();
	public static ArrayList<Cell> backpack = new ArrayList<Cell>();

	public static boolean isOpen = false;
	public static boolean isHolding = false;

	public Inventory() {

		// INV BAR
		for (int i = 0; i < INV_CELL_LENGHT; i++) {
			cells.add(new Cell(
					new Rectangle(
							(Game.pixel.width / 2)
									+ (i * (INV_CELL_SIZE + INV_CELL_SPACE) + INV_CELL_SPACE)
									- ((INV_CELL_LENGHT
											* (INV_CELL_SIZE + INV_CELL_SPACE) + INV_CELL_SPACE) / 2),
							Game.pixel.height
									- (INV_CELL_SIZE + INV_CELL_BORDER_SPACE),
							INV_CELL_SIZE, INV_CELL_SIZE), Tile.air, 0));
		}

		// INV BACKPACK
		int x = 0, y = 0;

		for (int i = 0; i < INV_BACKPACK_SLOTS; i++) {
			backpack.add(new Cell(
					new Rectangle(
							(Game.pixel.width / 2)
									+ (x * (INV_CELL_SIZE + INV_CELL_SPACE) + INV_CELL_SPACE)
									- ((INV_CELL_LENGHT
											* (INV_CELL_SIZE + INV_CELL_SPACE) + INV_CELL_SPACE) / 2),
							Game.pixel.height

									- (INV_CELL_SIZE + INV_CELL_BORDER_SPACE)
									- (INV_BACKPACK_HEIGHT * (INV_CELL_SIZE + INV_CELL_SPACE))
									+ (y * (INV_CELL_SIZE + INV_CELL_SPACE)),
							INV_CELL_SIZE, INV_CELL_SIZE), Tile.air, 0));
			x++;
			if (x == INV_BACKPACK_WIDTH) {
				x = 0;
				y++;
			}
		}

//		cells.get(0).id = Tile.dirt;
//		cells.get(1).id = Tile.gras;
//		cells.get(2).id = Tile.stone;
		cells.get(3).id = Tile.water;
		cells.get(3).itemCount = 500;
//		cells.get(5).id = Tile.dirt_gras;
	}

	public static void click(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (isOpen) {
				for (int i = 0; i < cells.size(); i++) {
					Cell c = cells.get(i);
					if (c.contains(Game.mouseS)) {
						if (c.id != Tile.air && !isHolding) {
							holdingId = c.id;
							holdingCount = c.itemCount;
							c.id = Tile.air;
							c.itemCount = 0;
							isHolding = true;
						} else if (isHolding && c.id == Tile.air) {
							c.id = holdingId;
							c.itemCount = holdingCount;
							isHolding = false;
						} else if (isHolding && c.id != Tile.air) {
							int[] con = c.id;
							int count = c.itemCount;
							c.itemCount = holdingCount;
							c.id = holdingId;
							holdingId = con;
							holdingCount = count;
						}
					}
				}
				for (int i = 0; i < backpack.size(); i++) {
					Cell b = backpack.get(i);
					if (b.contains(Game.mouseS)) {
						if (b.id != Tile.air && !isHolding) {
							holdingId = b.id;
							holdingCount = b.itemCount;
							b.id = Tile.air;
							b.itemCount = 0;
							isHolding = true;
						} else if (isHolding && b.id == Tile.air) {
							b.id = holdingId;
							b.itemCount = holdingCount;
							isHolding = false;
						} else if (isHolding && b.id != Tile.air) {
							int[] con = b.id;
							int count = b.itemCount;
							b.itemCount = holdingCount;
							b.id = holdingId;
							holdingId = con;
							holdingCount = count;
						}
					}
				}
			}
		}
	}

	public void render(Graphics g) {

		if (isOpen) {
			g.setColor(new Color(0, 0, 0, 60));
			g.fillRect(0, 0, Game.pixel.width, Game.pixel.height);

			for (int i = 0; i < backpack.size(); i++) {
				backpack.get(i).render(g, false);
			}
		}

		for (int i = 0; i < cells.size(); i++) {
			boolean isSelected = false;
			if (i == selected) {
				isSelected = true;
			}
			cells.get(i).render(g, isSelected);
		}

		if (isHolding) {
			g.drawImage(Tile.sprite, Game.mouseS.x - (INV_CELL_SIZE / 2),
					Game.mouseS.y - (INV_CELL_SIZE / 2),
					Game.mouseS.x, Game.mouseS.y, holdingId[0]
							* Tile.TILE_SIZE, holdingId[1] * Tile.TILE_SIZE,
					holdingId[0] * Tile.TILE_SIZE + Tile.TILE_SIZE,
					holdingId[1] * Tile.TILE_SIZE + Tile.TILE_SIZE, null);
		}
	}

	public static boolean decrease(int[] sb) {

		for (Cell c : cells) {
			if (c.id == sb) {
				if (c.itemCount == 0) {
					return false;
				}
				c.itemCount--;
				if(c.itemCount <= 0){
					c.id = Tile.air;
				}
				return true;
			}
		}
		return false;
	}

	public static boolean increase(int[] id) {

		if(id == Tile.water_half){
			id = Tile.water;
		}
		if(id == Tile.gras){
			id = Tile.dirt;
		}
		
		int emptySlotCells = -1, emptySlotBackpack = -1, itemIdCell = -1, itemIdBackpack = -1;

		for (int i = 0; i < cells.size(); i++) {

			if (cells.get(i).id == Tile.air && emptySlotCells == -1) {

				emptySlotCells = i;
			}

			if (cells.get(i).id == id && cells.get(i).itemCount <= 50) {
				itemIdCell = i;
				break;

			}
		}
		if (itemIdCell == -1) {
			for (int i = 0; i < backpack.size(); i++) {

				if (backpack.get(i).id == Tile.air && emptySlotBackpack == -1) {
					emptySlotBackpack = i;
				}

				if (backpack.get(i).id == id && backpack.get(i).itemCount <= 50) {
					itemIdBackpack = i;
					break;
				}

			}
		}

		if (itemIdCell != -1) {
			cells.get(itemIdCell).itemCount++;
			return true;
		} else if (itemIdBackpack != -1) {
			backpack.get(itemIdBackpack).itemCount++;
			return true;
		} else {

			if (emptySlotCells != -1) {
				cells.get(emptySlotCells).id = id;
				cells.get(emptySlotCells).itemCount=1;
				return true;
			} else if (emptySlotBackpack != -1) {
				backpack.get(emptySlotBackpack).id = id;
				backpack.get(emptySlotBackpack).itemCount=1;
				return true;
			} else {
				return false;
			}
		}
	}
}
