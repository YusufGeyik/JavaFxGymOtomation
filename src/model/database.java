package model;

import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class database {

	 final static String database="gymotomation";
	 final static String username = "root";
	 final static String password = "123456";
	 final static String port="3306";
	 final static String host="localhost";
	 final static String url = "jdbc:mysql://"+host+":"+port+"/"+ database;
     Connection connection;

	 
	 public database()
	 {
		 try  {
	    	 Connection connection = DriverManager.getConnection(url,username,password);
		     
		     this.connection=connection;
		   
		     
		 } catch (SQLException e) {
		     throw new IllegalStateException("Cannot connect the database!", e);
		     
		 }
		 
		 
	 }
	
     
     
     
     
	//sql connection adresi portu vb propertylerini yaz.
	//sql bağlantısı için bir fonksiyon yaz
	//sql işlemleri için sql command bağlantı vb alan bir fonksiyon.
	
	 public ObservableList<Operator> bringOperators()
	 {
		 ObservableList<Operator> operators=FXCollections.observableArrayList();
		 String sql="Select * FROM operators";
		 try {
			Statement statement=this.connection.createStatement();
			ResultSet result=statement.executeQuery(sql);
			while(result.next()) 
			{
				 
				  
				operators.add(new Operator(result.getString("operatorName"), result.getString("operatorPassword"),result.getBoolean("registerMember"), result.getBoolean("updateMember"), result.getBoolean("createPackage"), result.getBoolean("updatePackage"), result.getBoolean("sell"), result.getBoolean("registerInv"), result.getBoolean("updateInv"), result.getBoolean("accessToLogs"), result.getBoolean("takesPayment"), Roles.valueOf(result.getString("Role"))));
				
				
			}
			
			return operators;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 return operators;
		 
	 }
	 
	 
	 public void updateExpiredMemberships() 
	 {
		 
		 String sql="UPDATE members SET paidBasePrice = 0 WHERE membershipEnd < CURDATE();";
		 try {
		 Statement statement = connection.createStatement();
	            statement.executeUpdate(sql);
		 }catch(Exception e) {
		 e.printStackTrace();
		 }
		 
	 }
	 
	public  model.Operator Login(String name,String password) 
	{
	
		
		
		try 
		{
			String Query="Select * From operators Where operatorName = ? AND operatorPassword = ?";
			PreparedStatement statement=this.connection.prepareStatement(Query);
			statement.setString(1,name);
			statement.setString(2, password);
			ResultSet result=statement.executeQuery();
			
			if(result.next())
				return new model.Operator(name, password, result.getBoolean("registerMember"),result.getBoolean("updateMember"), result.getBoolean("createPackage"),result.getBoolean("updatePackage"), result.getBoolean("sell"), result.getBoolean("registerInv"), result.getBoolean("updateInv"), result.getBoolean("accessToLogs"), result.getBoolean("takesPayment"), Roles.valueOf(result.getString("Role")));
		
		}catch(SQLException e) 
		{
			e.printStackTrace();
		}
		
	   closeConnection();
		return null;
		
	}
	
	
	public void operatorDataOperations(model.Operator operator,String sql,List<Object> psValues)  
	{
		try {
		PreparedStatement ps=this.connection.prepareStatement(sql);
		
		
		for(int i=0; i<psValues.size();i++) 
		{
			
			
		 if(psValues.get(i) instanceof String) 
		 {
			 
			 ps.setString(i+1,(String)psValues.get(i));
			 
			 
		 }
		 else if(psValues.get(i) instanceof Boolean) 
		 {
			 
			 ps.setBoolean(i+1, (Boolean)psValues.get(i));
			 
			 
		 }
			
			
			
			
		}//psvalues for
		
	  
	    ps.executeUpdate();
	    
	    
	    FormFunctions.alertMessageInf("Operator Data Operation", "Successfull", ""+ operator.getOperatorName()+ " registered");
		}catch(SQLIntegrityConstraintViolationException e) 
		{
			e.printStackTrace();
			FormFunctions.alertMessageErr("Registerin New Operator", "Failed", "There is a record with this name");
			
			
		}
		
		
		
		
		catch(Exception e) 
		{
			
			e.printStackTrace();
			
		}
		
		
		
	}
	
	


	public ObservableList<transactions> bringTransactions(String sql,LocalDate startDate,LocalDate endDate)  
	{
		ObservableList<transactions> selectedTransactions=FXCollections.observableArrayList();
	 
    	
    	try {
    		
    		ResultSet result;
    		if(startDate!=null && endDate!=null) {
    		PreparedStatement ps = connection.prepareStatement(sql);
    		ps.setObject(1, startDate);
    		ps.setObject(2, endDate);
            result = ps.executeQuery();
            }else 
            {   sql="SELECT * FROM transactions";
            	PreparedStatement ps=connection.prepareStatement(sql);
            	result=ps.executeQuery();
            }
            
            while(result.next()) 
            {
            
            	
            	selectedTransactions.add(new transactions(
                		result.getDouble("amount"),
                		result.getObject("transactionDate",LocalDate.class),
                		result.getString("transactionType")
                		));
            
            
            }
            return selectedTransactions;
            
       
		}catch(Exception e)
		{
			e.printStackTrace();
			
			
		}
		return selectedTransactions;
		
		
		
	}
	
	
	public void addTransaction(transactions transaction)  
	{
		try {
    		String query="INSERT INTO transactions (amount, transactionDate, transactionType) VALUES (?, ?, ?)";
    		PreparedStatement ps=connection.prepareStatement(query);
    		ps.setDouble(1,transaction.getAmount());
    		ps.setObject(2,transaction.getTransactionDate());
    		ps.setString(3,transaction.getTransactionType());
    		
    		
    		
    		ps.executeUpdate();
    		
		}
    		catch(Exception e) 
    		{
    			
    			e.printStackTrace();
    			
    		}
    				
		
	}
	
	
	
	
	
	
	
	
	public void updateMember(member newVersionMember,member oldVersionMember,String logType) 
	{
		try 
		{
			String query="UPDATE members SET memberName=?, memberMembership=?,  creationDate=?, membershipStart=?, membershipEnd=?, gender=?, phoneNumber=?, birthDate=?, membershipPeriod=?, balance=?, paidBasePrice=? WHERE memberID=?";
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1,newVersionMember.getMemberName());
			ps.setString(2,newVersionMember.getMemberMembership());
			ps.setObject(3,newVersionMember.getCreationDate());
			ps.setObject(4,newVersionMember.getMembershipStart());
			ps.setObject(5,newVersionMember.getMembershipEnd());
			ps.setString(6,newVersionMember.getGender());
			ps.setString(7,newVersionMember.getPhoneNumber());
			ps.setObject(8,newVersionMember.getBirthDate());
			ps.setString(9,newVersionMember.getMembershipPeriod());
			ps.setDouble(10,newVersionMember.getBalance());
			ps.setDouble(11, newVersionMember.getPaidBasePrice());
			ps.setInt(12, newVersionMember.getMemberID());
			int success=ps.executeUpdate();
			
			
			String logDetails="OLD VERSİON ----> NEW VERSİON\n "+oldVersionMember.getMemberName()+"---->"+newVersionMember.getMemberName()+"\n"+oldVersionMember.getPhoneNumber()+"---->"+newVersionMember.getPhoneNumber()+"\n"+
	    	    	oldVersionMember.getGender()+"---->"+newVersionMember.getGender()+"\n"+oldVersionMember.getBirthDate()+"---->"+newVersionMember.getBirthDate()+"\n"+oldVersionMember.getBalance()+"---->"+newVersionMember.getBalance()+"\n"+oldVersionMember.getMembershipPeriod()+"---->"+newVersionMember.getMembershipPeriod()+"\n"
	    	    			+oldVersionMember.getMemberMembership()+"---->"+newVersionMember.getMemberMembership()+"\n"+oldVersionMember.getMembershipStart()+"---->"+newVersionMember.getMembershipStart()+"\n"+oldVersionMember.getMembershipEnd()+"---->"+newVersionMember.getMembershipEnd();
	    	FormFunctions.createLog(logType,logDetails);
	    	database db=new database();
	
			db.closeConnection();
			
			
			
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	  public ObservableList<member> bringMembers()
  	{
  		try {
  		ObservableList<member> memberList=FXCollections.observableArrayList();
  		String Query="SELECT * FROM members";
  		Statement statement = connection.createStatement();
          ResultSet result = statement.executeQuery(Query);
          
          while(result.next()) 
          {
          
          	
          	memberList.add(new model.member(
              		result.getInt("memberID"),
              		result.getString("memberName"),
              		result.getString("membershipPeriod"),
              		result.getString("memberMembership"),
              		result.getString("gender"),
              		result.getString("phoneNumber"),
              		result.getObject("birthDate",LocalDate.class),
              		result.getObject("membershipStart",LocalDate.class),
              		result.getObject("membershipEnd",LocalDate.class),
              		result.getInt("balance"),
              		result.getObject("creationDate",LocalDate.class),
              		result.getDouble("paidBasePrice")
              		));
          
          
          }
          return memberList;
     
		}catch(Exception e)
		{
			System.out.println(e);
			return null;
			
		}
		
		
		
	
		
		
		
	}
	
        
        
        
        
        
        public ObservableList<membership> bringPackages()
    	{
    		try {
    		ObservableList<membership> membershipList=FXCollections.observableArrayList();
    		String Query="SELECT * FROM membership_packages";
    		Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(Query);
            
            while(result.next()) 
            {
            
            	
            	membershipList.add(new model.membership(
                		result.getString("packageName"),
                		result.getInt("packagePrice"),
                		result.getString("accesibleZones"),
                		result.getString("periods"),
                		result.getDouble("discountRate")
                		));
            
            
            }
            return membershipList;
       
		}catch(Exception e)
		{
			System.out.println(e);
			return null;
			
		}
		
		
		
	
		
		
		
	}
        
        
        
        
 
     
        	
        	public ObservableList<logs> logOperations (String sql,List<Object> psValues)  
        	{
        		ObservableList<logs> foundLogs=FXCollections.observableArrayList();
        		try {
        		PreparedStatement ps=this.connection.prepareStatement(sql);
        		
        		
        		for(int i=0; i<psValues.size();i++) 
        		{
        			
        			
        		 if(psValues.get(i) instanceof String) 
        		 {
        			 
        			 ps.setString(i+1,(String)psValues.get(i));
        			 
        			 
        		 }
        		 else if(psValues.get(i) instanceof Boolean) 
        		 {
        			 
        			 ps.setBoolean(i+1, (Boolean)psValues.get(i));
        			 
        			 
        		 }
        		 
        		 else if(psValues.get(i) instanceof Object) 
        		 {
        			 
        			 ps.setObject(i+1,(Object)psValues.get(i));
        			 
        			 
        		 }
        			
        			
        			
        			
        		}//psvalues for
        		
        	  
        	    ResultSet rs=ps.executeQuery();
        	    while(rs.next()) 
                {
                
                	
                	foundLogs.add(new logs(
                    		rs.getInt("logID"),
                    		rs.getString("logType"),
                    		rs.getString("logOperator"),
                    		rs.getObject("logDate",LocalDate.class),
                    		rs.getString("logDetails")
                    		));
                
                
                }
                return foundLogs;
        	    
        		}catch(SQLIntegrityConstraintViolationException e) 
        		{
        			e.printStackTrace();
        			FormFunctions.alertMessageErr("Registerin New Operator", "Failed", "There is a record with this name");
        			
        			
        		}
        		
        		
        		
        		
        		catch(Exception e) 
        		{
        			
        			e.printStackTrace();
        			
        		}
        		
        		
        		return foundLogs;
        	
        }
        
        
        
        
    
        public ObservableList<items> bringItems()
        {
        	ObservableList<items> itemsList=FXCollections.observableArrayList();
        	
        	try {
        		
        		String Query="SELECT * FROM items";
        		Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(Query);
                
                while(result.next()) 
                {
                
                	
                	itemsList.add(new items(
                    		result.getString("itemName"),
                    		result.getInt("stockCount"),
                    		result.getDouble("sellingPrice"),
                    		result.getDouble("unitCost")
                    		));
                
                
                }
                return itemsList;
           
    		}catch(Exception e)
    		{
    			System.out.println(e);
    			return itemsList;
    			
    		}
    		
    		
    		
    	
        	
        	
        }
        
        public void registerItem(items item) throws SQLException 
    	{
    		try {
    		String query="INSERT INTO items (itemName, stockCount, sellingPrice, unitCost) VALUES (?, ?, ?, ?)";
    		PreparedStatement ps=connection.prepareStatement(query);
    		ps.setString(1,item.getItemName());
    		ps.setInt(2,item.getStockCount());
    		ps.setDouble(3,item.getSellingPrice());
    		ps.setDouble(4,item.getUnitCost());
    		
    		
    		
    		int success=ps.executeUpdate();
    		
    		if(success>0) 
    		{
    			FormFunctions.alertMessageInf ("Register Item", "Registering Item Operation", "Successfull");
    			
    		}
    		else 
    		{
    			FormFunctions.alertMessageErr("Register Item", "Registerin Item Operation", "Unsuccessfull");
    			
    		}
    		}catch(SQLIntegrityConstraintViolationException e ) 
    		{
    			FormFunctions.alertMessageErr("Register Item", "Register Item Operation", "unsuccessfull,a item with this name is already exist.");
    			
    			
    		}catch(Exception e) 
    		{
    			
    			e.printStackTrace();
    			
    		}
    		
    		
    		
    		
    	}
        
        
        public void registerLog(logs log) 
    	{
    		try {
    			
    		String query="INSERT INTO logs (logType, logOperator, logDate, logDetails) VALUES (?, ?, ?, ?)";
    		PreparedStatement ps=connection.prepareStatement(query);
    		ps.setString(1,log.getLogType());
    		ps.setString(2,log.getLogOperator());
    		ps.setObject(3,log.getLogDate());
    		ps.setString(4,log.getLogDetails());

    	    ps.executeUpdate();
    		}catch(SQLIntegrityConstraintViolationException e ) 
    		{
    			
    			
    			
    		}catch(Exception e) 
    		{
    			
    			e.printStackTrace();
    			
    		}
    		
    		
    		
    		
    	}
    	
    	
	
	public void registerMember(model.member member) throws SQLException 
	{
		try {
		String query="INSERT INTO members (memberName, memberMembership, creationDate, membershipStart, membershipEnd, gender, phoneNumber, birthDate, membershipPeriod, balance,paidBasePrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		PreparedStatement ps=connection.prepareStatement(query);
		ps.setString(1,member.getMemberName());
		ps.setString(2,member.getMemberMembership());
		ps.setObject(3,member.getCreationDate());
		ps.setObject(4,member.getMembershipStart());
		ps.setObject(5,member.getMembershipEnd());
		ps.setString(6,member.getGender());
		ps.setString(7,member.getPhoneNumber());
		ps.setObject(8,member.getBirthDate());
		ps.setString(9,member.getMembershipPeriod());
		ps.setDouble(10,member.getBalance());
		ps.setDouble(11, member.getPaidBasePrice());
		
		
		int success=ps.executeUpdate();
		;
		if(success>0) 
		{
			FormFunctions.alertMessageInf ("New Member", "Registering New Member Operation", "Successfull");
			String logDetails=member.getMemberName()+"\nMembership: "+ member.getMemberMembership()+"\nCreation Date: "+member.getCreationDate()+"\nMembership Start:"+member.getMembershipStart()+
    				"\nMembership End: "+ member.getMembershipEnd()+"\ngender: "+member.getGender()+"\nPhone Number"+member.getPhoneNumber()+"\nBirth Date: "+member.getBirthDate()+"\nmembershipPeriod: "
    						+member.getMembershipPeriod()+"\nBalance: "+member.getBalance();
    				FormFunctions.createLog("New Member", logDetails);
    				database db=new database();
    	
    				db.closeConnection();
			
		}
		else 
		{
			FormFunctions.alertMessageErr("New Member", "Registering New Member Operation", "Unsuccessfull");
			
		}
		}catch(SQLIntegrityConstraintViolationException e ) 
		{
		
			
			
		}catch(Exception e) 
		{
			
			e.printStackTrace();
			
		}
		
		
		
		
	}
	
	public void updateItem(items oldItem,items item) 
	{
		try 
		{
			String query="UPDATE items SET itemName=?, stockCount=?,  sellingPrice=?, unitCost=? WHERE itemName=?";
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1,item.getItemName());
			ps.setInt(2,item.getStockCount());
			ps.setDouble(3,item.getSellingPrice());
			ps.setObject(4,item.getUnitCost());
			ps.setObject(5,oldItem.getItemName());
			
			int success=ps.executeUpdate();
			
			
		}catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public void registerPackage(model.membership membership) throws SQLException 
	{
		
		
		try {
		String query="INSERT INTO membership_packages values(?,?,?,?,?)";
		PreparedStatement ps=connection.prepareStatement(query);
		ps.setString(1,membership.getMembershipName());
		ps.setDouble(2,membership.getMembershipPrice());
		ps.setString(3,membership.getAccesibleZones());
		ps.setString(4,membership.getPeriods() );
		ps.setDouble(5, membership.getDiscountRate());
		
		int success=ps.executeUpdate();
		
		
		
		if(success>0) 
		{
			FormFunctions.alertMessageInf ("New Package", "Creating New Package Operation", "Successfull");
			
		}
		else 
		{
			FormFunctions.alertMessageErr("New Package", "Creating New Package Operation", "Unsuccessfull");
		}
		}catch(SQLIntegrityConstraintViolationException e ) 
		{
			FormFunctions.alertMessageErr("New Package", "Creating New Package Operation", "Unsuccessfull. A package with this name already exists");
			
			
		}catch(Exception e) 
		{
			
			
			
		}
		
	
	                       
		
	}
	
	
	public void updateMembership(membership oldVersion,membership newVersion ) 
	{   
		String response=FormFunctions.alertMessageConf("Updating Package", "Package Update Operation", "Are you sure you want to update the package? Changes will be applied to the package, and the members who have purchased the package will also be affected.");
		if(response.equals("YES")){
		
		
		try {
	   String query="Update membership_packages SET packageName=?,packagePrice=?,accesibleZones=?,periods=?,discountRate=? WHERE packageName=?";
       PreparedStatement ps=connection.prepareStatement(query);
	   ps.setString(1,newVersion.getMembershipName());
	   ps.setDouble(2,newVersion.getMembershipPrice());
	   ps.setString(3,newVersion.getAccesibleZones());
	   ps.setString(4,newVersion.getPeriods());
	   ps.setDouble(5, newVersion.getDiscountRate());
	   ps.setString(6, oldVersion.getMembershipName());	
	   int count=ps.executeUpdate();
	   
	   if(count>0) 
	   {
		   
		   FormFunctions.alertMessageInf("Updating Package", "Package Update Operation", "Successfull");
		   
	   
	  
	   
	   if(!oldVersion.getMembershipName().equals(newVersion.getMembershipName())) 
	   {
		
		   String memberUpdate="Update members SET memberMembership=? Where memberMembership=?";
		   PreparedStatement ps2=connection.prepareStatement(memberUpdate);
		   ps2.setString(1,newVersion.getMembershipName());
		   ps2.setString(2, oldVersion.getMembershipName());
		   System.out.println(oldVersion.getMembershipName() +" "+newVersion.getMembershipName());
		   int count2=ps2.executeUpdate();
		   if(count2>0) 
		   {
			   FormFunctions.alertMessageInf("Updating Package", " Package Update Operation ", "Successfull. " + count2 + " Members who purchased '"+ oldVersion.getMembershipName()+ "' Now has '" +newVersion.getMembershipName()+"'");
			   
		   }
		   else 
		   {
			   
			   
			   
		   }
	   }
	   
	   }
	   else 
	   {
		   
		   FormFunctions.alertMessageErr("Updating Package", "Package Update Operation", "unsuccessfull");
		   
	   }
	}catch(Exception e) 
	{
		e.printStackTrace();
		FormFunctions.alertMessageErr("Updating Package", "Package Update Operation","unsuccessfull");
	}
		}
	
		String logDetails="Old Version ----> New Version\n" + oldVersion.getMembershipName()+"---->"+newVersion.getMembershipName()+"\n"+oldVersion.getMembershipPrice()+"---->"+newVersion.getMembershipPrice()+"\n"+
		oldVersion.getAccesibleZones()+"---->"+newVersion.getAccesibleZones()+"\n"+oldVersion.getPeriods()+"---->"+newVersion.getPeriods()+"\n"+oldVersion.getDiscountRate()+"---->"+newVersion.getDiscountRate();
		FormFunctions.createLog("Package Update", logDetails);
		database db=new database();
	
		db.closeConnection();
		
	}
	

	
	public void deleteMember(member member) 
	{
		try 
		{
		   
			String query="DELETE FROM members WHERE memberID=?";
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setInt(1, member.getMemberID());
			ps.executeUpdate();
		
			
			
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			
		}
		
		
		
		
	}
	
	
	public void deleteItem(items item) 
	{
		try 
		{
			String query="DELETE FROM items WHERE itemNamee=? ";
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, item.getItemName());
			ps.executeUpdate();
			
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	public void deletePackage(membership membership) 
	{
		try 
		{
			String query="DELETE FROM membership_packages WHERE packageName=? ";
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1, membership.getMembershipName());
			ps.executeUpdate();
			
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public void closeConnection() 
	{
		try {
			if(connection!=null)
				connection.close();
		}
		catch(SQLException e){
			
			e.printStackTrace();
			
		}
		
		
	}
	
	
}

     


