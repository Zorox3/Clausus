package main.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

import main.Game;
import main.level.blocks.Block;

public class Client implements Runnable {

	private int _port;

	public Client(int port) {
		this._port = port;
	}

	@Override
	public void run() {

		Socket socket = null;
		try {

			socket = new Socket("localhost", this._port);
			System.out.println("Erwarte Daten");

			while (Game.isClient) {
				
				if (socket != null) {
					DataInputStream dIn = new DataInputStream(
							socket.getInputStream());
					
					if (dIn.available() != 0) {

						
								String message = "";
								
								message = dIn.readUTF();
								
								
								
								System.err.println(message);
								//System.err.println();
							}
							
			
							

						}
					}
			
			socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public List<Block[][]> getLevel(){
		
		
		
		
		return null;
	}
	
	
}
