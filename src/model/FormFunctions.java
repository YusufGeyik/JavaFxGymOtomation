package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

import controller.mainPage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
public class FormFunctions {

	public static String mainPage="/view/MainPage.fxml";
	public static String notifications="/view/notifications.fxml";
	public static String newMember="/view/newMember.fxml";
    public static String updatedeleteMember="/view/updatedeleteMember.fxml";    //main hariç sil
    public static String updatedeletePackages="/view/updatedeletePackages.fxml";
    public static String payment="/view/payment.fxml";
    public static String createPackage="/view/createPackage.fxml";
    public static String bringLogs="/view/bringLogs.fxml";
    public static String registerItem="/view/registerItem.fxml";
    public static String snackBar="/view/snackBar.fxml";
    
	private static ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
	private static ObservableList<String> periods = FXCollections.observableArrayList("1 month","3 months","6 months","12 months");
	private static ObservableList<membership> packages =FXCollections.observableArrayList();
	private static ObservableList<member> members=FXCollections.observableArrayList();
	private static ObservableList<items> items=FXCollections.observableArrayList();
	private static ObservableList<logs> logs=FXCollections.observableArrayList();
	private static Operator operator;
	
	


	public static Operator getOperator() {
		return operator;
	}

	public static void setOperator(Operator operator) {
		FormFunctions.operator = operator;
	}

	public static ObservableList<logs> getLogs() {
		return logs;
	}

	public static void setLogs(ObservableList<logs> logs) {
		FormFunctions.logs = logs;
	}

	public static ObservableList<items> getItems() {
		return items;
	}

	public static void setItems(ObservableList<items> items) {
		FormFunctions.items = items;
	}

	public static ObservableList<String> getGenders() {
		return genders;
	}

	public static void setGenders(ObservableList<String> genders) {
		FormFunctions.genders = genders;
	}

	public static ObservableList<String> getPeriods() {
		return periods;
	}

	public static void setPeriods(ObservableList<String> periods) {
		FormFunctions.periods = periods;
	}



	
	public static ObservableList<member> getMembers() {
		if(members==null)
			return FXCollections.observableArrayList();
		else 
		return members;
	}

	public static membership bringPackageObj(String PackageName) 
	{
		for(membership membership:FormFunctions.getPackages()) 
		{
			if(membership.getMembershipName().equals(PackageName)) 
			{
				return membership;
			}
		}
		return null;
	}


	public static void setMembers(ObservableList<member> members) {
		FormFunctions.members = members;
	}




	public static ObservableList<model.membership> getPackages() {
		return packages;
	}
	



	public static void setPackages(ObservableList<model.membership> packages) {
		FormFunctions.packages = packages;
	}




	public FormFunctions()
     {
    	 
     }
	
	public static ObservableList<String> bringPackageNames(ObservableList<model.membership> packages)
	{
		
        ObservableList<String> packageNames=packages.stream().map(model.membership::getMembershipName).collect(Collectors.toCollection(FXCollections::observableArrayList));
		
		return packageNames;
		
	}
	
	public static double getPackagePrice(ObservableList<membership> packages,String packageName) 
	{
		
		for(membership membershipPackage : packages) 
		{
			if(membershipPackage.getMembershipName().equals(packageName)) 
			{
				
				return membershipPackage.getMembershipPrice();
		     		
			}
			
			
		}
		
		
		return -1;
		
	}
	
	
  public static String parseAndCheckNumber(String number) 
  {
	  
		String phoneNumberFormat="\\d{11}";
		
		if(number.matches(phoneNumberFormat)) 
		{
			
			return number;
			
				
				
		}else 
		{

    		return "";
		}
	  
  }

	
	public static void createLog(String logType,String logDetails) 
	{
		database db=new database();
		logs log=new logs(logType,FormFunctions.getOperator().getOperatorName(),LocalDate.now(),logDetails );
		db.registerLog(log);
		FormFunctions.setLogs(db.bringLogs());
		db.closeConnection();
		
		
	}
	

	public static String alertMessageConf (String title, String context, String sidecontext) {
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(context);
	    alert.setContentText(sidecontext);
	    String response="";

	    ButtonType yesButton = new ButtonType("Yes");
	    ButtonType noButton = new ButtonType("No");

	    alert.getButtonTypes().setAll(yesButton, noButton);

	    Optional<ButtonType> result = alert.showAndWait();

	    if (result.isPresent() && result.get() == yesButton) {
	        return "YES";
	    } else {
	        return "NO";
	    }

	    
	}
	
	
	

	public static void alertMessageInf (String title, String context, String sidecontext) {
	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle(title);
	    alert.setHeaderText(context);
	    alert.setContentText(sidecontext);
	      

	    alert.showAndWait();
	    
	}
	
