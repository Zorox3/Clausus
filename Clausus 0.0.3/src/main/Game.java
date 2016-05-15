package main;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import main.entities.ClientPlayer;
import main.entities.Mob;
import main.entities.Player;
import main.gfx.gui.GUI;
import main.gfx.gui.menu.StaticMenues;
import main.inventory.Inventory;
import main.level.BlockPhysic;
import main.level.Building;
import main.level.Level;
import main.level.Sky;
import main.level.Water;
import main.level.blocks.Tile;
import main.status.Console;
import main.status.GameInfo;
import main.status.Messages;
import net.client.Client;
import net.server.Server;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	// PRIVATE STATIC FINALS VARs
	private static final String TITLE = "Clausus";
	private static final String VERSION = "0.0.4";
	private static final String BUILD = "8f9b5c";
	public static final int PIXEL_SIZE = 1;

	// PUBLIC STATICS VARs
	public static JFrame frame;
	public static Dimension realSize;
	public static Dimension size = new Dimension(1280, 720);
	public static boolean isRunning = false;
	public static Level level;
	public static Player player;
	public static Inventory inventory;
	public static Sky sky;
	public static Console console;

	public static double dir = 0;
	public static boolean isMoving = false;
	public static boolean isJumping = false;
	public static boolean isLeftMousePressed = false;
	public static boolean isRightMousePressed = false;
	public static Point mouse = new Point(0, 0);
	public static Point mouseS;
	public static int sX = 0, sY = 0;
	public static int centerX, centerY;
	public static int winCenterX, winCenterY;
	public static ArrayList<Mob> mobs = new ArrayList<Mob>();
	public static boolean gameStart = false;
	public static Graphics g;
	public static Messages messages;
	public static GameInfo gi;
	public static BlockPhysic blockphysic;
	// PUBLIC VARs
	public static int ticks;
	public static int frames;
	public static int[] lastFrames = new int[15];
	public static Building building;
	public static Water water;

	public static boolean showConsole = false;

	public static Thread cThread;
	public static Thread lThread;
	public static Thread sThread;

	public static boolean gameinfo = true;
	public static boolean showShadow = false;
	public static String path = "";
	public static int updatesPerTick = 0;
	public static int totalUpdatesPerTick = 0;
	public static String lastError = "";

	public static GUI gui;
	public static GUI lastGui;

	public static int WIDTH;
	public static int HEIGHT;

	public static Long preSeed = 0L;

	public static Random globalRandom;

	// PRIVATE VARs
	public static boolean vsync = true;

	public static int debugRendering = 0;

	public static Game instance;

	public static boolean shadowDebug = false;

	public static Server server;

	public static Client client;

	public static boolean isServer;

	public static boolean isClient;

	public static ClientPlayer clientPlayer;

	public static void main(String args[]) {
		new Game();
	}

	public void setPath() {
		String propertiesFilePath = "";
		File propertiesFile = new File(propertiesFilePath);

		if (!propertiesFile.exists()) {
			try {
				CodeSource codeSource = Game.class.getProtectionDomain()
						.getCodeSource();
				File jarFile = new File(codeSource.getLocation().toURI()
						.getPath());
				String jarDir = jarFile.getParentFile().getPath();
				propertiesFile = new File(jarDir
						+ System.getProperty("file.separator")
						+ propertiesFilePath);
			} catch (Exception ex) {
				System.err.println("Hauptpfad nicht gefunden.");
			}
		}

		path = propertiesFile.getAbsolutePath();
	}

	public Game() {
		setPreferredSize(size);

		frame = new JFrame();
		// frame.setUndecorated(true);
		frame.setTitle(TITLE + " " + VERSION + ": Build: " + BUILD);
		frame.add(this);
		frame.pack();
		realSize = new Dimension(frame.getWidth(), frame.getHeight());
		// frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		// frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
		
		addKeyListener(new Listener());
		addMouseListener(new Listener());
		addMouseMotionListener(new Listener());
		addMouseWheelListener(new Listener());
		setPath();

		winCenterX = getWidth() / 2;
		winCenterY = getHeight() / 2;

		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		cThread = new Thread(this, "Main Thread");

		instance = this;
		start();
		
	}

	public void start() {
		isRunning = true;

		cThread.start();
		System.out.println("Thread Main gestartet");
	}

	public void stop() {
		isRunning = false;
	}

	// ####################
	// INIT
	// ####################
	public void init() {
		gui = StaticMenues.mainMenu();
		gui.setActive(true);
		messages = new Messages();

		console = new Console();

		requestFocus();
	}

	public static void startGame() {
		sThread = new Thread(new StartUp());

		sThread.start();
		System.out.println("Thread Startup gestartet");
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;
		double nsPerTickRender = 1000000000D / 60D;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		double deltaRender = 0;

		int ticks = 0;
		int frames = 0;
		int i = 0;

		this.init();

		while (isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			deltaRender += (now - lastTime) / nsPerTickRender;
			lastTime = now;

			boolean shouldRender = !vsync;

			if (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;

			}
			if (deltaRender >= 1) {
				deltaRender -= 1;
				shouldRender = true;
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 15) {
				secondTick(isMoving);
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				Game.frames = frames;
				Game.ticks = ticks;

				lastFrames[i++] = frames;

				if (i > lastFrames.length - 1) {
					i = 0;
				}

				frames = 0;
				ticks = 0;

				secondTick(true);
				
				setUpdatesPerTick();
				Game.updatesPerTick = 0;
			}

		}
	}

	public void secondTick(boolean isMoving) {

		if (isServer && gameStart) {

			if (isMoving) {
				client.addMessage("x", String.valueOf((int) player.x));
				client.addMessage("y", String.valueOf((int) player.y));
			}
			if (clientPlayer == null) {
				clientPlayer = new ClientPlayer(Tile.TILE_SIZE,
						Tile.TILE_SIZE * 2);
			} else {
				clientPlayer.x = Integer.valueOf(client.input.getData("x"));
				clientPlayer.y = Integer.valueOf(client.input.getData("y"));
			}

		} else if (isClient && gameStart) {

			if (client.isConnected()) {
				clientPlayer = new ClientPlayer(Tile.TILE_SIZE,
						Tile.TILE_SIZE * 2);

				clientPlayer.x = Integer.valueOf(client.input.getData("x"));
				clientPlayer.y = Integer.valueOf(client.input.getData("y"));
				
				if (isMoving) {
					client.addMessage("x", String.valueOf((int) player.x));
					client.addMessage("y", String.valueOf((int) player.y));
				}
			}

		}

		if (client != null) {
			if (client.input != null) {
				String updateString[] = client.input.getData("block")
						.split(" ");
				int updateInt[] = new int[5];
				int i = 0;
				for (String s : updateString) {
					updateInt[i] = Integer.valueOf(s);
					i++;
				}
				if (updateInt[0] != -1) {
					level.chunk.get(updateInt[0])[updateInt[1]][updateInt[2]].id = new int[] {
							updateInt[3], updateInt[4] };

				}
			}

			client.sendMessages();
		}
	}

	// ###############################################################################
	// ###############################################################################

	// ##################
	// GAME TICK UPDATE
	// ##################
	public void tick() {

		if (gui.isActive()) {
			gui.tick();
		}

		if (gameStart) {
			if (!Inventory.isOpen && !gui.isActive()) {
				level.tick();
				player.tick();

				building.tick();
				water.tick();
				blockphysic.tick();
				sky.tick();
				for (Mob m : mobs) {
					m.tick();
				}

				if (gameinfo) {

					gi.tick();
				}
			}
			centerX = (int) player.x - sX;
			centerY = (int) player.y - sY;
		}

		// if (frame.getWidth() != realSize.width
		// || frame.getHeight() != realSize.height) {
		// frame.pack();
		// }

		mouseS = new Point(mouse.x - sX, mouse.y - sY);

	}

	// ##################
	// GAME RENDER
	// ##################
	public void render() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		g = bs.getDrawGraphics();

		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHints(rh);

		if (gameStart) {
			sky.render(g);

			for (Mob m : mobs) {
				m.render(g);
			}

			if (!gui.isActive()) {
				player.render(g);
			}

			if (clientPlayer != null) {
				clientPlayer.render(g);
			}

			level.render(g);

			if (!gui.isActive()) {

				inventory.render(g);

				if (gameinfo) {

					gi.render(g);
				}
			}
		}

		messages.render(g);
		if (gui.isActive()) {
			gui.render(g);
		}

		if (showConsole) {
			Game.console.render(g);
		}
		g.dispose();
		bs.show();
	}

	public static void vSync(boolean toggle) {
		Game.vsync = toggle;
	}

	private void setUpdatesPerTick() {
		Game.totalUpdatesPerTick = Game.updatesPerTick;
	}

	public static void switchGui(GUI guiNew) {
		Game.gui.setActive(false);

		if (lastGui == null) {
			Game.lastGui = Game.gui;
		}
		if (guiNew.getType() != Game.gui.getType())
			Game.lastGui = Game.gui;

		Game.gui = guiNew;
		Game.gui.setActive(true);

		System.out.println("GUI: " + guiNew.getType().name());
	}

	public static long getSeed() {
		if (preSeed > 0L) {
			return preSeed;
		}
		return Level.levelSeed;
	}

}
