package main.gfx.gui;

import java.awt.event.WindowEvent;
import java.io.IOException;

import net.client.Client;
import net.server.Server;
import main.Game;
import main.gfx.gui.menu.StaticMenues;
import main.level.Level;

public enum Action {

	NONE, gameStart, gameOptions, gameExit, gamePause, gameContinue, toggelVsync, guiBack, showShadow, addTime, removeTime, switchTime, showConsole, inputSeed, setVsync, setShadow, serverStart, clientStart, maxChunks;

	public static void manageActions(Action a, Object o) {
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
			Game.instance.stop();
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
		case setVsync:
				Game.vSync(Boolean.valueOf((String)o));
			break;
		case setShadow:
				Game.showShadow = Boolean.valueOf((String)o);
			break;
		case showShadow:
			Game.showShadow = Game.showShadow ? false : true;
			Game.switchGui(StaticMenues.gameOptions());
			break;
		case addTime:
			Game.sky.dayFrame += 1000;
			break;
		case removeTime:
			Game.sky.dayFrame -= 1000;
			break;
		case switchTime:
			Game.sky.time = Game.sky.time == 1 ? 0 : 1;
			break;
		case showConsole:
			Game.showConsole = Game.showConsole ? false : true;
			break;
		case inputSeed:
			Long tempSeed = Long.parseLong((String) o, 36);
			Game.preSeed = tempSeed;
			break;
		case serverStart:
			Game.isServer = true;
			try {
				Game.server = new Server(7777);
				Game.client  = new Client(1, "localhost", 7777);
				Game.server.start();
				Game.client.start();
				Action.manageActions(gameStart);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case clientStart:
			Game.isClient = true;
			try {
				Game.client = new Client(2, (String)o, 7777);
				Game.client.start();
				while(!Game.client.isConnected()){
					System.out.println("T");
				}				
				Action.manageActions(gameStart);
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case maxChunks:
			Level.maxChunks = Integer.valueOf((String) o);
		default:

		}
	}

	public static void manageActions(Action a) {

		manageActions(a, null);

	}

}
