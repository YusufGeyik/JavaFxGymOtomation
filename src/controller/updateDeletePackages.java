package controller;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.FormFunctions;
import model.database;
import model.member;
import model.membership;
public class updateDeletePackages {

    @FXML
    private TextField searchtxt;
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<model.membership, String> accesibleZones;

    @FXML
    private CheckBox cOneMonth;

    @FXML
    private TableView<model.membership> cPackageTable;

    @FXML
    private CheckBox cSixMonths;

    @FXML
    private CheckBox cThreeMonths;

    @FXML
    private CheckBox cTwelveMonths;

    @FXML
    private Button deleteBt;

    @FXML
    private TableColumn<model.membership, Double> discountRate;

    @FXML
    private TextField discountRatetxt;

    @FXML
    private TableColumn<model.membership, String> packageName;

    @FXML
    private TableColumn<model.membership, Integer> packagePrice;

    @FXML
    private TableColumn<model.membership, String> periods;

    @FXML
    private CheckBox cFitnessChckbx;

    @FXML
    private TextField cPackagePriceTxt;

    @FXML
    private TextField cPackageNameTxt;

    @FXML
    private CheckBox cPoolchckbx;

    @FXML
    private CheckBox cSpiningChckbx;

    @FXML
    private CheckBox cZumbaChckbx;

    @FXML
    private Button updateBt;

    @FXML
    void searchInputReceived(KeyEvent event) {

    	try {
    	 	
        	if(searchtxt.getText().isEmpty()) //button clicked without any input
        	{
        	
        		FormFunctions.addDataToTableView(cPackageTable, FormFunctions.getPackages(), packageName,packagePrice,accesibleZones,periods,discountRate);
        		
        
        		
        	}
        	else //there is an input
        	{ 
        	
        		ObservableList<membership> foundPackages=FXCollections.observableArrayList();
        	for(membership membershipPackage : FormFunctions.getPackages())
        	{
        		
        
        		if(membershipPackage.getMembershipName().toLowerCase().contains(searchtxt.getText()))
        		{
        			foundPackages.add(membershipPackage);
        			
        		}
        	}
        	
        	
        	FormFunctions.addDataToTableView(cPackageTable, foundPackages, packageName,packagePrice,accesibleZones,periods,discountRate);
        	
      
        	}
        	
        	}catch(Exception e) 
        	{
        		
        		e.printStackTrace();
        		
        	}
    }

