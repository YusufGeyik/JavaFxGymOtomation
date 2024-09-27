package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.FormFunctions;
import model.Roles;
import model.member;

public class mainPage {

	
    @FXML
    private TitledPane adminPaneltilted;
    @FXML
    private Button logOut;

    @FXML
    private Button operatorUpdate;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bringLogsbt;

    @FXML
    private AnchorPane leftAnchor;

    @FXML
    private Button mainPagebt;

    @FXML
    private Button registerItembt;

    @FXML
    private Button newMemberbt;

    @FXML
    private Button newPackagebt;

    @FXML
    private Label operatorNamelbl;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button paymentbt;

    @FXML
    private AnchorPane rightAnchor;

    @FXML
    private Button sellbt;

    @FXML
    private Button updateInvbt;

    @FXML
    private Button updateMemberbt;

    @FXML
    private Button updatePackagebt;

    @FXML
    private Button revenues;
    @FXML
    private ImageView operatorVector;

    @FXML
    void revenuesClicked(ActionEvent event) {


    	if(FormFunctions.getOperator().getRole()==Roles.ADMIN) 
    	{
    		FormFunctions F=new FormFunctions();
    		F.changeAnchor(FormFunctions.revenues, pane, rightAnchor);
    	}
    	else 
         	FormFunctions.alertMessageErr("Accessing Module", "unsuccessfull", "You don't have "
         			+ "permission to access this module");
    }

    model.Operator operator=FormFunctions.getOperator();
    @FXML
    void logsClicked(ActionEvent event) {

        if(operator.isAccessToLogs()||operator.getRole()==Roles.ADMIN) {
    	
    	 FormFunctions F=new FormFunctions();
         F.changeAnchor(F.bringLogs,pane,rightAnchor);
        }
        else 
        	FormFunctions.alertMessageErr("Accessing Module", "unsuccessfull", "You don't have "
        			+ "permission to access this module");
    }
    
    

    @FXML
    void registerItemClicked(ActionEvent event) {
    	
    	if(operator.isRegisterInv()||operator.getRole()==Roles.ADMIN) {
    	
       FormFunctions F=new FormFunctions();
       F.changeAnchor(F.registerItem, pane, rightAnchor);
    	}
    	 else 
         	FormFunctions.alertMessageErr("Accessing Module", "unsuccessfull", "You don't have "
         			+ "permission to access this module");
    }

    @FXML
    void newMemberClicked(ActionEvent event) {

    	if(operator.isRegisterMember()||operator.getRole()==Roles.ADMIN) {
    		
    	 FormFunctions F=new FormFunctions();
         F.changeAnchor(F.newMember,pane,rightAnchor);
    	}
    	 else 
         	FormFunctions.alertMessageErr("Accessing Module", "unsuccessfull", "You don't have "
         			+ "permission to access this module");
    	
    }

    @FXML
    void newPackageClicked(ActionEvent event) {

    	if(operator.isCreatePackage()||operator.getRole()==Roles.ADMIN) {
    	
        FormFunctions F=new FormFunctions();
        F.changeAnchor(F.createPackage,pane,rightAnchor);
    	}
    	 else 
         	FormFunctions.alertMessageErr("Accessing Module", "unsuccessfull", "You don't have "
         			+ "permission to access this  module");
    }

    @FXML
    void paymentClicked(ActionEvent event) {

    	if(operator.isTakesPayment()||operator.getRole()==Roles.ADMIN) {
    	
        FormFunctions F=new FormFunctions();
        F.changeAnchor(F.payment,pane,rightAnchor);
    	}
    	 else 
         	FormFunctions.alertMessageErr("Accessing Module", "unsuccessfull", "You don't have "
         			+ "permission to access this module");
    }

    @FXML
    void sellClicked(ActionEvent event) {

    	if(operator.isSell()||operator.getRole()==Roles.ADMIN) {
    	
    	FormFunctions F=new FormFunctions();
    	F.changeAnchor(F.snackBar, pane, rightAnchor);
    	}
    	 else 
         	FormFunctions.alertMessageErr("Accessing Module", "unsuccessfull", "You don't have "
         			+ "permission to access this module");
    	
    }
    

    @FXML
    void logOutClicked(ActionEvent event) throws IOException {

    	FormFunctions F=new FormFunctions();
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  
        stage.close();
    	F.sceneChange(FormFunctions.loginPage,400,398);
    
    }

    @FXML
    void showMainPage(ActionEvent event) {
    	
         FormFunctions F=new FormFunctions();
         F.changeAnchor(F.notifications,pane,rightAnchor);

    	
    }

    @FXML
    void updateInvClicked(ActionEvent event) {

    	if(operator.isUpdateInv()||operator.getRole()==Roles.ADMIN) {
    	
    	FormFunctions f= new FormFunctions();
    	f.changeAnchor(f.updateInventory, pane, rightAnchor);
    	}
    	 else 
         	FormFunctions.alertMessageErr("Accessing Module", "unsuccessfull", "You don't have "
         			+ "permission to access this module");
    }

    @FXML
    void updateMemberClicked(ActionEvent event) {
    	if(operator.isUpdateMember()||operator.getRole()==Roles.ADMIN) {
    	
    		
        FormFunctions F=new FormFunctions();
        F.changeAnchor(F.updatedeleteMember,pane,rightAnchor);
    	}
    	 else 
         	FormFunctions.alertMessageErr("Accessing Module", "unsuccessfull", "You don't have "
         			+ "permission to access this module");
    }

    @FXML
    void updatePackageClicked(ActionEvent event) {

    	if(operator.isUpdatePackage()||operator.getRole()==Roles.ADMIN) {
    	
        FormFunctions F=new FormFunctions();
        F.changeAnchor(F.updatedeletePackages,pane,rightAnchor);
    	}
    	 else 
         	FormFunctions.alertMessageErr("Accessing Module", "unsuccessfull", "You don't have "
         			+ "permission to access this module");
    	
    	
    }
    public void setOperatorName() {
        operatorNamelbl.setText(FormFunctions.getOperator().getOperatorName());
        
    }
    @FXML
    void operatorUpdateClicked(ActionEvent event) {

    	if(FormFunctions.getOperator().getRole()==Roles.ADMIN) 
    	{
    		FormFunctions F=new FormFunctions();
    		F.changeAnchor(FormFunctions.updateDeleteOperator, pane, rightAnchor);
    	}
    	else 
         	FormFunctions.alertMessageErr("Accessing Module", "unsuccessfull", "You don't have "
         			+ "permission to access this module");
    }
    @FXML
    void initialize() {
  
    	
    	
    	if(!(FormFunctions.getOperator().getRole()==Roles.ADMIN))
    		
    	{
    		adminPaneltilted.setVisible(false);
    		
    	}
    	Image operatorVectorImg = new Image(getClass().getResourceAsStream("/images/operatorvector.png"));
    	operatorVector.setImage(operatorVectorImg);
    	FormFunctions f=new FormFunctions();
    	f.changeAnchor(FormFunctions.notifications, pane, rightAnchor);
    	

     
    }
    

}
