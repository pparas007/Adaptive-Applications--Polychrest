package griup.middleware;

public class Utility{
	private static float e=2.73f;
	public static  float changeExponentially(float hasWeeklyWeightage, float boost) {
		return (float) (hasWeeklyWeightage*(Math.pow(e, boost)));
	}
	public static float convertToProbability(float first, float second, float third) {
		return (first/(first+second+third));
	}
}
