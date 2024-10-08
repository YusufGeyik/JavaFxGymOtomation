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
import model.items;
import model.member;
import model.transactions;

public class registerItem {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    

    @FXML
    private TextField findItemtxt;

    @FXML
    private TableColumn<items, String> itemName;

    @FXML
    private TextField itemNametxt;

    @FXML
    private TableColumn<items, Double> sellingPrice;
    @FXML
    private TableColumn<items, Double> unitCost;

    @FXML
    private TableView<items> itemView;

    @FXML
    private Button registerItem;

    @FXML
    private TextField sellingPricetxt;

    @FXML
    private TableColumn<items, Integer> stockCount;

    @FXML
    private TextField stockCounttxt;

    @FXML
    private TextField unitCosttxt;

    @FXML
    void findItemClicked(KeyEvent event) {

    	if(FormFunctions.isString(findItemtxt.getText())==true) {
    	
    	try{
        	ObservableList<items> foundItems=FormFunctions.findItemsByName( findItemtxt.getText());
        	if(!foundItems.equals(null)) 
        	{
        		FormFunctions.addDataToTableView(itemView,foundItems, itemName,
            			stockCount, sellingPrice,
            			unitCost);
        		
        		
        		
        	}
        	else 
        	{
        		FormFunctions.addDataToTableView(itemView,FormFunctions.getItems(), itemName,
            			stockCount, sellingPrice,
            			unitCost);
        		
        		
        	}
        	
        	}catch(Exception e) 
        	{
        		e.printStackTrace();
        	}
        	
    }
    else
    	FormFunctions.addDataToTableView(itemView,FormFunctions.getItems(), itemName,
    			stockCount, sellingPrice,
    			unitCost);
    	
    }

    @FXML
    void registerItemClicked(ActionEvent event) {

    	if(FormFunctions.isString(itemNametxt.getText())==true && FormFunctions.isDouble(sellingPricetxt.getText())==true 
    			&& FormFunctions.isDouble(unitCosttxt.getText())==true && FormFunctions.isInteger(stockCounttxt.getText())==true    ) {
    	
    	try 
    	{
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
    			
    			
    			
        		
    			
    			items item=new items(itemName,stockCount,sellingPrice,unitCost);
    			
    			
    			
    			model.database db=new model.database();
    			db.registerItem(item);
    			FormFunctions.setItems(db.bringItems());
    			transactions transaction=new transactions(stockCount*unitCost, LocalDate.now(), "SnackBar new item purchase");
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
    		
    		
    		
    		
    		
    	}catch(Exception e) 
    	{
    	  e.printStackTrace();
    	}

    	FormFunctions.addDataToTableView(itemView,FormFunctions.getItems(), itemName,
    			stockCount, sellingPrice,
    			unitCost);
    	
    }
    else
    	FormFunctions.alertMessageErr("Registerin Item Operation", "Failed", "Please provide valid values.");
    	
    }

    @FXML
    void initialize() {
       
    	FormFunctions.addDataToTableView(itemView,FormFunctions.getItems(), itemName,
    			stockCount, sellingPrice,
    			unitCost);
		
    }

}
