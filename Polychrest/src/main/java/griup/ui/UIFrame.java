package griup.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import griup.beans.Food;
import griup.beans.Pattern;
import griup.beans.Recommendation;
import griup.beans.Shop;
import griup.beans.Shopping;
import griup.beans.User;
import griup.middleware.Middleware;
import griup.polychrest.Constants;

public class UIFrame extends JFrame implements ActionListener{
	JButton submitShopping=new JButton("Shop");
	JButton checkWeightageButton=new JButton("Check");
	JComboBox <String> userComboBox = new JComboBox<String>(Constants.userList);
	JComboBox <String> foodComboBox = new JComboBox<String>(Constants.foodList);
	JComboBox <String> checkWeightageFoodComboBox = new JComboBox<String>(Constants.foodList);
	JComboBox <String> shopComboBox = new JComboBox<String>(Constants.shopList);
	JComboBox <String> patternComboBox = new JComboBox<String>(Constants.patternList);
	JComboBox <String> patternConfidenceComboBox = new JComboBox<String>(Constants.patternConfidenceList);
	JComboBox <String> quantityComboBox = new JComboBox<String>(Constants.quantityList);
	JComboBox <String> priceComboBox = new JComboBox<String>(Constants.priceList);
	
	JLabel foodLabel=new JLabel("Item");
	JLabel shopLabel=new JLabel("Shop");
	JLabel priceLabel=new JLabel("Price");
	JLabel patternLabel=new JLabel("Pattern");
	JLabel patternConfidenceLabel=new JLabel("Pattern COnfidence");
	
	JLabel userLabel=new JLabel("Select User");
	JLabel shoppingLabel=new JLabel("Add shopping entry");
	JLabel checkWeightageLabel=new JLabel("Check Pattern Weightage");
	JLabel userNote=new JLabel("*To simulate login user");
	JLabel checkWeightageNote=new JLabel("*See how pattern weightage changes as you shop");
	JLabel shoppingNote=new JLabel("*This shpping entry will be inserted by external receipt analyzer tool (e.g. TagGun)");
	JLabel patternNote=new JLabel("*Pattern detection system shall guess pattern as per the last shopping instance.");
	JLabel patternNote2=new JLabel("Presently, this system is considered external and can be added in future!");
	JLabel weeklyWeightageLabel=new JLabel("");
	JLabel biweeklyWeightageLabel=new JLabel("");
	JLabel monthlyWeightageLabel=new JLabel("");
	
	//global variables to hold screen data
	Food food,weightageFood;
	User user;
	Shop shop;
	Shopping shopping;
	String patternName;
	float price, quantity,patternConfidence;

	public UIFrame() {
		//load style
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} 
	    catch (Exception e) {e.printStackTrace();}
		
		//initialize
		setSize(800, 600);
		setTitle("Team Polychrest");
		setVisible(true);
		setResizable(false);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFont (new Font("Lucida Console", Font.PLAIN, 10));
		//initialize ends
		
