package main.gfx.gui.menu;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import main.Game;
import main.gfx.gui.Action;
import main.gfx.gui.GUI;
import main.gfx.gui.GUI_Element;
import main.gfx.gui.GUI_Type;
import main.level.blocks.Tile;

public class StaticMenues {

	public static GUI mainMenu() {

		List<GUI_Element> elements = new ArrayList<>();

		Color textColor = Color.WHITE;
		Font font = new Font("Franklin Gothic Demi", Font.BOLD, 40 / Game.PIXEL_SIZE);

		elements.add(new GUI_Element("Start", font, textColor, Action.gameStart));
		elements.add(new GUI_Element("Start as Server", font, textColor, Action.gameStartServer));
		elements.add(new GUI_Element("Connect...", font, textColor, Action.gameStartConnect));
		elements.add(new GUI_Element("Options", font, textColor,
				Action.gameOptions));
		elements.add(new GUI_Element("Exit", font, textColor, Action.gameExit));

		GUI gui = new GUI(GUI_Type.mainMenu, Color.RED, elements, Game.pixel.width / 2 - 130, 100);

		gui.setHeadline("CLAUSUS");
		try {
			gui.setBgImage(ImageIO.read(Tile.class
					.getResource("/menuBgImage.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//gui.setCentered(true);

		return gui;
	}

	public static GUI pauseMenu() {

		List<GUI_Element> elements = new ArrayList<>();

		Color textColor = Color.WHITE;
		Font font = new Font("Franklin Gothic Demi", Font.BOLD, 40 / Game.PIXEL_SIZE);

		elements.add(new GUI_Element("Continue", font, textColor,
				Action.gameContinue));
		elements.add(new GUI_Element("Options", font, textColor,
				Action.gameOptions));
		elements.add(new GUI_Element("Exit", font, textColor, Action.gameExit));

		GUI gui = new GUI(GUI_Type.pauseMenu, new Color(0,0,0,180), elements, 100, 100);

		gui.setHeadline("Pause");
		
		return gui;
	}

	public static GUI gameOptions() {

		List<GUI_Element> elements = new ArrayList<>();

		Color textColor = Color.BLACK;
		Font font = new Font("Franklin Gothic Demi", Font.BOLD, 40 / Game.PIXEL_SIZE);

		elements.add(new GUI_Element("VSync: " + (Game.vsync ? "On" : "Off"),
				font, textColor, Action.toggelVsync));
		elements.add(new GUI_Element("Option 2", font, textColor, Action.NONE));
		elements.add(new GUI_Element("Option 3", font, textColor, Action.NONE));
		elements.add(new GUI_Element("<- Back", font, textColor, Action.guiBack));

		GUI gui = new GUI(GUI_Type.optionMenu, Color.RED, elements, 100, 100);

		gui.setHeadline("Options");
		
		return gui;
	}

}
