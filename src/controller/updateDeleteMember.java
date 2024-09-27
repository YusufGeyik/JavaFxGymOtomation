package controller;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.FormFunctions;
import model.database;
import model.member;
import model.membership;
import model.transactions;


public class updateDeleteMember {


    @FXML
    private TableColumn<member, Double> balancetc;

    @FXML
    private TableColumn<member, LocalDate> birthDatetc;

    @FXML
    private DatePicker birthdatepicker;

    @FXML
    private Button deletebt;

    @FXML
    private TableColumn<member, LocalDate> endDatetc;
    
    
    @FXML
    private ComboBox<String> genderbx;

    @FXML
    private TableColumn<member, String> gendertc;

    @FXML
    private TableColumn<member, Integer> idtc;

    @FXML
    private TextField findMembertxt;

    @FXML
    private TextField memberNametxt;


    @FXML
    private ComboBox<String> membershipbx;

  

    @FXML
    private ComboBox<String> membershipperiodbx;

    @FXML
    private TableView<member> memberview;

    @FXML
    private TableColumn<member, String> nametc;

    @FXML
    private TextField numbertxt;

    @FXML
    private TableColumn<member, String> packageNametc;

    @FXML
    private TableColumn<member, String> periodtc;

    @FXML
    private TableColumn<member, String> phoneNumbertc;

    @FXML
    private TableColumn<member, LocalDate> startDatetc;

    @FXML
    private Button updatebt;


    @FXML
    void deleteMember(ActionEvent event) {
    	String logDetails="";
    	try {
    		if(memberview.getSelectionModel().getSelectedIndex()!=-1) // isSelected
    		{
    			String response=FormFunctions.alertMessageConf("Deleting Member", "Deleting Member Operation", "Are you sure to delete this member");
    			if(response.equals("YES")) //want to delete
    			{
    		   double paid=0;
    			member member=memberview.getSelectionModel().getSelectedItem();
    			
           
             
    			double earnedPayment=0;
                
    			Period restOfTime=Period.between(LocalDate.now(),member.getMembershipEnd());
    			int paidMonthDiff=restOfTime.getMonths();
    			paid=(member.getPaidBasePrice())*paidMonthDiff;
    			
    			earnedPayment=paid+member.getBalance();
    			
    			
    			if(earnedPayment>0) //refund needed optional to operator
    			{
    				response=FormFunctions.alertMessageConf("Deleting Member", "Deleting Member Operation", "Refund amount " +(int)(Math.abs(earnedPayment))+" Will a refund be issued to the user?");
    			if(response.equals("YES")) //earnedpayment will be the same
    			{
    			
    				FormFunctions.alertMessageInf("Deleting Member", "Deleting Member Operation", "Company should pay to the member " +(int)(Math.abs(earnedPayment))+" deleting Member Operation successfull");
    			
    			
    			}	
    			else //earnedpayment will be 0
    				 earnedPayment=0;
    				
    			 
    				                                                                     
    			
    			
    			}else if(earnedPayment<0 )  // customer should pay
    			{
    				
    				FormFunctions.alertMessageInf("Deleting Member", "Deleting Member Operation", "Member should pay " +(int)(Math.abs(earnedPayment))+" deleting Member Operation successfull");
    				
    			}
    			else if(earnedPayment==0) 
    			{
    				
    				FormFunctions.alertMessageInf("Deleting Member", "Deleting Member Operation", "Deleting member is successfull there is no need for extra payment or refund");
    				
    			}
    		
    			model.database db=new model.database();
    			db.deleteMember(member);
    			FormFunctions.alertMessageInf("Deleting Member", "Deleting Member Operation", "deleting Member Operation successfull");
    			FormFunctions.setMembers(db.bringMembers());
    			FormFunctions.addDataToTableView(memberview, FormFunctions.getMembers(),
    	    			idtc, nametc,
    	    			packageNametc, startDatetc,
    	    			endDatetc,gendertc,phoneNumbertc,
    	    			birthDatetc,balancetc,periodtc);
    			
    			
    			
    			if(earnedPayment<0)    //company pays
    			{
    			 logDetails=member.getMemberName()+"\nPhone Number: "+member.getPhoneNumber()+"\n Former member paid for remaining balance:"+ Math.abs(earnedPayment);
    			transactions transaction=new transactions(earnedPayment, LocalDate.now(), "Paid To Member For DELETİNG subscription");
    			db.addTransaction(transaction);
    			
    			
    			}
    			else //customer pays
    			{
    		     logDetails=member.getMemberName()+"\nPhone Number: "+member.getPhoneNumber()+"\nCompany Paid:"+earnedPayment;
    		     transactions transaction=new transactions(earnedPayment, LocalDate.now(), "Customer paid for DELETİNG subscription process.");
    		     db.addTransaction(transaction);
     			
    			}
    			FormFunctions.createLog("Deleting Member", logDetails);
    		
    			db.closeConnection();
    			}
    			else // Dont want to delete 
    			{
    				
    				
    			}
    		}
    		
    		
    		
    		else 
    		FormFunctions.alertMessageErr("Deleting Member", "Deleting Member Operation", "Unsuccessfull, please choose the member you want to delete");
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
    	}catch(Exception e) 
    	{
    		
    		e.printStackTrace();
    		
    	}
    	
    	
    	
    	
    	
    }


    	
    	
    	

