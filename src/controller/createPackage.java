package controller;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.FormFunctions;
import model.logs;
import model.membership;

public class createPackage {
	  @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private TableColumn<model.membership, Double> discountRate;

	    @FXML
	    private TableColumn<model.membership, String> accesibleZones;

	    @FXML
	    private CheckBox cFitnessChckbx;

	    @FXML
	    private CheckBox cOneMonth;

	    @FXML
	    private TextField cPackageNameTxt;

	    @FXML
	    private TextField cPackagePriceTxt;

	    @FXML
	    private TableView<model.membership> cPackageTable;

	    @FXML
	    private CheckBox cPoolchckbx;

	    @FXML
	    private CheckBox cSixMonths;

	    @FXML
	    private CheckBox cSpiningChckbx;

	    @FXML
	    private CheckBox cThreeMonths;

	    @FXML
	    private CheckBox cTwelveMonths;

	    @FXML
	    private CheckBox cZumbaChckbx;

	    @FXML
	    private TextField discountRatetxt;

	    @FXML
	    private TableColumn<model.membership, String> packageName;

	    @FXML
	    private TableColumn<model.membership, Integer> packagePrice;

	    @FXML
	    private TableColumn<model.membership, String> periods;

	    @FXML
	    private Button saveBt;

    @FXML
    void SaveMemberShipPackage(ActionEvent event) {
    	try {
    		membership newMembership;
    	double discountRate=1-(Double.parseDouble(discountRatetxt.getText())/100); //ready to multiply with base price for long-term memberships.
    	
    	System.out.println(discountRate);
    	try {
    	
    		List<CheckBox> zones=new ArrayList<>();
    	    List<CheckBox> periods=new ArrayList<>();
    	    zones.add(cFitnessChckbx);     
    	    zones.add(cPoolchckbx);     
    	    zones.add(cSpiningChckbx); 
    	    zones.add(cZumbaChckbx); 
    	    
    	    periods.add(cOneMonth);
    	    periods.add(cThreeMonths);
    	    periods.add(cSixMonths);
    	    periods.add(cTwelveMonths);
    	        

    	
    	String accesibleZones="";
    	
    	for(CheckBox z : zones) 
    	{
    		if(z.isSelected())
    			accesibleZones+=z.getText()+" ";
    		
    	}
    	
    	String pPeriods="";
    	
    	for(CheckBox p : periods) 
    	{
    		if(p.isSelected())
    			pPeriods+=p.getText()+"  ";
    		
    	}
    	
    	
       
    	      newMembership=new model.membership(
    			cPackageNameTxt.getText(),
    			Integer.parseInt(cPackagePriceTxt.getText()),
    			accesibleZones,
    			pPeriods,
    			discountRate
    			);
    	
    	
    	
    	model.database db=new model.database();
    
			db.registerPackage(newMembership);
			FormFunctions.setPackages(db.bringPackages());
			String logDetails=newMembership.getMembershipName()+"\nBase Price : "+ String.valueOf(newMembership.getMembershipPrice())+"\nDiscount Rate: " 
			+String.valueOf(newMembership.getDiscountRate())+"\nAccesible Zones: "+newMembership.getAccesibleZones()+"\nPurchasable Periods " + newMembership.getPeriods() ;
			FormFunctions.createLog("New Membership Package", logDetails);
			db.closeConnection();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	
    	}catch(NumberFormatException | InvocationTargetException e) 
    	{
    		
    		FormFunctions.alertMessageErr("New Package","Creating New Package Operation ", "Unsuccessfull, please provide valid values ");
    		
    	}
    	
    	FormFunctions.addDataToTableView(cPackageTable, FormFunctions.getPackages(), packageName,packagePrice,accesibleZones,periods,discountRate);
    	
    }
    

    @FXML
    void initialize() {
        
    	try {
    	FormFunctions.addDataToTableView(cPackageTable, FormFunctions.getPackages(), packageName,packagePrice,accesibleZones,periods,discountRate);
    	}catch(Exception e)
    	{
    		System.out.println(e);
    	}
    }

}
