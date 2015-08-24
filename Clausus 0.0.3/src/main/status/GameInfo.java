package main.status;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import main.Game;
import main.gfx.TextRenderer;
import main.level.Level;
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
		
		info.add("Shadow: " + (Game.showShadow ? "On" : "Off") + " | Darkness: " + Game.sky.getDarkness());
		
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

		if (Game.lastError != "")
			info.add("Last Error: " + Game.lastError);
		else
			info.add("");
	}

	public void render(Graphics g) {

		int c = 0;
		Color color;
		for (String s : info) {

			if (c == info.size() - 1) {
				color = warningColor;
			} else {
				color = normalColor;
			}

			g.setFont(new Font("Franklin Gothic Demi", Font.BOLD, 20 / Game.PIXEL_SIZE));
			
			TextRenderer.writeText(g, s, color, Game.centerX
					- (Game.size.width / Game.PIXEL_SIZE) / 2 + 20,
					Game.centerY - (Game.size.height / Game.PIXEL_SIZE) / 2
							+ 50 + (c * (20 / Game.PIXEL_SIZE) + 5));

			c++;
		}
	}
}