    @FXML
    void printPackageData(membership oldmembership) {

    	  
        List<CheckBox> chckzones=new ArrayList<>();
 	    List<CheckBox> chckperiods=new ArrayList<>();
 	   chckzones.add(cFitnessChckbx);     
	    chckzones.add(cPoolchckbx);     
	    chckzones.add(cSpiningChckbx); 
	    chckzones.add(cZumbaChckbx); 
	    
	    chckperiods.add(cOneMonth);
	    chckperiods.add(cThreeMonths);
	    chckperiods.add(cSixMonths);
	    chckperiods.add(cTwelveMonths);
 	    for(CheckBox cz:chckzones)
 	    	cz.setSelected(false);
    		
 	    for(CheckBox cp:chckperiods)
 	    	cp.setSelected(false);
         cPackageNameTxt.setText(oldmembership.getMembershipName());
         cPackagePriceTxt.setText(String.valueOf(oldmembership.getMembershipPrice()));
         discountRatetxt.setText(String.valueOf(100-oldmembership.getDiscountRate()*100)); //changing type to percent
         
    
         String parsedZones[]=oldmembership.getAccesibleZones().split(" ");
         String parsedPeriods[]=oldmembership.getPeriods().split(",");//değişti
       
     
 	        
 	    for(String pz:parsedZones) {
 	    for(CheckBox cz : chckzones) 
 	    {
 	    	
 	    	if(cz.getText().equals(pz)) 
 	    	{
 	    		cz.setSelected(true);
 	    		
 	    	}
 	    	
 	    }
 	    
    	}
 	    
 	    
 	      
 	 	    for(String pp:parsedPeriods) {
 	 	    for(CheckBox cp : chckperiods) 
 	 	    {
 	 	    	
 	 	    	if(cp.getText().equals(pp)) 
 	 	    	{
 	 	    		cp.setSelected(true);
 	 	    		
 	 	    	}
 	 	    	
 	 	    }
 	 	    
 	    	}
 	 	    
 	    
 	    
 	    
    		
    	}
    	
    
    	
    
    @FXML
    void updatePackageData(ActionEvent event) {


		List<CheckBox> zones=new ArrayList<>();
	    List<CheckBox> lperiods=new ArrayList<>();
	    zones.add(cFitnessChckbx);     
	    zones.add(cPoolchckbx);     
	    zones.add(cSpiningChckbx); 
	    zones.add(cZumbaChckbx); 
	    
	    lperiods.add(cOneMonth);
	    lperiods.add(cThreeMonths);
	    lperiods.add(cSixMonths);
	    lperiods.add(cTwelveMonths);
	        

	
	String zaccesibleZones="";
	
	for(CheckBox z : zones) 
	{
		if(z.isSelected())
			zaccesibleZones+=z.getText()+" ";
		
	}
	
	String pPeriods="";
	
	for(CheckBox p : lperiods) 
	{
		if(p.isSelected())
			pPeriods+=p.getText()+",";
		
	}
	
	
    	if(FormFunctions.isString(cPackageNameTxt.getText())==true && FormFunctions.isDouble(cPackagePriceTxt.getText())==true
    			&& FormFunctions.isDouble(discountRatetxt.getText())==true && !zaccesibleZones.equals("") && !pPeriods.equals(""))   {
    	
    	
    	       if(cPackageTable.getSelectionModel().getSelectedIndex()!=-1) {
    	       
    	    	try {
    	    		
    	    	membership oldVersion=cPackageTable.getSelectionModel().getSelectedItem();
    	    		
    	    	double discountRate=1-(Double.parseDouble(discountRatetxt.getText())/100); //ready to multiply with base price for long-term memberships.
    	    	
    	    	
    	    	
    	    	
    	       
    	    	model.membership newVersion=new model.membership(
    	    			cPackageNameTxt.getText(),
    	    			Double.parseDouble(cPackagePriceTxt.getText()),
    	    			zaccesibleZones,
    	    			pPeriods,
    	    			discountRate
    	    			);
    	    	
    	    	
    	    	
    	    	model.database db=new model.database();
    	    
    				db.updateMembership(oldVersion,newVersion);
    				FormFunctions.setPackages(db.bringPackages());
    				
    				db.closeConnection();
    			} 
    			
    	    	
    	    	catch(Exception e) 
    	    	{
    	    		
    	    		e.printStackTrace();
    	    		
    	    	}
    	    	
    	    	
    	    	FormFunctions.addDataToTableView(cPackageTable, FormFunctions.getPackages(), packageName,packagePrice,accesibleZones,periods,discountRate);
    	   
    	      
    	       }else 
    	       {
    	    	   FormFunctions.alertMessageErr("Updating Package", "Package Update Operation", "unsuccessfull,please choose the package you wanted to update first");
    	       }
    	       
    }else
    	FormFunctions.alertMessageErr("Updating Package Operation", "Failed", "Please provide valid values.");
    }
    

