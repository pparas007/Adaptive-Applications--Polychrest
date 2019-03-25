package griup.beans;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Food {
String foodName;
ArrayList<String> categoryList;
public String getFoodName() {
	return foodName;
}
public void setFoodName(String foodName) {
	this.foodName = foodName;
}
public ArrayList<String> getCategoryList() {
	return categoryList;
}
public void setCategoryList(ArrayList<String> categoryList) {
	this.categoryList = categoryList;
}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return foodName.equals(((Food)arg0).getFoodName());
	}
	
	@Override
		public int hashCode() {
			int hashcode=1;
			for(int i=0;i<foodName.length();i++) {
				hashcode*=( Character.getNumericValue(foodName.charAt(i)) );
			}
			return hashcode;
		}


}
