package griup.polychrest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.jena.datatypes.RDFDatatype;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.Ontology;
import org.apache.jena.rdf.model.Model;
//import org.apache.jena.ontology
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.XSD;

public class OntologyFactory{
	public static String link="http://polychrest/ontology";
	public static String base="http://polychrest/ontology#";
	public static String polychrest_ontology="resource\\polychrest_ontology.ttl";
	public static String owlForm="resource\\ontology_owl.owl";
	public static String ontology_owl_version = "resource\\ontologyInOwl.owl";
	public static String bbcOntologyOnline="https://www.bbc.co.uk/ontologies/fo/1.1.ttl";
	public static String bbcOntologyLocal="resource\\bbc.ttl";
	
	public static OntModel model;
	public static Ontology ontology;
	public static OntClass food, shop, user, shopping, recommendation;
	public static DatatypeProperty ofCategory, atPrice, hasShopName, hasShopAddress, hasShopType, quantity, atDateTime, hasName, hasFoodName,hasGoal, hasWeelyWeightage, hasBiweelyWeightage, hasMonthlyWeightage, hasUserInterest, isEnabled;
	public static ObjectProperty bought, atShop, shopped, sells, availableAt, isRelatedTo, hasRecommendation, recommendedTo;
    public static Individual paras,anirban,shubham,aditya;
    public static Individual euroGeneralFairview, tescoCityCentre, lidlCityCentre, lidlArtane;
    public static Individual dietCoke, bread,brownBread,coke, chickenBreasts, chickenLegs, sweeseCheese, greekCheese, butter, lowFatMilk, milk, apple, kiwi;
    public static Individual orange, spinach, mushroom, brocolli;
    
    public void intializeOntology() throws Exception{
		Files.copy( URI.create(bbcOntologyOnline).toURL().openStream(), Paths.get(bbcOntologyLocal),StandardCopyOption.REPLACE_EXISTING);
	}
	public void createOntology() throws Exception {
		model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		model.setNsPrefix("base", base);
		ontology = model.createOntology(link);
        ontology.addLabel("Ontology Designed for Adaptive application module Project-Group Polychrest", "en");
        ontology.addProperty(DCTerms.description, "Ontology Designed for Adaptive application module Project-Group Polychrest");
        ontology.addProperty(DCTerms.date, "08-Mar-2019");
        ontology.addProperty(DCTerms.contributor, "Anirban");
        ontology.addProperty(DCTerms.contributor, "Aditya");
        ontology.addProperty(DCTerms.contributor, "Shubham");
        ontology.addProperty(DCTerms.contributor, "Pavan");
        ontology.addProperty(DCTerms.contributor, "Gauransh");
        ontology.addProperty(DCTerms.contributor, "Paras");
        
        /*--------------------- External Resources ---------------------*/
        Model bbcOntology = RDFDataMgr.loadModel(bbcOntologyLocal);
        Resource bbcFood=bbcOntology.getResource("http://purl.org/ontology/fo/Food");
        Model dbpediaOntology = RDFDataMgr.loadModel("http://dbpedia.org/resource/classes#");
        Resource dbpediaFood=dbpediaOntology.getResource("http://dbpedia.org/ontology/Food");
        Resource dbpediaShop=dbpediaOntology.getResource("http://dbpedia.org/ontology/Shop");
        
        /*--------------------- Define Classes ---------------------*/
        food = model.createClass(base + "food");
        food.addLabel("food", "en");
        food.addSameAs(bbcFood); food.addSameAs(dbpediaFood);
        
        shop = model.createClass(base + "shop");
        shop.addLabel("any grocery shop", "en");
        shop.addSameAs(dbpediaShop);
        
        //TODO: add more properties to this class to create full-fledged user modeling
        user = model.createClass(base + "user");
        user.addLabel("app user", "en");
        
        shopping = model.createClass(base + "shopping");
        shopping.addLabel("user shopping details", "en");
        
        recommendation = model.createClass(base + "recommendation");
        recommendation.addLabel("recommendations for user", "en");
        /*--------------------- Define Properties ---------------------*/
        ofCategory = model.createDatatypeProperty(base + "ofCategory");
        ofCategory.addLabel("food category", "en");
        ofCategory.setDomain(food);
        ofCategory.setRange(XSD.xstring);
        atPrice = model.createDatatypeProperty(base + "atPrice");
        atPrice.addLabel("food price", "en");
        atPrice.setDomain(food);
        atPrice.setRange(XSD.xfloat);
        hasFoodName = model.createDatatypeProperty(base + "hasFoodName");
        hasFoodName.addLabel("food name", "en");
        hasFoodName.setDomain(food);
        hasFoodName.setRange(XSD.xstring);
        
        hasShopName = model.createDatatypeProperty(base + "hasShopName");
        hasShopName.addLabel("grocery shop name", "en");
        hasShopName.setDomain(shop);
        hasShopName.setRange(XSD.xstring);
        hasShopAddress = model.createDatatypeProperty(base + "hasShopAddress");
        hasShopAddress.addLabel("grocery shop address", "en");
        hasShopAddress.setDomain(shop);
        hasShopAddress.setRange(XSD.xstring);
        hasShopType = model.createDatatypeProperty(base + "hasShopType");
        hasShopType.addLabel("grocery shop type (eg. mall, supermarket, shop)", "en");
        hasShopType.setDomain(shop);
        hasShopType.setRange(XSD.xstring);
        
        availableAt = model.createObjectProperty(base + "availableAt");
        availableAt.addLabel("food item is available at this shop", "en");
        availableAt.setDomain(food);
        availableAt.setRange(shop);
        sells = model.createObjectProperty(base + "sells");
        sells.addLabel("this shop sells the food item", "en");
        sells.addInverseOf(availableAt);
        
        atShop = model.createObjectProperty(base + "atShop");
        atShop.addLabel("the user shopped at this shop", "en");
        atShop.setDomain(shopping);
        atShop.setRange(shop);
        bought = model.createObjectProperty(base + "bought");
        bought.addLabel("the user bought this item", "en");
        bought.setDomain(shopping);
        bought.setRange(food);
        quantity = model.createDatatypeProperty(base + "quantity");
        quantity.addLabel("quantity bought", "en");
        quantity.setDomain(shopping);
        quantity.setRange(XSD.xfloat);
        atDateTime = model.createDatatypeProperty(base + "atDateTime");
        atDateTime.addLabel("the user shopped at this date-time", "en");
        atDateTime.setDomain(shopping);
        atDateTime.setRange(XSD.date);
        
        hasName = model.createDatatypeProperty(base + "hasName");
        hasName.addLabel("user name", "en");
        hasName.setDomain(user);
        hasName.setRange(XSD.xstring);
        hasGoal = model.createDatatypeProperty(base + "hasGoal");
        hasGoal.addLabel("user goal", "en");
        hasGoal.setDomain(user);
        hasGoal.setRange(XSD.xstring);
        shopped = model.createObjectProperty(base + "shopped");
        shopped.addLabel("the user shopped this shopping", "en");
        shopped.setDomain(user);
        shopped.setRange(shopping);
        
        isRelatedTo = model.createObjectProperty(base + "isRelatedTo");
        isRelatedTo.addLabel("recommendation is related to this food item", "en");
        isRelatedTo.setDomain(recommendation);
        isRelatedTo.setRange(food);
        
        hasRecommendation = model.createObjectProperty(base + "hasRecommendation");
        hasRecommendation.addLabel("user has this recommendation", "en");
        hasRecommendation.setDomain(user);
        hasRecommendation.setRange(recommendation);
        
        recommendedTo = model.createObjectProperty(base + "recommendedTo");
        recommendedTo.addLabel("recommendation is recommended to this user", "en");
        recommendedTo.addInverseOf(hasRecommendation);
        
        hasWeelyWeightage = model.createDatatypeProperty(base + "hasWeelyWeightage");
        hasBiweelyWeightage = model.createDatatypeProperty(base + "hasBiweelyWeightage");
        hasMonthlyWeightage = model.createDatatypeProperty(base + "hasMonthlyWeightage");
        hasUserInterest = model.createDatatypeProperty(base + "hasUserInterest");
        hasWeelyWeightage.addLabel("denotes the weekly weightage for the recommendation", "en");
        hasBiweelyWeightage.addLabel("denotes the bi-weekly weightage for the recommendation", "en");
        hasMonthlyWeightage.addLabel("denotes the monthly weightage for the recommendation", "en");
        hasUserInterest.addLabel("denotes user's interest in an item", "en");
        hasWeelyWeightage.setDomain(recommendation);hasBiweelyWeightage.setDomain(recommendation);hasMonthlyWeightage.setDomain(recommendation);hasUserInterest.setDomain(recommendation);
        hasWeelyWeightage.setRange(XSD.xfloat);hasBiweelyWeightage.setRange(XSD.xfloat);hasMonthlyWeightage.setRange(XSD.xfloat);hasUserInterest.setRange(XSD.xfloat);
        
        isEnabled = model.createDatatypeProperty(base + "isEnabled");
        isEnabled.addLabel("denotes if recommendation is enabled to be shown to user", "en");
        isEnabled.setDomain(recommendation);
        isEnabled.setRange(XSD.xboolean);
	}
	
