package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import model.FormFunctions;
import model.items;
import model.member;
import model.cartItems;
import model.database;
public class snackBar {

	ObservableList<cartItems> cart=FXCollections.observableArrayList();
	ObservableList<items> inventory=FormFunctions.getItems();
	double total=0;
	items item = null;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<items, Button> addtc;

    @FXML
    private TableColumn<member, Double> balancetc;

    @FXML
    private TableView<items> barView;

    @FXML
    private TableColumn<cartItems, Double> cartItemPricetc;

    @FXML
    private TableColumn<cartItems, Integer> cartQuantitytc;


    @FXML
    private TableColumn<items, Double> unitCosttc;
   
    @FXML
    private TableView<cartItems> cartView;

    @FXML
    private Button clearCartbt;

    @FXML
    private Button clearCartbt1;

    @FXML
    private Button findItembt;

    @FXML
    private TextField findItemtxt;

    @FXML
    private Button findMemberbt;

    @FXML
    private TextField findMembertxt;

    @FXML
    private TableColumn<items, String> itemNametc;

    @FXML
    private TableColumn<cartItems, String> cartItemNametc;

    @FXML
    private TableColumn<items, Double> itemPricetc;

    @FXML
    private TableColumn<member, String> memberNametc;

    @FXML
    private TableView<member> memberView;

    @FXML
    private TableColumn<member, String> membershiptc;

    @FXML
    private TableColumn<member, String> phoneNumbertc;

    @FXML
    private TableColumn<items, Integer> quantitytc;

    @FXML
    private TableColumn<items, Integer> stockCounttc;

    @FXML
    private Button completeSalebt;
    
    @FXML
    void completeSaleClicked(ActionEvent event) {

    	if(memberView.getSelectionModel().getSelectedIndex()!=-1 && !cart.isEmpty()) 
    	{
    		String logDetails="";
    		member member=memberView.getSelectionModel().getSelectedItem();
    		member oldMember=member;
    		member.setBalance(member.getBalance()-total);
    		model.database db=new model.database();
    		db.updateMember(member,oldMember,"SnackBar Purchase");
    	    logDetails="Purchaser: "+member.getMemberName();
    	    logDetails+="\nTotal: " + total;
    	    for(cartItems ci:cart) {
    	    	logDetails+="\nİtem Name: "+ci.getItemName()+"X"+ci.getQuantity();
    		for(items inv:inventory) 
    		{
    			if(inv.getItemName().equals(ci.getItemName())) 
    			{
    				db.updateItem(inv);
    			}
    		}
    	    }
    	 
    	    FormFunctions.createLog("SnackBar", logDetails);
    		FormFunctions.setLogs(db.bringLogs());
    		
    	    cart.clear();
        	cartView.refresh();
        
        	
        	FormFunctions.alertMessageInf("Selling", "Selling Operation","successfully completed.");
        	barView.refresh();
        	FormFunctions.setItems(db.bringItems());
        	FormFunctions.setMembers(db.bringMembers());
        	
        	inventory=FormFunctions.getItems();
        	db.closeConnection();
        	FormFunctions.memberTableSnackBar(memberView, FormFunctions.getMembers(), memberNametc, membershiptc, phoneNumbertc, balancetc);
    	}
    	else 
    	{
    		FormFunctions.alertMessageErr(null, null, "please choose the member who makes purchase and be sure you had items in the cart");
    	}
    	
    	
    }
    @FXML
    private Label totallbl;
    
    

    @FXML
    void clearCartClicked(ActionEvent event) {

    	for(cartItems ci:cart) 
    	{
    		for(items i:inventory) 
    		{
    			if(ci.getItemName().equals(i.getItemName())) 
    			{
    				i.setStockCount(i.getStockCount()+ci.getQuantity());
    			}
    			
    		}
    	}
    	total=0;
    	totallbl.setText(String.valueOf(total));
    	barView.refresh();
    	cart.clear();
    }

    @FXML
    void findItemClicked(ActionEvent event) {

     ObservableList<items>foundItems=FormFunctions.findItemsByName(findItemtxt.getText());
     FormFunctions.addDataToTableView(barView,foundItems, itemNametc,
 			stockCounttc, itemPricetc,
 			unitCosttc);
    	
    }
    

