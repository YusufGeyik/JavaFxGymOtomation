package model;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.stage.StageStyle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
public class FormFunctions {

	public static String mainPage="/view/MainPage.fxml";
	public static String loginPage="/view/loginPage.fxml";
	public static String notifications="/view/notifications.fxml";
	public static String newMember="/view/newMember.fxml";
    public static String updatedeleteMember="/view/updatedeleteMember.fxml";    //main hariç sil
    public static String updatedeletePackages="/view/updatedeletePackages.fxml";
    public static String payment="/view/payment.fxml";
    public static String createPackage="/view/createPackage.fxml";
    public static String bringLogs="/view/bringLogs.fxml";
    public static String registerItem="/view/registerItem.fxml";
    public static String snackBar="/view/snackBar.fxml";
    public static String updateInventory="/view/updateInventory.fxml";
    public static String registerOperator="/view/registerOperator.fxml";
    public static String updateDeleteOperator="/view/updateDeleteOperator.fxml";
    public static String revenues="/view/revenues.fxml";
	private static ObservableList<String> genders = FXCollections.observableArrayList("Male", "Female");
	private static ObservableList<String> periods = FXCollections.observableArrayList("1 month","3 months","6 months","12 months");
	private static ObservableList<membership> packages =FXCollections.observableArrayList();
	private static ObservableList<member> members=FXCollections.observableArrayList();
	private static ObservableList<items> items=FXCollections.observableArrayList();
	private static ObservableList<Operator> operators=FXCollections.observableArrayList();
	
	
	

	public static ObservableList<Operator> getOperators() {
		return operators;
	}

	public static void setOperators(ObservableList<Operator> operators) {
		FormFunctions.operators = operators;
	}




	private static Operator operator;

	
   public static ObservableList<String> periodFill(membership membership) 
   {
	  ObservableList<String> periods=FXCollections.observableArrayList();
	  Pattern ptrn = Pattern.compile("\\d+\\s+Month");
      Matcher match = ptrn.matcher(membership.getPeriods());

      
      
      // Eşleşen her parçayı ObservableList'e ekleyin
      while (match.find()) {
          periods.add(match.group());
      }
	  
	  System.out.println(periods.get(0));
	  
	   return  periods;
	   
   }

   public  String hello() 
   {
	   
	   
	   return "Yusuf";
   }


	public static Operator getOperator() {
		return operator;
	}

	public static void setOperator(Operator operator) {
		FormFunctions.operator = operator;
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
		
		db.closeConnection();
		
		
	}
	
