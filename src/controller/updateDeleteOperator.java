package controller;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.FormFunctions;
import model.Operator;
import model.Roles;
import model.member;
import model.membership;

public class updateDeleteOperator {
   @FXML
	private TableColumn<Operator, String> operatorPasswordtc;
    @FXML
    private ResourceBundle resources;
    @FXML
    private Button delete;
    @FXML
    private URL location;

    @FXML
    private CheckBox accessLogscheck;

    @FXML
    private CheckBox createPackagecheck;

    @FXML
    private TableColumn<Operator, Boolean> createPackagetc;

    @FXML
    private TextField findOperatortxt;

    @FXML
    private Button findbt;

    @FXML
    private TableColumn<Operator, Boolean> logstc;

    @FXML
    private TableColumn<Operator, String> nametc;

    @FXML
    private TextField operatorNametxt;

    @FXML
    private PasswordField operatorPasswordtxt;

    @FXML
    private TableView<Operator> operatorView;

    @FXML
    private CheckBox registerInvcheck;

    @FXML
    private TableColumn<Operator, Boolean> registerInvtc;

    @FXML
    private CheckBox registerMembercheck;

    @FXML
    private TableColumn<Operator, Boolean> registerMembertc;

    @FXML
    private CheckBox sellcheck;

    @FXML
    private TableColumn<Operator, Boolean> selltc;

    @FXML
    private CheckBox takesPaymentscheck;

    @FXML
    private TableColumn<Operator, Boolean> takesPaymentstc;

    @FXML
    private TableColumn<Operator, Boolean> upMembertc;

    @FXML
    private TableColumn<Operator,Boolean> upPackagetc;

    @FXML
    private CheckBox updateInvcheck;

    @FXML
    private TableColumn<Operator, Boolean> updateInvtc;

    @FXML
    private CheckBox updateMembercheck;

    @FXML
    private CheckBox updatePackagecheck;

    @FXML
    private Button updatebt;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    void deleteClicked(ActionEvent event) {
   try {
    	Operator operator=operatorView.getSelectionModel().getSelectedItem();
    	if(!operator.getOperatorName().equals("admin")) 
    	{
    		String response=FormFunctions.alertMessageConf("Deleting Operator", "Action confirmation", "Are you sure you want to delete operator named: " +operator.getOperatorName());
    		if(response.equals("YES")) 
    		{
    		 
    			
    			TextInputDialog dialog= new TextInputDialog();
    			dialog.setTitle("Deleting an Operator");
    			dialog.setHeaderText("Operation Authentication");
    			dialog.setContentText("Enter admin password in order to complete action");
    			
    			Optional<String> dialogResponse=dialog.showAndWait();
    			model.database db=new model.database();
    			String hashedPass=FormFunctions.hashIt(dialogResponse.get(), "admin");
    			
    			Operator admin=db.Login("admin", hashedPass);
    			if(admin!=null) 
    			{
    				
    				
    				List<Object> psValues=Arrays.asList(operator.getOperatorName());
    			String sql="DELETE FROM operators WHERE operatorName = ?";
    				db.operatorDataOperations(operator, sql, psValues);
    				FormFunctions.setOperators(db.bringOperators());
    				FormFunctions.addDataToTableView(operatorView, FormFunctions.getOperators(), nametc, registerMembertc, upMembertc, createPackagetc, upPackagetc, selltc, registerInvtc, updateInvtc, logstc, takesPaymentstc, operatorPasswordtc);
    				//table güncelle
    			 
    				
    				db.closeConnection();
    			}
    			else //admin password is incorrect
    			{
    				
    				
    				FormFunctions.alertMessageErr("Deleting an Operator", "Failed", "admin password is incorrect");
    				
    				
    			}
    			
    			
    			
    		}else // delete operation cancelled
    	
    		{
    			
    			
    			
    		}
    		
    		
    		
    		
    	}
    	else //trying to delete admin 
    	{
    		
    		FormFunctions.alertMessageErr("Deleting an Oprator", "Failed", "admin cannot be deleted");
    		
    		
    	}
   }catch(Exception e) 
   {
	   
	   
	   e.printStackTrace();
   }
    	
    }

