package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.FormFunctions;
import model.items;
import model.member;
import model.logs;

public class notifications {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<items, String> itemNametc;

    @FXML
    private Label change;
   
  
    @FXML
    private TableColumn<member, String> dPhoneNumbertc;
    @FXML
    private TableColumn<member, String> dPackageNametc;

    @FXML
    private TableColumn<member, Double> dDebttc;

    @FXML
    private TableColumn<member, String> dMemberNametc;
  
    @FXML
    private TableView<member> debtstb;

    
    @FXML
    private TableColumn<member, LocalDate> eDatetc;
    @FXML
    private TableColumn<member, String> ePhoneNumbertc;
   

    @FXML
    private TableColumn<member, String> eMemberNametc;

    @FXML
    private TableColumn<member, String> eMemberPackagetc;

    @FXML
    private TableView<member> expiringMembershipstb;

    @FXML
    private TableView<items> inventorytb;

    @FXML
    private TableColumn<items, Double> sellingPricetc;

    @FXML
    private TableColumn<items, Double> unitCosttc;
 

    @FXML
    private TableColumn<items, Integer> itemQuantitytc;


    @FXML
    private TableColumn<logs, LocalDate> logDate;

    @FXML
    private TableColumn<logs, String> logDetails;

    @FXML
    private TableColumn<logs, String> logOperator;

    @FXML
    private TableColumn<logs, String> logType;
    
    @FXML
    private TableColumn<logs, Integer> logID;

    @FXML
    private TableView<logs> logstb;

    
    @FXML
    void initialize() {
    
    	ObservableList<member> memberExpiringMembership=FXCollections.observableArrayList();
    	ObservableList<member>memberWithDebts=FXCollections.observableArrayList();
    	ObservableList<items> stockProblem=FXCollections.observableArrayList();
    	
    	
    	for(items i:FormFunctions.getItems()) 
    	{
    		if(i.getStockCount()<10) 
    		{
    			stockProblem.add(i);
    			
    		}
    	}
    	FormFunctions.addDataToTableView(inventorytb,stockProblem, itemNametc,
    			itemQuantitytc, sellingPricetc,
    			unitCosttc);
    	
    	for(member m:FormFunctions.getMembers()) 
    	{
    		if(m.getBalance()<0) 
    		{
    			
    			memberWithDebts.add(m);
    		}
    		
    	}
    	
    		FormFunctions.memberTableSnackBar(debtstb, memberWithDebts,
    			
    			
    			
    			dMemberNametc,
    			dPackageNametc,dPhoneNumbertc,dDebttc);
    		for(member m:FormFunctions.getMembers()) 
    		{
    			long remainingMembership = ChronoUnit.DAYS.between(LocalDate.now(),m.getMembershipEnd());
    			
    			if(remainingMembership<7) 
    			{
    				memberExpiringMembership.add(m);
    			}
    			
    		}
    		FormFunctions.memberTableMembershipExpiring(expiringMembershipstb, memberExpiringMembership,
    				
    				
    				
    				eMemberNametc,
    				eMemberPackagetc,ePhoneNumbertc,eDatetc); {
    	
    }
    				
    				String sql="SELECT * FROM logs WHERE logDate=?";
    			 	List<Object> psValues=new ArrayList<>();
    			 	psValues.add(LocalDate.now());
    			 	
    			       model.database database=new model.database();
    			       ObservableList<logs> todaysLogs=database.logOperations(sql, psValues);
    			    	
    			    	database.closeConnection();		
    	FormFunctions.addLogsToTable(logstb, todaysLogs, logID, logType, logOperator, logDate, logDetails);
    
    
    }
}
