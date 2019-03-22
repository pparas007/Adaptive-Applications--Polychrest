package griup.polychrest;

public class Shopping {

	float atPrice;
	String atTime;
	float quantity;
	Shop atShop;
	Food bought;
	public float getAtPrice() {
		return atPrice;
	}
	public void setAtPrice(float atPrice) {
		this.atPrice = atPrice;
	}
	public String getAtTime() {
		return atTime;
	}
	public void setAtTime(String atTime) {
		this.atTime = atTime;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public Shop getAtShop() {
		return atShop;
	}
	public void setAtShop(Shop atShop) {
		this.atShop = atShop;
	}
	public Food getBought() {
		return bought;
	}
	public void setBought(Food bought) {
		this.bought = bought;
	}
	
}
