package leftfoot;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

	public static FoodData LOAD(String path) {

		String absPath = new File(path).getAbsolutePath();

		//読み込めた前提
		List<Map<String, String>> foodDictionaries = FoodBrowser.LOADMAP(absPath);

		for (Map<String, String> map : foodDictionaries) {
			System.out.println("+--------------+");
			for (String key : map.keySet()) {
				System.out.println(key + ", " + map.get(key));
			}
		}

		//FoodDataインスタンス生成

		return null;	//実装の際は削除

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

		return null;	//実装の際は削除

	}

}
