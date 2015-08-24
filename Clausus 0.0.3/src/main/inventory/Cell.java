package main.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;
import main.level.blocks.Tile;

public class Cell extends Rectangle{
	
	public static final int CELL_ITEM_BORDER = 5;
	
	private static final long serialVersionUID = 1L;

	public int id[] = {0, 0};
	public int itemCount = 0;
	
	public Cell(Rectangle size, int[] id, int itemCount){
		setBounds(size);
		this.id = id;
		this.itemCount = itemCount;
	}
	
	public void render(Graphics g, boolean isSelected){
		

		
		
		if(isSelected && !Inventory.isOpen)
			g.drawImage(Tile.invCellSelected, x, y, width, height, null);
		else
			g.drawImage(Tile.invCell, x, y, width, height, null);
		
		if(id != Tile.air){

			g.drawImage(Tile.textureSmall.get(id),x+ CELL_ITEM_BORDER, y+CELL_ITEM_BORDER, null );
			
			
			g.setColor(Color.RED);
			g.setFont(new Font("Verdanda", Font.BOLD, 12));
			g.drawString(itemCount + "", x+ CELL_ITEM_BORDER + width/2 -10, y+CELL_ITEM_BORDER + height/2 + 5);
		}
		
		if(Inventory.isOpen && contains(Game.mouseS)){
			g.setColor(new Color(255,255,255,100));
			g.fillRect(x, y, width, height);
		}
		
		
	}
}
