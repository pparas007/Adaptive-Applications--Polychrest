package griup.middleware;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.andrewoma.dexx.collection.HashMap;
import griup.beans.Food;
import griup.beans.Pattern;
import griup.beans.Recommendation;
import griup.beans.Shop;
import griup.beans.Shopping;
import griup.beans.User;
import griup.polychrest.Constants;
import griup.polychrest.InteractWithOntology;


public class Middleware {
	static final float boost=0.3f;
	static final float interestBoost=0.6f;
	
	public static void insertShoppingInstance(User user,Shopping shopping, Pattern pattern){
		
		System.out.println("Middleware method insertShoppingInstance with parameters:\n"+user+"\n"+shopping+"\n"+pattern);
		//insert new entry to ontology
		new InteractWithOntology().insertShoppingInstance(user,shopping);
		
		//get current recommendation weightage
		Recommendation recommendation=getRecommendationForUserAndFoodPair(user, shopping.getBought());
		Recommendation oldRecommendation=new Recommendation(recommendation);
		//recommendation=getDummyRecommendation();//dummy data; to be removed after above method is available
		
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
		new InteractWithOntology().updateRecommendationForUserAndFoodPair(user, shopping.getBought(), oldRecommendation, recommendation);
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

	public static void upgradeInterest(User user, Food weightageFood) {
		System.out.println("Middleware method downgradeInterest with parameters:\n"+user+"\n"+weightageFood);
		//get current recommendation weightage
		Recommendation recommendation=getRecommendationForUserAndFoodPair(user, weightageFood);
		Recommendation oldRecommendation=new Recommendation(recommendation);
		//recommendation=getDummyRecommendation();//dummy data; to be removed after above method is available
		
		float hasUserInterest=recommendation.getHasUserInterest();
		System.out.println("User interest before: "+hasUserInterest);
		//boost pattern weightage for highest pattern confidence
		hasUserInterest=Utility.changeExponentially(hasUserInterest, interestBoost);
	
		//compress weightage to probability i.e. between 0-1
		hasUserInterest=Utility.convertToProbability(hasUserInterest,0.1f,0.1f);
		System.out.println("User interest before: "+hasUserInterest);
		
		recommendation.setHasUserInterest(hasUserInterest);
		
		//update weightage to ontology
		new InteractWithOntology().updateRecommendationForUserAndFoodPair(user, weightageFood, oldRecommendation, recommendation);
	}
	
	public static void downgradeInterest(User user, Food weightageFood) {
		System.out.println("Middleware method downgradeInterest with parameters:\n"+user+"\n"+weightageFood);
		//get current recommendation weightage
		Recommendation recommendation=getRecommendationForUserAndFoodPair(user, weightageFood);
		Recommendation oldRecommendation=new Recommendation(recommendation);
		//recommendation=getDummyRecommendation();//dummy data; to be removed after above method is available

		float hasUserInterest=recommendation.getHasUserInterest();
		System.out.println("User interest before: "+hasUserInterest);
		//boost pattern weightage for highest pattern confidence
		hasUserInterest=Utility.changeExponentially(hasUserInterest, -interestBoost);
	
		//compress weightage to probability i.e. between 0-1
		hasUserInterest=Utility.convertToProbability(hasUserInterest,0.1f,0.1f);
		System.out.println("User interest before: "+hasUserInterest);
		
		recommendation.setHasUserInterest(hasUserInterest);
		
		//update weightage to ontology
		new InteractWithOntology().updateRecommendationForUserAndFoodPair(user, weightageFood, oldRecommendation, recommendation);
	}
	
	public static void upGradePattern(User user, Food weightageFood) {
		System.out.println("Middleware method upGradePattern with parameters:\n"+user+"\n"+weightageFood);
		//get current recommendation weightage
		Recommendation recommendation=getRecommendationForUserAndFoodPair(user, weightageFood);
		Recommendation oldRecommendation=new Recommendation(recommendation);
		//recommendation=getDummyRecommendation();//dummy data; to be removed after above method is available
		
		float hasWeeklyWeightage=recommendation.getHasWeeklyWeightage();
		float hasByWeeklyWeightage=recommendation.getHasByWeeklyWeightage();
		float hasMonthlyWeightage=recommendation.getHasMonthlyWeightage();
		System.out.println("Weightage before: "+hasWeeklyWeightage+", "+hasByWeeklyWeightage+", "+hasMonthlyWeightage+", "+(hasWeeklyWeightage+hasByWeeklyWeightage+hasMonthlyWeightage));
		//boost pattern weightage for highest pattern confidence
		if(recommendation.getHighestWeightage()==recommendation.getHasWeeklyWeightage()) {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage, boost);
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage, - boost);
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage, - boost);
		}
		else if(recommendation.getHighestWeightage()==recommendation.getHasByWeeklyWeightage()) {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage, -boost);
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage,  boost);
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage, - boost);
		}
		else {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage, - boost);
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage, - boost);
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage,  boost);
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
		new InteractWithOntology().updateRecommendationForUserAndFoodPair(user, weightageFood, oldRecommendation, recommendation);
	}

	public static void downGradePattern(User user, Food weightageFood) {
		System.out.println("Middleware method downGradePattern with parameters:\n"+user+"\n"+weightageFood);
		//get current recommendation weightage
		Recommendation recommendation=getRecommendationForUserAndFoodPair(user, weightageFood);
		Recommendation oldRecommendation=new Recommendation(recommendation);
		//recommendation=getDummyRecommendation();//dummy data; to be removed after above method is available
		
		float hasWeeklyWeightage=recommendation.getHasWeeklyWeightage();
		float hasByWeeklyWeightage=recommendation.getHasByWeeklyWeightage();
		float hasMonthlyWeightage=recommendation.getHasMonthlyWeightage();
		System.out.println("Weightage before: "+hasWeeklyWeightage+", "+hasByWeeklyWeightage+", "+hasMonthlyWeightage+", "+(hasWeeklyWeightage+hasByWeeklyWeightage+hasMonthlyWeightage));
		//boost pattern weightage for highest pattern confidence
		if(recommendation.getHighestWeightage()==recommendation.getHasWeeklyWeightage()) {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage, -boost);
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage,  boost);
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage,  boost);
		}
		else if(recommendation.getHighestWeightage()==recommendation.getHasByWeeklyWeightage()) {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage, boost);
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage, - boost);
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage,  boost);
		}
		else {
			hasWeeklyWeightage=Utility.changeExponentially(hasWeeklyWeightage,  boost);
			hasByWeeklyWeightage=Utility.changeExponentially(hasByWeeklyWeightage,  boost);
			hasMonthlyWeightage=Utility.changeExponentially(hasMonthlyWeightage, - boost);
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
		new InteractWithOntology().updateRecommendationForUserAndFoodPair(user, weightageFood, oldRecommendation, recommendation);
	}
	
	public static Recommendation getDummyRecommendation() {
		return new Recommendation(0.1f, 0.20f, 0.70f,0.66f);//dummy data; to be removed after above method is available
	}

	public static void generateCsv(User user) {
		System.out.println("Middleware method generateCsv with parameters:\n"+user);
		
		//get all foods list
		List<Food> foodList=getAllFoodList(user);
		Map<String, Recommendation> recommendationListForAllFoods=new java.util.HashMap<String, Recommendation>();
		
		//form recommendation object list
		for(Food food:foodList) {
			Recommendation recommendation= new InteractWithOntology().getRecommendationForUserAndFoodPair(user, food);
			recommendationListForAllFoods.put(food.getFoodName(),recommendation);
		}
		
		Utility.generateUserInterestCsv(user,recommendationListForAllFoods);
	}

	private static List<Food> getAllFoodList(User user) {
		List<Food> foodList=new ArrayList<Food>();
		for(String foodString:Constants.foodList) {
			Food food=new Food();
			food.setFoodName(foodString);
			
			foodList.add(food);
		}
		return foodList;
	}
	
}
