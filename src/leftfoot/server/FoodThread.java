package leftfoot.server;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

			ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			String text;

			System.out.println("\topen socket(" + this.getId() + "): " + this.socket.getInetAddress());

			//受信待ち
			while((text = (String)objectInputStream.readObject()) != null) {

				System.out.println("\treceived(" + this.getId() + "): " + text);	//受信メッセージ
				try {

					//検索
					int id = Integer.parseInt(text);
					FoodData foodData = this.foodBrowser.search(id);
					//表示
					System.out.println(foodData.toString());

					//出力ストリーム作成
					ObjectOutputStream objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
					objectOutputStream.writeObject((Object)foodData);
					objectOutputStream.flush();

					System.out.println("\treturned(" + this.getId() + ")");

				}catch (Exception e) {
					e.printStackTrace();
				}

			}

			bufferedReader.close();
			this.disconnect();

		}catch (EOFException e){
			System.out.println("\tread eof (" + this.getId() + ")");
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
