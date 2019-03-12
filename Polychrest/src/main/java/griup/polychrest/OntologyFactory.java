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
	
	private OntModel model;
	private Ontology ontology;
	private OntClass food, shop, user, shopping;
	private DatatypeProperty ofCategory, atPrice, hasShopName, hasShopAddress, hasShopType, quantity, atDateTime, hasName,hasGoal;
    private ObjectProperty bought, atShop, shopped, sells, availableAt;
    private Individual paras,anirban,shubham,aditya,pavan,gauranksh;
	
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
        ontology.addProperty(DCTerms.contributor, "Gauranksh");
        ontology.addProperty(DCTerms.contributor, "Paras");
        
        /*--------------------- External Resources ---------------------*/
        Model bbcOntology = RDFDataMgr.loadModel(bbcOntologyLocal);
        Resource bbcFood=bbcOntology.getResource("http://purl.org/ontology/fo/Food");
        Model dbpediaOntology = RDFDataMgr.loadModel("http://dbpedia.org/resource/classes#");
        Resource dbpediaFood=dbpediaOntology.getResource("http://dbpedia.org/ontology/Food");
        
        //There is no resource as Shop in dbpedia so there is no binding possible. Still keeping it!
        Resource dbpediaShop=dbpediaOntology.getResource("http://dbpedia.org/ontology/Shop");
        
        /*--------------------- Define Classes ---------------------*/
        food = model.createClass(base + "food");
        food.addLabel("food", "en");
        food.addSameAs(bbcFood); 
        food.addSameAs(dbpediaFood);
        
        shop = model.createClass(base + "shop");
        shop.addLabel("any grocery shop", "en");
        shop.addSameAs(dbpediaShop);
        
        //TODO: add more properties to this class to create full-fledged user modeling
        user = model.createClass(base + "user");
        user.addLabel("app user", "en");
        
        shopping = model.createClass(base + "shopping");
        shopping.addLabel("user shopping details", "en");
        
        /*--------------------- Define Properties ---------------------*/
        ofCategory = model.createDatatypeProperty(base + "ofCategory");
        ofCategory.addLabel("food category", "en");
        ofCategory.setDomain(food);
        ofCategory.setRange(XSD.xstring);
        
        atPrice = model.createDatatypeProperty(base + "atPrice");
        atPrice.addLabel("food price", "en");
        atPrice.setDomain(food);
        atPrice.setRange(XSD.xfloat);
        
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
        
        /* All Object Properties 
         * 
         */
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
        
        shopped = model.createObjectProperty(base + "shopped");
        shopped.addLabel("the user shopped this shopping", "en");
        shopped.setDomain(user);
        shopped.setRange(shopping);
	}
	
	public void createInstances() {
		/*--------------------- User Instances ---------------------*/
		Individual paras=user.createIndividual(base+"paras");
		paras.addProperty(hasName, "paras");
		paras.addProperty(hasGoal, Constants.GOAL_ECONOMICAL);
		
		Individual shubham=user.createIndividual(base+"shubham");
		shubham.addProperty(hasName, "shubham");
		shubham.addProperty(hasGoal, Constants.GOAL_VEGGIE);
		
		Individual anirban=user.createIndividual(base+"anirban");
		anirban.addProperty(hasName, "anirban");
		anirban.addProperty(hasGoal, Constants.GOAL_MEAT_LOVER);
		anirban.addProperty(hasGoal, Constants.GOAL_ECONOMICAL);
		
		/*--------------------- Shop Instances ---------------------*/
		Individual lidlArtane=shop.createIndividual(base+"lidlArtane");
		lidlArtane.addProperty(hasShopName, "LiDL");
		lidlArtane.addProperty(hasShopAddress, "Artane, Dublin 5");
		lidlArtane.addProperty(hasShopType, Constants.SHOP_SUPERMARKET);
		
		Individual lidlCityCentre=shop.createIndividual(base+"lidlCityCentre");
		lidlCityCentre.addProperty(hasShopName, "LiDL");
		lidlCityCentre.addProperty(hasShopAddress, "City Centre, Dublin");
		lidlCityCentre.addProperty(hasShopType, Constants.SHOP_SUPERMARKET);
		
		Individual tescoCityCentre=shop.createIndividual(base+"tescoCityCentre");
		tescoCityCentre.addProperty(hasShopName, "Tesco");
		tescoCityCentre.addProperty(hasShopAddress, "City Centre, Dublin");
		tescoCityCentre.addProperty(hasShopType, Constants.SHOP_SUPERMARKET);
		
		Individual euroGeneralFairview=shop.createIndividual(base+"euroGeneralFairview");
		euroGeneralFairview.addProperty(hasShopName, "Euro General");
		euroGeneralFairview.addProperty(hasShopAddress, "Fairview, Dublin");
		euroGeneralFairview.addProperty(hasShopType, Constants.SHOP_STORE);
		
		/*--------------------- Food Properties ---------------------*/
		Individual spinach=food.createIndividual(base+"spinach");
		spinach.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		spinach.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		spinach.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEGETABLES);
		
		Individual mushroom=food.createIndividual(base+"mushroom");
		mushroom.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		mushroom.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		mushroom.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEGETABLES);
		Individual brocolli=food.createIndividual(base+"brocolli");
		
		brocolli.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		brocolli.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		brocolli.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEGETABLES);
		
		Individual rocketLeaves=food.createIndividual(base+"rocketLeaves");
		rocketLeaves.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		rocketLeaves.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		rocketLeaves.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEGETABLES);
		
		Individual capsicum=food.createIndividual(base+"capsicum");
		capsicum.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		capsicum.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		capsicum.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEGETABLES);
		
		Individual noodles=food.createIndividual(base+"noodles");
		noodles.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHINESE);
		noodles.addProperty(ofCategory, Constants.FOOD_CATEGORY_ASIAN);
		noodles.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		
		Individual orange=food.createIndividual(base+"orange");
		orange.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		orange.addProperty(ofCategory, Constants.FOOD_CATEGORY_FRUITS);
		orange.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		
		Individual kiwi=food.createIndividual(base+"kiwi");
		kiwi.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		kiwi.addProperty(ofCategory, Constants.FOOD_CATEGORY_FRUITS);
		kiwi.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		
		Individual apple=food.createIndividual(base+"apple");
		apple.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		apple.addProperty(ofCategory, Constants.FOOD_CATEGORY_FRUITS);
		apple.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		
		Individual irishApple=food.createIndividual(base+"irishApple");
		irishApple.addProperty(ofCategory, Constants.FOOD_CATEGORY_HEALTHY);
		irishApple.addProperty(ofCategory, Constants.FOOD_CATEGORY_FRUITS);
		irishApple.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		
		Individual lowFatMilk=food.createIndividual(base+"lowFatMilk");
		lowFatMilk.addProperty(ofCategory, Constants.FOOD_CATEGORY_MILK);
		lowFatMilk.addProperty(ofCategory, Constants.FOOD_CATEGORY_DAIRY);
		lowFatMilk.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		
		Individual milk=food.createIndividual(base+"milk");
		milk.addProperty(ofCategory, Constants.FOOD_CATEGORY_MILK);
		milk.addProperty(ofCategory, Constants.FOOD_CATEGORY_DAIRY);
		milk.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		
		Individual butter=food.createIndividual(base+"butter");
		butter.addProperty(ofCategory, Constants.FOOD_CATEGORY_DAIRY);
		butter.addProperty(ofCategory, Constants.FOOD_CATEGORY_VEG);
		butter.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);
		
		Individual greekCheese=food.createIndividual(base+"greekCheese");
		greekCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_DAIRY);
		greekCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHEESE);
		greekCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);
		
		Individual sweeseCheese=food.createIndividual(base+"sweeseCheese");
		sweeseCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_DAIRY);
		sweeseCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHEESE);
		sweeseCheese.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);
		
		Individual chickenLegs=food.createIndividual(base+"chickenLegs");
		chickenLegs.addProperty(ofCategory, Constants.FOOD_CATEGORY_NONVEG);
		chickenLegs.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHICKEN);
		chickenLegs.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);
		
		Individual chickenBreasts=food.createIndividual(base+"chickenBreasts");
		chickenBreasts.addProperty(ofCategory, Constants.FOOD_CATEGORY_NONVEG);
		chickenBreasts.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHICKEN);
		chickenBreasts.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);
		
		Individual lamb=food.createIndividual(base+"lamb");
		lamb.addProperty(ofCategory, Constants.FOOD_CATEGORY_NONVEG);
		lamb.addProperty(ofCategory, Constants.FOOD_CATEGORY_LAMB);
		lamb.addProperty(ofCategory, Constants.FOOD_CATEGORY_CHOLESTEROL);

		Individual pepsi=food.createIndividual(base+"pepsi");
		pepsi.addProperty(ofCategory, Constants.FOOD_CATEGORY_SOFT_DRINK);
		
		Individual coke=food.createIndividual(base+"coke");
		coke.addProperty(ofCategory, Constants.FOOD_CATEGORY_SOFT_DRINK);
		
		Individual fanta=food.createIndividual(base+"fanta");
		fanta.addProperty(ofCategory, Constants.FOOD_CATEGORY_SOFT_DRINK);
		
		Individual sprite=food.createIndividual(base+"sprite");
		sprite.addProperty(ofCategory, Constants.FOOD_CATEGORY_SOFT_DRINK);
		
		Individual dietCoke=food.createIndividual(base+"dietCoke");
		dietCoke.addProperty(ofCategory, Constants.FOOD_CATEGORY_SOFT_DRINK);
		
		Individual bread=food.createIndividual(base+"bread");
		bread.addProperty(ofCategory, Constants.FOOD_CATEGORY_BAKERY);
		bread.addProperty(ofCategory, Constants.FOOD_CATEGORY_BREAD);
		
		Individual brownBread=food.createIndividual(base+"brownBread");
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
		//paras shops 5 items on a day
		Individual parasShops11=shopping.createIndividual(base+"parasShops11");
		parasShops11.addProperty(atShop, lidlArtane);
		parasShops11.addProperty(bought, kiwi);
		parasShops11.addProperty(atDateTime, "08-Mar-2019");
		parasShops11.addProperty(quantity, "1");
		parasShops11.addProperty(atPrice, "1");
		
		Individual parasShops12=shopping.createIndividual(base+"parasShops12");
		parasShops12.addProperty(atShop, lidlArtane);
		parasShops12.addProperty(bought, orange);
		parasShops12.addProperty(atDateTime, "08-Mar-2019");
		parasShops12.addProperty(quantity, "2");
		parasShops12.addProperty(atPrice, "2");
		
		Individual parasShops13=shopping.createIndividual(base+"parasShops13");
		parasShops13.addProperty(atShop, lidlArtane);
		parasShops13.addProperty(bought, apple);
		parasShops13.addProperty(atDateTime, "08-Mar-2019");
		parasShops13.addProperty(quantity, "1");
		parasShops13.addProperty(atPrice, "2");
		
		Individual parasShops14=shopping.createIndividual(base+"parasShops14");
		parasShops14.addProperty(atShop, lidlArtane);
		parasShops14.addProperty(bought, lowFatMilk);
		parasShops14.addProperty(atDateTime, "08-Mar-2019");
		parasShops14.addProperty(quantity, "2");
		parasShops14.addProperty(atPrice, "2");
		
		Individual parasShops15=shopping.createIndividual(base+"parasShops15");
		parasShops15.addProperty(atShop, lidlArtane);
		parasShops15.addProperty(bought, chickenLegs);
		parasShops15.addProperty(atDateTime, "08-Mar-2019");
		parasShops15.addProperty(quantity, "1");
		parasShops15.addProperty(atPrice, "2.5");
		
		paras.addProperty(shopped, parasShops11); paras.addProperty(shopped, parasShops12); paras.addProperty(shopped, parasShops13);
		paras.addProperty(shopped, parasShops14); paras.addProperty(shopped, parasShops15);
		
		//paras shops 5 items on another day
		Individual parasShops21=shopping.createIndividual(base+"parasShops21");
		parasShops21.addProperty(atShop, lidlArtane);
		parasShops21.addProperty(bought, kiwi);
		parasShops21.addProperty(atDateTime, "13-Mar-2019");
		parasShops21.addProperty(quantity, "1");
		parasShops21.addProperty(atPrice, "1");
		
		Individual parasShops22=shopping.createIndividual(base+"parasShops22");
		parasShops22.addProperty(atShop, lidlArtane);
		parasShops22.addProperty(bought, orange);
		parasShops22.addProperty(atDateTime, "13-Mar-2019");
		parasShops22.addProperty(quantity, "2");
		parasShops22.addProperty(atPrice, "2");
		
		Individual parasShops23=shopping.createIndividual(base+"parasShops23");
		parasShops23.addProperty(atShop, lidlArtane);
		parasShops23.addProperty(bought, apple);
		parasShops23.addProperty(atDateTime, "13-Mar-2019");
		parasShops23.addProperty(quantity, "1");
		parasShops23.addProperty(atPrice, "2");
		
		Individual parasShops24=shopping.createIndividual(base+"parasShops24");
		parasShops24.addProperty(atShop, lidlArtane);
		parasShops24.addProperty(bought, lowFatMilk);
		parasShops24.addProperty(atDateTime, "13-Mar-2019");
		parasShops24.addProperty(quantity, "2");
		parasShops24.addProperty(atPrice, "2");
		
		Individual parasShops25=shopping.createIndividual(base+"parasShops25");
		parasShops25.addProperty(atShop, lidlArtane);
		parasShops25.addProperty(bought, chickenLegs);
		parasShops25.addProperty(atDateTime, "13-Mar-2019");
		parasShops25.addProperty(quantity, "1");
		parasShops25.addProperty(atPrice, "2.5");
		
		Individual parasShops26=shopping.createIndividual(base+"parasShops26");
		parasShops26.addProperty(atShop, lidlArtane);
		parasShops26.addProperty(bought, brownBread);
		parasShops26.addProperty(atDateTime, "13-Mar-2019");
		parasShops26.addProperty(quantity, "2");
		parasShops26.addProperty(atPrice, "2");
		
		paras.addProperty(shopped, parasShops21); paras.addProperty(shopped, parasShops22); paras.addProperty(shopped, parasShops23);
		paras.addProperty(shopped, parasShops24); paras.addProperty(shopped, parasShops25); paras.addProperty(shopped, parasShops25);
	}
	
	public void writeOntology() throws Exception {
		//Ontology completed -- write to file
        model.write(new FileWriter(polychrest_ontology,true), "TURTLE");
	}
	
}