		//setup all screen
		setup();
	}

	private void setup() {
		//upper section
		userLabel.setBounds(100, 20, 150, 40);
		userComboBox.setBounds(300, 20, 150, 40);
		userNote.setBounds(100, 35, 150, 40);
		userNote.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		userNote.setForeground(Color.RED);
		
		
		//Shopping section label
		shoppingLabel.setBounds(20, 80, 150, 40);

		//red line notes
		shoppingNote.setBounds(20, 100, 550, 40);
		shoppingNote.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		shoppingNote.setForeground(Color.RED);
		patternNote.setBounds(20, 115, 600, 40);
		patternNote.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		patternNote.setForeground(Color.RED);
		patternNote2.setBounds(20, 125, 600, 40);
		patternNote2.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		patternNote2.setForeground(Color.RED);
		
		//dropdown boxes
		foodLabel.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		foodLabel.setForeground(Color.GREEN);
		foodLabel.setBounds(20, 145, 120, 30);
		foodComboBox.setBounds(20, 165, 120, 30);
		
		shopLabel.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		shopLabel.setForeground(Color.GREEN);
		shopLabel.setBounds(170, 145, 120, 30);
		shopComboBox.setBounds(170, 165, 130, 30);
		
		priceLabel.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		priceLabel.setForeground(Color.GREEN);
		priceLabel.setBounds(320, 145, 120, 30);
		priceComboBox.setBounds(320, 165, 120, 30);
		
		patternLabel.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		patternLabel.setForeground(Color.GREEN);
		patternLabel.setBounds(460, 145, 120, 30);
		patternComboBox.setBounds(460, 165, 120, 30);
		
		patternConfidenceLabel.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		patternConfidenceLabel.setForeground(Color.GREEN);
		patternConfidenceLabel.setBounds(600, 145, 120, 30);
		patternConfidenceComboBox.setBounds(600, 165, 100, 30);
		
		//shop button
		submitShopping.setBounds(20, 200, 100, 30);
		submitShopping.setBackground(Color.GREEN);
		//add action listeners
		submitShopping.addActionListener(this);
		
		//check weightage section
		checkWeightageLabel.setBounds(20, 240, 200, 30);
		checkWeightageNote.setBounds(20, 250, 600, 40);
		checkWeightageNote.setFont(new Font("Lucida Console", Font.PLAIN, 11));
		checkWeightageNote.setForeground(Color.RED);
		checkWeightageFoodComboBox.setBounds(20, 280, 120, 30);
		weeklyWeightageLabel.setBounds(150, 280, 200, 30);
		biweeklyWeightageLabel.setBounds(300, 280, 200, 30);
		monthlyWeightageLabel.setBounds(450, 280, 200, 30);
		weeklyWeightageLabel.setForeground(Color.GREEN);biweeklyWeightageLabel.setForeground(Color.GREEN);monthlyWeightageLabel.setForeground(Color.GREEN);
		checkWeightageButton.setBounds(20, 320, 120, 40);
		checkWeightageButton.setBackground(Color.GREEN);
		//add action listeners
		checkWeightageButton.addActionListener(this);
		
		//add components to frame
		add(userLabel);add(userComboBox);add(userNote);
		add(foodComboBox); add(shopComboBox); add(priceComboBox); add(patternComboBox); add(shoppingLabel);add(shoppingNote);add(patternNote);add(patternNote2);add(submitShopping);
		add(checkWeightageLabel);add(checkWeightageNote);add(checkWeightageFoodComboBox);add(checkWeightageButton);
		add(weeklyWeightageLabel);add(biweeklyWeightageLabel);add(monthlyWeightageLabel);add(patternConfidenceComboBox);
		add(foodLabel);add(priceLabel);add(patternLabel);add(patternConfidenceLabel);add(shopLabel);
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("Shop")) {
			insertShoppingInstance();
		}else if(arg0.getActionCommand().equals("Check")) {
			checkWeightage();
		} 
	}

	private void insertShoppingInstance() {
		updateScreenData();
		
		Pattern pattern=getPattern(patternName,patternConfidence);
		Middleware.insertShoppingInstance(user,shopping, pattern);
		
		System.out.println("Shopping instance insterted!");
	}
	
	private void checkWeightage() {
		System.out.println("check weightage function called");
		updateScreenData();
		
		Recommendation recommendation=Middleware.getRecommendationForUserAndFoodPair(user, weightageFood);
		recommendation=new Recommendation(0.1f, 0.1f, 0.8f);//dummy data; to be removed after above method is available
		weeklyWeightageLabel.setText("Weekly weightage: "+recommendation.getHasWeeklyWeightage());
		biweeklyWeightageLabel.setText("Biweekly weightage: "+recommendation.getHasByWeeklyWeightage());
		monthlyWeightageLabel.setText("Monthly weightage: "+recommendation.getHasMonthlyWeightage());
		
	}
	private Pattern getPattern(String patternName,float patternConfidence) {
		Pattern pattern;
		if(patternName.equals(Constants.PATTERN_WEEKLY) ) {
			pattern=new Pattern(patternConfidence, 1-patternConfidence, 1-patternConfidence);
		}else if(patternName.equals(Constants.PATTERN_BIWEEKLY) ) {
			pattern=new Pattern(1-patternConfidence, patternConfidence, 1-patternConfidence);
		}else {
			pattern=new Pattern(1-patternConfidence, 1-patternConfidence, patternConfidence);
		}
		return pattern;
	}
	private void updateScreenData() {
		patternName=patternComboBox.getSelectedItem().toString();
		patternConfidence=Float.parseFloat(patternConfidenceComboBox.getSelectedItem().toString());
		
		String userName=userComboBox.getSelectedItem().toString();
		user=new User ();
		user.setName(userName);
		
		String shopName=shopComboBox.getSelectedItem().toString();
		shop=new Shop();
		shop.setShopName(shopName);
		
		String foodName=foodComboBox.getSelectedItem().toString();
		food=new Food();
		food.setFoodName(foodName);
		
		price=Float.parseFloat(priceComboBox.getSelectedItem().toString());
		quantity=1f;
		
		shopping=new Shopping();
		shopping.setAtPrice(price);
		shopping.setAtShop(shop);
		shopping.setAtDateTime("12:00");
		shopping.setBought(food);
		shopping.setQuantity(quantity);

		String weightageFoodName=checkWeightageFoodComboBox.getSelectedItem().toString();
		weightageFood=new Food();
		weightageFood.setFoodName(weightageFoodName);
		
	}
	private void refresh() {
		revalidate();repaint();
	}
	
	
}
