package starter;

import java.io.IOException;

import net.GameServer;
import chatnet.ChatServer;

/**
 * Starts both the GameServer and ChatServer in separate threads
 * 
 * @author Michael Tuer
 * 
 */
public class ServerStarter {
	public static int CHAT_PORT = 4002;
	public static int GAME_PORT = 4003;

	int gamePort;
	int chatPort;
	ServerThread chat;
	ServerThread game;

	/**
	 * Starts both servers
	 */
	public static void main(String[] args) {
		ServerStarter servers = new ServerStarter(CHAT_PORT, GAME_PORT);
		servers.runServers();

	}

	/**
	 * Constructor with ports to start on
	 * 
	 * @param chatPort
	 * @param gamePort
	 */
	public ServerStarter(int chatPort, int gamePort) {
		this.chatPort = chatPort;
		this.gamePort = gamePort;

		chat = new ServerThread(chatPort) {
			public void run() {
				try {
					ChatServer chatServ = new ChatServer(port);
					this.setChatServer(chatServ);
					chatServ.acceptClients();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				while (true)
					;
			}
		};

		game = new ServerThread(gamePort) {
			public void run() {
				try {
					GameServer gameServer = new GameServer(port, cs);
					gameServer.acceptClients();
					// gameServer.createTestGames();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while (true)
					;
			}
		};

	}

	/**
	 * Start both threads created in the constructor
	 */
	public void runServers() {
		System.out.println("Running servers");
		System.out.println("Start chat server...");
		chat.start();
		// while(chat.getChatServer() == null);
		long time = System.currentTimeMillis();
		while ((System.currentTimeMillis() - time) < 2000)
			;
		System.out.println("Start game server...");
		game.setChatServer(chat.getChatServer());
		game.start();

	}

}
