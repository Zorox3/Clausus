package main.gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class TextRenderer {
	public static void writeText(Graphics g, String text, Color color, int x,
			int y) {
		g.setColor(color);
		g.drawString(text, x, y);
	}

	public static void writeTextBG(Graphics g, String text, Color color, int x,
			int y, Color bgColor) {
		g.setColor(bgColor);
		g.fillRect(0, 0, Game.pixel.width, Game.pixel.height);

		writeText(g, text, color, x, y);
	}

	public static void writeTextBGCentered(Graphics g, String text,
			Color color, Color bgColor) {

		writeTextBG(g, text, color,
				Game.winCenterX - ((text.length() / 2) * 5),
				Game.winCenterY - 3, bgColor);

	}

	public static void writeTextBGImageCentered(Graphics g, String text,
			Color color, Color bgColor, BufferedImage image) {

		g.drawImage(image, 0, 0, Game.pixel.width, Game.pixel.height, null);
		
		writeText(g, text, color, Game.winCenterX - ((text.length() / 2) * 5),
				Game.winCenterY - 3);

	}
}