	public static void alertMessageErr (String title, String context, String sidecontext) {
	    Alert alert = new Alert(Alert.AlertType.ERROR);
	    alert.setTitle(title);
	    alert.setHeaderText(context);
	    alert.setContentText(sidecontext);
	      

	    alert.showAndWait();
	    
	}
	
	public void sceneChange (String fxmlPath,String extra) throws IOException 
	{
		
		
		   
		    Parent root;
	        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
	      
            root = loader.load();
            if(fxmlPath.equals(mainPage)) 
	        {
	        	   mainPage main = loader.getController();
	        	    main.setOperatorName(extra);
	        	
	        
	        	
	        }
            Scene sc= new Scene(root,936,645);
            Stage stage=new Stage();
            stage.setScene(sc);
            stage.show();
            
            
          
	}
	
	
	public static ObservableList<member> findMembersByName(String textForSearch)
	{
	// sırayla
    	try {
    	 	
    	if(textForSearch.isEmpty()) //button clicked without any input
    	{
    	
    	 	return FormFunctions.getMembers();
    		
    
    		
    	}
    	else //there is an input
    	{ 
    	
    		ObservableList<member> foundMembers=FXCollections.observableArrayList();
    	for(member m : FormFunctions.getMembers())
    	{
    		
    		if(m.getMemberName().toLowerCase().contains(textForSearch.toLowerCase())) 
    		{
    			foundMembers.add(m);
    			
    		}
    	}
    	
    	
    	return foundMembers;
    	
  
    	}
    	
    	}catch(Exception e) 
    	{
    		
    		e.printStackTrace();
    		
    	}
    	ObservableList<member> foundMembers=FXCollections.observableArrayList();
		return foundMembers;
    	}
	
	
	
	public static ObservableList<items> findItemsByName(String textForSearch)
	{
		
		

    	try {
    	 	
    	if(textForSearch.isEmpty()) //button clicked without any input
    	{
    	
    	 	return FormFunctions.getItems();
    		
    
    		
    	}
    	else //there is an input
    	{ 
    	
    		ObservableList<items> foundItems=FXCollections.observableArrayList();
    	for(items i: FormFunctions.getItems())
    	{
    		
    		if(i.getItemName().toLowerCase().contains(textForSearch.toLowerCase())) 
    		{
    			foundItems.add(i);
    			
    		}
    	}
    	
    	
    	return foundItems;
    	
  
    	}
    	
    	}catch(Exception e) 
    	{
    		
    		e.printStackTrace();
    		
    	}
    	ObservableList<items> foundItems=FXCollections.observableArrayList();
		return foundItems;
		
		
		
		
	}
	
