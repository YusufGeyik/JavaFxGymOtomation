package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.FormFunctions;
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
    void initialize() {
      
     logDetailstxta.selectedTextProperty();
     
    	FormFunctions.addLogsToTable(logstb, FormFunctions.getLogs(), logID, logType, logOperator, logDates, logDetails);
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
