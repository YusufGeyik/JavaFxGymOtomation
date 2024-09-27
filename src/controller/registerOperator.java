package controller;


	
	
	

	import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
	import javafx.event.ActionEvent;
	import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
	import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.FormFunctions;
import model.Operator;
import model.Roles;
	public class registerOperator{

	    @FXML
	    private ResourceBundle resources;

	    @FXML
	    private URL location;

	    @FXML
	    private CheckBox accessLogscheck;

	    @FXML
	    private PasswordField adminPasswordtxt;

	    @FXML
	    private CheckBox createPackagecheck;

	    @FXML
	    private TextField operatorNametxt;

	    @FXML
	    private PasswordField operatorPasswordtxt;

	    @FXML
	    private CheckBox registerInvcheck;

	    @FXML
	    private CheckBox registerMembercheck;

	    @FXML
	    private Button registerOperator;

	    @FXML
	    private Button backbt;

	    @FXML
	    private CheckBox sellcheck;

	    @FXML
	    private CheckBox takesPaymentscheck;

	    @FXML
	    private CheckBox updateInvcheck;

	    @FXML
	    private CheckBox updateMembercheck;

	    @FXML
	    private CheckBox updatePackagecheck;

	    @FXML
	    private AnchorPane anchorPane;
	    
	    @FXML
	    void backToLogin(ActionEvent event) {
	    	
	      FormFunctions F =new FormFunctions();
	      F.changeAnchor(F.loginPage, anchorPane);

	    }
	    @FXML
	    void registerOperatorClicked(ActionEvent event) {
	    String adminHashedpass=FormFunctions.hashIt(adminPasswordtxt.getText(), "admin");
	    model.database dbInstance = new model.database();
	    Operator admin=dbInstance.Login("admin", adminHashedpass);
	    dbInstance.closeConnection();
	    	
	    	
	    	
	    if(admin!=null) //adminpass control
	    {
	    	boolean registerMember=registerMembercheck.isSelected();
	    	boolean updateMember=updateMembercheck.isSelected();
	    	boolean createPackage=createPackagecheck.isSelected();
	    	boolean updatePackage=updatePackagecheck.isSelected();
	    	boolean sell=sellcheck.isSelected();
	    	boolean registerInv=registerInvcheck.isSelected();
	    	boolean updateInv=updateInvcheck.isSelected();
	    	boolean accessToLogs=accessLogscheck.isSelected();
	    	boolean takesPayments=takesPaymentscheck.isSelected();
	    	
	    	
	    if(FormFunctions.isString(operatorNametxt.getText())==true) 
	    {
	    
	    	String operatorName=operatorNametxt.getText().trim();
	    	String operatorPassword=operatorPasswordtxt.getText();
	    	String hashedPass=FormFunctions.hashIt(operatorPassword, operatorName);
	    	
	    	
	    		        
	        
	        model.Operator operator=new model.Operator(operatorName, hashedPass, registerMember, updateMember, createPackage, updatePackage, sell, registerInv, updateInv, accessToLogs, takesPayments,Roles.STAFF);
	        model.database db=new model.database();
	        String sql="INSERT INTO operators (operatorName, operatorPassword, registerMember , updateMember , createPackage , updatePackage , sell , registerInv , updateInv , accessToLogs , takesPayment) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
	        List<Object> psValues=Arrays.asList(operator.getOperatorName(),
	        		operator.getOperatorPassword(),operator.isRegisterMember(),operator.isUpdateMember(),operator.isCreatePackage(),operator.isUpdatePackage(),operator.isSell(),operator.isRegisterInv(),operator.isUpdateInv(),operator.isAccessToLogs(),operator.isTakesPayment());
	        db.operatorDataOperations(operator, sql,psValues);
	        db.closeConnection();
	        operatorNametxt.setText("");
            operatorPasswordtxt.setText("");
            adminPasswordtxt.setText("");
	        for(Node node:anchorPane.getChildren()) 
	        {
	        	if(node instanceof CheckBox) 
	        	{
	        		CheckBox checkBox = (CheckBox) node;
	        		checkBox.setSelected(false);
	        		
	        		
	        	}
	        	
	        	
	        }
	        
	        
	    }
	    else 
	    {
	    	FormFunctions.alertMessageErr("Registering New Operator", "Failed", "Please provide valid values");
	    	
	    }
	    	
	    	
	    }
	    else 
	    {
	    	FormFunctions.alertMessageErr("Registering New Operator", "Failed", "admin password is not correct");
	    }
	    
	    
	    
	    
	    }

	    @FXML
	    void initialize() {
	    
	    	
	    	
	    	

	    }

	}

	


