package leftfoot;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FoodData {

    int productid;
    int iniPrice;
    String productName;
    Date bestBeforeDate;

    public static FoodData DUMMY = FoodData.getDummy();

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

    private static FoodData getDummy() {

    	Map<String, String> map = new HashMap<String, String>();
    	map.put("id", "-1");
    	map.put("iniPrice", "0");
    	map.put("productName", "DUMMY");
    	map.put("bestBeforeDate", "1970/01/01 00:00:00");

    	return new FoodData(map);

    }

    @Override
    public String toString() {
    	String string = "ID: " + this.productid +
    					"\nProductName: " + this.productName +
    					"\nInitialPrice: " + this.iniPrice +
    					"\nBestBoforeDate: " + this.bestBeforeDate.toString();

    	return string;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bestBeforeDate == null) ? 0 : bestBeforeDate.hashCode());
		result = prime * result + iniPrice;
		result = prime * result + ((productName == null) ? 0 : productName.hashCode());
		result = prime * result + productid;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodData other = (FoodData) obj;
		if (bestBeforeDate == null) {
			if (other.bestBeforeDate != null)
				return false;
		} else if (!bestBeforeDate.equals(other.bestBeforeDate))
			return false;
		if (iniPrice != other.iniPrice)
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		if (productid != other.productid)
			return false;
		return true;
	}

}
