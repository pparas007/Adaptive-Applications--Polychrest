package griup.middleware;

import java.util.ArrayList;
import java.util.List;

import javax.rmi.CORBA.Util;

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
		//insert new entry to ontology
		new InteractWithOntology().insertShoppingInstance(user,shopping);
		
		//get current recommendation weightage
		Recommendation recommendation=new InteractWithOntology().getRecommendationForUserAndFoodPair(user, shopping.getBought());
		recommendation=new Recommendation(0.100541875f, 0.0315531f, 0.915471f);//dummy data; to be removed after above method is available
		
		float hasWeeklyWeightage=recommendation.getHasWeeklyWeightage();
		float hasByWeeklyWeightage=recommendation.getHasByWeeklyWeightage();
		float hasMonthlyWeightage=recommendation.getHasMonthlyWeightage();
		System.out.println("Weightage before: "+hasWeeklyWeightage+", "+hasByWeeklyWeightage+", "+hasMonthlyWeightage+", "+(hasWeeklyWeightage+hasByWeeklyWeightage+hasMonthlyWeightage));
		//boost pattern weightage for highest pattern confidence
		if(pattern.getHighestConfidence()==pattern.getWeeklyPatternConfidence()) {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage, pattern.getWeeklyPatternConfidence());
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage, - pattern.getBiweeklyPatternConfidence());
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage, - pattern.getMonthlyPatternConfidence());
		}else if(pattern.getHighestConfidence()==pattern.getBiweeklyPatternConfidence()) {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage, - pattern.getWeeklyPatternConfidence());
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage, pattern.getBiweeklyPatternConfidence());
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage, - pattern.getMonthlyPatternConfidence());
		}else if(pattern.getHighestConfidence()==pattern.getMonthlyPatternConfidence()) {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage, - pattern.getWeeklyPatternConfidence());
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage, - pattern.getBiweeklyPatternConfidence());
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage, pattern.getMonthlyPatternConfidence());
		}
		
		//compress weightage to probability i.e. between 0-1
		hasWeeklyWeightage=Utility.convertToProbability(hasWeeklyWeightage,hasByWeeklyWeightage,hasMonthlyWeightage);
		hasByWeeklyWeightage=Utility.convertToProbability(hasByWeeklyWeightage,hasWeeklyWeightage,hasMonthlyWeightage);
		hasMonthlyWeightage=Utility.convertToProbability(hasMonthlyWeightage,hasWeeklyWeightage,hasByWeeklyWeightage);
		System.out.println("Weightage before: "+hasWeeklyWeightage+", "+hasByWeeklyWeightage+", "+hasMonthlyWeightage+", "+(hasWeeklyWeightage+hasByWeeklyWeightage+hasMonthlyWeightage));
		
		recommendation.setHasWeeklyWeightage(hasWeeklyWeightage);
		recommendation.setHasByWeeklyWeightage(hasByWeeklyWeightage);
		recommendation.setHasMonthlyWeightage(hasMonthlyWeightage);
		
		//update weightage to ontology
		new InteractWithOntology().updateRecommendationForUserAndFoodPair(user, shopping.getBought(), recommendation);
	}

	public static HashMap<Food,Recommendation> getRecommendationListForUser(User user) {
		// TODO Auto-generated method stub
		HashMap<Food,Recommendation> recommendationListForUser=new InteractWithOntology().getRecommendationListForUser(user);
		return recommendationListForUser;
	}
}
