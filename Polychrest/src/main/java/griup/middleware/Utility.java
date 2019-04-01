package griup.middleware;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import com.opencsv.CSVWriter;

import griup.beans.Recommendation;
import griup.beans.User;

public class Utility{
	private static float e=2.73f;
	private static String csvPrefix="resource\\csv\\";
	private static String userInterestCsvPostfix="_user_interest.csv";
	
	public static  float changeExponentially(float weightage, float boost) {
		System.out.println("###########");
		System.out.println(weightage+" "+boost);
		float result=(float) (weightage*(Math.pow(e, boost)));
		System.out.println(result);
		System.out.println("@@@@@@@@@@@@@");
		return result;
		
	}
	public static float convertToProbability(float first, float second, float third) {
		return (first/(first+second+third));
	}
	
	public static void generateUserInterestCsv(User user, Map<String, Recommendation> recommendationListForAllFoods) {
		File file = new File(csvPrefix+user.getName()+userInterestCsvPostfix); 
	    try { 
	        // create FileWriter object with file as parameter 
	        FileWriter outputfile = new FileWriter(file); 
	  
	        // create CSVWriter object filewriter object as parameter 
	        CSVWriter writer = new CSVWriter(outputfile); 
	  
	        // adding header to csv 
	        String[] header = { "Food", "User Interest" }; 
	        writer.writeNext(header); 
	  
	        // add data to csv
	        for(String food:recommendationListForAllFoods.keySet()) {
	        	String stringUserInterest=Float.toString(recommendationListForAllFoods.get(food).getHasUserInterest());
	        	String[] data = { food,  stringUserInterest};
	        	writer.writeNext(data);
	        }
	  
	        writer.close(); 
	    } 
	    catch (Exception e) { e.printStackTrace(); } 
	}
}
