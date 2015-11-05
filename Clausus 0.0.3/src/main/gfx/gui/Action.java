package main.gfx.gui;

import java.awt.event.WindowEvent;

import main.Game;
import main.client.Client;
import main.gfx.gui.menu.StaticMenues;
import main.server.Server;

public enum Action {

	NONE, gameStart, gameStartServer, gameStartConnect, gameOptions, gameExit, gamePause, gameContinue, toggelVsync, guiBack;

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
		case gameStartConnect:
			Game.client = new Client(2222);
			Game.isClient = true;
			Game.clientThread = new Thread(Game.client, "Client Thread");
			Game.clientThread.start();
			
			Game.gui.setActive(false);

			//Action.manageActions(Action.gameStart);
			break;
		case gameStartServer:
			Game.server = new Server(2222);
			Game.isServer = true;
			Game.serverThread = new Thread(Game.server, "Server Thread");
			Game.serverThread.start();
			
			Action.manageActions(Action.gameStart);
			break;
		default:

		}

	}

}
