package griup.polychrest;

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
	public static String bbcOntologyOnline="https://www.bbc.co.uk/ontologies/fo/1.1.ttl";
	public static String bbcOntologyLocal="resource\\bbc.ttl";
	
	public static OntModel model;
	public static Ontology ontology;
	public static OntClass food, shop, user, shopping, recommendation;
	public static DatatypeProperty ofCategory, atPrice, hasShopName, hasShopAddress, hasShopType, quantity, atDateTime, hasName, hasFoodName,hasGoal, hasWeelyWeightage, hasBiweelyWeightage, hasMonthlyWeightage, isEnabled;
	public static ObjectProperty bought, atShop, shopped, sells, availableAt, isRelatedTo, hasRecommendation, recommendedTo;
    public static Individual paras,anirban,shubham,aditya,pavan,gauranksh;
    public static Individual euroGeneralFairview, tescoCityCentre, lidlCityCentre, lidlArtane;
    public static Individual sprite, dietCoke, bread,brownBread,pepsi,coke,fanta, lamb, chickenBreasts, chickenLegs, sweeseCheese, greekCheese, butter, lowFatMilk, milk, irishApple, apple, kiwi;
    public static Individual orange, noodles, capsicum, rocketLeaves, spinach, mushroom, brocolli;
    
    public void intializeOntology() throws Exception{
		Files.copy( URI.create(bbcOntologyOnline).toURL().openStream(), Paths.get(bbcOntologyLocal),StandardCopyOption.REPLACE_EXISTING);
	}
	public void createOntology() throws Exception {
		model = ModelFactory.createOntologyModel();
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
        quantity = model.createDatatypeProperty(base + "atTime");
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
        hasWeelyWeightage.addLabel("denotes the weekly weightage for the recommendation", "en");
        hasBiweelyWeightage.addLabel("denotes the bi-weekly weightage for the recommendation", "en");
        hasMonthlyWeightage.addLabel("denotes the monthly weightage for the recommendation", "en");
        hasWeelyWeightage.setDomain(recommendation);hasBiweelyWeightage.setDomain(recommendation);hasMonthlyWeightage.setDomain(recommendation);
        hasWeelyWeightage.setRange(XSD.xfloat);hasBiweelyWeightage.setRange(XSD.xfloat);hasMonthlyWeightage.setRange(XSD.xfloat);
        
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
		pavan=user.createIndividual(base+"pavan");
		pavan.addProperty(hasName, "pavan");
		pavan.addProperty(hasGoal, Constants.GOAL_VEGGIE);
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
		rocketLeaves=food.createIndividual(base+"rocketLeaves");
		rocketLeaves.addProperty(hasFoodName, "rocketLeaves");
		rocketLeaves.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		rocketLeaves.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		rocketLeaves.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEGETABLES);
		capsicum=food.createIndividual(base+"capsicum");
		capsicum.addProperty(hasFoodName, "capsicum");
		capsicum.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		capsicum.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		capsicum.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEGETABLES);
		
		noodles=food.createIndividual(base+"noodles");
		noodles.addProperty(hasFoodName, "noodles");
		noodles.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHINESE);
		noodles.addProperty(ofCategory, Constants.FOOD_CATEGORY_ASIAN);
		noodles.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		
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
		irishApple=food.createIndividual(base+"irishApple");
		irishApple.addProperty(hasFoodName, "irishApple");
		irishApple.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		irishApple.addProperty(ofCategory, Constants.FOOD_CATEGORY_FRUITS);
		irishApple.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		
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
		lamb=food.createIndividual(base+"lamb");
		lamb.addProperty(hasFoodName, "lamb");
		lamb.addProperty(ofCategory, Constants.FOOD_CATEGORY_NONVEG);
		lamb.addProperty(ofCategory, Constants.FOOD_CATEGORY_LAMB);
		lamb.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);

		pepsi=food.createIndividual(base+"pepsi");
		pepsi.addProperty(hasFoodName, "pepsi");
		pepsi.addProperty(ofCategory, Constants.FOOD_CATEGORY_SOFT_DRINK);
		coke=food.createIndividual(base+"coke");
		coke.addProperty(hasFoodName, "coke");
		coke.addProperty(ofCategory, Constants.FOOD_CATEGORY_SOFT_DRINK);
		fanta=food.createIndividual(base+"fanta");
		fanta.addProperty(hasFoodName, "fanta");
		fanta.addProperty(ofCategory, Constants.FOOD_CATEGORY_SOFT_DRINK);
		sprite=food.createIndividual(base+"sprite");
		sprite.addProperty(hasFoodName, "sprite");
		sprite.addProperty(ofCategory, Constants.FOOD_CATEGORY_SOFT_DRINK);
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
		lidlArtane.addProperty(sells, noodles);
		lidlArtane.addProperty(sells, spinach);
		lidlArtane.addProperty(sells, mushroom);
		lidlArtane.addProperty(sells, brocolli);
		lidlArtane.addProperty(sells, rocketLeaves);
		lidlArtane.addProperty(sells, capsicum);
		lidlArtane.addProperty(sells, orange);
		lidlArtane.addProperty(sells, kiwi);
		lidlArtane.addProperty(sells, apple);
		lidlArtane.addProperty(sells, irishApple);
		lidlArtane.addProperty(sells, lowFatMilk);
		lidlArtane.addProperty(sells, butter);
		lidlArtane.addProperty(sells, greekCheese);
		lidlArtane.addProperty(sells, sweeseCheese);
		lidlArtane.addProperty(sells, chickenLegs);
		lidlArtane.addProperty(sells, chickenBreasts);
		lidlArtane.addProperty(sells, lamb);
		lidlArtane.addProperty(sells, pepsi);
		lidlArtane.addProperty(sells, coke);
		lidlArtane.addProperty(sells, fanta);
		lidlArtane.addProperty(sells, sprite);
		lidlArtane.addProperty(sells, dietCoke);
		lidlArtane.addProperty(sells, fanta);
		lidlArtane.addProperty(sells, bread);
		
		lidlCityCentre.addProperty(sells, noodles);
		lidlCityCentre.addProperty(sells, spinach);
		lidlCityCentre.addProperty(sells, mushroom);
		lidlCityCentre.addProperty(sells, rocketLeaves);
		lidlCityCentre.addProperty(sells, capsicum);
		lidlCityCentre.addProperty(sells, orange);
		lidlCityCentre.addProperty(sells, kiwi);
		lidlCityCentre.addProperty(sells, apple);
		lidlCityCentre.addProperty(sells, lowFatMilk);
		lidlCityCentre.addProperty(sells, butter);
		lidlCityCentre.addProperty(sells, greekCheese);
		lidlCityCentre.addProperty(sells, sweeseCheese);
		lidlCityCentre.addProperty(sells, chickenLegs);
		lidlCityCentre.addProperty(sells, chickenBreasts);
		lidlCityCentre.addProperty(sells, lamb);
		lidlCityCentre.addProperty(sells, pepsi);
		lidlCityCentre.addProperty(sells, coke);
		lidlCityCentre.addProperty(sells, fanta);
		lidlCityCentre.addProperty(sells, sprite);
		lidlCityCentre.addProperty(sells, fanta);
		lidlCityCentre.addProperty(sells, bread);
		lidlCityCentre.addProperty(sells, brownBread);
		
		tescoCityCentre.addProperty(sells, spinach);
		tescoCityCentre.addProperty(sells, mushroom);
		tescoCityCentre.addProperty(sells, brocolli);
		tescoCityCentre.addProperty(sells, rocketLeaves);
		tescoCityCentre.addProperty(sells, capsicum);
		tescoCityCentre.addProperty(sells, orange);
		tescoCityCentre.addProperty(sells, kiwi);
		tescoCityCentre.addProperty(sells, apple);
		tescoCityCentre.addProperty(sells, irishApple);
		tescoCityCentre.addProperty(sells, lowFatMilk);
		tescoCityCentre.addProperty(sells, butter);
		tescoCityCentre.addProperty(sells, greekCheese);
		tescoCityCentre.addProperty(sells, sweeseCheese);
		tescoCityCentre.addProperty(sells, chickenLegs);
		tescoCityCentre.addProperty(sells, chickenBreasts);
		tescoCityCentre.addProperty(sells, lamb);
		tescoCityCentre.addProperty(sells, pepsi);
		tescoCityCentre.addProperty(sells, coke);
		tescoCityCentre.addProperty(sells, fanta);
		tescoCityCentre.addProperty(sells, sprite);
		tescoCityCentre.addProperty(sells, dietCoke);
		tescoCityCentre.addProperty(sells, fanta);
		tescoCityCentre.addProperty(sells, bread);
		tescoCityCentre.addProperty(sells, brownBread);
		
		euroGeneralFairview.addProperty(sells, spinach);
		euroGeneralFairview.addProperty(sells, capsicum);
		euroGeneralFairview.addProperty(sells, orange);
		euroGeneralFairview.addProperty(sells, apple);
		euroGeneralFairview.addProperty(sells, butter);
		euroGeneralFairview.addProperty(sells, chickenLegs);
		euroGeneralFairview.addProperty(sells, chickenBreasts);
		euroGeneralFairview.addProperty(sells, lamb);
		euroGeneralFairview.addProperty(sells, pepsi);
		euroGeneralFairview.addProperty(sells, coke);
		euroGeneralFairview.addProperty(sells, fanta);
		euroGeneralFairview.addProperty(sells, fanta);
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
		Individual anirbanShops13=shopping.createIndividual(base+"anirbanShops13");
		anirbanShops13.addProperty(atShop, lidlCityCentre);
		anirbanShops13.addProperty(bought, lamb);
		anirbanShops13.addProperty(atDateTime, "06-Mar-2019");
		anirbanShops13.addProperty(quantity, "1");
		anirbanShops13.addProperty(atPrice, "2");
		
		
		anirban.addProperty(shopped, anirbanShops11); anirban.addProperty(shopped, anirbanShops12); anirban.addProperty(shopped, anirbanShops13);
		
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
		Individual anirbanShops23=shopping.createIndividual(base+"anirbanShops23");
		anirbanShops23.addProperty(atShop, lidlCityCentre);
		anirbanShops23.addProperty(bought, lamb);
		anirbanShops23.addProperty(atDateTime, "14-Mar-2019");
		anirbanShops23.addProperty(quantity, "1");
		anirbanShops23.addProperty(atPrice, "2");
		Individual anirbanShops24=shopping.createIndividual(base+"anirbanShops24");
		anirbanShops24.addProperty(atShop, lidlCityCentre);
		anirbanShops24.addProperty(bought, butter);
		anirbanShops24.addProperty(atDateTime, "14-Mar-2019");
		anirbanShops24.addProperty(quantity, "1");
		anirbanShops24.addProperty(atPrice, "1.5");

		
		anirban.addProperty(shopped, anirbanShops21); anirban.addProperty(shopped, anirbanShops22); anirban.addProperty(shopped, anirbanShops23); anirban.addProperty(shopped, anirbanShops24);
		
		/*--------------------- Shopping Instances for Aditya---------------------*/
		//shopping instance 1
		//aditya shops 3 items on a day
		Individual adityaShops11=shopping.createIndividual(base+"adityaShops11");
		adityaShops11.addProperty(atShop, tescoCityCentre);
		adityaShops11.addProperty(bought, capsicum);
		adityaShops11.addProperty(atDateTime, "07-Mar-2019");
		adityaShops11.addProperty(quantity, "1");
		adityaShops11.addProperty(atPrice, "1");
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
		
		aditya.addProperty(shopped, adityaShops11); aditya.addProperty(shopped, adityaShops12); aditya.addProperty(shopped, adityaShops13);
		
		//shopping instance 2
		//aditya shops 5 items on another day
		Individual adityaShops21=shopping.createIndividual(base+"adityaShops21");
		adityaShops21.addProperty(atShop, tescoCityCentre);
		adityaShops21.addProperty(bought, capsicum);
		adityaShops21.addProperty(atDateTime, "16-Mar-2019");
		adityaShops21.addProperty(quantity, "1");
		adityaShops21.addProperty(atPrice, "1");
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
		adityaShops24.addProperty(quantity, "2");
		adityaShops24.addProperty(atPrice, "2.5");

		
		aditya.addProperty(shopped, adityaShops21); aditya.addProperty(shopped, adityaShops22); aditya.addProperty(shopped, adityaShops23); aditya.addProperty(shopped, adityaShops24);
		
	}
	
	public void createRecommendationInstances() {
		//this sample recommendations entries are created based on the above shopping instances
		Individual parasRecommendedKiwi=recommendation.createIndividual(base+"parasRecommendedKiwi");
		parasRecommendedKiwi.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedKiwi.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedKiwi.addProperty(hasMonthlyWeightage, "0.1");
		
		parasRecommendedKiwi.addProperty(isRelatedTo, kiwi);		
		paras.addProperty(hasRecommendation, parasRecommendedKiwi);
		
		
		Individual parasRecommendedApple=recommendation.createIndividual(base+"parasRecommendedApple");
		parasRecommendedApple.addProperty(hasWeelyWeightage, "0.1");
		parasRecommendedApple.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedApple.addProperty(hasMonthlyWeightage, "0.1");
		
		parasRecommendedApple.addProperty(isRelatedTo, apple);
		paras.addProperty(hasRecommendation, parasRecommendedApple);
		
		
		Individual parasRecommendedOrange=recommendation.createIndividual(base+"parasRecommendedOrange");
		parasRecommendedOrange.addProperty(hasWeelyWeightage, "0.1");
		parasRecommendedOrange.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedOrange.addProperty(hasMonthlyWeightage, "0.1");
		
		parasRecommendedOrange.addProperty(isRelatedTo, orange);
		paras.addProperty(hasRecommendation, parasRecommendedOrange);
		
		
		Individual parasRecommendedChickenLegs=recommendation.createIndividual(base+"parasRecommendedChickenLegs");
		parasRecommendedChickenLegs.addProperty(hasWeelyWeightage, "0.2");
		parasRecommendedChickenLegs.addProperty(hasBiweelyWeightage, "0.1");
		parasRecommendedChickenLegs.addProperty(hasMonthlyWeightage, "0.1");
		
		parasRecommendedChickenLegs.addProperty(isRelatedTo, chickenLegs);
		paras.addProperty(hasRecommendation, parasRecommendedChickenLegs);
		
		// Anirban
		Individual anirbanRecommendedbread=recommendation.createIndividual(base+"anirbanRecommendedbread");
		anirbanRecommendedbread.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedbread.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedbread.addProperty(hasMonthlyWeightage, "0.1");
				
		anirbanRecommendedbread.addProperty(isRelatedTo, bread);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedbread);
		
		
		Individual anirbanRecommendedcoke=recommendation.createIndividual(base+"anirbanRecommendedcoke");
		anirbanRecommendedcoke.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedcoke.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedcoke.addProperty(hasMonthlyWeightage, "0.1");
				
		anirbanRecommendedcoke.addProperty(isRelatedTo, coke);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedcoke);
		
		
		Individual anirbanRecommendedlamb=recommendation.createIndividual(base+"anirbanRecommendedlamb");
		anirbanRecommendedlamb.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedlamb.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedlamb.addProperty(hasMonthlyWeightage, "0.1");
				
		anirbanRecommendedlamb.addProperty(isRelatedTo, lamb);		
		anirban.addProperty(hasRecommendation, anirbanRecommendedlamb);
		
		Individual anirbanRecommendedbutter=recommendation.createIndividual(base+"anirbanRecommendedbutter");
		anirbanRecommendedbutter.addProperty(hasWeelyWeightage, "0.2");
		anirbanRecommendedbutter.addProperty(hasBiweelyWeightage, "0.1");
		anirbanRecommendedbutter.addProperty(hasMonthlyWeightage, "0.1");
				
		anirbanRecommendedbutter.addProperty(isRelatedTo, butter);		
		anirbanRecommendedbutter.addProperty(hasRecommendation, anirbanRecommendedbutter);
		
		//Aditya
		Individual adityaRecommendedcapsicum=recommendation.createIndividual(base+"adityaRecommendedcapsicum");
		adityaRecommendedcapsicum.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedcapsicum.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedcapsicum.addProperty(hasMonthlyWeightage, "0.1");
				
		adityaRecommendedcapsicum.addProperty(isRelatedTo, capsicum);		
		aditya.addProperty(hasRecommendation, adityaRecommendedcapsicum);
		
		Individual adityaRecommendedlowFatMilk=recommendation.createIndividual(base+"adityaRecommendedlowFatMilk");
		adityaRecommendedlowFatMilk.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedlowFatMilk.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedlowFatMilk.addProperty(hasMonthlyWeightage, "0.1");
				
		adityaRecommendedlowFatMilk.addProperty(isRelatedTo, lowFatMilk);		
		aditya.addProperty(hasRecommendation, adityaRecommendedlowFatMilk);
		
		Individual adityaRecommendedchickenLegs=recommendation.createIndividual(base+"adityaRecommendedchickenLegs");
		adityaRecommendedchickenLegs.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedchickenLegs.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedchickenLegs.addProperty(hasMonthlyWeightage, "0.1");
				
		adityaRecommendedchickenLegs.addProperty(isRelatedTo, kiwi);		
		adityaRecommendedchickenLegs.addProperty(hasRecommendation, adityaRecommendedchickenLegs);
		
		
		Individual adityaRecommendedspinach=recommendation.createIndividual(base+"adityaRecommendedspinach");
		adityaRecommendedspinach.addProperty(hasWeelyWeightage, "0.2");
		adityaRecommendedspinach.addProperty(hasBiweelyWeightage, "0.1");
		adityaRecommendedspinach.addProperty(hasMonthlyWeightage, "0.1");
				
		adityaRecommendedspinach.addProperty(isRelatedTo, kiwi);		
		aditya.addProperty(hasRecommendation, adityaRecommendedspinach);
		
		
	}
	
	public void writeOntology() throws Exception {
		//Ontology completed -- write to file
        model.write(new FileWriter(polychrest_ontology,false), "TURTLE");
	}
	
	
	
}
