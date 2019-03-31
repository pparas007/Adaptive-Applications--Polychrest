package griup.polychrest;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.github.andrewoma.dexx.collection.HashMap;

import griup.beans.Food;
import griup.beans.Recommendation;
import griup.beans.Shop;
import griup.beans.Shopping;
import griup.beans.User;

public interface InteractWithOntologyInterafce {
	
	//already added some parts by shubham and aditya
	public ArrayList<User> getAllUser();
	public ArrayList<Shopping> getAll();
	public ArrayList<Shopping> getShoppingByUser(User user);
	public HashMap<Food,Recommendation> getRecommendationListForUser(User user);
	public ArrayList<Food> getFoodAtShop(String shopName);
	public ArrayList<Shop> getShopThatSellsFood(String foodName);
	
	//yet to be implemented 
	public void insertShoppingInstance(User user,Shopping shopping);
	public Recommendation getRecommendationForUserAndFoodPair(User user,Food food);
	public void updateRecommendationForUserAndFoodPair(User user, Food food, Recommendation oldRecommendation, Recommendation newRecommendation);
	

}