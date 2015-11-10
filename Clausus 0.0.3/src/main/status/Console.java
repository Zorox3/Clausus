package main.status;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import main.Game;

public class Console {

	public static final Color CONSOLE_BACKGROUND = new Color(0, 0, 0, 150);
	public static final Color FONT_COLOR = new Color(255, 255, 255);
	public static final Font FONT = new Font("Franklin Gothic Demi", Font.BOLD,
			20 / Game.PIXEL_SIZE);
	
	private List<String> lastCommands = new ArrayList<>();

	private String command = "";

	private String pattern = "[a-zA-Z0-9\\s]*";

	private int height = 200;
	private int width = Game.WIDTH;

	private int commandOffset = 20;

	public void tick() {

	}

	public void render(Graphics g) {

		g.setFont(FONT);

		g.setColor(CONSOLE_BACKGROUND);
		g.fillRect(0, Game.HEIGHT - height, width, height);

		g.setColor(FONT_COLOR);
		g.drawString(">", 100, Game.HEIGHT - 20);

		g.drawString(command, 140, Game.HEIGHT - 20);

		int yOffset = commandOffset * 2;
		for (int i = lastCommands.size() - 1; i >= 0; i--) {
			g.drawString(lastCommands.get(i), 140, Game.HEIGHT - yOffset);
			yOffset += commandOffset;
		}

	}

	public void addCommand() {

		lastCommands.add(command);
		checkCommand(command);
		command = "";

		if (lastCommands.size() > 8) {
			lastCommands.remove(0);

			List<String> TempLastCommands = new ArrayList<>();

			for (String temp : lastCommands) {

				TempLastCommands.add(temp);
			}
			lastCommands.clear();
			lastCommands.addAll(TempLastCommands);
		}
	}

	private void checkCommand(String command) {

		String[] parts = command.split(" ");

		if (parts.length >= 2) {
			command = parts[0];
			String value = parts[1].trim();

			System.out.println(command + "" + value);

			switch (command) {
			case "seed":
				Long tempSeed = new Long(30);
				tempSeed = Long.parseUnsignedLong(value, 36);
				Game.preSeed = tempSeed;
				break;

			default:
				break;
			}
		}
	}
	
	

	public void add2Command(char key) {
		String k = String.valueOf(key);

		if (k.matches(pattern)) {
			command += key;
		}
	}

	public List<String> getCommands() {
		return lastCommands;
	}

}
