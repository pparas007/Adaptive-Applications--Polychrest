package griup.beans;

public class Shop {

	String shopType;
	String shopAddress;
	String shopName;
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	@Override
	public String toString() {
		return "Shop [shopType=" + shopType + ", shopAddress=" + shopAddress + ", shopName=" + shopName + "]";
	}
	
	
}
