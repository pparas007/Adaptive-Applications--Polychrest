package griup.beans;


import java.util.ArrayList;

public class User {
String name;
ArrayList<String> goalsList;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public ArrayList<String> getGoalsList() {
	return goalsList;
}
public void setGoalsList(ArrayList<String> goalsList) {
	this.goalsList = goalsList;
}
@Override
public String toString() {
	return "User [name=" + name + ", goalsList=" + goalsList + "]";
}

public User(User user) {
	super();
	this.name = user.getName();
	this.goalsList = user.getGoalsList();
}

public User() {
}


}
