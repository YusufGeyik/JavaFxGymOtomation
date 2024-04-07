package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.FormFunctions;
import model.member;

public class mainPage {

	private String operatorName;
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
    void logsClicked(ActionEvent event) {

    	 FormFunctions F=new FormFunctions();
         F.changeAnchor(F.bringLogs,pane,rightAnchor);
    }
    
    

    @FXML
    void registerItemClicked(ActionEvent event) {
       FormFunctions F=new FormFunctions();
       F.changeAnchor(F.registerItem, pane, rightAnchor);
    }

    @FXML
    void newMemberClicked(ActionEvent event) {

    	 FormFunctions F=new FormFunctions();
         F.changeAnchor(F.newMember,pane,rightAnchor);
    }

    @FXML
    void newPackageClicked(ActionEvent event) {

        FormFunctions F=new FormFunctions();
        F.changeAnchor(F.createPackage,pane,rightAnchor);
    }

    @FXML
    void paymentClicked(ActionEvent event) {

        FormFunctions F=new FormFunctions();
        F.changeAnchor(F.payment,pane,rightAnchor);
    }

    @FXML
    void sellClicked(ActionEvent event) {

    	FormFunctions F=new FormFunctions();
    	F.changeAnchor(F.snackBar, pane, rightAnchor);
    }

    @FXML
    void showMainPage(ActionEvent event) {
         FormFunctions F=new FormFunctions();
         F.changeAnchor(F.notifications,pane,rightAnchor);

    	
    }

    @FXML
    void updateInvClicked(ActionEvent event) {

    }

    @FXML
    void updateMemberClicked(ActionEvent event) {
        FormFunctions F=new FormFunctions();
        F.changeAnchor(F.updatedeleteMember,pane,rightAnchor);
    	
    }

    @FXML
    void updatePackageClicked(ActionEvent event) {

        FormFunctions F=new FormFunctions();
        F.changeAnchor(F.updatedeletePackages,pane,rightAnchor);
    }
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
        operatorNamelbl.setText(operatorName);
        
    }
    @FXML
    void initialize() {
  
    	FormFunctions f=new FormFunctions();
    	f.changeAnchor(f.notifications, pane, rightAnchor);
     
    }

}
