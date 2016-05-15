package main.gfx.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import main.Game;

public class GUI {

	private List<GUI_Element> elements;
	private Color bgColor;
	private int x;
	private int y;
	private GUI_Type type;

	private boolean centered = false;

	private boolean active = false;
	public int selected = 0;

	private String headline = "";

	private BufferedImage bgImage = null;

	public GUI(GUI_Type type, Color bgColor, List<GUI_Element> elements, int x,
			int y) {
		this.bgColor = bgColor;
		this.elements = elements;
		this.x = x;
		this.y = y;
		this.type = type;
	}
	public GUI(GUI_Type type, Color bgColor, List<GUI_Element> elements, int x,
			int y, int selected) {
		this.bgColor = bgColor;
		this.elements = elements;
		this.x = x;
		this.y = y;
		this.type = type;
		this.selected = selected;
	}

	public GUI_Type getType() {
		return type;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getHeadline() {
		return headline;
	}

	public void setBgImage(BufferedImage bgImage) {
		this.bgImage = bgImage;
	}

	public void setCentered(boolean b) {
		this.centered = b;
	}

	public void render(Graphics g) {
		if (bgImage == null) {
			g.setColor(bgColor);
			g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		} else {
			g.drawImage(bgImage, 0, 0, Game.WIDTH, Game.HEIGHT,
					null);
		}

		int startY = y;

		if (headline != "") {
			GUI_Element tempE = elements.get(0);
			g.setFont(new Font(tempE.font.getFontName(), tempE.font.getStyle(),
					tempE.font.getSize() * 2));
			g.setColor(tempE.textColor);
			g.drawString(headline, x - 100, y);
			startY += 50;
		}

		int i = 0;
		for (GUI_Element e : elements) {

			if (e.visible) {

				if (i == selected) {
					e.selected = true;
				} else {
					e.selected = false;
				}

				startY += e.font.getSize() + 30;

				if (centered) {
					int nx = x - (e.getName().length() * e.font.getSize()) / 2;
					e.render(g, nx, startY);
				} else {
					e.render(g, x, startY);
				}

			}
			i++;
		}
	}

	public void performAction() {
		Action action = elements.get(selected).getAction();
		Action.manageActions(action);
	}

	public void tick() {
		if (selected <= 0) {
			selected = 0;
		}
		if (selected >= elements.size() - 1) {
			selected = elements.size() - 1;
		}
	}

	public void setActive(boolean a) {
		this.active = a;
	}

	public boolean isActive() {
		return active;
	}

}
