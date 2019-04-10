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
	static final float conflictBoost=0.6f;
	
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

	public static String upgradeAndCheckConflict(User user, Food weightageFood) {
		System.out.println("Middleware method upgradeAndCheckConflict with parameters:\n"+user+"\n"+weightageFood);
		//get current recommendation conflict weightage
		Recommendation recommendation=getRecommendationForUserAndFoodPair(user, weightageFood);
		Recommendation oldRecommendation=new Recommendation(recommendation);
		
		float hasGoalConflict=recommendation.getHasGoalConflict();
		System.out.println("User Goal Conflict before: "+hasGoalConflict);
		
		ArrayList<String> foodCategoryList= new InteractWithOntology().getFoodCategory(weightageFood);
		ArrayList<String> userGoalsList= new InteractWithOntology().getUserGoals(user);
		System.out.println("FoodCategoryList: "+foodCategoryList+"\nUserGoalsList: "+userGoalsList);
		boolean didNotFoundAnyConflict=true;
		String conflictingWithCategory=null;
		for(String goal:userGoalsList) {
			for(String category:foodCategoryList) {
				if(Constants.conflictMap.get(goal).contains(category)) {
					System.out.println("Conflict found with user goal "+goal+" and food category "+category+" for food "+weightageFood.getFoodName());
					hasGoalConflict=Utility.changeExponentially(hasGoalConflict, conflictBoost);
					didNotFoundAnyConflict=false;
					conflictingWithCategory=category;
				}
			}
		}
		if(didNotFoundAnyConflict) {
			System.out.println("Did not found any conflict, so reducing the conflict");
			if(hasGoalConflict>0.3) hasGoalConflict=hasGoalConflict-0.1f;
		}
		
		//compress weightage to probability i.e. between 0-1
		hasGoalConflict=Utility.convertToProbability(hasGoalConflict,0.1f,0.1f);
		System.out.println("User Goal Conflict after: "+hasGoalConflict);
		
		recommendation.setHasGoalConflict(hasGoalConflict);
		
		//update weightage to ontology
		new InteractWithOntology().updateRecommendationForUserAndFoodPair(user, weightageFood, oldRecommendation, recommendation);
		String movingToGoal=null;
		if(conflictingWithCategory!=null && hasGoalConflict>0.7) {
			System.out.println("Conflict exceeded the threshold of 0.7: "+hasGoalConflict+" for regular goal ");
			for(String goal:Constants.goalList) {
				if(Constants.suitableMap.get(goal).contains(conflictingWithCategory)) {
					movingToGoal=goal;
					break;
				}
			}
			System.out.println("and seems to be moving to "+movingToGoal);
		}
		
		return movingToGoal;
	}

	public static void reset(User user) {
		System.out.println("Middleware method upgradeAndCheckConflict with parameters:\n"+user);
		//get current recommendation conflict weightage
		
		for(String foodName:Constants.foodList) {
			Food food=new Food();
			food.setFoodName(foodName);
			
			Recommendation recommendation=getRecommendationForUserAndFoodPair(user, food);
			Recommendation oldRecommendation=new Recommendation(recommendation);
			
			recommendation.setHasWeeklyWeightage(0.33f);
			recommendation.setHasByWeeklyWeightage(0.33f);
			recommendation.setHasMonthlyWeightage(0.33f);
			recommendation.setHasUserInterest(0.3f);
			recommendation.setHasGoalConflict(0.2f);
			
			new InteractWithOntology().updateRecommendationForUserAndFoodPair(user, food, oldRecommendation, recommendation);
		}
		
		
	}
	
}
