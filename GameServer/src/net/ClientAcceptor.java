package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Thread to listen for join requests
 * 
 * @author Michael Tuer
 * 
 */
public class ClientAcceptor extends Thread {
	ServerSocket listener;
	GameServer gameServer;

	public ClientAcceptor(GameServer gs, ServerSocket ss) {
		listener = ss;
		this.gameServer = gs;
	}

	/**
	 * Thread loop
	 */
	public void run() {

		while (true) {
			try {
				System.out.println("Waiting...");
				Socket s = listener.accept();
				new ServerConnection(gameServer, s).start();

			} catch (IOException e) {

				e.printStackTrace();
				return;
			}
		}
	}
}
