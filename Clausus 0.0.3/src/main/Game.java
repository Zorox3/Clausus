package main;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import main.entities.Mob;
import main.entities.Player;
import main.gfx.ShadowRenderer;
import main.gfx.gui.GUI;
import main.gfx.gui.menu.StaticMenues;
import main.inventory.Inventory;
import main.level.BlockPhysic;
import main.level.Building;
import main.level.Level;
import main.level.Sky;
import main.level.Water;
import main.status.GameInfo;
import main.status.Messages;

public class Game extends Applet implements Runnable {

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
	public static Dimension pixel;
	public static Level level;
	public static Player player;
	public static Inventory inventory;
	public static Sky sky;

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
	public static int[] lastFrames = new int[20];
	public static Building building;
	public static Water water;
	public static ShadowRenderer shadow;

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

	
	public static Random globalRandom = new Random(getSeed());
	
	// PRIVATE VARs
	public static Image screen;
	public static boolean vsync = true;

	public static int debugRendering = 0;

	public static Game game;

	public static boolean shadowDebug = false;

	public static void main(String args[]) {
		game = new Game();

		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setTitle(TITLE + " " + VERSION + ": Build: " + BUILD);
		frame.add(game);
		frame.pack();
		realSize = new Dimension(frame.getWidth(), frame.getHeight());
		// frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		// frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		pixel = new Dimension(frame.getWidth() / PIXEL_SIZE, frame.getHeight()
				/ PIXEL_SIZE);
		winCenterX = pixel.width / 2;
		winCenterY = pixel.height / 2;

		game.start();
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

		addKeyListener(new Listener());
		addMouseListener(new Listener());
		addMouseMotionListener(new Listener());
		addMouseWheelListener(new Listener());
		setPath();

		cThread = new Thread(this, "Main Thread");

	}

	@Override
	public void start() {
		isRunning = true;

		cThread.start();
		System.out.println("Thread Main gestartet");
	}

	@Override
	public void stop() {
		isRunning = false;
	}

	// ####################
	// INIT
	// ####################
	@Override
	public void init() {
		screen = createVolatileImage(pixel.width, pixel.height);

		gui = StaticMenues.mainMenu();
		gui.setActive(true);
		messages = new Messages();
		// messages.setMessageType(MessageTypes.START);

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
		double nsPerTickRender = 1000000000D / 30D;
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

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				Game.frames = frames;
				Game.ticks = ticks;

				lastFrames[i++] = frames;

				if(i > lastFrames.length-1){
					i = 0;
				}
				
				
				frames = 0;
				ticks = 0;

				setUpdatesPerTick();
				Game.updatesPerTick = 0;
			}

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
		g = screen.getGraphics();

		if (gameStart) {
			sky.render(g);

			for (Mob m : mobs) {
				m.render(g);
			}

			if (!gui.isActive()) {
				player.render(g);
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

		g = getGraphics();
		g.drawImage(screen, 0, 0, size.width, size.height, 0, 0, pixel.width,
				pixel.height, null);
		g.dispose();
	}

	public static void vSync(boolean toggle) {
		Game.vsync = toggle;
	}

	private void setUpdatesPerTick() {
		Game.totalUpdatesPerTick = Game.updatesPerTick;
	}

	public static void switchGui(GUI guiNew) {
		Game.gui.setActive(false);
		boolean temp = false;

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
		return Level.levelSeed;
	}

}