    @FXML
    void findOperator(KeyEvent event) {
    	try {
    	 	
        	if(findOperatortxt.getText().isEmpty()) //button clicked without any input
        	{
        	
        		FormFunctions.addDataToTableView(operatorView, FormFunctions.getOperators(), nametc, registerMembertc, upMembertc, createPackagetc, upPackagetc, selltc, registerInvtc, updateInvtc, logstc, takesPaymentstc, operatorPasswordtc);
        		
        
        		
        	}
        	else //there is an input
        	{ 
        	
        		ObservableList<Operator> foundOperator=FXCollections.observableArrayList();
        	for(Operator operator : FormFunctions.getOperators())
        	{
        		
        
        		if(operator.getOperatorName().toLowerCase().contains(findOperatortxt.getText()))
        		{
        			foundOperator.add(operator);
        			
        		}
        	}
        	
        	
        	FormFunctions.addDataToTableView(operatorView, foundOperator, nametc, registerMembertc, upMembertc, createPackagetc, upPackagetc, selltc, registerInvtc, updateInvtc, logstc, takesPaymentstc, operatorPasswordtc);
        	
      
        	}
        	
        	}catch(Exception e) 
        	{
        		
        		e.printStackTrace();
        		
        	}
    }

    @FXML
    void updateMember(ActionEvent event) {

    	try {
    	model.database db=new model.database();
    	TextInputDialog auth=new TextInputDialog("admin password");
    	auth.setTitle("Operator Data Update");
    	auth.setHeaderText("Authentication");
    	auth.setContentText("Enter current admin password to complete update");
    	Optional<String> response=auth.showAndWait();
    	if(response.isPresent()) 
    	{
    	String hashedPass=FormFunctions.hashIt(response.get(), "admin");
    	model.database database=new model.database();
    	Operator admin=database.Login("admin", hashedPass);
    	
    	database.closeConnection();
    	
    		if(admin!=null) //correct admin pass
    		{
    			Operator oldVersion=operatorView.getSelectionModel().getSelectedItem();
    			Operator newVersion=new Operator(operatorNametxt.getText().trim(), operatorPasswordtxt.getText(), registerMembercheck.isSelected(),
    					registerMembercheck.isSelected(), createPackagecheck.isSelected(), updatePackagecheck.isSelected(), sellcheck.isSelected(), registerInvcheck.isSelected(),
    					updateInvcheck.isSelected(), accessLogscheck.isSelected(), takesPaymentscheck.isSelected(),oldVersion.getRole());
    			if(oldVersion.getRole().equals(Roles.ADMIN)) //trying update admin
    			
    			{
    				if(!oldVersion.getOperatorPassword().equals(newVersion.getOperatorPassword())) 
    				// password changed
    				{
    				
    				String newHashedPass=FormFunctions.hashIt(operatorPasswordtxt.getText(), "admin");
    				oldVersion.setOperatorPassword(newHashedPass);

					String sql="UPDATE operators SET  operatorPassword = ? WHERE operatorName = ?";
					 List<Object> psValues=Arrays.asList(oldVersion.getOperatorPassword(),oldVersion.getOperatorName());
				      
					 db.operatorDataOperations(oldVersion, sql,psValues);
    				//update operators and admin FormFunctions
    				FormFunctions.alertMessageInf("Updating Operator Data", "Restriction", "admin's name and permissions cannot be altered. Password change completed.");
    				}
    				else 
    			 //password not changed
    				{
    					FormFunctions.alertMessageErr("Updating Operator Data", "Restriction", "admin's name and permissions cannot be altered. Only Password updates are accepted.");
    					
    					
    				}
    				
    				
    				
    			}
    			else //just an regular operator. 
    			{
    				if(FormFunctions.isString(operatorNametxt.getText().trim())) // valid string value for name
    				{
    					
    					if(!oldVersion.getOperatorPassword().equals(newVersion.getOperatorPassword())) {
    						newVersion.setOperatorPassword(FormFunctions.hashIt(operatorPasswordtxt.getText(), newVersion.getOperatorName()));
    					String sql="UPDATE operators SET operatorName = ?, operatorPassword = ?, registerMember = ? , updateMember= ? ,"
    							+ " createPackage = ? , updatePackage = ? , sell = ? , registerInv = ? , updateInv = ? , accessToLogs = ? ,"
    							+ " takesPayment = ? WHERE operatorName = ?";
    					 List<Object> psValues=Arrays.asList(newVersion.getOperatorName(),
    				        		newVersion.getOperatorPassword(),newVersion.isRegisterMember(),newVersion.isUpdateMember(),newVersion.isCreatePackage(),newVersion.isUpdatePackage(),
    				        		newVersion.isSell(),newVersion.isRegisterInv(),newVersion.isUpdateInv(),newVersion.isAccessToLogs(),newVersion.isTakesPayment(),oldVersion.getOperatorName());
    				      
    					 db.operatorDataOperations(newVersion, sql,psValues);
    					 FormFunctions.setOperators(db.bringOperators());
    					} //password is changed
    					
    					else //pasword is not changed 
    					{
    						String sql="UPDATE operators SET operatorName = ?, operatorPassword = ?, registerMember = ? , updateMember= ? ,"
        							+ " createPackage = ? , updatePackage = ? , sell = ? , registerInv = ? , updateInv = ? , accessToLogs = ? ,"
        							+ " takesPayment = ? WHERE operatorName = ?";
        					 List<Object> psValues=Arrays.asList(newVersion.getOperatorName(),
        				        		newVersion.getOperatorPassword(),newVersion.isRegisterMember(),newVersion.isUpdateMember(),newVersion.isCreatePackage(),newVersion.isUpdatePackage(),
        				        		newVersion.isSell(),newVersion.isRegisterInv(),newVersion.isUpdateInv(),newVersion.isAccessToLogs(),newVersion.isTakesPayment(),oldVersion.getOperatorName());
        				      
        					 db.operatorDataOperations(newVersion, sql,psValues);
        					 FormFunctions.setOperators(db.bringOperators());
    						
    						
    						
    					}
    					
    					
    				}else 
    				{
    					
    					//hata var
    				}
    					
    				
    				
    				
    			}
    			
    			
    			
    			
    			
    			
    			
    			
    			
    		}
    		else //wrong admin pass
    		{
    			
    			FormFunctions.alertMessageErr("Updating Operator Data", "Failed", "Provided admin password is not correct");
    			
    			
    		}
    		
    	}
    	//dialog penceresi olarak adminpass sor ilk olarak sonra ilk ifle kontrol et
    	// eski ve yeniyi tut ikisinin arasında passworleri kıyasla eğer farklıysa yenisinin hashini alıp kaydet.
    	
    	FormFunctions.setOperators(db.bringOperators());
    	db.closeConnection();
    	FormFunctions.addDataToTableView(operatorView, FormFunctions.getOperators(), nametc, registerMembertc, upMembertc, createPackagetc, upPackagetc, selltc, registerInvtc, updateInvtc, logstc, takesPaymentstc, operatorPasswordtc);
    	for(Node node:anchorPane.getChildren()) 
        {
        	if(node instanceof CheckBox) 
        	{
        		CheckBox checkBox = (CheckBox) node;
        		checkBox.setSelected(false);
        		
        		
        	}
        	
        	
        }
    	operatorNametxt.setText("");
    	operatorPasswordtxt.setText("");
    	}catch(Exception e) 
    	{
    		e.printStackTrace();
    	}
    }
    
    
    void printSelectedOperatorData(Operator operator) 
    {
       operatorNametxt.setText(operator.getOperatorName());
       operatorPasswordtxt.setText(operator.getOperatorPassword());
       registerMembercheck.setSelected(operator.isRegisterMember());
       updateMembercheck.setSelected(operator.isUpdateMember());
       createPackagecheck.setSelected(operator.isCreatePackage());
       updatePackagecheck.setSelected(operator.isUpdatePackage());
       sellcheck.setSelected(operator.isSell());
       registerInvcheck.setSelected(operator.isRegisterInv());
       updateInvcheck.setSelected(operator.isUpdateInv());
       accessLogscheck.setSelected(operator.isAccessToLogs());
       takesPaymentscheck.setSelected(operator.isTakesPayment());
       
    	
    	
    }

    @FXML
    void initialize() {
    	//null ayarla
    	
    	for(int i=0;i>FormFunctions.getOperators().size();i++) 
    	{
    		System.out.println(FormFunctions.getOperators().get(i));
    		
    	}
       FormFunctions.addDataToTableView(operatorView, FormFunctions.getOperators(), nametc, registerMembertc, upMembertc, createPackagetc, upPackagetc, selltc, registerInvtc, updateInvtc, logstc, takesPaymentstc, operatorPasswordtc);
    	 operatorView.setOnMouseClicked(event -> {
     		
     	    if (event.getClickCount() == 1) { 
     	        int selectedIndex = operatorView.getSelectionModel().getSelectedIndex();
     	        if (selectedIndex != -1) { 
     	        	Operator selectedOperator=operatorView.getSelectionModel().getSelectedItem();
     	        	printSelectedOperatorData(selectedOperator);
     	        	
     	        }
     	    }
     	});
            
    	
    }

}
