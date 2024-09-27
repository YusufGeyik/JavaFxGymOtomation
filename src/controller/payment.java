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
import model.member;
import model.transactions;

public class payment {

	member member;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addBalance;

    @FXML
    private TableColumn<member, Double> balancetc;

    @FXML
    private TableColumn<member, LocalDate> birthDatetc;

    @FXML
    private TableColumn<member, LocalDate> endDatetc;
    
    

    @FXML
    private TableColumn<member, String> gendertc;

    @FXML
    private TableColumn<member, Integer> idtc;

    @FXML
   private TextField memberNametxt;

    @FXML
    private TextField paymentAmount;
    @FXML
    private TableView<member> memberview;

    @FXML
    private TableColumn<member, String> nametc;

    @FXML
    private TableColumn<member, String> packageNametc;

    @FXML
    private TableColumn<member, String> periodtc;

    @FXML
    private TableColumn<member, String> phoneNumbertc;

    @FXML
    private TableColumn<member, LocalDate> startDatetc;

    @FXML
    void AddBalanceClicked(ActionEvent event) {

    	
    if(memberview.getSelectionModel().getSelectedIndex()!=-1) 
    {
    	
    	if(paymentAmount.getText().matches("\\d+")) {
    	
    	member=memberview.getSelectionModel().getSelectedItem();
    	member oldMember=member;
    	double newBalance=member.getBalance()+Double.parseDouble(paymentAmount.getText());
    	member.setBalance(newBalance);
    	model.database db=new model.database();
    	db.updateMember(member,oldMember,"Payment");
    	//customer pays
    	transactions transaction=new transactions(Double.parseDouble(paymentAmount.getText()), LocalDate.now(), "Customer added balance");
    	db.addTransaction(transaction);
		
    	FormFunctions.setMembers(db.bringMembers());
    	FormFunctions.addDataToTableView(memberview, FormFunctions.getMembers(),
    			idtc, nametc,
    			packageNametc, startDatetc,
    			endDatetc,gendertc,phoneNumbertc,
    			birthDatetc,balancetc,periodtc);
    	paymentAmount.setText("");
    	
    	db.closeConnection();
    	FormFunctions.alertMessageInf("Payment", "Payment Operation", "Successfull.New balance is "+member.getBalance());
    	}
    	else 
    	{
    		FormFunctions.alertMessageErr("Payment", "Payment Operation", "unsuccessfull, please enter a valid amount");
    	}
    }
    else
     FormFunctions.alertMessageErr("Payment", "Payment Operation", "unsuccessfull. Please choose the member first");
    	
    }

  
    @FXML
    void findMember(KeyEvent event) {
    	
    	if(FormFunctions.isString(memberNametxt.getText())==true) {
        	
        	try{
            	ObservableList<member> foundMembers=FormFunctions.findMembersByName( memberNametxt.getText());
            	if(!foundMembers.equals(null)) 
            	{
            		FormFunctions.addDataToTableView(memberview, foundMembers,
                			idtc, nametc,
                			packageNametc, startDatetc,
                			endDatetc,gendertc,phoneNumbertc,
                			birthDatetc,balancetc,periodtc);
            		
            		
            		
            	}
            	else 
            	{
            		FormFunctions.addDataToTableView(memberview, FormFunctions.getMembers(),
                			idtc, nametc,
                			packageNametc, startDatetc,
                			endDatetc,gendertc,phoneNumbertc,
                			birthDatetc,balancetc,periodtc);
            	}
            	
            	}catch(Exception e) 
            	{
            		e.printStackTrace();
            	}
        	
        }
        else
        	FormFunctions.addDataToTableView(memberview, FormFunctions.getMembers(),
        			idtc, nametc,
        			packageNametc, startDatetc,
        			endDatetc,gendertc,phoneNumbertc,
        			birthDatetc,balancetc,periodtc);
    	

    }
    
    
    
    
    @FXML
    void initialize() {
        
    	  memberview.setOnMouseClicked(event -> {
      		
      	    if (event.getClickCount() == 1) { 
      	        int selectedIndex = memberview.getSelectionModel().getSelectedIndex();
      	        if (selectedIndex != -1) { 
      	        	member selectedMember=memberview.getSelectionModel().getSelectedItem();
      	        	
      	        	
      	        }
      	    }
      	});
    	
    	FormFunctions.addDataToTableView(memberview, FormFunctions.getMembers(),
    			idtc, nametc,
    			packageNametc, startDatetc,
    			endDatetc,gendertc,phoneNumbertc,
    			birthDatetc,balancetc,periodtc);
    	

    }

}
