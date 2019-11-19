package leftfoot.server;

import java.net.ServerSocket;
import java.net.Socket;

import leftfoot.FoodBrowser;

public class FoodServer {

	private static int PORT = 14200;

	private FoodBrowser foodBrowser;

	private ServerSocket serverSocket;

	public FoodServer() {

		this.foodBrowser = FoodBrowser.LOAD("foodData");

	}

	public void Open() {

	try {

		//サーバー作成
		this.serverSocket = new ServerSocket(PORT);
		//this.serverSocket.bind(new InetSocketAddress(IP, PORT));

		//接続待ち
		System.out.println("server open: " + this.serverSocket.getLocalSocketAddress());
		Socket socket;
		while((socket = this.serverSocket.accept()) != null) {

			FoodThread foodThread = new FoodThread(socket, this.foodBrowser);
			foodThread.start();

		}

	} catch (Exception e) {
		e.printStackTrace();
	}

	}

}
