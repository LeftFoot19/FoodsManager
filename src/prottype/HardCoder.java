package prottype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HardCoder {

	public static List<HashMap<String, String>> LOAD() {

		List<HashMap<String, String>> dictionaries = new ArrayList<>();

		//ここから手打ち

		HashMap<String, String> data0 = new HashMap<String, String>();
		data0.put("id", "0");
		data0.put("iniPrice", "180");
		data0.put("productName", "おにぎり");
		data0.put("bestBeforeDate", "2019/12/10 00:00:00");
		dictionaries.add(data0);

		//繰り返し


		//ここまで

		return dictionaries;

	}

}
