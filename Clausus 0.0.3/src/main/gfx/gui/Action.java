package main.gfx.gui;

import java.awt.event.WindowEvent;

import main.Game;
import main.gfx.gui.menu.StaticMenues;

public enum Action {

	NONE, gameStart, gameOptions, gameExit, gamePause, gameContinue, toggelVsync, guiBack;

	public static void manageActions(Action a) {

		switch (a) {

		case gameStart:
			if (!Game.gameStart) {
				Game.startGame();
			}
			Game.gui.setActive(false);
			break;
		case gameOptions:
			Game.switchGui(StaticMenues.gameOptions());
			break;
		case gameExit:
			Game.game.stop();
			Game.frame.dispatchEvent(new WindowEvent(Game.frame,
					WindowEvent.WINDOW_CLOSING));
			break;
		case gamePause:
			
			break;
		case gameContinue:
			Game.gui.setActive(false);
			break;
		case guiBack:
			Game.switchGui(Game.lastGui);
			break;
		case toggelVsync:
			Game.vSync(Game.vsync ? false : true);
			Game.switchGui(StaticMenues.gameOptions());
		default:

		}

	}

}
