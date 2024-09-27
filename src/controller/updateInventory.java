package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.FormFunctions;
import model.database;
import model.items;
import model.member;
import model.transactions;

public class updateInventory {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteItem;

   

    @FXML
    private TextField findItemtxt;

    @FXML
    private TableColumn<items, String> itemNametc;

    @FXML
    private TextField itemNametxt;

    @FXML
    private TableView<items> itemView;

    @FXML
    private TableColumn<items, Double> sellingPricetc;

    @FXML
    private TextField sellingPricetxt;

    @FXML
    private TableColumn<items, Integer> stockCounttc;

    @FXML
    private TextField stockCounttxt;

    @FXML
    private TableColumn<items, Double> unitCosttc;

    @FXML
    private TextField unitCosttxt;

    @FXML
    private Button updateItem;

    @FXML
    void deleteItemClicked(ActionEvent event) {

    	try {
    	if(itemView.getSelectionModel().getSelectedIndex()!=-1) 
    	{
    		items item=itemView.getSelectionModel().getSelectedItem();
    		database db=new database();
    		db.deleteItem(item);
    		String logDetails="Deleted Item Data\n" +item.getItemName()+"\n"+item.getStockCount()+"\n"+item.getSellingPrice()+"\n"+item.getUnitCost();
    		FormFunctions.createLog("Delete Inventory Item", logDetails);
    		db.closeConnection();
    		//log log güncelleme item listesi güncelleme
    		
    		
    		
    	}
    	
    	}catch(Exception e) 
    	{
    		e.printStackTrace();
    	}
    	
    }

   

    @FXML
    void updateItemClicked(ActionEvent event) {
    	if(FormFunctions.isString(itemNametxt.getText())==true && FormFunctions.isInteger(stockCounttxt.getText())==true 
    			&& FormFunctions.isDouble(sellingPricetxt.getText())==true && FormFunctions.isDouble(unitCosttxt.getText())==true) {
    	try {
    		
    	
    		if(itemView.getSelectionModel().getSelectedIndex()!=-1) 
    		{
    		 items oldItem=itemView.getSelectionModel().getSelectedItem();
    			
    				boolean validName=FormFunctions.isString(itemNametxt.getText());
    	    		boolean validStock=FormFunctions.isInteger(stockCounttxt.getText());
    	    		boolean validSellingPrice=FormFunctions.isDouble(sellingPricetxt.getText());
    	    		boolean validUnitCost=FormFunctions.isDouble(unitCosttxt.getText());
    	    		
    	    		if(validName&&validStock&&validSellingPrice&&validUnitCost) 
    	    		{
    	    			String itemName=itemNametxt.getText();
    	    			int stockCount=Integer.parseInt(stockCounttxt.getText());
    	    			double sellingPrice=Double.parseDouble(sellingPricetxt.getText());
    	    			double unitCost=Double.parseDouble(unitCosttxt.getText());
    	    			
    	    			
    	    			
    	        		
    	    			
    	    			items newItem=new items(itemName,stockCount,sellingPrice,unitCost);
    	    			
    	    			
    	    			String logDetails="Previous Item Data ----> Updated Item Data\n" +oldItem.getItemName()+"---->"+newItem.getItemName()+"\n"+oldItem.getStockCount()+"---->"+newItem.getStockCount()+"\n"+
    	    			oldItem.getSellingPrice()+"---->"+newItem.getSellingPrice()+"\n"+oldItem.getUnitCost()+"---->"+newItem.getUnitCost();
    	    			double oldCost=oldItem.getStockCount()*oldItem.getUnitCost();
    	    			double newCost=newItem.getStockCount()*newItem.getUnitCost();
    	    			transactions transaction=new transactions(newCost-oldCost, LocalDate.now(), "SnackBar Inventory Update Purchase");
    	    	    	
    	    			model.database db=new model.database();
    	    			db.updateItem(oldItem,newItem);
    	    			FormFunctions.setItems(db.bringItems());
    	    			
    	    			FormFunctions.createLog("Update Inventory Item", logDetails);
    	    			db.addTransaction(transaction);		
    	    			db.closeConnection();
    	    			itemNametxt.setText("");
    	    			stockCounttxt.setText("");
    	    			sellingPricetxt.setText("");
    	    			unitCosttxt.setText("");
    	    			
                        
    	    	    
    	        		
    	    		}
    	    		else 
    	    		{
    	    			FormFunctions.alertMessageErr("Register Item", "Register Item Operation", "unsuccessfull,please provide valid values.");
    	    		}
    	    		
    	    		
    	    		
    	    		
    	    		
    	    	

    	    	FormFunctions.addDataToTableView(itemView,FormFunctions.getItems(), itemNametc,
    	    			stockCounttc, sellingPricetc,
    	    			unitCosttc);
    	    }
    			
    			
    			
    			
    		
    		
    		
    		
    		
    		
    	}
    	catch(Exception e)
    	{
    		
    		e.printStackTrace();
    		
    		
    		
    		
    	}
    	
    }else
    	FormFunctions.alertMessageErr("Updating Inventory Item Operation", "Failed" , "Please provide valid values.");
    }


    @FXML
    void findItem(KeyEvent event) {

    	if(FormFunctions.isString(findItemtxt.getText())==true) {
    	try{
        	ObservableList<items> foundItems=FormFunctions.findItemsByName( findItemtxt.getText());
        	if(!foundItems.equals(null)) 
        	{
        		FormFunctions.addDataToTableView(itemView,foundItems, itemNametc,
            			stockCounttc, sellingPricetc,
            			unitCosttc);
        		
        		
        		
        	}
        	else 
        	{
        		FormFunctions.addDataToTableView(itemView,FormFunctions.getItems(), itemNametc,
            			stockCounttc, sellingPricetc,
            			unitCosttc);
        		
        		
        	}
        	
        	}catch(Exception e) 
        	{
        		e.printStackTrace();
        	}
        	
    }else
    	FormFunctions.addDataToTableView(itemView,FormFunctions.getItems(), itemNametc,
    			stockCounttc, sellingPricetc,
    			unitCosttc);
    	
    }

    void fillSelectedItemData(items item) 
    {
    	itemNametxt.setText(item.getItemName());
    	stockCounttxt.setText(String.valueOf(item.getStockCount()));
    	sellingPricetxt.setText(String.valueOf(item.getSellingPrice()));
    	unitCosttxt.setText(String.valueOf(item.getUnitCost()));
    	
    	
    	
    	
    }
    

    	

    @FXML
    void initialize() {
       
    	FormFunctions.addDataToTableView(itemView,FormFunctions.getItems(), itemNametc,
    			stockCounttc, sellingPricetc,
    			unitCosttc);
    	 itemView.setOnMouseClicked(event -> {
     		
     	    if (event.getClickCount() == 1) { 
     	        int selectedIndex = itemView.getSelectionModel().getSelectedIndex();
     	        if (selectedIndex != -1) { 
     	        	items item=itemView.getSelectionModel().getSelectedItem();
     	        	fillSelectedItemData(item);
     	        	
     	        }
     	    }
     	});
		
    }

}
