package griup.dummyui;

import java.util.ArrayList;
import java.util.List;

import com.github.andrewoma.dexx.collection.HashMap;

import griup.beans.Food;
import griup.beans.Pattern;
import griup.beans.Recommendation;
import griup.beans.Shop;
import griup.beans.Shopping;
import griup.beans.User;
import griup.middleware.Middleware;
import griup.polychrest.Constants;
import griup.polychrest.InteractWithOntology;

public class DummyUI {

	public static void main(String[] args) {
		//createShoppingInstance();
		InteractWithOntology io= new InteractWithOntology();
		io.getAllUser();
		io.getAll();
		Shop lidlArtane=new Shop();
		lidlArtane.setShopName("lidlArtane");
		Food kiwi=new Food();
		kiwi.setFoodName("kiwi");
		User paras=new User ();
		paras.setName("shubham");
		User user=new User();
		Food food= new Food ();
		food.setFoodName("chickenLegs");
		user.setName("shubham");
		ArrayList<String> goalOld=new ArrayList<String>();
		ArrayList<String> goalNew=new ArrayList<String>();
		goalOld.add("veggie");
		goalNew.add("nonVeg");
		user.setGoalsList(goalOld);
		paras.setGoalsList(goalNew);
		//io.getFoodAtShop(shopName);
		io.updateUserGoal(user, paras);
		//io. getShoppingByUser( user);
		//io.getRecommendationListForUser(user);
		Shop shop= new Shop();
		//io.getFoodAtShop("lidlArtane");
		//io.getShopThatSellsFood("butter");
		Shopping shopping=new Shopping();
		shopping.setAtPrice(1f);
		shopping.setAtShop(lidlArtane);
		shopping.setAtDateTime("12:00");
		shopping.setBought(kiwi);
		shopping.setQuantity(1);
		int shopingNo=(int)(Math.random()*10000);
		System.out.println(shopingNo);
		shopping.setShoppingName(user.getName()+"Shops"+shopingNo);
		io.insertShoppingInstance(user, shopping);
		Recommendation rec=io.getRecommendationForUserAndFoodPair(user, food);
		rec.setHasByWeeklyWeightage((float) 0.912312);
		rec.setHasUserInterest((float) 0.931231);
		rec.setHasWeeklyWeightage((float) 0.93123123);
		//io.updateRecommendationForUserAndFoodPair(user, food,io.getRecommendationForUserAndFoodPair(user, food), rec);
		//io.getRecommendationListForUser(user);
		//io.getFoodCategory(food);
		io.getUserGoals(user);
		//io.updateUserGoal(userOld, userNew);
		
		
	}
	
	public static void createShoppingInstance() {
		Shop lidlArtane=new Shop();
		lidlArtane.setShopName("lidlArtane");
		Food kiwi=new Food();
		kiwi.setFoodName("kiwi");
		User paras=new User ();
		paras.setName("paras");
		
		Shopping shopping=new Shopping();
		shopping.setAtPrice(1f);
		shopping.setAtShop(lidlArtane);
		shopping.setAtDateTime("12:00");
		shopping.setBought(kiwi);
		shopping.setQuantity(1);
		
		Pattern pattern=new Pattern(0.3f, 0.1f, 0.6f);
		Middleware.insertShoppingInstance(paras,shopping, pattern);
	}
	
	public static void getRecommendation() {
		User paras=new User();
		paras.setName("paras");
		
	HashMap<Food,Recommendation> recommendationListForUser=Middleware.getRecommendationListForUser(paras);
	}
}