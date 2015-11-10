package main.status;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.Game;
import main.gfx.TextRenderer;
import main.level.Level;
import main.level.Sky;
import main.level.blocks.Tile;

public class GameInfo {

	private List<String> info = new ArrayList<>();

	private static final Color normalColor = Color.YELLOW;
	private static final Color warningColor = Color.RED;

	public void tick() {

		info.clear();

		info.add(Game.frames + " FPS - " + Game.ticks + " UPS");

		info.add("Position: (" + Game.player.positionX + ", "
				+ Game.player.positionY + ") Chunk: " + Game.player.playerChunk
				+ " (" + Game.level.getBiomName() + ")");

		info.add("Chunks: (R: " + Level.CHUNKS_RENDERED + ")"
				+ Game.level.chunk.size());

		info.add("Updates pro Tick: " + Game.totalUpdatesPerTick);

		info.add("VSync: " + (Game.vsync ? "On" : "Off"));

		info.add("Shadow: " + (Game.showShadow ? "On" : "Off")
				+ " | Darkness: " + Game.sky.getDarkness() + " (" + Game.sky.getDayTimeName() + ")");

		info.add("Time: " + Game.sky.dayFrame + " / " + Game.sky.dayTime);

		String db = "";
		switch (Game.debugRendering) {
		case 1:
			db = "Block";
			break;
		case 2:
			db = "Chunk";
			break;
		default:
			db = "Off";

		}

		info.add("Debug Rendering: " + db);

		
		info.add("Seed: " + Game.getSeed());
		
		
		
		if (Game.lastError != "")
			info.add("Last Error: " + Game.lastError);
		else
			info.add("");
	}

	public void render(Graphics g) {

		int c = 0;
		Color color = Color.BLACK;
		for (String s : info) {

			if (c == info.size() - 1) {
				color = warningColor;
			} else {
				color = normalColor;
			}

			g.setFont(new Font("Franklin Gothic Demi", Font.BOLD,
					20 / Game.PIXEL_SIZE));

			TextRenderer.writeText(g, s, color, Game.centerX
					- (Game.size.width / Game.PIXEL_SIZE) / 2 + 20,
					Game.centerY - (Game.size.height / Game.PIXEL_SIZE) / 2
							+ 50 + (c * (20 / Game.PIXEL_SIZE) + 5));

			c++;
		}
		int nx = 20;
		g.setColor(new Color(0,0,0,100));
		g.fillRect(0, Game.pixel.height - 100, 150, 100);
		
		
		for (int f : Game.lastFrames) {
			
			if(f*2 > 60){
				if(f*2 >= 255){
					f = 255/2 - 1;
				}
				color = new Color(0, f*2, 0);
			}else if(f*2 < 60){
				color = new Color(f*2, f*2, 0);
			}
			nx += 5;
			
			g.setColor(color);
			g.fillRect(nx, Game.pixel.height-f*2, 4, f*2);
		}

	}
}
