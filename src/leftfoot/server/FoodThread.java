package leftfoot.server;

import java.io.BufferedReader;
import java.net.Socket;

import org.python.jline.internal.InputStreamReader;

import leftfoot.FoodBrowser;
import leftfoot.FoodData;

public class FoodThread extends Thread {

	private Socket socket;

	private FoodBrowser foodBrowser;

	public FoodThread(Socket socket, FoodBrowser foodBrowser) {

		this.socket = socket;
		this.foodBrowser = foodBrowser;

	}

	@Override
	public void run() {

		try {

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			String text;

			System.out.println("\topen socket(" + this.getId() + "): " + this.socket.getInetAddress());

			//受信待ち
			while((text = bufferedReader.readLine()) != null) {

				System.out.println("\treceived(" + this.getId() + "): " + text);
				try {
					
					//検索
					int id = Integer.parseInt(text);
					FoodData foodData = this.foodBrowser.search(id);
					//表示
					System.out.println(foodData);

				}catch (Exception e) {
					e.printStackTrace();
				}

			}

			this.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void disconnect() {

		try {

			this.socket.close();
			System.out.println("\tclose(" + this.getId() + ")");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