	public  void changeAnchor(String fxmlPath,AnchorPane pane1,AnchorPane pane2) {

	    	try {
	    		mainPage main=new mainPage();
	    		
	    		pane1=(AnchorPane) FXMLLoader.load(getClass().getResource(fxmlPath));
	    		pane2.getChildren().setAll(pane1);
	    		
	    		
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	
	
	public static void addDataToTableView(TableView<member> memberView, ObservableList<member> members,
			
			
			
			TableColumn<member, Integer> id, TableColumn<member, String> name,
			TableColumn<member, String> packageName, TableColumn<member, LocalDate> startDate,
			TableColumn<member, LocalDate> endDate,TableColumn<member, String> gender,TableColumn<member,String>phoneNumber,
			TableColumn<member,LocalDate>birthDate,TableColumn<member,Double> balance,TableColumn<member,String>period) {
		
		
		try {
		
		    
            id.setCellValueFactory(new PropertyValueFactory<>("memberID"));
            name.setCellValueFactory(new PropertyValueFactory<>("memberName"));
            packageName.setCellValueFactory(new PropertyValueFactory<>("memberMembership"));
            startDate.setCellValueFactory(new PropertyValueFactory<>("membershipStart"));
            endDate.setCellValueFactory(new PropertyValueFactory<>("membershipEnd"));
            gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
            phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            birthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
            balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
            period.setCellValueFactory(new PropertyValueFactory<>("membershipPeriod"));
            
            
	    
	        memberView.setItems(members);

	
		}catch(Exception e) 
		{
			e.getMessage();
		}
	        
	        
	 
	}
	
	
public static void addLogsToTable(TableView<logs> logsView, ObservableList<logs> logs,
			
			
			
			TableColumn<logs, Integer> logID, TableColumn<logs, String> logType,
			TableColumn<logs, String> logOperator, TableColumn<logs, LocalDate> logDate,
			TableColumn<logs, String> logDetails) {
		
		
		try {
		
		    
            logID.setCellValueFactory(new PropertyValueFactory<>("logID"));
            logType.setCellValueFactory(new PropertyValueFactory<>("logType"));
            logOperator.setCellValueFactory(new PropertyValueFactory<>("logOperator"));
            logDate.setCellValueFactory(new PropertyValueFactory<>("logDate"));
            logDate.setSortType(TableColumn.SortType.DESCENDING);
            logDetails.setCellValueFactory(new PropertyValueFactory<>("logDetails"));
            
            
            
	    
	        logsView.setItems(logs);

	
		}catch(Exception e) 
		{
			e.getMessage();
		}
	        
	        
	 
	}
	
	
	

	public static void memberTableMembershipExpiring(TableView<member> memberView, ObservableList<member> members,
			
			
			
			TableColumn<member, String> name,
			TableColumn<member, String> packageName,TableColumn<member,String>phoneNumber,TableColumn<member, LocalDate> expiringDate) {
		
		
		try {
		
		    
           
            name.setCellValueFactory(new PropertyValueFactory<>("memberName"));
            packageName.setCellValueFactory(new PropertyValueFactory<>("memberMembership"));
            phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            expiringDate.setCellValueFactory(new PropertyValueFactory<>("membershipEnd"));
           
            
	    
	        memberView.setItems(members);

	
		}catch(Exception e) 
		{
			e.getMessage();
		}
	        
	        
	 
	}
	
	
	
	public static void memberTableSnackBar(TableView<member> memberView, ObservableList<member> members,
			
			
			
			TableColumn<member, String> name,
			TableColumn<member, String> packageName,TableColumn<member,String>phoneNumber,TableColumn<member,Double> balance) {
		
		
		try {
		
		    
           
            name.setCellValueFactory(new PropertyValueFactory<>("memberName"));
            packageName.setCellValueFactory(new PropertyValueFactory<>("memberMembership"));
            phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
           
            
	    
	        memberView.setItems(members);

	
		}catch(Exception e) 
		{
			e.getMessage();
		}
	        
	        
	 
	}
	
public static void addToCartTable(TableView<cartItems> cartView, ObservableList<cartItems> cart,
			
			
			
			TableColumn<cartItems, String> itemNametc,
			TableColumn<cartItems, Double> itemPricetc,TableColumn<cartItems,Integer>quantitytc) {
		
		
		try {
		
		    
           
            itemNametc.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            itemPricetc.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
            quantitytc.setCellValueFactory(new PropertyValueFactory<>("quantity"));
           
           
            
	    
	        cartView.setItems(cart);

	
		}catch(Exception e) 
		{
			e.getMessage();
		}
	        
	        
	 
	}
	
	
	
	public static boolean isString(String input) 
	{
	
		        return input.matches("[a-zA-Z]+");
		   
	}
	
	
	public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	
	public static boolean isDouble(String input) {
        try {
        	if(input.contains(",")&& input.contains("."))
        	{
        		
        	}
        	else if(input.contains(",")) 
        	{
        		System.out.println("değerde virgül var");
        		input=input.replaceAll(".", ",");
        	}
        	
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	

public static void addDataToTableView(TableView<items> itemView, ObservableList<items> items,
			
			
			
			TableColumn<items, String> itemName, TableColumn<items, Integer> stockCount,
			TableColumn<items, Double> sellingPrice, TableColumn<items, Double> unitCost) {
		
		
		try {
		
		    
            itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            stockCount.setCellValueFactory(new PropertyValueFactory<>("stockCount"));
            sellingPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
            unitCost.setCellValueFactory(new PropertyValueFactory<>("unitCost"));
          
            
            
	    
	        itemView.setItems(items);

	
		}catch(Exception e) 
		{
			e.getMessage();
		}
	        
	        
	 
	}
	
	
	
	
	

	public static void addDataToTableView(TableView<membership> cPackageTable, ObservableList<membership> packages,
			TableColumn<membership, String> packageName, TableColumn<membership, Integer> packagePrice,
			TableColumn<membership, String> accesibleZones, TableColumn<membership, String> periods,
			TableColumn<membership, Double> discountRate) {
		
		
		
		
		    
            packageName.setCellValueFactory(new PropertyValueFactory<>("membershipName"));
            packagePrice.setCellValueFactory(new PropertyValueFactory<>("membershipPrice"));
            accesibleZones.setCellValueFactory(new PropertyValueFactory<>("accesibleZones"));
            periods.setCellValueFactory(new PropertyValueFactory<>("periods"));
            discountRate.setCellValueFactory(new PropertyValueFactory<>("discountRate"));
		  
            
            
	    // Verileri TableView'a ekleyin
	    cPackageTable.setItems(packages);

	 
	}


	


	
	
	
	
}
