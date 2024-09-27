package controller;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import model.FormFunctions;
import model.member;
import model.membership;
import model.database;
import model.membership;
import model.member;
public class newMember {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker birthDatepicker;

    @FXML
    private ComboBox<String> genderbx;

    @FXML
    private TextField memberNametxt1;

    @FXML
    private TextField memberSurnametxt2;

    @FXML
    private ComboBox<String> membershipPeriodbx;


    @FXML
    private DatePicker membershipStartDatepicker;
    @FXML
    private ComboBox<String> membershipbx;

    @FXML
    private TextField numbertxt;

    @FXML
    private TextField pricetxt;

    @FXML
    private Button savebt;

    @FXML
    private CheckBox isPaid;
    @FXML
    void membershipTypeChanged(ActionEvent event) {
    	String membershipName=membershipbx.getValue();
    	membership membership=FormFunctions.bringPackageObj(membershipName);
    	String[] words = membership.getPeriods().split(",");
    	ObservableList<String> periods=FXCollections.observableArrayList();
    	for(int i=0;i<words.length-1;i++) 
    	{
    		periods.add(words[i]);
    		System.out.println(words[i]);
    		
    	}
    	membershipPeriodbx.setItems(periods);
    	membershipPeriodbx.getSelectionModel().select(0);
    	
    	
    	
    	if(membershipPeriodbx.getSelectionModel().getSelectedItem()!=null && membershipbx.getSelectionModel().getSelectedItem()!=null) {
    	String price;
       
    	
    
    	String period=membershipPeriodbx.getValue();
    	period=period.replaceAll("\\D", "");
		int months=Integer.parseInt(period);
		double discountRate=membership.getDiscountRate();
		if(months>1) 
		{
			
			price=String.valueOf(membership.getMembershipPrice()*months*discountRate);
			pricetxt.setText(price);
		}else if(months==1) 
		{
			price=String.valueOf(membership.getMembershipPrice());
			pricetxt.setText(price);
			
			
		}
		
		
		
        }
        
        
        
		
		
    }

    @FXML
    void periodChanged(ActionEvent event) {
    	
    	 if(membershipbx.getSelectionModel().getSelectedItem()!=null && membershipPeriodbx.getSelectionModel().getSelectedItem()!=null) {
    	
    	String price;
    	String membershipName=membershipbx.getValue();
    	membership membership=FormFunctions.bringPackageObj(membershipName);
    	String period=membershipPeriodbx.getValue();
    	period=period.replaceAll("\\D", "");
		int months=Integer.parseInt(period);
		double discountRate=membership.getDiscountRate();
		if(months>1) 
		{
			
			price=String.valueOf(membership.getMembershipPrice()*months*discountRate);
			pricetxt.setText(price);
		}else if(months==1) 
		{
			price=String.valueOf(membership.getMembershipPrice());
			pricetxt.setText(price);
			
			
		}
		
		
		
		
    	 }

    }

    @FXML
    void saveMember(ActionEvent event) {
    	if(FormFunctions.isString(memberNametxt1.getText())==true && FormFunctions.isString(memberSurnametxt2.getText())==true && membershipbx.getValue()!=null ) {
    	
    	try 
    	{
    		
    		String name=memberNametxt1.getText()+" "+memberSurnametxt2.getText();
    		String period=membershipPeriodbx.getValue().replaceAll("\\D", "");// parse for numbers 
    		String membershipName=membershipbx.getValue();
    		String gender=genderbx.getValue();
    		String phoneNumberFormat="\\d{11}";
    		String phoneNumber;
    		membership membership=FormFunctions.bringPackageObj(membershipName);
    		LocalDate birthDate=birthDatepicker.getValue();
    		LocalDate membershipStart=membershipStartDatepicker.getValue();
			int months=Integer.parseInt(period);
    		LocalDate membershipEnd=membershipStart.plusMonths(months);
    		double balance;
    		double BasePrice=0;
    		
            LocalDate creationDate=LocalDate.now();
    		if(isPaid.isSelected()) 
    		{
    			balance=0;  
    			if(months>1) 
    			{
    				BasePrice=(membership.getMembershipPrice()*membership.getDiscountRate());
    				
    			}
    			else                             
    			{
    				BasePrice=(membership.getMembershipPrice());
    				
    			}
    			//client paid //customer pays
    		}
    		else 
    		{
    			if(months>1) 
    			{
    				BasePrice=(membership.getMembershipPrice()*membership.getDiscountRate());
    				balance=-(double)(membership.getMembershipPrice()*membership.getDiscountRate()*months); 
    			}
    			else                             
    			{
    				BasePrice=(membership.getMembershipPrice());
    				balance=-(double)(membership.getMembershipPrice()*months);
    			}
    		}
    		
    		if(numbertxt.getText().matches(phoneNumberFormat)) 
    		{
    			
    			phoneNumber=numbertxt.getText();
    			    
    			   
    				model.member member=new member(name,membershipPeriodbx.getValue(),membershipName,gender,phoneNumber,birthDate,membershipStart,membershipEnd,balance,creationDate,BasePrice);
    				database db=new database();
    				db.registerMember(member);;
    				FormFunctions.setMembers(db.bringMembers());
    				db.closeConnection();
    				
    		}else 
    		{

        		FormFunctions.alertMessageErr("New Member","Creating New Member Operation ", "Unsuccessfull, please provide valid values ");
    		}
    		
    	}catch(NumberFormatException | InvocationTargetException e) 
    	{
    		
    		FormFunctions.alertMessageErr("New Member","Creating New Member Operation ", "Unsuccessfull, please provide valid values ");
    		
    	}catch(Exception e) 
    	{
    		e.printStackTrace();
    	}
    	
    	
    }//
    else	
    	FormFunctions.alertMessageErr("New Mmember Operation", "Failed", "Please provide valid values.");
   
  }


    @FXML
    void initialize() {
    	 
    	 Tooltip startDatetp=new Tooltip("Choose the start date for the membership");
    	 startDatetp.install(membershipStartDatepicker, startDatetp);
    	 Tooltip numbertp=new Tooltip("Enter a gsm number");
    	 numbertp.install(numbertxt, numbertp);
       
         
    	 membershipStartDatepicker.setValue(LocalDate.now());
         genderbx.setItems(FormFunctions.getGenders());
         genderbx.getSelectionModel().select(0);
         membershipbx.setItems(FormFunctions.bringPackageNames(FormFunctions.getPackages()));
        // membershipPeriodbx.setItems(FormFunctions.getPeriods());
         
         birthDatepicker.setDayCellFactory(call->new DateCell() 
         {
        	 @Override
        	 public void updateItem(LocalDate date,boolean empty) 
        	 {
        		 super.updateItem(date,empty);
        		 setDisable(date.compareTo(LocalDate.now())>0);
        		 
        		 
        	 }
        	 
        	 
        	 
         });
     
    }

}
