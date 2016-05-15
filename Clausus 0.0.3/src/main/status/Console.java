package main.status;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import main.Game;
import main.gfx.gui.Action;

public class Console {

	public static final Color CONSOLE_BACKGROUND = new Color(0, 0, 0, 150);
	public static final Color FONT_COLOR = new Color(255, 255, 255);
	public static final Font FONT = new Font("Franklin Gothic Demi", Font.BOLD,
			20 / Game.PIXEL_SIZE);

	private List<Command> lastCommands = new ArrayList<>();

	private String fullInput = "";

	private String inputCommand = "";
	private String inputValue = "";

	private String pattern = "[a-zA-Z0-9\\s_.]*";

	private int height = 200;
	private int width = Game.WIDTH;

	private int commandOffset = 20;

	private List<Command> commandList = new ArrayList<>();

	public Console() {
		commandList.add(new Command("Command not Found! ", Action.NONE));

		commandList.add(new Command("seed", Action.inputSeed));
		Command temp = new Command("exit", Action.gameExit);
		temp.setNoValue(true);
		commandList.add(temp);
		
		temp = new Command("start", Action.gameStart);
		temp.setNoValue(true);
		commandList.add(temp);
		
		temp = new Command("vsync", Action.setVsync);
		commandList.add(temp);
		
		temp = new Command("shadow", Action.setShadow);
		commandList.add(temp);
		
		temp = new Command("server", Action.serverStart);
		temp.setNoValue(true);
		commandList.add(temp);
		
		temp = new Command("preLoadedChunks", Action.maxChunks);
		commandList.add(temp);
		
		commandList.add(new Command("client", Action.clientStart));

	}

	public void tick() {

	}

	public void render(Graphics g) {

		g.setFont(FONT);

		g.setColor(CONSOLE_BACKGROUND);
		g.fillRect(0, Game.HEIGHT - height, width, height);

		g.setColor(FONT_COLOR);
		g.drawString(">", 100, Game.HEIGHT - 20);

		g.drawString(fullInput, 120, Game.HEIGHT - 20);

		int yOffset = commandOffset * 2;
		for (int i = lastCommands.size() - 1; i >= 0; i--) {
			g.setColor(lastCommands.get(i).getTextColor());
			g.drawString("-> " + lastCommands.get(i).getFullCommand(), 100,
					Game.HEIGHT - yOffset);
			yOffset += commandOffset;
		}

	}

	public void addCommand() {

		parseInput();

		Command c = checkCommand(inputCommand);

		if (c != null) {

			Command newCommand = new Command(inputCommand, inputValue,
					c.getAction());
			newCommand.performAction();
			lastCommands.add(newCommand);

		} else {
			Command invCommand = new Command(commandList.get(0).getCommand(),
					fullInput, commandList.get(0).getAction());
			invCommand.setTextColor(Command.ERROR);
			lastCommands.add(invCommand);

			System.err.println("No Command Found: " + fullInput);
		}

		fullInput = "";

		if (lastCommands.size() > 8) {
			lastCommands.remove(0);

			List<Command> TempLastCommands = new ArrayList<>();

			for (Command temp : lastCommands) {

				TempLastCommands.add(temp);
			}
			lastCommands.clear();
			lastCommands.addAll(TempLastCommands);
		}
	}

	private void parseInput() {
		String[] parts = fullInput.split(" ");

		inputCommand = parts[0].trim();
		if (parts.length >= 2)
			inputValue = parts[1].trim();

	}

	private Command checkCommand(String command) {
		for (Command c : commandList) {
			if (c.getCommand().hashCode() == command.hashCode()) {
				return c;
			}
		}
		return null;
	}

	// private void checkCommand(String command) {
	//
	// String fullCommand = command;
	//
	// String[] parts = command.split(" ");
	//
	// if (parts.length >= 2) {
	// command = parts[0];
	// String value = parts[1].trim();
	//
	// System.out.println(command + "" + value);
	//
	// switch (command) {
	// case "seed":
	//
	// Long tempSeed = Long.parseUnsignedLong(value, 36);
	// Game.preSeed = tempSeed;
	// break;
	//
	// default:
	// this.command = "Invalid command: " + fullCommand;
	// break;
	// }
	// } else {
	// this.command = "Invalid command: " + fullCommand;
	// }
	// }

	public void add2Command(char key) {
		String k = String.valueOf(key);

		if (k.matches(pattern)) {
			fullInput += key;
		}
	}

	public List<Command> getCommands() {
		return lastCommands;
	}

	public void removeLast() {
		if (fullInput.length() > 0) {
			fullInput = fullInput.substring(0, fullInput.length() - 1);
		}
	}

}
