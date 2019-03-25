package griup.middleware;

import java.util.ArrayList;
import java.util.List;
import com.github.andrewoma.dexx.collection.HashMap;
import griup.beans.Food;
import griup.beans.Pattern;
import griup.beans.Recommendation;
import griup.beans.Shop;
import griup.beans.Shopping;
import griup.beans.User;
import griup.polychrest.InteractWithOntology;


public class Middleware {

	public static void insertShoppingInstance(User user,Shopping shopping, Pattern pattern){
		
		System.out.println("Middleware method insertShoppingInstance with parameters:\n"+user+"\n"+shopping+"\n"+pattern);
		//insert new entry to ontology
		new InteractWithOntology().insertShoppingInstance(user,shopping);
		
		//get current recommendation weightage
		Recommendation recommendation=getRecommendationForUserAndFoodPair(user, shopping.getBought());
		recommendation=new Recommendation(0.1f, 0.1f, 0.8f);//dummy data; to be removed after above method is available
		
		float hasWeeklyWeightage=recommendation.getHasWeeklyWeightage();
		float hasByWeeklyWeightage=recommendation.getHasByWeeklyWeightage();
		float hasMonthlyWeightage=recommendation.getHasMonthlyWeightage();
		System.out.println("Weightage before: "+hasWeeklyWeightage+", "+hasByWeeklyWeightage+", "+hasMonthlyWeightage+", "+(hasWeeklyWeightage+hasByWeeklyWeightage+hasMonthlyWeightage));
		//boost pattern weightage for highest pattern confidence
		if(pattern.getHighestConfidence()==pattern.getWeeklyPatternConfidence()) {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage, pattern.getWeeklyPatternConfidence());
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage, - pattern.getWeeklyPatternConfidence());
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage, - pattern.getWeeklyPatternConfidence());
		}else if(pattern.getHighestConfidence()==pattern.getBiweeklyPatternConfidence()) {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage, - pattern.getBiweeklyPatternConfidence());
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage, pattern.getBiweeklyPatternConfidence());
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage, - pattern.getBiweeklyPatternConfidence());
		}else if(pattern.getHighestConfidence()==pattern.getMonthlyPatternConfidence()) {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage, - pattern.getMonthlyPatternConfidence());
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage, - pattern.getMonthlyPatternConfidence());
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage, pattern.getMonthlyPatternConfidence());
		}
		
		//compress weightage to probability i.e. between 0-1
		hasWeeklyWeightage=Utility.convertToProbability(hasWeeklyWeightage,hasByWeeklyWeightage,hasMonthlyWeightage);
		hasByWeeklyWeightage=Utility.convertToProbability(hasByWeeklyWeightage,hasWeeklyWeightage,hasMonthlyWeightage);
		hasMonthlyWeightage=Utility.convertToProbability(hasMonthlyWeightage,hasWeeklyWeightage,hasByWeeklyWeightage);
		System.out.println("Weightage after: "+hasWeeklyWeightage+", "+hasByWeeklyWeightage+", "+hasMonthlyWeightage+", "+(hasWeeklyWeightage+hasByWeeklyWeightage+hasMonthlyWeightage));
		
		recommendation.setHasWeeklyWeightage(hasWeeklyWeightage);
		recommendation.setHasByWeeklyWeightage(hasByWeeklyWeightage);
		recommendation.setHasMonthlyWeightage(hasMonthlyWeightage);
		
		//update weightage to ontology
		new InteractWithOntology().updateRecommendationForUserAndFoodPair(user, shopping.getBought(), recommendation);
	}
	
	public static Recommendation getRecommendationForUserAndFoodPair(User user, Food food) {
		System.out.println("Middleware method getRecommendationForUserAndFoodPair with parameters:\n"+user+"\n"+food);
		Recommendation recommendation=new InteractWithOntology().getRecommendationForUserAndFoodPair(user, food);
		return recommendation;
	}
	
	public static HashMap<Food,Recommendation> getRecommendationListForUser(User user) {
		System.out.println("Middleware method getRecommendationForUserAndFoodPair with parameters:\n"+user);
		HashMap<Food,Recommendation> recommendationListForUser=new InteractWithOntology().getRecommendationListForUser(user);
		return recommendationListForUser;
	}
}
