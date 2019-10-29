package leftfoot;

import java.util.Dictionary;
import java.util.List;

import prottype.HardCoder;

public class FoodBrowser {

	public List<FoodData> foodDatas;

	private FoodBrowser(List<FoodData> foodDatas) {
		this.foodDatas = foodDatas;
	}

	public static FoodData LOAD() {

		//読み込めた前提
		List<Dictionary<String, String>> foodDictionaries = HardCoder.LOAD();

		//FoodDataインスタンス生成

		return null;	//実装の際は削除

	}

	public FoodData search(int id) {

		return null;	//実装の際は削除

	}

}
