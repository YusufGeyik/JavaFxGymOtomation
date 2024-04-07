package model;

public class cartItems {
	String itemName;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	double itemPrice;
	int quantity;
	
	
	public cartItems(String itemName,double itemPrice,int quantity) 
	{
		
		this.itemName=itemName;
		this.itemPrice=itemPrice;
		this.quantity=quantity;
		
	}
}
