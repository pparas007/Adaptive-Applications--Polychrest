package griup.middleware;

public class Utility{
	private static float e=2.73f;
	public static  float changeExponentially(float weightage, float boost) {
		System.out.println("###########");
		System.out.println(weightage+" "+boost);
		float result=(float) (weightage*(Math.pow(e, boost)));
		System.out.println(result);
		System.out.println("@@@@@@@@@@@@@");
		return result;
		
	}
	public static float convertToProbability(float first, float second, float third) {
		return (first/(first+second+third));
	}
}
