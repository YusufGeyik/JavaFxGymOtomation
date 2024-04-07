package model;




import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class items {

	private String itemName;
	private int stockCount;
	private double sellingPrice;
	private double unitCost;
	
	
	public items (String itemName,int stockCount,double sellingPrice,double unitCost)
	{
		this.itemName=itemName;
		this.stockCount=stockCount;
		this.sellingPrice=sellingPrice;
		this.unitCost=unitCost;
		
		
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public int getStockCount() {
		return stockCount;
	}


	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}


	public double getSellingPrice() {
		return sellingPrice;
	}


	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}


	public double getUnitCost() {
		return unitCost;
	}


	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}
	
	
}

     


