package leftfoot;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class FoodData {
    int productid;
    int iniPrice;
    String productName;
    Date bestBeforeDate;

    public FoodData(Map<String, String> Dictionary) {
    	String a = Dictionary.get("id");
    	this.productid = Integer.parseInt(a);

    	String b = Dictionary.get("iniPrice");
        this.iniPrice = Integer.parseInt(b);

        this.productName = Dictionary.get("productName");

        try {
			String stDate = Dictionary.get("bestBeforeDate");

			this.bestBeforeDate = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(stDate);

		} catch (ParseException ex) {
			ex.printStackTrace();
		}
    }
}