	public static void deletePackageCalculations(double oldDiscountRate,double newDiscountRate, String oldPackageName,String newPackageName,membership deletedMembership) throws ParseException 
	{   model.database db=new model.database();
	//	membership deletedMembership=FormFunctions.bringPackageObj(oldPackageName);
	    membership transitionedMembership=FormFunctions.bringPackageObj(newPackageName);
	    String logDetails=deletedMembership.getMembershipName()+"Deleted,payments made,members transitioned to"+transitionedMembership.getMembershipName()+"\nTransitioned Members\n";
		double  newPrice=FormFunctions.getPackagePrice(FormFunctions.getPackages(),newPackageName);
		for(member member: FormFunctions.getMembers()) 
		{
			logDetails+="\n"+member.getMemberID()+" "+member.getMemberName();
			if(member.getMemberMembership().equals(oldPackageName))
			{   member oldMember=member;
				
				
				String period=member.getMembershipPeriod().replaceAll("\\D", "");
				int months=Integer.parseInt(period);
				LocalDate membershipEnd=member.getMembershipEnd();
				LocalDate currentDate=LocalDate.now();
				 
				
		         Period restOfTime=Period.between(currentDate, membershipEnd);
		         int paidMonthDiff=restOfTime.getMonths();
				 int newPayMonthDiff=(int) ChronoUnit.MONTHS.between(currentDate, membershipEnd);
				 
				 if (membershipEnd.getDayOfMonth() < currentDate.getDayOfMonth() && membershipEnd.getDayOfMonth() > 1) {
			            newPayMonthDiff++;
			        }
				
				
				if(months>1 && paidMonthDiff>=0) {
					
					
				double paid = member.getPaidBasePrice()*paidMonthDiff;//Calculation of the rest days in oldmembership
				double newServicePrice=((newPrice)*newDiscountRate*newPayMonthDiff);
				member.setBalance((member.getBalance()+(paid-newServicePrice))); // - if the member has to pay + if the company has to pay;
				member.setPaidBasePrice(((newPrice*newDiscountRate)));
				member.setMemberMembership(newPackageName);
				
				db.updateMember(member,oldMember,"Package Transition Due To Deleted Package");
				
				}
				else if(months==1 && paidMonthDiff>=0)  //without discount rate due to short membershipPeriod
				{
					double paid = member.getPaidBasePrice()*paidMonthDiff;
					double newServicePrice=((newPrice)*newPayMonthDiff);
					member.setBalance((member.getBalance()+(paid-newServicePrice))); // - if the member has to pay + if the company has to pay;
					member.setMemberMembership(newPackageName);
					member.setPaidBasePrice((newPrice));
					db.updateMember(member,oldMember,"Package Transition Due To Deleted Package");
				}
				else if(paidMonthDiff<0) 
				{
					member.setPaidBasePrice(0);
					member.setMemberMembership(newPackageName);
					db.updateMember(member,oldMember,"Package Transition Due To Deleted Package");
					System.out.println("deneme");
					
				}
				
				
				
				
				
			}
			
			FormFunctions.createLog("Deleted Package", logDetails);
		
		}
		
	
		
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
	
	public void sceneChange (String fxmlPath,int width,int height) throws IOException 
	{
		
		
		   
		    Parent root;
	        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
	      
            root = loader.load();
            if(fxmlPath.equals(mainPage)) 
	        {
	        	   mainPage main = loader.getController();
	        	    main.setOperatorName();
	        	
	        
	        	
	        }
            Scene sc= new Scene(root,width,height);
            Stage stage=new Stage();
            stage.setScene(sc);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle("GYM");
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
	
	   
    public static Double calculateRevenue(ObservableList<transactions>transactionsList) 
    {
    	double totalRevenue=0;
    	
    	if(!transactionsList.isEmpty()) 
    	{
    		
    		for(transactions transaction:transactionsList) 
    		{
    			
    	       totalRevenue=totalRevenue+transaction.getAmount();
    			
    			
    		}
    		
    		return totalRevenue;
    		
    		
    		
    	}
    	else
       return totalRevenue;
    }
    


	public static ObservableList<member> dynamicSearchMembers(String textForSearch)
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
	    		
	    		
	    		pane1=(AnchorPane) FXMLLoader.load(getClass().getResource(fxmlPath));
	    		pane2.getChildren().setAll(pane1);
	    		
	    		
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	
	public  void changeAnchor(String fxmlPath,AnchorPane pane1) {

    	try {
    		
    		
    		AnchorPane pane=(AnchorPane) FXMLLoader.load(getClass().getResource(fxmlPath));
    		pane1.getChildren().setAll(pane);
    		
    		
    		
    		
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
	

public static String hashIt(String password,String name) {

	try 
{
	
	MessageDigest md=MessageDigest.getInstance("MD5");
	byte[] byteArray=md.digest((password+name.trim()).getBytes());
	BigInteger number=new BigInteger(1,byteArray);
	String hashed=number.toString(16);
	while(hashed.length()<32) 
	{
		hashed="0"+ hashed;
		
	}
	
	return hashed;
	
	

}catch(NoSuchAlgorithmException e) 
{
	
 throw new RuntimeException(e);

}
	
	
	
	
	
}



	
	
	
	public static boolean isString(String input) {
	    return input == null || input.matches("^[a-zA-ZçÇğĞıİöÖşŞüÜ ]+$");
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
	
	



public static void addDataToTableView(TableView<transactions> transactionView, ObservableList<transactions> transactionsList,
		
		
		
		TableColumn<transactions, String> transactionTypetc, TableColumn<transactions, Double> transactionAmounttc,
		TableColumn<transactions, LocalDate> transactionDatetc) {
	
	
	try {
	
	    
		transactionTypetc.setCellValueFactory(new PropertyValueFactory<>("transactionType"));
        transactionAmounttc.setCellValueFactory(new PropertyValueFactory<>("amount"));
        transactionDatetc.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
       
      
        
        
    
        transactionView.setItems(transactionsList);


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


	

	public static void addDataToTableView(TableView<Operator> operatorView,ObservableList<Operator> operators,
			TableColumn<Operator,String> nametc, TableColumn<Operator,Boolean> registerMembertc,TableColumn<Operator,Boolean> updateMembertc,
			TableColumn<Operator,Boolean> createPackagetc,TableColumn<Operator,Boolean> updatePackagetc,TableColumn<Operator,Boolean> selltc,
			TableColumn<Operator,Boolean> registerInvtc,TableColumn<Operator,Boolean> updateInvtc,TableColumn<Operator,Boolean> accessToLogstc,TableColumn<Operator,Boolean> takesPaymenttc,TableColumn<Operator,String> operatorPasswordtc   ) 
	{
		nametc.setCellValueFactory(new PropertyValueFactory<>("operatorName"));
		operatorPasswordtc.setCellValueFactory(new PropertyValueFactory<>("operatorPassword"));
		registerMembertc.setCellValueFactory(new PropertyValueFactory<>("registerMember"));
		updateMembertc.setCellValueFactory(new PropertyValueFactory<>("updateMember"));
		createPackagetc.setCellValueFactory(new PropertyValueFactory<>("createPackage"));
		updatePackagetc.setCellValueFactory(new PropertyValueFactory<>("updatePackage"));
		selltc.setCellValueFactory(new PropertyValueFactory<>("sell"));
		registerInvtc.setCellValueFactory(new PropertyValueFactory<>("registerInv"));
		updateInvtc.setCellValueFactory(new PropertyValueFactory<>("updateInv"));
		accessToLogstc.setCellValueFactory(new PropertyValueFactory<>("accessToLogs"));
		takesPaymenttc.setCellValueFactory(new PropertyValueFactory<>("takesPayment"));
		
		operatorView.setItems(operators);
		
		
		
	}

	
	
	
	
}