    @FXML
    void findMember(KeyEvent event) {

    	if(FormFunctions.isString(findMembertxt.getText())==true) {
        	try{
        	ObservableList<member> foundMembers=FormFunctions.findMembersByName( findMembertxt.getText());
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
    void updateMember(ActionEvent event) {
        //if baseprice==0 membershipstart=Localdate.now()4
    	
    	
    	
    	if(FormFunctions.isString(memberNametxt.getText())==true ) {
    	
    	member oldVersionMember;
        member newVersionMember;
    	if(memberview.getSelectionModel().getSelectedIndex()!=-1) {
    	try {
    		
    	 oldVersionMember=memberview.getSelectionModel().getSelectedItem();
    	 if(oldVersionMember.getPaidBasePrice()==0) 
    	 {
    	    oldVersionMember.setMembershipStart(LocalDate.now());  // ended membership startdate is corrected for further membership buying
    	 }
    	//package and period
    	
    	String number=FormFunctions.parseAndCheckNumber(numbertxt.getText());
    	 if(!number.equals("")) {
    	if((!oldVersionMember.getMemberMembership().equals(membershipbx.getValue()) || !oldVersionMember.getMembershipPeriod().equals(membershipperiodbx.getValue()) )) 
    	{
    		
    		
    		
    		
    		
    		
    		
    		
    	
    		double restFromFirstMembership;
    		double diff;
    		double newCal=0;

			String newPeriod=membershipperiodbx.getValue().replaceAll("\\D", "");
			int newMonths=Integer.parseInt(newPeriod);
    	    membership newMembership=FormFunctions.bringPackageObj(membershipbx.getValue());
    	    LocalDate currentDate=LocalDate.now();
	    	
	    	
	    	Period restOfTime=Period.between(currentDate,oldVersionMember.getMembershipEnd());
	         int paidMonthDiff=restOfTime.getMonths();
			 int newPayMonthDiff=(int) ChronoUnit.MONTHS.between(currentDate, oldVersionMember.getMembershipStart().plusMonths(newMonths));
			 
			 if (oldVersionMember.getMembershipEnd().getDayOfMonth() < currentDate.getDayOfMonth() && oldVersionMember.getMembershipEnd().getDayOfMonth() > 1) {
		            newPayMonthDiff++;
		        }
	    	
	    	 newVersionMember=new member(oldVersionMember.getMemberID(),memberNametxt.getText(),membershipperiodbx.getValue()
	    			 ,membershipbx.getValue(),genderbx.getValue(),number,birthdatepicker.getValue(),oldVersionMember.getMembershipStart()
	    			 ,LocalDate.now().plusMonths(newMonths) ,oldVersionMember.getBalance(),oldVersionMember.getCreationDate(),0);
    	    
    	    
    	   
	    	 restFromFirstMembership=oldVersionMember.getPaidBasePrice()*paidMonthDiff;
    	    	
    	    	
    	    	if(newMonths>1) 
    	    	{
    	    		
    	    		newCal=(double) ((newMembership.getMembershipPrice()*newMembership.getDiscountRate())*newPayMonthDiff);  //cost for the remaining  
    	    		diff=restFromFirstMembership-newCal;
    	    		newVersionMember.setPaidBasePrice((newMembership.getMembershipPrice()*newMembership.getDiscountRate())); 
    	    		decideWhoPaysAndUpdate(diff,newVersionMember,oldVersionMember);
    	    	}else if(newMonths==1) 
    	    	{
    	    		newCal=(double) ((newMembership.getMembershipPrice())*newPayMonthDiff);
    	    		diff=restFromFirstMembership-newCal;
    	    		newVersionMember.setPaidBasePrice((newMembership.getMembershipPrice())); 
    	    		decideWhoPaysAndUpdate(diff,newVersionMember,oldVersionMember);
    	    		
    	    	}
    	    	
    	    	
    	    	
    	    	
    	    	
    	   
    	    
    	    
    	    
    		
    		
    	}
    	else 
    	{
    		newVersionMember=oldVersionMember;
    		newVersionMember.setMemberName(memberNametxt.getText());
    		newVersionMember.setPhoneNumber(number);
    		newVersionMember.setGender(genderbx.getValue());
    		newVersionMember.setBirthDate(birthdatepicker.getValue());
    		
    	
    		
    		
    	    model.database db=new model.database();
    	    db.updateMember(newVersionMember,oldVersionMember,"Update Member");
    	    FormFunctions.alertMessageInf("Updating Member", "Updatin Member Operation", "Member successfully updated.");
    		
    	}
    	
    	
    	 }
    	 
    	 else 
    	 {
    		 
    		 FormFunctions.alertMessageErr("Updating Member", "Updating Member Operation", "Please provide a valid gsm number");
    	 }
    	}catch(InvocationTargetException e) 
    	{
    		//alert    	
    		
    	}
    	catch(Exception e) 
    	{
    		e.printStackTrace();
    		
    	}
    	}
    	else 
    	{
    		FormFunctions.alertMessageErr("Updating Memeber", "Updating Member Operation", "Please choose the member you want to update");
    	}
    	
    	model.database db=new model.database();
    	FormFunctions.setMembers(db.bringMembers());
    	
    	FormFunctions.addDataToTableView(memberview, FormFunctions.getMembers(),
    			idtc, nametc,
    			packageNametc, startDatetc,
    			endDatetc,gendertc,phoneNumbertc,
    			birthDatetc,balancetc,periodtc);
   
    }
    	else
    		FormFunctions.alertMessageErr("Updating Member Operation", "Failed", "Please provide valid values.");
    	
    }

    
    void decideWhoPaysAndUpdate(double diff,member newVersionMember,member oldVersionMember) 
    { 
    	model.database db=new model.database();
    	if(diff>0) //company should pay optional
    	{  
    		String response=FormFunctions.alertMessageConf("Updating Member", "Updating Member Operation","Does company willing to make repayment "+diff+"?");
    		if(response.equals("YES")) //company pay
    		{   
    			newVersionMember.setBalance(newVersionMember.getBalance()+diff);
    			db.updateMember(newVersionMember,oldVersionMember,"Update Member");
    			FormFunctions.alertMessageInf("Updating Member", "Updating Member Operation","Member successfully updated.The required payment amount has been deducted from the user's balance." );
    			
    		}else //company will not pay
    		{
    			diff=0;
    			db.updateMember(newVersionMember,oldVersionMember,"Update Member");
    			FormFunctions.alertMessageInf("Updating Member", "Updating Member Operation","Member successfully updated.Refund has not been issued to the member" );
    		}
    	
    		
    	}
    	else if(diff<0) //customer has to pay
    	{
    		newVersionMember.setBalance(newVersionMember.getBalance()+diff);
    		db.updateMember(newVersionMember,oldVersionMember,"Update Member");
    		FormFunctions.alertMessageInf("Updating Member", "Updating Member Operation","Member successfully updated.The required payment amount has been deducted from the user's balance." );
    		
    		
    		
    	}
    	else  //no difference
    	{
    		db.updateMember(newVersionMember,oldVersionMember,"Update Member");
    		FormFunctions.alertMessageInf("Updating Member", "Updating Member Operation","Member successfully updated.The transition requires no additional payment or repayment" );
    		
    		
    		
    	}
    	
    	
    	db.closeConnection();
    	
    	
    }
    
    
    void printSelectedMemberData(member selectedMember) 
    {
    	
    	memberNametxt.setText(selectedMember.getMemberName());
    	numbertxt.setText(selectedMember.getPhoneNumber());
    	genderbx.setValue(selectedMember.getGender());
    	birthdatepicker.setValue(selectedMember.getBirthDate());
    	membershipbx.setValue(selectedMember.getMemberMembership());
    	membershipperiodbx.setValue(selectedMember.getMembershipPeriod());
    	
    	
    }
    
    public void initialize() {
    	
           memberview.setOnMouseClicked(event -> {
    		
    	    if (event.getClickCount() == 1) { 
    	        int selectedIndex = memberview.getSelectionModel().getSelectedIndex();
    	        if (selectedIndex != -1) { 
    	        	member selectedMember=memberview.getSelectionModel().getSelectedItem();
    	        	printSelectedMemberData(selectedMember);
    	        	
    	        }
    	    }
    	});
           
           birthdatepicker.setDayCellFactory(call->new DateCell() 
           {
          	 @Override
          	 public void updateItem(LocalDate date,boolean empty) 
          	 {
          		 super.updateItem(date,empty);
          		 setDisable(date.compareTo(LocalDate.now())>0);
          		 
          		 
          	 }
          	 
          	 
          	 
           });
    	
    	
    	//combo box settings
       
        genderbx.setItems(FormFunctions.getGenders());
        membershipbx.setItems(FormFunctions.bringPackageNames(FormFunctions.getPackages()));
        
        //membership packages will be read from membershippackagestable
        
        membershipperiodbx.setItems(FormFunctions.getPeriods());
        
        FormFunctions.addDataToTableView(memberview, FormFunctions.getMembers(),
    			idtc, nametc,
    			packageNametc, startDatetc,
    			endDatetc,gendertc,phoneNumbertc,
    			birthDatetc,balancetc,periodtc);
   
    }

}