    @FXML//üyelerle tekrar kontrol tek paket kalsa da siliyor ama heralde üye olmadığından dikkat et. Bride pakete kayıtlı üye yoksa boşu boşuna başka paket seçtirmesek mi    
    void deletePackage(ActionEvent event) {

    	
    	try 
    	{
    	if(cPackageTable.getSelectionModel().getSelectedIndex()!=-1) {
    	
    	 String response=FormFunctions.alertMessageConf("Deleting Package","Deleting Package Operation","Are you sure you want to delete the Package?");
    	 
  
    	 if(response.equals("YES")) 
    	 {
    	
    		 if(FormFunctions.getPackages().size()>1 || (FormFunctions.getPackages().size()==1 && FormFunctions.getMembers().size()==0 )) {
    		 
    			 membership oldMembership=cPackageTable.getSelectionModel().getSelectedItem();
    			 
    			 ObservableList<String>packages=FormFunctions.bringPackageNames(FormFunctions.getPackages());
    			 packages.remove(oldMembership.getMembershipName());
    			
    			 ChoiceDialog<String>  dia=new ChoiceDialog<String>("",packages);
    			 
    		 
    			 
    		
    		 VBox vbox = new VBox();
    		 CheckBox chckbx=new CheckBox();
    		 chckbx.setText("Calculate the refunds or additional payment amounts related to transitioning subscribers who have purchased the package to be deleted to another package? Calculations will be made based on current membership fees.");
    		 vbox.getChildren().addAll(chckbx);
    	
    		 if(packages.isEmpty()) 
    		 {
    			 dia.setTitle("Deleting Package");
        		 dia.setHeaderText("Deleting Package Operation");
        		 dia.setContentText("There is not any memebers who purchased this membership.Click 'OK' to delete it.");
    			
    			 chckbx.setVisible(false);
    		 }
    		 else {
    		
    			
    			 dia.setTitle("Deleting Package");
        		 dia.setHeaderText("Deleting Package Operation");
        		 dia.setContentText("The members who have purchased the package to be deleted will be transitioned to which package? Will refunds or additional payments be calculated?");
        		 dia.getDialogPane().setContent(new VBox(dia.getDialogPane().getContent(), vbox));
        		 dia.setSelectedItem(packages.get(0));
    		 
    			chckbx.setVisible(true);
    		 }
    		 
    		 Optional<String> choice=dia.showAndWait();
    		 choice.ifPresent(selected->{
    	     
    	      
    	       System.out.println(selected);
    			 
    			 String newPackageName=selected;
    			 
    			
    			 if(chckbx.isSelected()==true) 
    			 {
    				 
    				 double oldDiscountRate=oldMembership.getDiscountRate();
    				 String oldPackageName=oldMembership.getMembershipName();
    				 for(membership newMembership:FormFunctions.getPackages()) 
    				 {
    					 if(newMembership.getMembershipName().equals(newPackageName));
    					 double newDiscountRate=newMembership.getDiscountRate();
    					 database db=new database();
    					 try {
							FormFunctions.deletePackageCalculations(oldDiscountRate, newDiscountRate, oldPackageName,newPackageName,oldMembership);
						
							db.deletePackage(oldMembership);
							
							FormFunctions.setPackages(db.bringPackages());
							FormFunctions.setMembers(db.bringMembers());
							FormFunctions.addDataToTableView(cPackageTable, FormFunctions.getPackages(), packageName,packagePrice,accesibleZones,periods,discountRate);
							
							
							db.closeConnection();//dikkat connection kapatma
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
    				 }
    				 
    				 
    			 }
    			 else 
    			 {  
    				 
    				 database db=new database();
    				 String oldPackageName=oldMembership.getMembershipName();
    				 String logDetails=oldPackageName+"Deleted,payments made,members transitioned to"+newPackageName+"\nTransitioned Members\n";
    				 for(member member:FormFunctions.getMembers()) 
    				 {
    					 if(member.getMemberMembership().equals(oldPackageName)) 
    					 {
    						 logDetails+="\n"+member.getMemberID()+" "+member.getMemberName();
    						 member oldMember=member;
    						 member.setMemberMembership(newPackageName);
    						 
    						 db.updateMember(member,oldMember,"Package Transition Due To Deleted Package");
    						 
    					 }
    					 
    				 }
    				 
    				 db.deletePackage(oldMembership);
    				    FormFunctions.createLog("Delete Package", logDetails);			  
    				    FormFunctions.setPackages(db.bringPackages());
						FormFunctions.setMembers(db.bringMembers());
						FormFunctions.addDataToTableView(cPackageTable, FormFunctions.getPackages(), packageName,packagePrice,accesibleZones,periods,discountRate);
    				 db.closeConnection();
    				 
    				 
    				 
    				 
    				 
    			 }
    		
    			 
    		
    		 
    		 
    		 
    		 }
    		 
    				 
    				 
    				 
    				 
    				 );
    		 
    		 
    		 
    		
    		 }
    		 else if(FormFunctions.getPackages().size()==1 && FormFunctions.getMembers().size()>0) 
    		 {
    			 FormFunctions.alertMessageErr("Deleting Package", "Deleting Package Operation", "unsuccessfull,there is no other package that current members will be transitioned to");
    	    		//there is no other package to current members will be trasitioned to
    		 }
    		 
    	 }
    	 else 
    	 {
    		 
    		 //Operator choose "NO" with delete operation confirmation.
    		 
    	 }
    	
    
    	
    		
    		
    		
    		
    		
    	
    	}
    	else 
    	{
    		
    		 FormFunctions.alertMessageErr("Deleting Package", "Deleting Package Operation", "unsuccessfull,please choose the package you wanted to delete first");
    		
    	}
    	}catch(Exception e) 
    	{
    		e.printStackTrace();
    	}
    	
    	
    }

    @FXML
    void initialize() {

    	
    	cPackageTable.setOnMouseClicked(event -> {
    		
    	    if (event.getClickCount() == 1) { 
    	        int selectedIndex = cPackageTable.getSelectionModel().getSelectedIndex();
    	        if (selectedIndex != -1) { 
    	        	membership oldmembership=cPackageTable.getSelectionModel().getSelectedItem();
    	        	printPackageData(oldmembership);
    	        	
    	        }
    	    }
    	});
    	
    	
    	try {
    	FormFunctions.addDataToTableView(cPackageTable, FormFunctions.getPackages(), packageName,packagePrice,accesibleZones,periods,discountRate);
    	}catch(Exception e)
    	{
    		System.out.println(e);
    	}

    	

    }

}
