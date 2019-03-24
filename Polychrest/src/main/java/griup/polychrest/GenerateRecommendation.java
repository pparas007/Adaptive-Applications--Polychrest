package griup.polychrest;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.jena.atlas.io.InStreamASCII;

public class GenerateRecommendation {
	
	
	public static ArrayList<User> getAllUser()
	{
		ArrayList<User> allUsersList = new ArrayList<User>();		
		try {
		String queryString = "PREFIX base:  <http://polychrest/ontology#>\r\n" + 
				"PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
				"PREFIX owl:   <http://www.w3.org/2002/07/owl#>\r\n" + 
				"PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\r\n" + 
				"PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
				"\r\n" + 
				"select ?user where \r\n" + 
				"{\r\n" + 
				"?user a base:user\r\n" + 
				"}";
		
		
		
		String s=	ReadOntology.query(queryString);
		
		String cleanString = s.replaceAll("[^a-zA-Z0-9\\s+]", "").trim();
		String extractName = cleanString.replaceAll("httppolychrestontology", "").replaceAll("user", "");
		String strArray[] = extractName.split("\r");
		
		for(int i=0; i<strArray.length;i++) {
		User us = new User();
		us.setName(strArray[i].trim());
		allUsersList.add(us);
		}	
		for(int i=0; i<allUsersList.size();i++) {
			System.out.println(allUsersList.get(i).getName());
		}
		
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return allUsersList;
	}
	
	public static ArrayList<Shopping> getAll()
	{
		ArrayList<Shopping>allShopping = new ArrayList<Shopping>();
		
		try {
			String queryString = "					PREFIX base:  <http://polychrest/ontology#>\n" + 
					"					PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"					PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"					PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"					PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"					select * where\n" + 
					"{\n" + 
					"?shopping a base:shopping.\n" + 
					" ?shopping  base:atDateTime   ?date.\n" + 
					" ?shopping   base:atPrice  ?price.\n" + 
					" ?shopping       base:atShop      ?shop.\n" + 
					" ?shopping       base:atTime      ?time.\n" + 
					" ?shopping       base:bought      ?bought\n" + 
					"}\n" + 
					"";
			
			
			
			String s=	ReadOntology.query(queryString);
			
		
			
			
			System.out.println(s);
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	public static ArrayList<Shopping> getShoppingByUser(User user)
	{
		try {
			String name = user.getName();
			String queryString = "										PREFIX base:  <http://polychrest/ontology#>\n" + 
					"					PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"					PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"					PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"					PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"					select * \n" + 
					"Where {\n" + 
					//"?user  a base:user.\n" + 
					"base:"+name +" base:shopped ?shopping \n." + 
					" ?shopping  base:atDateTime   ?date.\n" + 
					" ?shopping   base:atPrice  ?price.\n" + 
					" ?shopping       base:atShop      ?shop.\n" + 
					" ?shopping       base:atTime      ?time.\n" + 
					" ?shopping       base:bought      ?bought.\n" + 
					//" FILTER (CONTAINS (?user, \"aditya\"))\n" + 
					"}";
			
			
			
			String s=	ReadOntology.query(queryString);
			
			
			System.out.println(s);
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	
	public static ArrayList<Recommendation> getRecommendationListForUser(User user)
	{
		try {
			String name = user.getName();
			String queryString = "					PREFIX base:  <http://polychrest/ontology#>\n" + 
					"					PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"					PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"					PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"					PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"					select *\n" + 
					"where{\n" + 
					"base:"+name+"  base:hasRecommendation ?recommendations.\n" + 
					"?recommendations  base:hasBiweelyWeightage ?biweekly.\n" + 
					"?recommendations  base:hasMonthlyWeightage ?monthly.\n" + 
					"?recommendations  base:hasWeelyWeightage ?weekly.\n" + 
					"}";
			
			
			
			String s=	ReadOntology.query(queryString);
			
			
			System.out.println(s);
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		
		
		
	}
	
	public static ArrayList<Food> getFoodAtShop(String shopName)
	{
	
		try {
			
			String queryString = "									PREFIX base:  <http://polychrest/ontology#>\n" + 
					"					PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"					PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"					PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"					PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"					select *\n" + 
					"where{\n" + 
					"\n" + 
					"base:"+shopName+"  base:sells   ?prod\n" + 
					"}\n" + 
					"";
			
			
			
			String s=	ReadOntology.query(queryString);
			
			
			System.out.println(s);
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		
	}
	
	public static ArrayList<Shop> getShopThatSellsFood(String foodName)
	{
try {
			
			String queryString = "									PREFIX base:  <http://polychrest/ontology#>\n" + 
					"					PREFIX rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" + 
					"					PREFIX owl:   <http://www.w3.org/2002/07/owl#>\n" + 
					"					PREFIX xsd:   <http://www.w3.org/2001/XMLSchema#>\n" + 
					"					PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\n" + 
					"					select *\n" + 
					"where{\n" + 
					"\n" + 
					"?shop  base:sells    base:"+foodName+"\n" + 
					"}\n" + 
					"";
			
			
			
			String s=	ReadOntology.query(queryString);
			
			
			System.out.println(s);
			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	
	
	public static void main(String[] args) throws Exception{
		User user = new User();
		user.setName("paras");
	//	getAllUser();
		getAll();
	//	getShoppingByUser(user);
	//	getRecommendationListForUser( user);
	//	getFoodAtShop("lidlCityCentre");
	//	getShopThatSellsFood("brownBread");
	}
}
