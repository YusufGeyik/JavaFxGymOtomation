package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class loginPage {
 

	
    @FXML
    public Button loginbt;

    @FXML
    public TextField optNametf;

    @FXML
    public PasswordField passtf;

    @FXML
    private Button registerOperatorbt;
    @FXML
    private ImageView backgroundImgview;


    @FXML
    private AnchorPane anchorPane;

    
    @FXML
    void registerOperatorClicked(ActionEvent event) {

    	FormFunctions F=new FormFunctions();
    	F.changeAnchor(FormFunctions.registerOperator, anchorPane );
    }

    
    
    @FXML
    void loginClick(ActionEvent event) throws IOException {
         
    	String username = optNametf.getText().trim();
         String password = FormFunctions.hashIt(passtf.getText(), username.trim());
         model.database database=new database();
         database.updateExpiredMemberships();
         
     
         
         if(FormFunctions.isString(username)) {
         
         Operator isLogin = database.Login(username, password);
         
         
         FormFunctions.setMembers(database.bringMembers());
         FormFunctions.setPackages(database.bringPackages());
         FormFunctions.setItems(database.bringItems());
         FormFunctions.setOperators(database.bringOperators());
        
         
      //   FormFunctions.setTransactionsList(database.bringTransactions());

         database.closeConnection();
          if(isLogin!=null) {
        	 try { FormFunctions F=new FormFunctions();
        	 FormFunctions.setOperator(isLogin);
        	 
        	 F.sceneChange(FormFunctions.mainPage,936,645);
        	 
            
        	 }catch(Exception e) 
        	 {
        		 e.printStackTrace();
        	 }
        	 Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();  
             stage.close();
        	 
         } 
         else 
         {
        	 FormFunctions.alertMessageErr("Login Operation", "Failed" , "Check your operator name and password.");
         }
          
          
         }
         else 
        	 FormFunctions.alertMessageErr("Login Operation", "Failed", "Please provide valid username and password.");
          
    }
    
    @FXML
    void initialize() 
    {
    	Image backgroundImg = new Image(getClass().getResourceAsStream("/images/gymotomation.png"));
    	backgroundImgview.setImage(backgroundImg);
    	System.out.println("deneme");
    	
    	
    	
    	
    }
    
    
    
    
    
    

	

}
