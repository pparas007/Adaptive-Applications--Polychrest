package griup.polychrest;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.jena.ontology.Individual;

public class Constants {
	public static String GOAL_MEAT_LOVER="meatlover";
	public static String GOAL_ECONOMICAL="economical";
	public static String GOAL_VEGGIE="veggie";
	public static String GOAL_FITNESS_FREAK="fitness";
	public static String GOAL_FUN_IN_VARIETY="variety";
	
	public static String SHOP_SUPERMARKET="supermarket";
	public static String SHOP_STORE="store";
	public static String SHOP_MALL="mall";
	
	public static String FOOD_CATEGORY_VEG="veg";
	public static String FOOD_CATEGORY_VEGETABLES="vegetables";
	public static String FOOD_CATEGORY_FRUITS="fruits";
	public static String FOOD_CATEGORY_HEALTHY="healthy";
	public static String FOOD_CATEGORY_SALAD="salad";
	public static String FOOD_CATEGORY_CHINESE="chinese";
	public static String FOOD_CATEGORY_ASIAN="asian";
	public static String FOOD_CATEGORY_NONVEG="nonveg";
	public static String FOOD_CATEGORY_MEAT="meat";
	public static String FOOD_CATEGORY_CHICKEN="chicken";
	public static String FOOD_CATEGORY_FISH="fish";
	public static String FOOD_CATEGORY_PORK="pork";
	public static String FOOD_CATEGORY_LAMB="lamb";
	public static String FOOD_CATEGORY_BAKERY="bakery";
	public static String FOOD_CATEGORY_BREAD="bread";
	public static String FOOD_CATEGORY_DAIRY="dairy";
	public static String FOOD_CATEGORY_CHEESE="cheese";
	public static String FOOD_CATEGORY_MILK="milk";
	public static String FOOD_CATEGORY_SOFT_DRINK="softdrink";
	public static String FOOD_CATEGORY_ALCOHOL="alcohol";
	public static String FOOD_CATEGORY_CHOLESTEROL="colesterol";
	
	public static String PATTERN_WEEKLY="weekly";
	public static String PATTERN_BIWEEKLY="biweekly";
	public static String PATTERN_MONTHLY="monthly";
	
	//UI Contsants
	public static final String[] userList= {"paras","shubham","anirban", "aditya"};
  
	public static final String foodList[]= {"spinach","mushroom","brocolli","orange","kiwi","apple","lowFatMilk","milk","butter","greekCheese","sweeseCheese","chickenLegs","chickenBreasts","coke","dietCoke","bread","brownBread"};
	public static final String shopList[]= {"lidlArtane","lidlCityCentre","tescoCityCentre","euroGeneralFairview"};
	public static final String patternList[]= {PATTERN_WEEKLY,PATTERN_BIWEEKLY,PATTERN_MONTHLY};
	public static final String goalList[]= {GOAL_MEAT_LOVER,GOAL_ECONOMICAL,GOAL_VEGGIE,GOAL_FITNESS_FREAK,GOAL_FUN_IN_VARIETY};
	
	public static final String patternConfidenceList[]= {"0.9","0.8","0.7","0.6"};
	public static final String quantityList[]= {"1","2","3"};
	public static final String priceList[]= {"1","1.5","2","2.5","3","3.5","4","4.5","5"};
	//UI Constants ends
	
	public static HashMap<String, ArrayList<String>> conflictMap;
	static {
		conflictMap=new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> meatLoverConflictMap=new ArrayList<String>();
		meatLoverConflictMap.add(FOOD_CATEGORY_VEG);meatLoverConflictMap.add(FOOD_CATEGORY_VEGETABLES);meatLoverConflictMap.add(FOOD_CATEGORY_FRUITS);meatLoverConflictMap.add(FOOD_CATEGORY_SALAD);
		conflictMap.put(GOAL_MEAT_LOVER, meatLoverConflictMap);
		
		ArrayList<String> veggieConflictMap=new ArrayList<String>();
		veggieConflictMap.add(FOOD_CATEGORY_NONVEG);veggieConflictMap.add(FOOD_CATEGORY_MEAT);veggieConflictMap.add(FOOD_CATEGORY_CHICKEN);veggieConflictMap.add(FOOD_CATEGORY_FISH);
		veggieConflictMap.add(FOOD_CATEGORY_PORK);veggieConflictMap.add(FOOD_CATEGORY_LAMB);
		conflictMap.put(GOAL_VEGGIE, veggieConflictMap);
		
		ArrayList<String> fitnessConflictMap=new ArrayList<String>();
		fitnessConflictMap.add(FOOD_CATEGORY_SOFT_DRINK);fitnessConflictMap.add(FOOD_CATEGORY_ALCOHOL);fitnessConflictMap.add(FOOD_CATEGORY_CHOLESTEROL);
		conflictMap.put(GOAL_FITNESS_FREAK, fitnessConflictMap);
		
		conflictMap.put(GOAL_ECONOMICAL, new ArrayList<String>());
		conflictMap.put(GOAL_FUN_IN_VARIETY, new ArrayList<String>());
	}
	
	public static HashMap<String, ArrayList<String>> suitableMap;
	static {
		suitableMap=new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> meatLoverSuitableMap=new ArrayList<String>();
		meatLoverSuitableMap.add(FOOD_CATEGORY_NONVEG);meatLoverSuitableMap.add(FOOD_CATEGORY_MEAT);meatLoverSuitableMap.add(FOOD_CATEGORY_PORK);meatLoverSuitableMap.add(FOOD_CATEGORY_LAMB);
		meatLoverSuitableMap.add(FOOD_CATEGORY_FISH);meatLoverSuitableMap.add(FOOD_CATEGORY_CHICKEN);
		suitableMap.put(GOAL_MEAT_LOVER, meatLoverSuitableMap);
		
		ArrayList<String> veggieSuitableMap=new ArrayList<String>();
		veggieSuitableMap.add(FOOD_CATEGORY_VEG);veggieSuitableMap.add(FOOD_CATEGORY_VEGETABLES);veggieSuitableMap.add(FOOD_CATEGORY_FRUITS);veggieSuitableMap.add(FOOD_CATEGORY_HEALTHY);
		veggieSuitableMap.add(FOOD_CATEGORY_SALAD);
		suitableMap.put(GOAL_VEGGIE, veggieSuitableMap);
		
		ArrayList<String> fitnessSuitableMap=new ArrayList<String>();
		fitnessSuitableMap.add(FOOD_CATEGORY_VEG);fitnessSuitableMap.add(FOOD_CATEGORY_VEGETABLES);fitnessSuitableMap.add(FOOD_CATEGORY_FRUITS);fitnessSuitableMap.add(FOOD_CATEGORY_HEALTHY);
		fitnessSuitableMap.add(FOOD_CATEGORY_SALAD);fitnessSuitableMap.add(FOOD_CATEGORY_MILK);
		suitableMap.put(GOAL_FITNESS_FREAK, fitnessSuitableMap);
		
		suitableMap.put(GOAL_ECONOMICAL, new ArrayList<String>());
		suitableMap.put(GOAL_FUN_IN_VARIETY, new ArrayList<String>());
	}
}
