package main.gfx.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class GUI_Element {

	private String name;
	public Color textColor;
	private Action action;
	public boolean selected = false;
	public boolean visible = true;
	public Font font;

	public GUI_Element(String name, Font font, Color textColor, Action action) {
		this.name = name;
		this.textColor = textColor;
		this.action = action;
		this.font = font;
	}

	public String getName() {
		return name;
	}

	public void render(Graphics g, int x, int y) {

		g.setColor(textColor);
		if (selected) {
			g.fillOval(x - 30, y - font.getSize() / 2 - 5, font.getSize() / 2,
					font.getSize() / 2);
		}
		g.setFont(font);
		g.drawString(name, x, y);

	}

	public Action getAction() {
		return action;
	}

}
