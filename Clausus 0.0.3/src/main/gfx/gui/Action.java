package main.gfx.gui;

import java.awt.event.WindowEvent;

import main.Game;
import main.gfx.gui.menu.StaticMenues;

public enum Action {

	NONE, gameStart, gameOptions, gameExit, gamePause, gameContinue, toggelVsync, guiBack, showShadow, addTime, removeTime, switchTime;

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
			break; 
		case showShadow:
			Game.showShadow = Game.showShadow ? false : true;
			Game.switchGui(StaticMenues.gameOptions());
			break;
		case addTime:
			Game.sky.dayFrame+=1000;
			break;
		case removeTime:
			Game.sky.dayFrame-=1000;
			break;
		case switchTime:
			Game.sky.time = Game.sky.time == 1 ? 0 : 1;
			break;
		default:

		}

	}

}
