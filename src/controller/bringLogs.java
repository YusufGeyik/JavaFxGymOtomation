package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.FormFunctions;
import model.Operator;
import model.logs;
import model.member;

public class bringLogs {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<logs, LocalDate> logDates;

    @FXML
    private TableColumn<logs, String> logDetails;

    @FXML
    private TableColumn<logs, Integer> logID;

    @FXML
    private TableColumn<logs, String> logOperator;

    @FXML
    private TableColumn<logs, String> logType;

    @FXML
    private TableView<logs> logstb;
    @FXML
    private TextArea logDetailstxta;


    @FXML
    private DatePicker endDatepicker;

    @FXML
    private Button filterByIdbt;
    @FXML
    private TextField searchtxt;

    @FXML
    private DatePicker startDatepicker;

    ObservableList<logs> todaysLogs=FXCollections.observableArrayList();    
    @FXML
    void searchInputReceived(KeyEvent event) {

    	try {
    	 	
        	if(searchtxt.getText().isEmpty()) //button clicked without any input
        	{
        	
        		FormFunctions.addLogsToTable(logstb, todaysLogs, logID, logType, logOperator, logDates, logDetails);
        		
        
        		
        	}
        	else //there is an input
        	{
        		String psValue="%"+searchtxt.getText().trim()+"%";
        		String sql="SELECT * FROM logs WHERE logType LIKE ? OR  logOperator LIKE ? OR logDetails LIKE ?";
        	    List<Object> psValues=new ArrayList<>();
        	    psValues.add(psValue);
        	    psValues.add(psValue);
        	    psValues.add(psValue);
        		model.database database=new model.database();
        		ObservableList<logs> foundLogs=database.logOperations(sql, psValues);
        		
        	database.closeConnection();
        
        	
        	
        	FormFunctions.addLogsToTable(logstb, foundLogs, logID, logType, logOperator, logDates, logDetails);
        	
      
        	}
        	
        	}catch(Exception e) 
        	{
        		
        		e.printStackTrace();
        		
        	}
        	
        	}
    	
    	
    	
    	
    
    

    @FXML
    void filterClicked(ActionEvent event) {
 
    	ObservableList<logs> filteredLogs=FXCollections.observableArrayList();
    	ObservableList<logs> tableData=logstb.getItems();
    	LocalDate endDate=endDatepicker.getValue();
    	LocalDate startDate=startDatepicker.getValue();
    	if(!tableData.isEmpty() ||tableData.equals(todaysLogs)) {
    	
    	
    	LocalDate dateIterator=startDate;
    	while(!dateIterator.isAfter(endDate)) 
    	{
    		
    		for(logs log:tableData) 
    		{
    			
    			if(log.getLogDate().equals(dateIterator)) 
    			{
    				
    				filteredLogs.add(log);
    				
    			}
    			
    			
    		}
    		
    		
    		
          dateIterator=dateIterator.plusDays(1);		
    		
    	}
    	FormFunctions.addLogsToTable(logstb, filteredLogs, logID, logType, logOperator, logDates, logDetails);
    	
    	}
    	else
    	{                                                                         
    		
        	
      
        	String sql="SELECT * FROM logs WHERE logDate BETWEEN ? AND ?";
        	List<Object> psValues=new ArrayList<>();
        	psValues.add(startDate);
        	psValues.add(endDate);
        	model.database database=new model.database();
        	filteredLogs=database.logOperations(sql, psValues);
        	FormFunctions.addLogsToTable(logstb, filteredLogs, logID, logType, logOperator, logDates, logDetails);
    		database.closeConnection();
    		
    	}
    }
    
    
    @FXML
    void initialize() {
      
    	startDatepicker.setValue(LocalDate.now());
    	endDatepicker.setValue(LocalDate.now());
     logDetailstxta.selectedTextProperty();

 	String sql="SELECT * FROM logs WHERE logDate=?";
 	List<Object> psValues=new ArrayList<>();
 	psValues.add(LocalDate.now());
 	
       model.database database=new model.database();
       todaysLogs=database.logOperations(sql, psValues);
    	FormFunctions.addLogsToTable(logstb, todaysLogs, logID, logType, logOperator, logDates, logDetails);
    	database.closeConnection();
    	 logstb.setOnMouseClicked(event -> {
     		
     	    if (event.getClickCount() == 1) { 
     	        int selectedIndex = logstb.getSelectionModel().getSelectedIndex();
     	        if (selectedIndex != -1) { 
     	        	logs log=logstb.getSelectionModel().getSelectedItem();
     	        	logDetailstxta.setText(log.getLogDetails());
     	        	
     	        }
     	    }
     	});
    }

}