    @FXML
    void findMemberClicked(ActionEvent event) {
    	ObservableList<member> member=FormFunctions.findMembersByName(findMembertxt.getText());
    	FormFunctions.memberTableSnackBar(memberView, member, memberNametc, membershiptc, phoneNumbertc, balancetc);
    }
    
    @FXML
    void removeFromCart(MouseEvent event) {
    	
    	if(cartView.getSelectionModel().getSelectedIndex()!=-1) 
    	{
    		
    		
    		cartItems cartItem=cartView.getSelectionModel().getSelectedItem();
    		for(items i:inventory) 
    		{
    			
    			if(i.getItemName().equals(cartItem.getItemName())) 
    			{
    				item=i;
    				
    			}
    		}
    		
    		if(cartItem.getQuantity()==1) // last of this item
    		{
    			cart.remove(cartItem);
    			item.setStockCount(item.getStockCount()+1);
    			total=total-item.getSellingPrice();
    			totallbl.setText(String.valueOf(total));
    			barView.refresh();
    			cartView.refresh();
    			
    		}
    		else //cart contains more than one
    		{
    			cartItem.setQuantity(cartItem.getQuantity()-1);
    			item.setStockCount(item.getStockCount()+1);
    			total=total-item.getSellingPrice();
    			totallbl.setText(String.valueOf(total));
    		    cartView.refresh();
    		    barView.refresh();
    		}
    		
    		
    		
    		
    	}
    	else 
    	{
    		FormFunctions.alertMessageErr(null, null, "please choose the item you want to remove from cart");
    	}
    	
    	barView.getSelectionModel().clearSelection();
    	
    }

    
    @FXML
    void toCart(MouseEvent event) {

    	boolean isFirst=true;
     
    	if(barView.getSelectionModel().getSelectedIndex()!=-1) //operator selected an item from barView
    	{
    	
    		
    		items item=barView.getSelectionModel().getSelectedItem();
    	    cartItems cartItem=null;
    		if(cart.isEmpty()) // if cart is empty
    		{
    			cart.add(new cartItems(item.getItemName(),item.getSellingPrice(),1));
    			item.setStockCount(item.getStockCount()-1);
    			cartView.refresh();
    			barView.refresh();
    			total=item.getSellingPrice();
    			totallbl.setText(String.valueOf(total));
    			
    			
    		}else// if cart is not empty
    		{
    		for(cartItems ci:cart) // is there any of this item in the cart
    		{
    			
    			if(ci.getItemName().equals(item.getItemName())) //Yes there is at least 1 piece of this item in the cart
    			{
    				
    				cartItem=ci;
    				
    				isFirst=false;
    				
    			
    			}
    			
    		}
    		
    		if(isFirst==true) 
    		{
    			cart.add(new cartItems(item.getItemName(),item.getSellingPrice(),1));
				item.setStockCount(item.getStockCount()-1);
				barView.refresh();
				cartView.refresh();
				total=total+item.getSellingPrice();
				totallbl.setText(String.valueOf(total));
    			
    		}
    		else 
    		{
    			
    			cartItem.setQuantity(cartItem.getQuantity()+1);
				item.setStockCount(item.getStockCount()-1);
				barView.refresh();
				cartView.refresh();
				total=total+item.getSellingPrice();
				totallbl.setText(String.valueOf(total));
    		}
    		
    		
    		
    		}
    		
    		//
    	}
    	else //operator didn't choose an item from barView
    	{
    		FormFunctions.alertMessageErr(null, null, "please choose the item you want to add to cart.");
    	}
    	cartView.getSelectionModel().clearSelection();
    }

    @FXML
    void initialize() {
  
    	FormFunctions.memberTableSnackBar(memberView, FormFunctions.getMembers(), memberNametc, membershiptc, phoneNumbertc, balancetc);
    	FormFunctions.addDataToTableView(barView,inventory, itemNametc,
    			stockCounttc, itemPricetc,
    			unitCosttc);
		FormFunctions.addToCartTable(cartView, cart, cartItemNametc, cartItemPricetc, cartQuantitytc);
    }

}
