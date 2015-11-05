package main.server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import main.Game;
import main.level.Level;
import main.level.blocks.Block;

public class Server implements Runnable {

	private int _port;
	private ServerSocket server;
	private Socket socket;

	private boolean levelSend = false;
	private int errors = 0;

	public Server(int port) {
		this._port = port;

		try {
			server = new ServerSocket(port);
			System.err.println("Server gestartet");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static final int dataAtOnceDefault = 2;
	private static int dataAtOnce = 1;
	public static void setDataAtOnce(int x){
		dataAtOnce = x;
	}

	public boolean sendMessage() {

		DataOutputStream dOut;
		try {
			if (socket != null && packets.size() > 0) {
				dOut = new DataOutputStream(socket.getOutputStream());

				int counter = 0;
				String g = "";
				for (String message : new ArrayList<>(packets)) {
					
					
					if(counter >= dataAtOnce){
						dOut.writeUTF(g);
						break;
					}
					
					counter++;
					packets.remove(message);
					g += message;
				}
				
				Thread.sleep(10);
				

				return true;
			}

		} catch (IOException | InterruptedException e) {
			System.out.println("Send Error");
			errors++;
			if (errors >= 5) {
				Game.isServer = false;
			}
		}

		return false;

	}

	@Override
	public void run() {
		try {
			socket = server.accept();
			socket.setTcpNoDelay(true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (Game.isServer) {

				sendMessage();
	
				if (!levelSend) {
				// sendLevel();
				}

				
		}
	}

	public void sendLevel() {

		if (Game.level.chunk != null) {

			Level level = Game.level;

			addPacket("start");
			for (int i = Game.player.playerChunk == 0 ? 0
					: Game.player.playerChunk - 1; i < (Game.player.playerChunk == 0 ? 0
					: Game.player.playerChunk - 1)
					+ Level.CHUNKS_RENDERED; i++) {
				for (int x = 0; x < level.lWidth; x++) {
					
					for (int y = 0; y < level.lHeight; y++) {
						Block b = level.chunk.get(i)[x][y];

						setDataAtOnce(16);
						addPacket(Game.intArrayToString(new int[] {b.id[0], b.id[1], b.x, b.y}));

					}
				}
			}
			addPacket("stop");
			System.err.println("Finsihed Loading");
			levelSend = true;
		}
	}

	private static List<String> packets = new ArrayList<>();
	
	public static void addPacket(String b) {

		packets.add(b);

	}
}
