package griup.polychrest;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.jena.atlas.io.InStreamASCII;

import com.github.andrewoma.dexx.collection.HashMap;

import griup.beans.Food;
import griup.beans.Recommendation;
import griup.beans.Shop;
import griup.beans.Shopping;
import griup.beans.User;

public class InteractWithOntology implements InteractWithOntologyInterafce{
	public static void main(String[] args) throws Exception{
		User user = new User();
		user.setName("paras");
	//	new InteractWithOntology().getAllUser();
		new InteractWithOntology().getAll();
	//	new InteractWithOntology().getShoppingByUser(user);
	//	new InteractWithOntology().getRecommendationListForUser( user);
	//	new InteractWithOntology().getFoodAtShop("lidlCityCentre");
	//	new InteractWithOntology().getShopThatSellsFood("brownBread");
	}
	
	public ArrayList<User> getAllUser()
	{
		ArrayList<User> users= new ArrayList<User>();
		
		try {
		String queryString = "PREFIX base:  <http://polychrest/ontology#>\r\n" + 
				"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\r\n" + 
				"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"\r\n" + 
				"select * where \r\n" + 
				"{\r\n" + 
				"?user a base:user.\r\n" + 
				"?user  base:hasGoal ?goal\r\n" + 
				"}";
		
		System.out.println(queryString);
		
		String s=	ReadOntology.query(queryString);
		System.out.println(s);
		/*String cleanString = s.replaceAll("[^a-zA-Z0-9\\s+]", "").trim();
		String extractName = cleanString.replaceAll("httppolychrestontology", "").replaceAll("user", "");
		String strArray[] = extractName.split("\r");
		
		for(int i=0; i<strArray.length;i++) {
		User us = new User();
		us.setName(strArray[i].trim());
		allUsersList.add(us);
		}	
		for(int i=0; i<allUsersList.size();i++) {
			System.out.println(allUsersList.get(i).getName());
		}*/
		
		
		String [] s1= s.split("#");
		
		for (int i=1;i<s1.length;i++ )
		{
			
			//System.out.println("s1-"+i+" "+s1[i]);
			String s2[] =s1[i].split("\"");
			//System.out.println("s2[0] "+ s2[0]);
			//System.out.println("s2[1] "+ s2[1]);
			//System.out.println("Name: "+s2[0].replace(">", "").replace("|","").trim());
			//System.out.println("Goal: "+ s2[1].replace("\"", "").trim());
			
			if(!users.isEmpty())
			{int size=users.size();
			int check=-1;
		
				for(int j=0;j<size;j++)
				{
					if(users.get(j).getName().equals(s2[0].replace(">", "").replace("|","").trim()))
					{
						check=j;
					}	
				}	
				if(check!=-1)		
				{
						//System.out.println("Name  equal :"+users.get(check).getName()+ ":"+s2[0].replace(">", "").replace("|","").trim()+" ,goal :"+s2[1].replace("\"", "").trim());
						
					//users.get(j).getGoalsList().add(s2[1].replace("\"", "").trim());
					ArrayList <String> s3= users.get(check).getGoalsList();
					s3.add(s2[1].replace("\"", "").trim());
					users.get(check).setGoalsList(s3);
					}
					else
					{//System.out.println("Inside else inside else :Inserting into users:"+s2[0].replace(">", "").replace("|","").trim()+" goal "+s2[1].replace("\"", "").trim());
					
						User us= new User();
						ArrayList<String> gl = new ArrayList<String>();
						gl.add(s2[1].replace("\"", "").trim());
						us.setGoalsList(gl);
						us.setName(s2[0].replace(">", "").replace("|","").trim());
						users.add(us);
					}
				
			}
			else {
				//System.out.println("Inside else :Inserting into users:"+s2[0].replace(">", "").replace("|","").trim()+" goal "+s2[1].replace("\"", "").trim());
				User us= new User();
				ArrayList<String> gl = new ArrayList<String>();
				gl.add(s2[1].replace("\"", "").trim());//goal
				us.setGoalsList(gl);
				us.setName(s2[0].replace(">", "").replace("|","").trim());
				users.add(us);
			}
				
		
			
		}
	/*	for (int k=0;k<users.size();k++)
		{
			System.out.println("user name :"+users.get(k).getName());
			for(int l=0;l<users.get(k).getGoalsList().size();l++)
			{
				System.out.println("Goal "+l+" : "+users.get(k).getGoalsList().get(l));
			}
		}*/
		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return users;
	}
	
	
	public ArrayList<Shopping> getAll()
	{
		ArrayList<Shopping>allShopping = new ArrayList<Shopping>();
		
		try {
			String queryString = "PREFIX base:  <http://polychrest/ontology#>\n" + 
					"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"select * where\n" + 
					"{\n" + 
					"?shopping a base:shopping.\n" + 
					" ?shopping  base:atDateTime   ?date.\n" + 
					" ?shopping   base:atPrice  ?price.\n" + 
					" ?shopping       base:atShop      ?shop.\n" + 
					" ?shopping       base:quantity      ?quantity.\n" + 
					" ?shopping       base:bought      ?bought.\n" + 
					" ?shop       base:hasShopAddress      ?shopAddress.\n" + 
					" ?shop       base:hasShopType       ?ShopType\n" + 
					"}\n" + 
					"";
			
			System.out.println(queryString);
			
			String s=	ReadOntology.query(queryString);
			
			String [] s1= s.split("<");
			Shopping shopping= null;
			for(int i=1;i<s1.length;i++)
			{
				Shop shop= new Shop();
				//System.out.println("i: "+i+" "+s1[i]);
				if(i%3==1)
				{shopping = new Shopping();
					String s2[] = s1[i].split("\"");
					String s3[]= s2[0].split("#");
				//System.out.println(	s3[1].replace(">", "").replace("|", "").trim());
				//	System.out.println(s2[1]);
				//	System.out.println(s2[3]);
					shopping.setShoppingName(s3[1].replace(">", "").replace("|", "").trim());
					shopping.setAtDateTime(s2[1]);
					shopping.setAtPrice(Float.parseFloat(s2[3]));
					
				}
				else if(i%3==2)
				{
					String s2[] = s1[i].split("\"");
					String s3[]= s2[0].split("#");
				//	System.out.println(	s3[1].replace(">", "").replace("|", "").trim());
				//	System.out.println(s2[1]);
					shop.setShopName(s3[1].replace(">", "").replace("|", "").trim());
					shopping.setQuantity(Float.parseFloat(s2[1]));
					
				}
				else
				{
					String s2[] = s1[i].split("\"");
					String s3[]= s2[0].split("#");
					//System.out.println(	s3[1].replace(">", "").replace("|", "").trim());
					//System.out.println(s2[1]);
					//System.out.println(s2[3]);
					shop.setShopAddress(s2[1]);
					shop.setShopType(s2[3]);
					Food food = new Food ();
					food.setFoodName(s3[1].replace(">", "").replace("|", "").trim());
					shopping.setBought(food);
					allShopping.add(shopping);
				}
				
			}
			
			
			System.out.println(s);
			for (int k=0;k<allShopping.size();k++)
			{
				System.out.println("user name :"+allShopping.get(k).getShoppingName());
				
			}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return allShopping;
	}

	public ArrayList<Shopping> getShoppingByUser(User user)
	{ArrayList<Shopping>allShopping = new ArrayList<Shopping>();
		try {
			String name = user.getName();
			String queryString = "PREFIX base:  <http://polychrest/ontology#>\n" + 
					"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"select * \n" + 
					"Where {\n" + 
					//"?user  a base:user.\n" + 
					"base:"+name +" base:shopped ?shopping. \n" + 
					"?shopping  base:atDateTime   ?date.\n" + 
					"?shopping   base:atPrice  ?price.\n" + 
					"?shopping       base:atShop      ?shop.\n" + 
					"?shopping       base:quantity      ?quantity.\n" + 
					"?shopping       base:bought      ?bought.\n" +
					"?shop       base:hasShopAddress      ?shopAddress.\n" + 
							" ?shop       base:hasShopType       ?ShopType\n" + 
							   
					"}";
			
			System.out.println(queryString);
			
String s=	ReadOntology.query(queryString);
			
			String [] s1= s.split("<");
			Shopping shopping= null;
			for(int i=1;i<s1.length;i++)
			{
				Shop shop= new Shop();
				//System.out.println("i: "+i+" "+s1[i]);
				if(i%3==1)
				{shopping = new Shopping();
					String s2[] = s1[i].split("\"");
					String s3[]= s2[0].split("#");
			//	System.out.println(	s3[1].replace(">", "").replace("|", "").trim());
			//		System.out.println(s2[1]);
			//		System.out.println(s2[3]);
					shopping.setShoppingName(s3[1].replace(">", "").replace("|", "").trim());
					shopping.setAtDateTime(s2[1]);
					shopping.setAtPrice(Float.parseFloat(s2[3]));
					
				}
				else if(i%3==2)
				{
					String s2[] = s1[i].split("\"");
					String s3[]= s2[0].split("#");
				//	System.out.println(	s3[1].replace(">", "").replace("|", "").trim());
				//	System.out.println(s2[1]);
					shop.setShopName(s3[1].replace(">", "").replace("|", "").trim());
					shopping.setQuantity(Float.parseFloat(s2[1]));
					
				}
				else
				{
					String s2[] = s1[i].split("\"");
					String s3[]= s2[0].split("#");
				//	System.out.println(	s3[1].replace(">", "").replace("|", "").trim());
				//	System.out.println(s2[1]);
				//	System.out.println(s2[3]);
					shop.setShopAddress(s2[1]);
					shop.setShopType(s2[3]);
					Food food = new Food ();
					food.setFoodName(s3[1].replace(">", "").replace("|", "").trim());
					
					if(food.getFoodName().equals("sprite"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("softdrink");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("butter"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("colesterol" );
					cat.add("veg" );
					cat.add("dairy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("brocolli"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("vegetables");
					cat.add("veg" );
					cat.add("healthy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("spinach"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("vegetables");
					cat.add("veg" );
					cat.add("healthy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("orange"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("fruits");
					cat.add("veg" );
					cat.add("healthy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("rocketLeaves"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("vegetables");
					cat.add("veg" );
					cat.add("healthy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("greekCheese"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("colesterol");
					cat.add("cheese" );
					cat.add("dairy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("mushroom"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("vegetables");
					cat.add("veg" );
					cat.add("healthy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("chickenLegs"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("colesterol");
					cat.add("chicken" );
					cat.add("nonveg");
						food.setCategoryList(cat);
					}
					
					else if(food.getFoodName().equals("bread"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("bread");
					cat.add("bakery" );
		
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("capsicum"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("vegetables");
					cat.add("veg" );
					cat.add("healthy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("capsicum"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("vegetables");
					cat.add("veg" );
					cat.add("healthy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("irishApple"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("fruits");
					cat.add("veg" );
					cat.add("healthy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("noodles"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("asian");
					cat.add("veg" );
					cat.add("chinese");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("apple"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("fruits");
					cat.add("veg" );
					cat.add("healthy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("chickenBreasts"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("colesterol");
					cat.add("chicken" );
					cat.add("nonveg");
						food.setCategoryList(cat);
					}
					
					else if(food.getFoodName().equals("dietCoke"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("softdrink");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("coke"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("softdrink");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("sweeseCheese"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("colesterol");
					cat.add("cheese" );
					cat.add("dairy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("lowFatMilk"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("milk" );
					cat.add("veg" );
					cat.add("dairy");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("pepsi"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("softdrink");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("lamb"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("colesterol");
					cat.add("lamb" );
					cat.add("nonveg");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("fanta"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("softdrink");
						food.setCategoryList(cat);
					}
					else if(food.getFoodName().equals("kiwi"))
					{ArrayList<String> cat= new ArrayList<String>();
					cat.add("fruits");
					cat.add("veg" );
					cat.add("healthy");
						food.setCategoryList(cat);
					}
					shopping.setBought(food);
					allShopping.add(shopping);
				}
				
			}
			
			
			System.out.println(s);
			for (int k=0;k<allShopping.size();k++)
			{
			//	System.out.println("user name :"+allShopping.get(k).getShoppingName());
				
			}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return allShopping;
	}
	

	public HashMap<Food,Recommendation> getRecommendationListForUser(User user)
	{HashMap <Food,Recommendation> hs = new HashMap<Food,Recommendation>();
		try {
			String name = user.getName();
			String queryString = "PREFIX base:  <http://polychrest/ontology#>\n" + 
					"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"					select *\n" + 
					"where{\n" + 
					"base:"+name+"  base:hasRecommendation ?recommendations.\n" + 
					"?recommendations  base:hasBiweelyWeightage ?biweekly.\n" + 
					"?recommendations  base:hasMonthlyWeightage ?monthly.\n" + 
					"?recommendations  base:hasWeelyWeightage ?weekly.\n" + 
					"?recommendations base:hasGoalConflict      ?goal .\n"+
					" ?recommendations       base:hasUserInterest        ?userintrest\n" + 
					"}";
			
			System.out.println(queryString);
			
			String s=	ReadOntology.query(queryString);
			
			
			System.out.println(s);
			String [] s1= s.split("<");
			
			for(int i=1;i<s1.length;i++)
			{String s2[] = s1[i].split("\"");
			String s3[]= s2[0].split("#");
			//System.out.println(	s3[1].replace(">", "").replace("|", "").trim());
			String s4[]= 	s3[1].replace(">", "").replace("|", "").trim().split("Recommended");
		//	System.out.println(s4[1]);
		//	System.out.println(s2[1]);
		//	System.out.println(s2[3]);
		//	System.out.println(s2[5]);
		//	System.out.println(s2[7]);
		//	System.out.println(s2[9]);
			Recommendation recommendation = new Recommendation();
			recommendation.setHasByWeeklyWeightage(Float.parseFloat(s2[1]));
			recommendation.setHasGoalConflict(Float.parseFloat(s2[7]));
			recommendation.setHasMonthlyWeightage(Float.parseFloat(s2[3]));
			recommendation.setHasWeeklyWeightage(Float.parseFloat(s2[5]));
			recommendation.setHasUserInterest(Float.parseFloat(s2[9]));
			Food food= new Food();
			
			food.setFoodName(s4[1]);
			if(food.getFoodName().equals("sprite"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("softdrink");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("butter"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("colesterol" );
			cat.add("veg" );
			cat.add("dairy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("brocolli"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("vegetables");
			cat.add("veg" );
			cat.add("healthy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("spinach"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("vegetables");
			cat.add("veg" );
			cat.add("healthy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("orange"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("fruits");
			cat.add("veg" );
			cat.add("healthy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("rocketLeaves"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("vegetables");
			cat.add("veg" );
			cat.add("healthy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("greekCheese"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("colesterol");
			cat.add("cheese" );
			cat.add("dairy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("mushroom"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("vegetables");
			cat.add("veg" );
			cat.add("healthy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("chickenLegs"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("colesterol");
			cat.add("chicken" );
			cat.add("nonveg");
				food.setCategoryList(cat);
			}
			
			else if(food.getFoodName().equals("bread"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("bread");
			cat.add("bakery" );

				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("capsicum"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("vegetables");
			cat.add("veg" );
			cat.add("healthy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("capsicum"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("vegetables");
			cat.add("veg" );
			cat.add("healthy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("irishApple"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("fruits");
			cat.add("veg" );
			cat.add("healthy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("noodles"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("asian");
			cat.add("veg" );
			cat.add("chinese");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("apple"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("fruits");
			cat.add("veg" );
			cat.add("healthy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("chickenBreasts"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("colesterol");
			cat.add("chicken" );
			cat.add("nonveg");
				food.setCategoryList(cat);
			}
			
			else if(food.getFoodName().equals("dietCoke"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("softdrink");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("coke"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("softdrink");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("sweeseCheese"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("colesterol");
			cat.add("cheese" );
			cat.add("dairy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("lowFatMilk"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("milk" );
			cat.add("veg" );
			cat.add("dairy");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("pepsi"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("softdrink");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("lamb"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("colesterol");
			cat.add("lamb" );
			cat.add("nonveg");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("fanta"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("softdrink");
				food.setCategoryList(cat);
			}
			else if(food.getFoodName().equals("kiwi"))
			{ArrayList<String> cat= new ArrayList<String>();
			cat.add("fruits");
			cat.add("veg" );
			cat.add("healthy");
				food.setCategoryList(cat);
			}
			
			hs.put(food, recommendation);
			
			}
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
			return hs;
		
		
		
	}
	

	public ArrayList<Food> getFoodAtShop(String shopName)
	{
	ArrayList<Food> foods= new ArrayList<Food>();
		try {
			
			String queryString = "PREFIX base:  <http://polychrest/ontology#>\n" + 
					"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"					select *\n" + 
					"where{\n" + 
					"\n" + 
					"base:"+shopName+"  base:sells   ?prod\n" + 
					"}\n" + 
					"";
			System.out.println(queryString);
			
			
			String s=	ReadOntology.query(queryString);
			System.out.println(s);
			String [] s1= s.split("<");
			for(int i=1;i<s1.length;i++)
			{
				Food food= new Food();
			String s3[]= s1[i].split("#");
		//	System.out.println(	s3[1].replace(">", "").replace("|", "").trim());
			food.setFoodName(s3[1].replace(">", "").replace("|", "").trim());
			foods.add(food);
			}
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return foods;
		
	}
	
	
	public ArrayList<Shop> getShopThatSellsFood(String foodName)
	{ArrayList<Shop> shops = new ArrayList<Shop>();
		try {
			
			String queryString = "PREFIX base:  <http://polychrest/ontology#>\n" + 
					"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"					select *\n" + 
					"where{\n" + 
					"\n" + 
					"?shop  base:sells    base:"+foodName+"\n" + 
					"}\n" + 
					"";
			
			System.out.println(queryString);
			
			String s=	ReadOntology.query(queryString);
			
			
			System.out.println(s);
			
			String [] s1= s.split("<");
			for(int i=1;i<s1.length;i++)
			{
				Shop shop= new Shop();
			String s3[]= s1[i].split("#");
		//	System.out.println(	s3[1].replace(">", "").replace("|", "").trim());
			shop.setShopName(s3[1].replace(">", "").replace("|", "").trim());
			shops.add(shop);
			}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return shops;
	}
	
	

	
	public void insertShoppingInstance(User user,Shopping shopping) {
		// TODO Auto-generated method stub
		try {
			String queryString = "PREFIX base:  <http://polychrest/ontology#>\n" + 
					"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"\n" + 
					"INSERT {\n" + 
					"base:"+shopping.getShoppingName()+"  a   base:shopping ;\n" + 
					"        base:atDateTime  \""+shopping.getAtDateTime()+ "\"; \n" + 
					"        base:atPrice    \""+shopping.getAtPrice()+"\" ;\n" + 
					"        base:atShop      base:"+shopping.getAtShop().getShopName()+ " ;\n" + 
					"        base:bought     base:"+shopping.getBought().getFoodName()+" ;\n" + 
					"        base:quantity   \""+shopping.getQuantity()+"\" .\n" + 
					"base:"+user.getName()+"  base:shopped            base:"+shopping.getShoppingName()+".\n" + 
					"}\n" + 
					"\n" + 
					"Where {\n" + 
					"base:"+user.getName()+" a base:user .\n" + 
					"base:"+shopping.getAtShop().getShopName()+ "  a       base:shop.\n" + 
					"base:"+shopping.getBought().getFoodName()+"  a             base:food.\n" + 
					"\n" + 
					"}";
					System.out.println(queryString);
			ReadOntology.insert(queryString) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Recommendation getRecommendationForUserAndFoodPair(User user, Food food) {
		// TODO Auto-generated method stub
		Recommendation recommendation=null;
		try {
			String foodName=food.getFoodName();
			char  firstChar=foodName.charAt(0);
			String foodNameInCamelCase=Character.toUpperCase(firstChar)+foodName.substring(1);
			System.out.println("foodNameInCamelCase: "+foodNameInCamelCase);
		String queryString = "PREFIX base:  <http://polychrest/ontology#>\n" + 
				"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
				"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
				"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
				"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
				"					select *\n" + 
				"where{\n" + 
				"\n" + 
				"base:"+user.getName()+"Recommended"+foodNameInCamelCase+"  a                         base:recommendation.\n" + 
				"base:"+user.getName()+"Recommended"+foodNameInCamelCase+"  base:hasWeelyWeightage                        ?WeelyWeightage.\n" + 
				"base:"+user.getName()+"Recommended"+foodNameInCamelCase+"  base:hasBiweelyWeightage                        ?BiWeelyWeightage.\n" + 
				"base:"+user.getName()+"Recommended"+foodNameInCamelCase+"  base:hasMonthlyWeightage                        ?MonthlyWeelyWeightage.\n" + 
				"base:"+user.getName()+"Recommended"+foodNameInCamelCase+"  base:hasUserInterest                        ?hasUserInterest.\n" + 
				"base:"+user.getName()+"Recommended"+foodNameInCamelCase+"  base:hasGoalConflict      ?goal                        .\n" + 
				//"base:"+user.getName()+"Recommended"+food.getFoodName()+"  base:isRelatedTo                        base:"+food.getFoodName()+".\n" + 
				

				"}\n" + 
				"";
		
		
		System.out.println(queryString);
		String s=	ReadOntology.query(queryString);
		System.out.println(s);
		String s1[]= s.split("\"");
	//	System.out.println("hasWeeklyWeightage"+s1[1]);
	//	System.out.println("hasBiWeeklyWeightage"+s1[3]);
	//	System.out.println("hasmonthlyWeightage"+s1[5]);
	//	System.out.println("hasUserIntrest"+s1[7]);
	//	System.out.println("goalCOnflict"+s1[9]);
		 recommendation= new Recommendation();
		 

		 recommendation.setHasUserInterest(Float.parseFloat( s1[7]));
		 recommendation.setHasByWeeklyWeightage(Float.parseFloat(s1[3]));
		 recommendation.setHasGoalConflict(Float.parseFloat( s1[9]));
		 recommendation.setHasWeeklyWeightage(Float.parseFloat(s1[1]));
		 recommendation.setHasMonthlyWeightage(Float.parseFloat( s1[5]));
		return recommendation;
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return recommendation;
	}

	public void updateRecommendationForUserAndFoodPair(User user, Food food, Recommendation oldRecommendation, Recommendation newRecommendation) {
		// TODO Auto-generated method stub
		try {
		String foodName=food.getFoodName();
		char  firstChar=foodName.charAt(0);
		String foodNameInCamelCase=Character.toUpperCase(firstChar)+foodName.substring(1);
	//	System.out.println("foodNameInCamelCase: "+foodNameInCamelCase);
			String queryString="PREFIX base:  <http://polychrest/ontology#>\n" + 
					"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"DELETE {"
					+ "base:"+user.getName()+"Recommended"+foodNameInCamelCase+" base:hasBiweelyWeightage  \""+oldRecommendation.getHasByWeeklyWeightage()+"\" ;\n" + 
					"        base:hasMonthlyWeightage \""+oldRecommendation.getHasMonthlyWeightage()+"\" ;\n" + 
					"  	base:hasUserInterest      \""+oldRecommendation.getHasUserInterest()+"\" ;\n" + 
					"  	base:hasGoalConflict      \""+oldRecommendation.getHasGoalConflict()+"\" ;\n" +
					"        base:hasWeelyWeightage   \""+oldRecommendation.getHasWeeklyWeightage()+"\" .}\n" +  
					"INSERT {\n" + 
					 "base:"+user.getName()+"Recommended"+foodNameInCamelCase+" base:hasBiweelyWeightage \""+newRecommendation.getHasByWeeklyWeightage()+"\" ;\n" + 
					"        base:hasMonthlyWeightage  \""+newRecommendation.getHasMonthlyWeightage()+"\" ;\n" + 
					"  	base:hasUserInterest      \""+newRecommendation.getHasUserInterest()+"\" ;\n" + 
					"  	base:hasGoalConflict      \""+newRecommendation.getHasGoalConflict()+"\" ;\n" +
					"        base:hasWeelyWeightage   \""+newRecommendation.getHasWeeklyWeightage()+"\" .}\n" +  
					"Where {\n" + 
					"base:"+user.getName()+"Recommended"+foodNameInCamelCase +"   base:isRelatedTo  "+  "base:"+foodName+"." +"\n" + 
					 
					"}";

		System.out.println(queryString);

		
		ReadOntology.insert(queryString) ;
		

	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		
	}

	public ArrayList<String> getFoodCategory(Food food) {
		// TODO Auto-generated method stub
		ArrayList<String> catt= new ArrayList<String>();
		try {
		//String foodName=food.getFoodName();
		//char  firstChar=foodName.charAt(0);
		//String foodNameInCamelCase=Character.toUpperCase(firstChar)+foodName.substring(1);
		//System.out.println("foodNameInCamelCase: "+foodNameInCamelCase);
	String queryString = "PREFIX base:  <http://polychrest/ontology#>\n" + 
			"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" + 
			"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
			"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
			"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
			"select * \n" + 
			"where{\n" + 
			"base:"+food.getFoodName()+" a              base:food ;\n" + 
			"base:ofCategory   ?cat.\n" + 
			"}";
	System.out.println(queryString);
	String s=	ReadOntology.query(queryString);
	System.out.println(s);
	
	String s1[] = s.split("\"");
	for(int i=1;i<s1.length;i=i+2) 
	{
	//System.out.println(s1[i]);
	
	catt.add(s1[i]);
		
	}
	}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return catt;
	}

	public ArrayList<String> getUserGoals(User user) {
		// TODO Auto-generated method stub
		
		
		ArrayList<String> catt= new ArrayList<String>();
		try {
		//String foodName=food.getFoodName();
		//char  firstChar=foodName.charAt(0);
		//String foodNameInCamelCase=Character.toUpperCase(firstChar)+foodName.substring(1);
		//System.out.println("foodNameInCamelCase: "+foodNameInCamelCase);
	String queryString = "PREFIX base:  <http://polychrest/ontology#>\n" + 
			"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" + 
			"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
			"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
			"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
			"select * \n" + 
			"where{\n" + 
			"base:"+user.getName()+"  a                 base:user ;\n" + 
			"        base:hasGoal          ?aa ;\n" + 
			"}";
	System.out.println(queryString);
	String s=	ReadOntology.query(queryString);
	System.out.println(s);
	
	String s1[] = s.split("\"");
	for(int i=1;i<s1.length;i=i+2) 
	{
	System.out.println(s1[i]);
	
	catt.add(s1[i]);
		
	}
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return catt;
	
	}
	
	public void updateUserGoal(User userOld,User userNew )
	{
		try {
			String goalOld=userOld.getGoalsList().get(0);
			String goalNew=userNew.getGoalsList().get(0);
				String queryString="PREFIX base:  <http://polychrest/ontology#>\n" + 
						"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
						"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
						"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
						"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
						"DELETE {\n" + 
						"base:"+userNew.getName()+"  base:hasGoal            \""+goalOld+"\" .\n" + 
						"				\n" + 
						"				\n" + 
						"				}\n" + 
						"INSERT {\n" + 
						"base:"+userNew.getName()+"  base:hasGoal            \""+goalNew+ "\" .					\n" + 
						"					\n" + 
						"					}\n" + 
						"Where {\n" + 
						"					\n" + 
						"	base:"+userNew.getName()+"  a                 base:user .				\n" + 
						"					}";

			System.out.println(queryString);

			
			ReadOntology.insert(queryString) ;
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
}