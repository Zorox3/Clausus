package main.level;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class Sky {
	public int night = 0;
	public int day = 1;
	public int time = day;

	public int r1 = 70, g1 = 120, b1 = 230; // Tag
	public int r2 = 15, g2 = 15, b2 = 80; // Nacht

	public int r = r1, g = g1, b = b1;

	public int darknessFrom = 30;
	public int darknessTo = 200;
	public int darkness = 30;
	
	public int maxDarknessFrom = 15;
	public int maxDarknessTo = 25;
	public int maxDarkness = 15;
	
	public int dayFrame = 0, dayTime = 12000;
	public int changeFrame = 0, changeTime = 32;
	
	public static final String DAY = "Tag";
	public static final String NIGHT = "Nacht";

	public static final String[] NAMES = {NIGHT, DAY};
	
	public Sky() {
		if (day == time) {
			r = r1;
			g = g1;
			b = b1;
		} else if (night == time) {
			r = r2;
			g = g2;
			b = b2;
		}
	}

	public int getDarkness() {
		return darkness;
	}
	public int getMaxDarkness() {
		return maxDarkness;
	}
	public void tick() {
		if (dayFrame >= dayTime) {
			if (time == day) {
				time = night;
			} else if (time == night) {
				time = day;
			}

			dayFrame = 0;
		} else {
			dayFrame++;
		}

		if (changeFrame >= changeTime) {	
			if (time == day) {
				if (r < r1) {
					r++;
				}
				if (g < g1) {
					g++;
				}
				if (b < b1) {
					b++;
				}
				if (darkness > darknessFrom) {
					darkness--;
				}
				if (maxDarkness > maxDarknessFrom) {
					maxDarkness--;
				}
			} else if (time == night) {
				if (r > r2) {
					r--;
				}
				if (g > g2) {
					g--;
				}
				if (b > b2) {
					b--;
				}
				if (darkness < darknessTo) {
					darkness++;
				}
				
				if (maxDarkness < maxDarknessTo) {
					maxDarkness++;
				}
			}
			changeFrame = 0;
		} else {
			changeFrame++;
		}
	}

	public void render(Graphics gr) {
		gr.setColor(new Color(r, g, b));
		gr.fillRect(0, 0, Game.pixel.width, Game.pixel.height);
	}
	
	public  String getDayTimeName(){
		return NAMES[time];
	}
}
