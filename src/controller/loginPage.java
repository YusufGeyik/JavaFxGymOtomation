package controller;

import java.io.IOException;

import model.FormFunctions;
import model.Operator;
import model.database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class loginPage {
 

	private Parent root;
    @FXML
    public Button loginbt;

    @FXML
    public TextField optNametf;

    @FXML
    public PasswordField passtf;

    
    @FXML
    void loginClick(ActionEvent event) throws IOException {
         String username = optNametf.getText();
         String password = passtf.getText();
         model.database database=new database();
         Boolean isLogin = database.Login(username, password);
         
         
         FormFunctions.setMembers(database.bringMembers());
         FormFunctions.setPackages(database.bringPackages());
         FormFunctions.setItems(database.bringItems());
         FormFunctions.setLogs(database.bringLogs());
        FormFunctions.setOperator(new Operator(optNametf.getText()));
         database.closeConnection();
         if(isLogin) {
        	 try { FormFunctions F=new FormFunctions();
        	 
        	 F.sceneChange(FormFunctions.mainPage,FormFunctions.getOperator().getOperatorName());
        	 
            
        	 }catch(Exception e) 
        	 {
        		 e.printStackTrace();
        	 }
        	 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  
             stage.close();
        	 
         } 
    }
    
    
    
    
    
    

	

}
