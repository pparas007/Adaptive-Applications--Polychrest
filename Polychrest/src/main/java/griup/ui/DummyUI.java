package griup.ui;

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
		User user=new User();
		user.setName("paras");
		io. getShoppingByUser( user);
		io.getRecommendationListForUser(user);
		Shop shop= new Shop();
		io.getFoodAtShop("lidlArtane");
		io.getShopThatSellsFood("butter");
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
