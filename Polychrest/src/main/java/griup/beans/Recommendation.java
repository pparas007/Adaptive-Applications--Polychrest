package griup.beans;

import java.util.ArrayList;

public class Recommendation {

	float hasWeeklyWeightage;
	float hasByWeeklyWeightage;
	float hasMonthlyWeightage;
	float hasUserInterest;
	
	public float getHasWeeklyWeightage() {
		return hasWeeklyWeightage;
	}
	public void setHasWeeklyWeightage(float hasWeeklyWeightage) {
		this.hasWeeklyWeightage = hasWeeklyWeightage;
	}
	public float getHasByWeeklyWeightage() {
		return hasByWeeklyWeightage;
	}
	public void setHasByWeeklyWeightage(float hasByWeeklyWeightage) {
		this.hasByWeeklyWeightage = hasByWeeklyWeightage;
	}
	public float getHasMonthlyWeightage() {
		return hasMonthlyWeightage;
	}
	public void setHasMonthlyWeightage(float hasMonthlyWeightage) {
		this.hasMonthlyWeightage = hasMonthlyWeightage;
	}
	public float getHasUserInterest() {
		return hasUserInterest;
	}
	public void setHasUserInterest(float hasUserInterest) {
		this.hasUserInterest = hasUserInterest;
	}
	
	public Recommendation(float hasWeeklyWeightage, float hasByWeeklyWeightage, float hasMonthlyWeightage,
			float hasUserInterest) {
		super();
		this.hasWeeklyWeightage = hasWeeklyWeightage;
		this.hasByWeeklyWeightage = hasByWeeklyWeightage;
		this.hasMonthlyWeightage = hasMonthlyWeightage;
		this.hasUserInterest = hasUserInterest;
	}
	public float getHighestWeightage() {
		if((hasWeeklyWeightage>=hasByWeeklyWeightage) && (hasWeeklyWeightage>=hasMonthlyWeightage)) return hasWeeklyWeightage;
		else if((hasByWeeklyWeightage>=hasWeeklyWeightage) && (hasByWeeklyWeightage>=hasMonthlyWeightage)) return hasByWeeklyWeightage;
		else return hasMonthlyWeightage;
	}
	
	@Override
	public String toString() {
		return "Recommendation [hasWeeklyWeightage=" + hasWeeklyWeightage + ", hasByWeeklyWeightage="
				+ hasByWeeklyWeightage + ", hasMonthlyWeightage=" + hasMonthlyWeightage + ", hasUserInterest="
				+ hasUserInterest + "]";
	}
	
}
