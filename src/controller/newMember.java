package controller;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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
        if(membershipPeriodbx.getSelectionModel().getSelectedItem()!=null) {
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
    void periodChanged(ActionEvent event) {
    	
    	 if(membershipbx.getSelectionModel().getSelectedItem()!=null) {
    	
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
    	try 
    	{
    		
    		String name=memberNametxt1.getText()+" "+memberSurnametxt2.getText();
    		String period=membershipPeriodbx.getValue().replaceAll("\\D", "");//int çevir 
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
    		
            LocalDate creationDate=LocalDate.now();
    		if(isPaid.isSelected()) 
    		{
    			balance=0;                //client paid 
    		}
    		else 
    		{
    			if(months>1) 
    			{
    				
    				balance=-(double)(membership.getMembershipPrice()*membership.getDiscountRate()*months); 
    			}
    			else                             
    			{
    				balance=-(double)(membership.getMembershipPrice()*months);
    			}
    		}
    		
    		if(numbertxt.getText().matches(phoneNumberFormat)) 
    		{
    			System.out.println("number");
    			phoneNumber=numbertxt.getText();
    			
    				model.member member=new member(name,period,membershipName,gender,phoneNumber,birthDate,membershipStart,membershipEnd,balance,creationDate);
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
    	
    	
    	
    	
    	
    //balance ödeme nakit mi değil mi sor dateleri string çevir membershipend hesapla  isactive ve creationdate ayarla
   // member member=new member(memberNametxt1.getText()+" "+memberSurnametxt2.getText(),membershipPeriodbx.getValue(),membershipbx.getValue(),
  //  genderbx.getValue(),numbertxt.getText(),birthDatepicker.getValue(),membershipStart.getValue() 
    	//String membershipEnd,int balance,boolean isActive,String creationDate)
  }


    @FXML
    void initialize() {
    	 
    	 Tooltip startDatetp=new Tooltip("Choose the start date for the membership");
    	 startDatetp.install(membershipStartDatepicker, startDatetp);
    	 Tooltip numbertp=new Tooltip("Enter a gsm number");
    	 numbertp.install(numbertxt, numbertp);
    	
    	 membershipStartDatepicker.setValue(LocalDate.now());
         genderbx.setItems(FormFunctions.getGenders());
         membershipbx.setItems(FormFunctions.bringPackageNames(FormFunctions.getPackages()));
         membershipPeriodbx.setItems(FormFunctions.getPeriods());
         
         
         
         
         
    }

}
