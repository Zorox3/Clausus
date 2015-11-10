package main;

import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import main.entities.Player;
import main.inventory.Inventory;
import main.level.BlockPhysic;
import main.level.Building;
import main.level.Level;
import main.level.Sky;
import main.level.Water;
import main.level.blocks.Tile;
import main.level.worldGeneration.WorldGeneration;
import main.status.GameInfo;
import main.status.MessageTypes;

public class StartUp implements Runnable {

	@Override
	public void run() {
		startGame();
	}

	public static void startGame() {
		try {
			
			Game.globalRandom = new Random(Game.getSeed());
			
			
			
			// Vorbereitung der Nachrichtenausgaben
			Game.messages.setMessageType(MessageTypes.CUSTOM_IMAGED_CENTERED);

			// Texturen laden
			Game.messages.setCustomText("Loading...Texturen werden geladen");
			
			
			try {
				Game.messages.setCustomImage(ImageIO.read(Tile.class
						.getResource("/loadingImage.jpg")));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			
			new Tile();
			Thread.sleep(100);

			// Himmel erstellen
			Game.messages.setCustomText("Loading...Himmel wird erschaffen");
			Game.sky = new Sky();
			Thread.sleep(100);

			// Level-Klasse erstellen
			Game.messages.setCustomText("Loading...Welt wird erstellt");
			Game.level = new Level();
			Game.level.init();
			Game.level.wg = new WorldGeneration();
			// BlockPhysic-Klasse erstellen
			Game.blockphysic = new BlockPhysic(Game.level);
			// Wasser-Klasse erstellen
			Game.water = new Water(Game.level);
			// Building-Klasse erstellen -- Abbauen/Bauen von Blöcken
			Game.building = new Building();
			Thread.sleep(100);

			// Level Thread
			Game.lThread = new Thread(Game.level, "Level Thread");
			Game.lThread.start();

			System.out.println("Spiel  wird erstellt");

			// Laden der Chunks -- siehe Level.startGeneration()

			// Game.level.startGeneration();
			int chunks = 0;
			
			
			while(chunks < Game.level.maxChunks) {
				chunks = Game.level.generateChunk();
				double prozent = Math.round((double)chunks/(double)Game.level.maxChunks * 100D);
				Game.messages.setCustomText("Loading...Welt wird berechnet "
						+ prozent + "%");
			}


					
			// Inventar vorbereiten und erstellen
			Game.inventory = new Inventory();

			// Spielerfigur platziereen und initalisieren
			Game.player = new Player(Tile.TILE_SIZE, Tile.TILE_SIZE * 2);
			
			//Game.mobs.add(new Mob((int)Game.player.positionX, (int)Game.player.positionY, Tile.TILE_SIZE, Tile.TILE_SIZE * 2, Tile.mob1_left, Tile.mob1_right));
			

			//Game.shadow = new ShadowRenderer(Game.centerX, Game.centerY,Game.pixel.width/Game.PIXEL_SIZE, Game.pixel.height/Game.PIXEL_SIZE, 30);
				
			
			
			System.out.println("Spiel erstellt");

			Game.messages.setCustomText("Spiel wird gestartet");
			Thread.sleep(300);

			
			
			Game.gi = new GameInfo();
			
			
			
			// Rendern und Updates des Hauptspiels starten
			Game.gameStart = true;

			
			// Nachrichten ausstellen
			Game.messages.setMessageType(MessageTypes.NONE);

			
			
			
			// EXCEPTION  handling fÃ¼r die Thread.sleep()
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

}
