package griup.beans;

public class Pattern {
	private float weeklyPatternConfidence;
	private float biweeklyPatternConfidence;
	private float monthlyPatternConfidence;
	
	public Pattern(float weeklyPatternConfidence, float biweeklyPatternConfidence, float monthlyPatternConfidence) {
		this.weeklyPatternConfidence = weeklyPatternConfidence;
		this.biweeklyPatternConfidence = biweeklyPatternConfidence;
		this.monthlyPatternConfidence = monthlyPatternConfidence;
	}
	public float getWeeklyPatternConfidence() {
		return weeklyPatternConfidence;
	}
	public void setWeeklyPatternConfidence(float weeklyPatternConfidence) {
		this.weeklyPatternConfidence = weeklyPatternConfidence;
	}
	public float getBiweeklyPatternConfidence() {
		return biweeklyPatternConfidence;
	}
	public void setBiweeklyPatternConfidence(float biweeklyPatternConfidence) {
		this.biweeklyPatternConfidence = biweeklyPatternConfidence;
	}
	public float getMonthlyPatternConfidence() {
		return monthlyPatternConfidence;
	}
	public void setMonthlyPatternConfidence(float monthlyPatternConfidence) {
		this.monthlyPatternConfidence = monthlyPatternConfidence;
	}
	
	public float getHighestConfidence() {
		if (weeklyPatternConfidence >= biweeklyPatternConfidence && weeklyPatternConfidence >= monthlyPatternConfidence)
			return weeklyPatternConfidence;
		else if (biweeklyPatternConfidence >= weeklyPatternConfidence && biweeklyPatternConfidence >= monthlyPatternConfidence)
			return biweeklyPatternConfidence;
		else 
			return monthlyPatternConfidence;
				
	}
}