	public void createInstances() {
		/*--------------------- User Instances ---------------------*/
		paras=user.createIndividual(base+"paras");
		paras.addProperty(hasName, "paras");
		paras.addProperty(hasGoal, Constants.GOAL_ECONOMICAL);
		shubham=user.createIndividual(base+"shubham");
		shubham.addProperty(hasName, "shubham");
		shubham.addProperty(hasGoal, Constants.GOAL_VEGGIE);
		anirban=user.createIndividual(base+"anirban");
		anirban.addProperty(hasName, "anirban");
		anirban.addProperty(hasGoal, Constants.GOAL_MEAT_LOVER);
		anirban.addProperty(hasGoal, Constants.GOAL_ECONOMICAL);
		// New Users added
		
		aditya=user.createIndividual(base+"aditya");
		aditya.addProperty(hasName, "aditya");
		aditya.addProperty(hasGoal, Constants.GOAL_VEGGIE);
		aditya.addProperty(hasGoal, Constants.GOAL_MEAT_LOVER);
		
		/*--------------------- Shop Instances ---------------------*/
		lidlArtane=shop.createIndividual(base+"lidlArtane");
		lidlArtane.addProperty(hasShopName, "LiDL");
		lidlArtane.addProperty(hasShopAddress, "Artane, Dublin 5");
		lidlArtane.addProperty(hasShopType, Constants.SHOP_SUPERMARKET);
		lidlCityCentre=shop.createIndividual(base+"lidlCityCentre");
		lidlCityCentre.addProperty(hasShopName, "LiDL");
		lidlCityCentre.addProperty(hasShopAddress, "City Centre, Dublin");
		lidlCityCentre.addProperty(hasShopType, Constants.SHOP_SUPERMARKET);
		tescoCityCentre=shop.createIndividual(base+"tescoCityCentre");
		tescoCityCentre.addProperty(hasShopName, "Tesco");
		tescoCityCentre.addProperty(hasShopAddress, "City Centre, Dublin");
		tescoCityCentre.addProperty(hasShopType, Constants.SHOP_SUPERMARKET);
		euroGeneralFairview=shop.createIndividual(base+"euroGeneralFairview");
		euroGeneralFairview.addProperty(hasShopName, "Euro General");
		euroGeneralFairview.addProperty(hasShopAddress, "Fairview, Dublin");
		euroGeneralFairview.addProperty(hasShopType, Constants.SHOP_STORE);
		
		/*--------------------- Food Properties ---------------------*/
		spinach=food.createIndividual(base+"spinach");
		spinach.addProperty(hasFoodName, "spinach");
		spinach.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		spinach.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		spinach.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEGETABLES);
		mushroom=food.createIndividual(base+"mushroom");
		mushroom.addProperty(hasFoodName, "mushroom");
		mushroom.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		mushroom.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		mushroom.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEGETABLES);
		brocolli=food.createIndividual(base+"brocolli");
		brocolli.addProperty(hasFoodName, "brocolli");
		brocolli.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		brocolli.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		brocolli.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEGETABLES);
		
		
		orange=food.createIndividual(base+"orange");
		orange.addProperty(hasFoodName, "orange");
		orange.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		orange.addProperty(ofCategory, Constants.FOOD_CATEGORY_FRUITS);
		orange.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		kiwi=food.createIndividual(base+"kiwi");
		kiwi.addProperty(hasFoodName, "kiwi");
		kiwi.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		kiwi.addProperty(ofCategory, Constants.FOOD_CATEGORY_FRUITS);
		kiwi.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		apple=food.createIndividual(base+"apple");
		apple.addProperty(hasFoodName, "apple");
		apple.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		apple.addProperty(ofCategory, Constants.FOOD_CATEGORY_FRUITS);
		apple.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		
		lowFatMilk=food.createIndividual(base+"lowFatMilk");
		lowFatMilk.addProperty(hasFoodName, "lowFatMilk");
		lowFatMilk.addProperty(ofCategory, Constants.FOOD_CATEGORY_MILK);
		lowFatMilk.addProperty(ofCategory, Constants.FOOD_CATEGORY_DAIRY);
		lowFatMilk.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		milk=food.createIndividual(base+"milk");
		milk.addProperty(hasFoodName, "milk");
		milk.addProperty(ofCategory, Constants.FOOD_CATEGORY_MILK);
		milk.addProperty(ofCategory, Constants.FOOD_CATEGORY_DAIRY);
		milk.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		butter=food.createIndividual(base+"butter");
		butter.addProperty(hasFoodName, "butter");
		butter.addProperty(ofCategory, Constants.FOOD_CATEGORY_DAIRY);
		butter.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		butter.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);
		greekCheese=food.createIndividual(base+"greekCheese");
		greekCheese.addProperty(hasFoodName, "greekCheese");
		greekCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_DAIRY);
		greekCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHEESE);
		greekCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);
		sweeseCheese=food.createIndividual(base+"sweeseCheese");
		sweeseCheese.addProperty(hasFoodName, "sweeseCheese");
		sweeseCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_DAIRY);
		sweeseCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHEESE);
		sweeseCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);
		
		chickenLegs=food.createIndividual(base+"chickenLegs");
		chickenLegs.addProperty(hasFoodName, "chickenLegs");
		chickenLegs.addProperty(ofCategory, Constants.FOOD_CATEGORY_NONVEG);
		chickenLegs.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHICKEN);
		chickenLegs.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);
		chickenBreasts=food.createIndividual(base+"chickenBreasts");
		chickenBreasts.addProperty(hasFoodName, "chickenBreasts");
		chickenBreasts.addProperty(ofCategory, Constants.FOOD_CATEGORY_NONVEG);
		chickenBreasts.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHICKEN);
		chickenBreasts.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);
		
		coke=food.createIndividual(base+"coke");
		coke.addProperty(hasFoodName, "coke");
		coke.addProperty(ofCategory, Constants.FOOD_CATEGORY_SOFT_DRINK);
		
		dietCoke=food.createIndividual(base+"dietCoke");
		dietCoke.addProperty(hasFoodName, "dietCoke");
		dietCoke.addProperty(ofCategory, Constants.FOOD_CATEGORY_SOFT_DRINK);
		
		bread=food.createIndividual(base+"bread");
		bread.addProperty(hasFoodName, "bread");
		bread.addProperty(ofCategory, Constants.FOOD_CATEGORY_BAKERY);
		bread.addProperty(ofCategory, Constants.FOOD_CATEGORY_BREAD);
		brownBread=food.createIndividual(base+"brownBread");
		brownBread.addProperty(hasFoodName, "brownBread");
		brownBread.addProperty(ofCategory, Constants.FOOD_CATEGORY_BAKERY);
		brownBread.addProperty(ofCategory, Constants.FOOD_CATEGORY_BREAD);
		
		/*--------------------- Relate food and shops ---------------------*/
		lidlArtane.addProperty(sells, spinach);
		lidlArtane.addProperty(sells, mushroom);
		lidlArtane.addProperty(sells, brocolli);
		lidlArtane.addProperty(sells, orange);
		lidlArtane.addProperty(sells, kiwi);
		lidlArtane.addProperty(sells, apple);
		lidlArtane.addProperty(sells, lowFatMilk);
		lidlArtane.addProperty(sells, butter);
		lidlArtane.addProperty(sells, greekCheese);
		lidlArtane.addProperty(sells, sweeseCheese);
		lidlArtane.addProperty(sells, chickenLegs);
		lidlArtane.addProperty(sells, chickenBreasts);
		lidlArtane.addProperty(sells, coke);
		lidlArtane.addProperty(sells, dietCoke);
		lidlArtane.addProperty(sells, bread);
		
		lidlCityCentre.addProperty(sells, spinach);
		lidlCityCentre.addProperty(sells, mushroom);
		lidlCityCentre.addProperty(sells, orange);
		lidlCityCentre.addProperty(sells, kiwi);
		lidlCityCentre.addProperty(sells, apple);
		lidlCityCentre.addProperty(sells, lowFatMilk);
		lidlCityCentre.addProperty(sells, butter);
		lidlCityCentre.addProperty(sells, greekCheese);
		lidlCityCentre.addProperty(sells, sweeseCheese);
		lidlCityCentre.addProperty(sells, chickenLegs);
		lidlCityCentre.addProperty(sells, chickenBreasts);
		lidlCityCentre.addProperty(sells, coke);
		lidlCityCentre.addProperty(sells, bread);
		lidlCityCentre.addProperty(sells, brownBread);
		
		tescoCityCentre.addProperty(sells, spinach);
		tescoCityCentre.addProperty(sells, mushroom);
		tescoCityCentre.addProperty(sells, brocolli);
		tescoCityCentre.addProperty(sells, orange);
		tescoCityCentre.addProperty(sells, kiwi);
		tescoCityCentre.addProperty(sells, apple);
		tescoCityCentre.addProperty(sells, lowFatMilk);
		tescoCityCentre.addProperty(sells, butter);
		tescoCityCentre.addProperty(sells, greekCheese);
		tescoCityCentre.addProperty(sells, sweeseCheese);
		tescoCityCentre.addProperty(sells, chickenLegs);
		tescoCityCentre.addProperty(sells, chickenBreasts);
		tescoCityCentre.addProperty(sells, coke);
		tescoCityCentre.addProperty(sells, dietCoke);
		tescoCityCentre.addProperty(sells, bread);
		tescoCityCentre.addProperty(sells, brownBread);
		
		euroGeneralFairview.addProperty(sells, spinach);
		euroGeneralFairview.addProperty(sells, orange);
		euroGeneralFairview.addProperty(sells, apple);
		euroGeneralFairview.addProperty(sells, butter);
		euroGeneralFairview.addProperty(sells, chickenLegs);
		euroGeneralFairview.addProperty(sells, chickenBreasts);
		euroGeneralFairview.addProperty(sells, coke);
		euroGeneralFairview.addProperty(sells, bread);
		
		
		/*--------------------- Shopping Instances ---------------------*/
		//shopping instance 1
		//paras shops 3 items on a day
		Individual parasShops11=shopping.createIndividual(base+"parasShops11");
		parasShops11.addProperty(atShop, lidlArtane);
		parasShops11.addProperty(bought, kiwi);
		parasShops11.addProperty(atDateTime, "08-Mar-2019");
		parasShops11.addProperty(quantity, "1");
		parasShops11.addProperty(atPrice, "1");
		Individual parasShops12=shopping.createIndividual(base+"parasShops12");
		parasShops12.addProperty(atShop, lidlArtane);
		parasShops12.addProperty(bought, apple);
		parasShops12.addProperty(atDateTime, "08-Mar-2019");
		parasShops12.addProperty(quantity, "1");
		parasShops12.addProperty(atPrice, "2");
		Individual parasShops13=shopping.createIndividual(base+"parasShops13");
		parasShops13.addProperty(atShop, lidlArtane);
		parasShops13.addProperty(bought, chickenLegs);
		parasShops13.addProperty(atDateTime, "08-Mar-2019");
		parasShops13.addProperty(quantity, "1");
		parasShops13.addProperty(atPrice, "2.5");
		
		paras.addProperty(shopped, parasShops11); paras.addProperty(shopped, parasShops12); paras.addProperty(shopped, parasShops13);
		
		//shopping instance 2
		//paras shops 4 items on another day
		Individual parasShops21=shopping.createIndividual(base+"parasShops21");
		parasShops21.addProperty(atShop, lidlArtane);
		parasShops21.addProperty(bought, kiwi);
		parasShops21.addProperty(atDateTime, "15-Mar-2019");
		parasShops21.addProperty(quantity, "1");
		parasShops21.addProperty(atPrice, "1");
		Individual parasShops22=shopping.createIndividual(base+"parasShops22");
		parasShops22.addProperty(atShop, lidlArtane);
		parasShops22.addProperty(bought, orange);
		parasShops22.addProperty(atDateTime, "15-Mar-2019");
		parasShops22.addProperty(quantity, "2");
		parasShops22.addProperty(atPrice, "2");
		Individual parasShops23=shopping.createIndividual(base+"parasShops23");
		parasShops23.addProperty(atShop, lidlArtane);
		parasShops23.addProperty(bought, chickenLegs);
		parasShops23.addProperty(atDateTime, "15-Mar-2019");
		parasShops23.addProperty(quantity, "1");
		parasShops23.addProperty(atPrice, "2.5");

		
		paras.addProperty(shopped, parasShops21); paras.addProperty(shopped, parasShops22); paras.addProperty(shopped, parasShops23);
		
		/*--------------------- Shopping Instances for anirban ---------------------*/
		//shopping instance 1
		//anirban shops 4 items on a day
		Individual anirbanShops11=shopping.createIndividual(base+"anirbanShops11");
		anirbanShops11.addProperty(atShop, lidlCityCentre);
		anirbanShops11.addProperty(bought, bread);
		anirbanShops11.addProperty(atDateTime, "06-Mar-2019");
		anirbanShops11.addProperty(quantity, "1");
		anirbanShops11.addProperty(atPrice, "1.5");
		Individual anirbanShops12=shopping.createIndividual(base+"anirbanShops12");
		anirbanShops12.addProperty(atShop, lidlCityCentre);
		anirbanShops12.addProperty(bought, coke);
		anirbanShops12.addProperty(atDateTime, "06-Mar-2019");
		anirbanShops12.addProperty(quantity, "2");
		anirbanShops12.addProperty(atPrice, "2.5");

		
		
		anirban.addProperty(shopped, anirbanShops11); anirban.addProperty(shopped, anirbanShops12); 
		
		//shopping instance 2
		//anirban shops 5 items on another day
		Individual anirbanShops21=shopping.createIndividual(base+"anirbanShops21");
		anirbanShops21.addProperty(atShop, lidlCityCentre);
		anirbanShops21.addProperty(bought, bread);
		anirbanShops21.addProperty(atDateTime, "14-Mar-2019");
		anirbanShops21.addProperty(quantity, "1");
		anirbanShops21.addProperty(atPrice, "1.5");
		Individual anirbanShops22=shopping.createIndividual(base+"anirbanShops22");
		anirbanShops22.addProperty(atShop, lidlCityCentre);
		anirbanShops22.addProperty(bought, coke);
		anirbanShops22.addProperty(atDateTime, "14-Mar-2019");
		anirbanShops22.addProperty(quantity, "2");
		anirbanShops22.addProperty(atPrice, "2.5");
		
		Individual anirbanShops24=shopping.createIndividual(base+"anirbanShops24");
		anirbanShops24.addProperty(atShop, lidlCityCentre);
		anirbanShops24.addProperty(bought, butter);
		anirbanShops24.addProperty(atDateTime, "14-Mar-2019");
		anirbanShops24.addProperty(quantity, "1");
		anirbanShops24.addProperty(atPrice, "1.5");

		
		anirban.addProperty(shopped, anirbanShops21); anirban.addProperty(shopped, anirbanShops22);  anirban.addProperty(shopped, anirbanShops24);
		
		/*--------------------- Shopping Instances for Aditya---------------------*/
		//shopping instance 1
		//aditya shops 3 items on a day
		
		Individual adityaShops12=shopping.createIndividual(base+"adityaShops12");
		adityaShops12.addProperty(atShop, tescoCityCentre);
		adityaShops12.addProperty(bought, lowFatMilk);
		adityaShops12.addProperty(atDateTime, "07-Mar-2019");
		adityaShops12.addProperty(quantity, "1");
		adityaShops12.addProperty(atPrice, "1.8");
		Individual adityaShops13=shopping.createIndividual(base+"adityaShops13");
		adityaShops13.addProperty(atShop, tescoCityCentre);
		adityaShops13.addProperty(bought, chickenLegs);
		adityaShops13.addProperty(atDateTime, "07-Mar-2019");
		adityaShops13.addProperty(quantity, "1");
		adityaShops13.addProperty(atPrice, "2.5");
		
		aditya.addProperty(shopped, adityaShops12); aditya.addProperty(shopped, adityaShops13);
		
		//shopping instance 2
		//aditya shops 5 items on another day
		Individual adityaShops22=shopping.createIndividual(base+"adityaShops22");
		adityaShops22.addProperty(atShop, tescoCityCentre);
		adityaShops22.addProperty(bought, lowFatMilk);
		adityaShops22.addProperty(atDateTime, "16-Mar-2019");
		adityaShops22.addProperty(quantity, "1");
		adityaShops22.addProperty(atPrice, "1.8");
		Individual adityaShops23=shopping.createIndividual(base+"adityaShops23");
		adityaShops23.addProperty(atShop, tescoCityCentre);
		adityaShops23.addProperty(bought, chickenLegs);
		adityaShops23.addProperty(atDateTime, "16-Mar-2019");
		adityaShops23.addProperty(quantity, "1");
		adityaShops23.addProperty(atPrice, "2.5");
		Individual adityaShops24=shopping.createIndividual(base+"adityaShops24");
		adityaShops24.addProperty(atShop, tescoCityCentre);
		adityaShops24.addProperty(bought, spinach);
		adityaShops24.addProperty(atDateTime, "16-Mar-2019");
		adityaShops24.addProperty(quantity, "1");
		adityaShops24.addProperty(atPrice, "1");
		
		aditya.addProperty(shopped, adityaShops22); aditya.addProperty(shopped, adityaShops23); aditya.addProperty(shopped, adityaShops24);
		
	}
	
	public void createRecommendationInstances() {
		//this sample recommendations entries are created based on the above shopping instances
		//paras
		Individual parasRecommendedDietCoke=recommendation.createIndividual(base+"parasRecommendedDietCoke");
		parasRecommendedDietCoke.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedDietCoke.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedDietCoke.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedDietCoke.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedDietCoke.addProperty(isRelatedTo, dietCoke);		
		paras.addProperty(hasRecommendation, parasRecommendedDietCoke);
		
		Individual parasRecommendedBread=recommendation.createIndividual(base+"parasRecommendedBread");
		parasRecommendedBread.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedBread.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedBread.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedBread.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedBread.addProperty(isRelatedTo, bread);		
		paras.addProperty(hasRecommendation, parasRecommendedBread);
		
		Individual parasRecommendedBrownBread=recommendation.createIndividual(base+"parasRecommendedBrownBread");
		parasRecommendedBrownBread.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedBrownBread.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedBrownBread.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedBrownBread.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedBrownBread.addProperty(isRelatedTo, brownBread);		
		paras.addProperty(hasRecommendation, parasRecommendedBrownBread);

		Individual parasRecommendedCoke=recommendation.createIndividual(base+"parasRecommendedCoke");
		parasRecommendedCoke.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedCoke.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedCoke.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedCoke.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedCoke.addProperty(isRelatedTo, coke);		
		paras.addProperty(hasRecommendation, parasRecommendedCoke);
		
		Individual parasRecommendedChickenBreasts=recommendation.createIndividual(base+"parasRecommendedChickenBreasts");
		parasRecommendedChickenBreasts.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedChickenBreasts.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedChickenBreasts.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedChickenBreasts.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedChickenBreasts.addProperty(isRelatedTo, chickenBreasts);		
		paras.addProperty(hasRecommendation, parasRecommendedChickenBreasts);
		
		Individual parasRecommendedSweeseCheese=recommendation.createIndividual(base+"parasRecommendedSweeseCheese");
		parasRecommendedSweeseCheese.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedSweeseCheese.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedSweeseCheese.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedSweeseCheese.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedSweeseCheese.addProperty(isRelatedTo, sweeseCheese);		
		paras.addProperty(hasRecommendation, parasRecommendedSweeseCheese);
		
		Individual parasRecommendedGreekCheese=recommendation.createIndividual(base+"parasRecommendedGreekCheese");
		parasRecommendedGreekCheese.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedGreekCheese.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedGreekCheese.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedGreekCheese.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedGreekCheese.addProperty(isRelatedTo, greekCheese);		
		paras.addProperty(hasRecommendation, parasRecommendedGreekCheese);
		
		Individual parasRecommendedButter=recommendation.createIndividual(base+"parasRecommendedButter");
		parasRecommendedButter.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedButter.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedButter.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedButter.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedButter.addProperty(isRelatedTo, butter);		
		paras.addProperty(hasRecommendation, parasRecommendedButter);
		
		Individual parasRecommendedLowFatMilk=recommendation.createIndividual(base+"parasRecommendedLowFatMilk");
		parasRecommendedLowFatMilk.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedLowFatMilk.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedLowFatMilk.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedLowFatMilk.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedLowFatMilk.addProperty(isRelatedTo, lowFatMilk);		
		paras.addProperty(hasRecommendation, parasRecommendedLowFatMilk);
		
		Individual parasRecommendedMilk=recommendation.createIndividual(base+"parasRecommendedMilk");
		parasRecommendedMilk.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedMilk.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedMilk.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedMilk.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedMilk.addProperty(isRelatedTo, milk);		
		paras.addProperty(hasRecommendation, parasRecommendedMilk);
		
		Individual parasRecommendedSpinach=recommendation.createIndividual(base+"parasRecommendedSpinach");
		parasRecommendedSpinach.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedSpinach.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedSpinach.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedSpinach.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedSpinach.addProperty(isRelatedTo, spinach);		
		paras.addProperty(hasRecommendation, parasRecommendedSpinach);
		
		Individual parasRecommendedMushroom=recommendation.createIndividual(base+"parasRecommendedMushroom");
		parasRecommendedMushroom.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedMushroom.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedMushroom.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedMushroom.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedMushroom.addProperty(isRelatedTo, mushroom);		
		paras.addProperty(hasRecommendation, parasRecommendedMushroom);
		
		Individual parasRecommendedBrocolli=recommendation.createIndividual(base+"parasRecommendedBrocolli");
		parasRecommendedBrocolli.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedBrocolli.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedBrocolli.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedBrocolli.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedBrocolli.addProperty(isRelatedTo, brocolli);		
		paras.addProperty(hasRecommendation, parasRecommendedBrocolli);
		
		Individual parasRecommendedKiwi=recommendation.createIndividual(base+"parasRecommendedKiwi");
		parasRecommendedKiwi.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedKiwi.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedKiwi.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedKiwi.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedKiwi.addProperty(isRelatedTo, kiwi);		
		paras.addProperty(hasRecommendation, parasRecommendedKiwi);
		
		
		Individual parasRecommendedApple=recommendation.createIndividual(base+"parasRecommendedApple");
		parasRecommendedApple.addProperty(hasWeelyWeightage, "0.1");
		parasRecommendedApple.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedApple.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedApple.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedApple.addProperty(isRelatedTo, apple);
		paras.addProperty(hasRecommendation, parasRecommendedApple);
		
		
		Individual parasRecommendedOrange=recommendation.createIndividual(base+"parasRecommendedOrange");
		parasRecommendedOrange.addProperty(hasWeelyWeightage, "0.1");
		parasRecommendedOrange.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedOrange.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedOrange.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedOrange.addProperty(isRelatedTo, orange);
		paras.addProperty(hasRecommendation, parasRecommendedOrange);
		
		
		Individual parasRecommendedChickenLegs=recommendation.createIndividual(base+"parasRecommendedChickenLegs");
		parasRecommendedChickenLegs.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedChickenLegs.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedChickenLegs.addProperty(hasMonthlyWeightage, "0.1");
		parasRecommendedChickenLegs.addProperty(hasUserInterest, "0.5");
		
		parasRecommendedChickenLegs.addProperty(isRelatedTo, chickenLegs);
		paras.addProperty(hasRecommendation, parasRecommendedChickenLegs);
				
		
		//anirban
		Individual anirbanRecommendedDietCoke=recommendation.createIndividual(base+"anirbanRecommendedDietCoke");
		anirbanRecommendedDietCoke.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedDietCoke.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedDietCoke.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedDietCoke.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedDietCoke.addProperty(isRelatedTo, dietCoke);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedDietCoke);
		
		Individual anirbanRecommendedBread=recommendation.createIndividual(base+"anirbanRecommendedBread");
		anirbanRecommendedBread.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedBread.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedBread.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedBread.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedBread.addProperty(isRelatedTo, bread);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedBread);
		
		Individual anirbanRecommendedBrownBread=recommendation.createIndividual(base+"anirbanRecommendedBrownBread");
		anirbanRecommendedBrownBread.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedBrownBread.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedBrownBread.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedBrownBread.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedBrownBread.addProperty(isRelatedTo, brownBread);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedBrownBread);

		Individual anirbanRecommendedCoke=recommendation.createIndividual(base+"anirbanRecommendedCoke");
		anirbanRecommendedCoke.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedCoke.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedCoke.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedCoke.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedCoke.addProperty(isRelatedTo, coke);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedCoke);
		
		Individual anirbanRecommendedChickenBreasts=recommendation.createIndividual(base+"anirbanRecommendedChickenBreasts");
		anirbanRecommendedChickenBreasts.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedChickenBreasts.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedChickenBreasts.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedChickenBreasts.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedChickenBreasts.addProperty(isRelatedTo, chickenBreasts);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedChickenBreasts);
		
		Individual anirbanRecommendedSweeseCheese=recommendation.createIndividual(base+"anirbanRecommendedSweeseCheese");
		anirbanRecommendedSweeseCheese.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedSweeseCheese.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedSweeseCheese.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedSweeseCheese.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedSweeseCheese.addProperty(isRelatedTo, sweeseCheese);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedSweeseCheese);
		
		Individual anirbanRecommendedGreekCheese=recommendation.createIndividual(base+"anirbanRecommendedGreekCheese");
		anirbanRecommendedGreekCheese.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedGreekCheese.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedGreekCheese.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedGreekCheese.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedGreekCheese.addProperty(isRelatedTo, greekCheese);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedGreekCheese);
		
		Individual anirbanRecommendedButter=recommendation.createIndividual(base+"anirbanRecommendedButter");
		anirbanRecommendedButter.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedButter.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedButter.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedButter.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedButter.addProperty(isRelatedTo, butter);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedButter);
		
		Individual anirbanRecommendedLowFatMilk=recommendation.createIndividual(base+"anirbanRecommendedLowFatMilk");
		anirbanRecommendedLowFatMilk.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedLowFatMilk.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedLowFatMilk.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedLowFatMilk.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedLowFatMilk.addProperty(isRelatedTo, lowFatMilk);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedLowFatMilk);
		
		Individual anirbanRecommendedMilk=recommendation.createIndividual(base+"anirbanRecommendedMilk");
		anirbanRecommendedMilk.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedMilk.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedMilk.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedMilk.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedMilk.addProperty(isRelatedTo, milk);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedMilk);
		
		Individual anirbanRecommendedSpinach=recommendation.createIndividual(base+"anirbanRecommendedSpinach");
		anirbanRecommendedSpinach.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedSpinach.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedSpinach.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedSpinach.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedSpinach.addProperty(isRelatedTo, spinach);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedSpinach);
		
		Individual anirbanRecommendedMushroom=recommendation.createIndividual(base+"anirbanRecommendedMushroom");
		anirbanRecommendedMushroom.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedMushroom.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedMushroom.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedMushroom.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedMushroom.addProperty(isRelatedTo, mushroom);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedMushroom);
		
		Individual anirbanRecommendedBrocolli=recommendation.createIndividual(base+"anirbanRecommendedBrocolli");
		anirbanRecommendedBrocolli.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedBrocolli.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedBrocolli.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedBrocolli.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedBrocolli.addProperty(isRelatedTo, brocolli);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedBrocolli);
		
		Individual anirbanRecommendedKiwi=recommendation.createIndividual(base+"anirbanRecommendedKiwi");
		anirbanRecommendedKiwi.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedKiwi.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedKiwi.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedKiwi.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedKiwi.addProperty(isRelatedTo, kiwi);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedKiwi);
		
		
		Individual anirbanRecommendedApple=recommendation.createIndividual(base+"anirbanRecommendedApple");
		anirbanRecommendedApple.addProperty(hasWeelyWeightage, "0.1");
		anirbanRecommendedApple.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedApple.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedApple.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedApple.addProperty(isRelatedTo, apple);
		anirban.addProperty(hasRecommendation, anirbanRecommendedApple);
		
		
		Individual anirbanRecommendedOrange=recommendation.createIndividual(base+"anirbanRecommendedOrange");
		anirbanRecommendedOrange.addProperty(hasWeelyWeightage, "0.1");
		anirbanRecommendedOrange.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedOrange.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedOrange.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedOrange.addProperty(isRelatedTo, orange);
		anirban.addProperty(hasRecommendation, anirbanRecommendedOrange);
		
		
		Individual anirbanRecommendedChickenLegs=recommendation.createIndividual(base+"anirbanRecommendedChickenLegs");
		anirbanRecommendedChickenLegs.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedChickenLegs.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedChickenLegs.addProperty(hasMonthlyWeightage, "0.1");
		anirbanRecommendedChickenLegs.addProperty(hasUserInterest, "0.5");
		
		anirbanRecommendedChickenLegs.addProperty(isRelatedTo, chickenLegs);
		anirban.addProperty(hasRecommendation, anirbanRecommendedChickenLegs);
		
		
		//shubham
		Individual shubhamRecommendedDietCoke=recommendation.createIndividual(base+"shubhamRecommendedDietCoke");
		shubhamRecommendedDietCoke.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedDietCoke.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedDietCoke.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedDietCoke.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedDietCoke.addProperty(isRelatedTo, dietCoke);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedDietCoke);
		
		Individual shubhamRecommendedBread=recommendation.createIndividual(base+"shubhamRecommendedBread");
		shubhamRecommendedBread.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedBread.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedBread.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedBread.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedBread.addProperty(isRelatedTo, bread);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedBread);
		
		Individual shubhamRecommendedBrownBread=recommendation.createIndividual(base+"shubhamRecommendedBrownBread");
		shubhamRecommendedBrownBread.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedBrownBread.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedBrownBread.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedBrownBread.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedBrownBread.addProperty(isRelatedTo, brownBread);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedBrownBread);

		Individual shubhamRecommendedCoke=recommendation.createIndividual(base+"shubhamRecommendedCoke");
		shubhamRecommendedCoke.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedCoke.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedCoke.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedCoke.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedCoke.addProperty(isRelatedTo, coke);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedCoke);
		
		Individual shubhamRecommendedChickenBreasts=recommendation.createIndividual(base+"shubhamRecommendedChickenBreasts");
		shubhamRecommendedChickenBreasts.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedChickenBreasts.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedChickenBreasts.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedChickenBreasts.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedChickenBreasts.addProperty(isRelatedTo, chickenBreasts);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedChickenBreasts);
		
		Individual shubhamRecommendedSweeseCheese=recommendation.createIndividual(base+"shubhamRecommendedSweeseCheese");
		shubhamRecommendedSweeseCheese.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedSweeseCheese.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedSweeseCheese.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedSweeseCheese.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedSweeseCheese.addProperty(isRelatedTo, sweeseCheese);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedSweeseCheese);
		
		Individual shubhamRecommendedGreekCheese=recommendation.createIndividual(base+"shubhamRecommendedGreekCheese");
		shubhamRecommendedGreekCheese.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedGreekCheese.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedGreekCheese.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedGreekCheese.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedGreekCheese.addProperty(isRelatedTo, greekCheese);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedGreekCheese);
		
		Individual shubhamRecommendedButter=recommendation.createIndividual(base+"shubhamRecommendedButter");
		shubhamRecommendedButter.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedButter.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedButter.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedButter.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedButter.addProperty(isRelatedTo, butter);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedButter);
		
		Individual shubhamRecommendedLowFatMilk=recommendation.createIndividual(base+"shubhamRecommendedLowFatMilk");
		shubhamRecommendedLowFatMilk.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedLowFatMilk.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedLowFatMilk.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedLowFatMilk.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedLowFatMilk.addProperty(isRelatedTo, lowFatMilk);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedLowFatMilk);
		
		Individual shubhamRecommendedMilk=recommendation.createIndividual(base+"shubhamRecommendedMilk");
		shubhamRecommendedMilk.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedMilk.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedMilk.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedMilk.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedMilk.addProperty(isRelatedTo, milk);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedMilk);
		
		Individual shubhamRecommendedSpinach=recommendation.createIndividual(base+"shubhamRecommendedSpinach");
		shubhamRecommendedSpinach.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedSpinach.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedSpinach.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedSpinach.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedSpinach.addProperty(isRelatedTo, spinach);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedSpinach);
		
		Individual shubhamRecommendedMushroom=recommendation.createIndividual(base+"shubhamRecommendedMushroom");
		shubhamRecommendedMushroom.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedMushroom.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedMushroom.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedMushroom.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedMushroom.addProperty(isRelatedTo, mushroom);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedMushroom);
		
		Individual shubhamRecommendedBrocolli=recommendation.createIndividual(base+"shubhamRecommendedBrocolli");
		shubhamRecommendedBrocolli.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedBrocolli.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedBrocolli.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedBrocolli.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedBrocolli.addProperty(isRelatedTo, brocolli);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedBrocolli);
		
		Individual shubhamRecommendedKiwi=recommendation.createIndividual(base+"shubhamRecommendedKiwi");
		shubhamRecommendedKiwi.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedKiwi.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedKiwi.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedKiwi.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedKiwi.addProperty(isRelatedTo, kiwi);		
		shubham.addProperty(hasRecommendation, shubhamRecommendedKiwi);
		
		
		Individual shubhamRecommendedApple=recommendation.createIndividual(base+"shubhamRecommendedApple");
		shubhamRecommendedApple.addProperty(hasWeelyWeightage, "0.1");
		shubhamRecommendedApple.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedApple.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedApple.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedApple.addProperty(isRelatedTo, apple);
		shubham.addProperty(hasRecommendation, shubhamRecommendedApple);
		
		
		Individual shubhamRecommendedOrange=recommendation.createIndividual(base+"shubhamRecommendedOrange");
		shubhamRecommendedOrange.addProperty(hasWeelyWeightage, "0.1");
		shubhamRecommendedOrange.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedOrange.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedOrange.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedOrange.addProperty(isRelatedTo, orange);
		shubham.addProperty(hasRecommendation, shubhamRecommendedOrange);
		
		
		Individual shubhamRecommendedChickenLegs=recommendation.createIndividual(base+"shubhamRecommendedChickenLegs");
		shubhamRecommendedChickenLegs.addProperty(hasWeelyWeightage, "0.2");
		shubhamRecommendedChickenLegs.addProperty(hasBiweelyWeightage, "0.1");
		shubhamRecommendedChickenLegs.addProperty(hasMonthlyWeightage, "0.1");
		shubhamRecommendedChickenLegs.addProperty(hasUserInterest, "0.5");
		
		shubhamRecommendedChickenLegs.addProperty(isRelatedTo, chickenLegs);
		shubham.addProperty(hasRecommendation, shubhamRecommendedChickenLegs);
		
		
		//aditya
		Individual adityaRecommendedDietCoke=recommendation.createIndividual(base+"adityaRecommendedDietCoke");
		adityaRecommendedDietCoke.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedDietCoke.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedDietCoke.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedDietCoke.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedDietCoke.addProperty(isRelatedTo, dietCoke);		
		aditya.addProperty(hasRecommendation, adityaRecommendedDietCoke);
		
		Individual adityaRecommendedBread=recommendation.createIndividual(base+"adityaRecommendedBread");
		adityaRecommendedBread.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedBread.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedBread.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedBread.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedBread.addProperty(isRelatedTo, bread);		
		aditya.addProperty(hasRecommendation, adityaRecommendedBread);
		
		Individual adityaRecommendedBrownBread=recommendation.createIndividual(base+"adityaRecommendedBrownBread");
		adityaRecommendedBrownBread.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedBrownBread.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedBrownBread.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedBrownBread.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedBrownBread.addProperty(isRelatedTo, brownBread);		
		aditya.addProperty(hasRecommendation, adityaRecommendedBrownBread);

		Individual adityaRecommendedCoke=recommendation.createIndividual(base+"adityaRecommendedCoke");
		adityaRecommendedCoke.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedCoke.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedCoke.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedCoke.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedCoke.addProperty(isRelatedTo, coke);		
		aditya.addProperty(hasRecommendation, adityaRecommendedCoke);
		
		Individual adityaRecommendedChickenBreasts=recommendation.createIndividual(base+"adityaRecommendedChickenBreasts");
		adityaRecommendedChickenBreasts.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedChickenBreasts.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedChickenBreasts.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedChickenBreasts.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedChickenBreasts.addProperty(isRelatedTo, chickenBreasts);		
		aditya.addProperty(hasRecommendation, adityaRecommendedChickenBreasts);
		
		Individual adityaRecommendedSweeseCheese=recommendation.createIndividual(base+"adityaRecommendedSweeseCheese");
		adityaRecommendedSweeseCheese.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedSweeseCheese.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedSweeseCheese.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedSweeseCheese.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedSweeseCheese.addProperty(isRelatedTo, sweeseCheese);		
		aditya.addProperty(hasRecommendation, adityaRecommendedSweeseCheese);
		
		Individual adityaRecommendedGreekCheese=recommendation.createIndividual(base+"adityaRecommendedGreekCheese");
		adityaRecommendedGreekCheese.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedGreekCheese.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedGreekCheese.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedGreekCheese.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedGreekCheese.addProperty(isRelatedTo, greekCheese);		
		aditya.addProperty(hasRecommendation, adityaRecommendedGreekCheese);
		
		Individual adityaRecommendedButter=recommendation.createIndividual(base+"adityaRecommendedButter");
		adityaRecommendedButter.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedButter.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedButter.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedButter.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedButter.addProperty(isRelatedTo, butter);		
		aditya.addProperty(hasRecommendation, adityaRecommendedButter);
		
		Individual adityaRecommendedLowFatMilk=recommendation.createIndividual(base+"adityaRecommendedLowFatMilk");
		adityaRecommendedLowFatMilk.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedLowFatMilk.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedLowFatMilk.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedLowFatMilk.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedLowFatMilk.addProperty(isRelatedTo, lowFatMilk);		
		aditya.addProperty(hasRecommendation, adityaRecommendedLowFatMilk);
		
		Individual adityaRecommendedMilk=recommendation.createIndividual(base+"adityaRecommendedMilk");
		adityaRecommendedMilk.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedMilk.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedMilk.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedMilk.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedMilk.addProperty(isRelatedTo, milk);		
		aditya.addProperty(hasRecommendation, adityaRecommendedMilk);
		
		Individual adityaRecommendedSpinach=recommendation.createIndividual(base+"adityaRecommendedSpinach");
		adityaRecommendedSpinach.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedSpinach.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedSpinach.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedSpinach.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedSpinach.addProperty(isRelatedTo, spinach);		
		aditya.addProperty(hasRecommendation, adityaRecommendedSpinach);
		
		Individual adityaRecommendedMushroom=recommendation.createIndividual(base+"adityaRecommendedMushroom");
		adityaRecommendedMushroom.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedMushroom.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedMushroom.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedMushroom.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedMushroom.addProperty(isRelatedTo, mushroom);		
		aditya.addProperty(hasRecommendation, adityaRecommendedMushroom);
		
		Individual adityaRecommendedBrocolli=recommendation.createIndividual(base+"adityaRecommendedBrocolli");
		adityaRecommendedBrocolli.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedBrocolli.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedBrocolli.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedBrocolli.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedBrocolli.addProperty(isRelatedTo, brocolli);		
		aditya.addProperty(hasRecommendation, adityaRecommendedBrocolli);
		
		Individual adityaRecommendedKiwi=recommendation.createIndividual(base+"adityaRecommendedKiwi");
		adityaRecommendedKiwi.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedKiwi.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedKiwi.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedKiwi.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedKiwi.addProperty(isRelatedTo, kiwi);		
		aditya.addProperty(hasRecommendation, adityaRecommendedKiwi);
		
		
		Individual adityaRecommendedApple=recommendation.createIndividual(base+"adityaRecommendedApple");
		adityaRecommendedApple.addProperty(hasWeelyWeightage, "0.1");
		adityaRecommendedApple.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedApple.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedApple.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedApple.addProperty(isRelatedTo, apple);
		aditya.addProperty(hasRecommendation, adityaRecommendedApple);
		
		
		Individual adityaRecommendedOrange=recommendation.createIndividual(base+"adityaRecommendedOrange");
		adityaRecommendedOrange.addProperty(hasWeelyWeightage, "0.1");
		adityaRecommendedOrange.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedOrange.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedOrange.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedOrange.addProperty(isRelatedTo, orange);
		aditya.addProperty(hasRecommendation, adityaRecommendedOrange);
		
		
		Individual adityaRecommendedChickenLegs=recommendation.createIndividual(base+"adityaRecommendedChickenLegs");
		adityaRecommendedChickenLegs.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedChickenLegs.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedChickenLegs.addProperty(hasMonthlyWeightage, "0.1");
		adityaRecommendedChickenLegs.addProperty(hasUserInterest, "0.5");
		
		adityaRecommendedChickenLegs.addProperty(isRelatedTo, chickenLegs);
		aditya.addProperty(hasRecommendation, adityaRecommendedChickenLegs);
	}
	
	public void writeOntology() throws Exception {
		//Ontology completed -- write to file
        model.write(new FileWriter(polychrest_ontology,false), "TURTLE");
        model.write(new FileOutputStream(new File(owlForm)),"RDF/XML-ABBREV");
       
	}
	
	
	
}
