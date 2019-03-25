package griup.beans;

public class Shopping {

	String shoppingName;
	public String getShoppingName() {
		return shoppingName;
	}
	public void setShoppingName(String shoppingName) {
		this.shoppingName = shoppingName;
	}
	float atPrice;
	String atDateTime;
	float quantity;
	Shop atShop;
	Food bought;
	public float getAtPrice() {
		return atPrice;
	}
	public void setAtPrice(float atPrice) {
		this.atPrice = atPrice;
	}
	public String getAtDateTime() {
		return atDateTime;
	}
	public void setAtDateTime(String atTime) {
		this.atDateTime = atTime;
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
	@Override
	public String toString() {
		return "Shopping [shoppingName=" + shoppingName + ", atPrice=" + atPrice + ", atDateTime=" + atDateTime
				+ ", quantity=" + quantity + ", atShop=" + atShop + ", bought=" + bought + "]";
	}
	
	
}
