package leftfoot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.python.core.PyDictionary;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class FoodBrowser {

	public List<FoodData> foodDatas;

	private FoodBrowser(List<FoodData> foodDatas) {
		this.foodDatas = foodDatas;
	}

	public static FoodBrowser LOAD(String path) {

		//データファイルのディレクトリフルパス
		String absPath = new File(path).getAbsolutePath();

		//辞書オブジェクト
		List<Map<String, String>> foodDictionaries = FoodBrowser.LOADMAP(absPath);

		//FoodDataインスタンス生成
		List<FoodData> foodDatas = new ArrayList<>();
		for (Map<String, String> map : foodDictionaries) {
			foodDatas.add(new FoodData(map));
		}

		return new FoodBrowser(foodDatas);	//実装の際は削除

	}

	private static List<Map<String, String>> LOADMAP(String path){

		//出力
		List<Map<String, String>> foodDicts = new ArrayList<>();

		//インタプリタ設定
		Properties properties = new Properties();
		properties.put("python.console.encoding", "UTF-8");
		properties.put("python.path", "./python_sourse");		//pyソースコードディレクトリ

		//インタプリタ初期化
		PythonInterpreter.initialize(System.getProperties(), properties, new String[0]);

		//Python受け渡し
		PyObject[] pyFoodDicts;

		//インタプリタ起動
		try(PythonInterpreter interpreter = new PythonInterpreter()) {
			//Jythonここから
			interpreter.exec("import loadJson as lj");
			interpreter.set("path", path);
			interpreter.exec("foodDicts = lj.loadFoodData(path)");

			pyFoodDicts = ((PyList)interpreter.get("foodDicts")).getArray();
		}

		//キャスト(Map<PyObject, PyObject> -> Map<String, String>)
		for (PyObject pyObject : pyFoodDicts) {
			//変換
			PyDictionary pyDict = (PyDictionary)pyObject;
			Map<PyObject, PyObject> pyMap = pyDict.getMap();
			Map<String, String> strMap = new HashMap<>();
			for (PyObject keyObj : pyMap.keySet()) {
				strMap.put(((PyString)keyObj).asString(), ((PyString)pyMap.get(keyObj)).asString());
			}
			foodDicts.add(strMap);
		}

		return foodDicts;

	}

	public FoodData search(int id) {

		for (FoodData foodData : this.foodDatas) {
			if(foodData.productid == id) {
				return foodData;
			}
		}

		//ダミー
		return FoodData.DUMMY;

	}

	public void createQR(int matrixSize, Point imageSize, String savePath) {

		for (FoodData foodData : this.foodDatas) {
			BufferedImage qr;
			if((qr = foodData.createQR(matrixSize)) != null) {
				try {
					//リサイズ
					BufferedImage resizedQR = new BufferedImage((int)imageSize.getX(), (int)imageSize.getY() + 32, qr.getType());
					Graphics2D graphics2d = resizedQR.createGraphics();
					//白Fill
					graphics2d.setColor(Color.WHITE);
					graphics2d.fillRect(0, 0, resizedQR.getWidth(), resizedQR.getHeight());
					//QR表示
					graphics2d.drawImage(qr, 0, 0, (int)imageSize.getX(), (int)imageSize.getY(), null);
					//ラベル表示
					graphics2d.setFont(new Font("メイリオ", Font.BOLD, 24));
					graphics2d.setColor(Color.black);
					graphics2d.drawString(foodData.productName, 0, resizedQR.getHeight() - 1);
					//破棄
					graphics2d.dispose();

					//保存ファイルパス
					String ImagePath = new File(savePath).getAbsolutePath() + "\\" + foodData.productid + "_" + foodData.productName + ".png";
					//保存
					ImageIO.write(resizedQR, "png", new File(ImagePath));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public String toString() {
		String string = "";
		for (FoodData foodData : this.foodDatas) {
			string += foodData.toString() + "\n";
		}
		return string;
	}

}
