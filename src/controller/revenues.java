package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.FormFunctions;
import model.membership;
import model.transactions;

public class revenues {

	

    @FXML
    private VBox rightVbox;
    @FXML
    private ResourceBundle resources;

    @FXML
    private Button filterbt;
    
    @FXML
    private URL location;

    @FXML
    private TableColumn<transactions, LocalDate> transactionDatetc;

    @FXML
    private DatePicker endDatepicker;

    @FXML
    private BarChart<String, Double> revenueChart;

    @FXML
    private DatePicker startDatepicker;

    @FXML
    private TextField totalRevenuetxt;

    @FXML
    private TableColumn<transactions, Double> transactionAmounttc;

    @FXML
    private TableColumn<transactions, String> transactionTypetc;

    @FXML
    private TableView<transactions> transactionsView;

    @FXML
    void filterbtClicked(ActionEvent event) {

    	LocalDate startDate=startDatepicker.getValue();
    	LocalDate endDate=endDatepicker.getValue();
        model.database db=new model.database();
        String sql="SELECT * FROM transactions Where transactionDate BETWEEN ? AND ?";
    	ObservableList<transactions> foundTransactions=db.bringTransactions(sql, startDate, endDate);
    	
       FormFunctions.addDataToTableView(transactionsView,foundTransactions,transactionTypetc, transactionAmounttc,transactionDatetc); 
    	totalRevenuetxt.setText(FormFunctions.calculateRevenue(foundTransactions).toString());
    	db.closeConnection();
    }
    
   

    @FXML
    void initialize() {
   try {

	   int memberCount=0;
	   for(membership membershipPackage:FormFunctions.getPackages()) 
	   {
		   for(model.member m:FormFunctions.getMembers()) 
		   {
			   if(m.getMemberMembership().equals(membershipPackage.getMembershipName())) 
			   {
				   memberCount++;
				   
			   }
			   
			   
		   }
		   
		   Label packageName=new Label();
		   packageName.setText(membershipPackage.getMembershipName()+"\n" );
		   packageName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		   packageName.setTextFill(Color.rgb(255, 140, 0));
                    
		   
		   Label registeredNumber=new Label();
		   registeredNumber.setText("\t\t\t"+String.valueOf(memberCount));
		   
		   Label packageDescription=new Label();
		  registeredNumber.setTextFill(Color.rgb(255, 140, 0));
		  registeredNumber.setFont(Font.font("Arial", FontWeight.BOLD,14));
		   packageDescription.setText("NUMBER OF REGİSTERED MEMBERS \n ");
		   
		   rightVbox.getChildren().add(packageName);
		   rightVbox.getChildren().add(packageDescription);
		   rightVbox.getChildren().add(registeredNumber);
		   memberCount=0;
	   }
	   memberCount=FormFunctions.getMembers().size();
	   Label totalDescription=new Label();
	   totalDescription.setText("\n\nTOTAL REGİSTERED MEMBERS\n ");
	   totalDescription.setFont(Font.font("Arial", FontWeight.BOLD, 14));
	   rightVbox.getChildren().add(totalDescription);
	   Label totalMember=new Label();
	   totalMember.setText("\t\t "+String.valueOf(memberCount));
	   totalMember.setFont(Font.font("Arial", FontWeight.BOLD, 20));
	   totalMember.setTextFill(Color.rgb(255, 140, 0));
	   rightVbox.getChildren().add(totalMember);
	 
	   
	   String sql="SELECT * FROM transactions Where transactionDate BETWEEN ? AND ?";
	   model.database db=new model.database();
	   LocalDate date1 = LocalDate.now().minusMonths(1);
	   LocalDate date2 = LocalDate.now();
	   ObservableList<transactions> foundTransactions=db.bringTransactions(sql, date1, date2);
	   FormFunctions.addDataToTableView(transactionsView,foundTransactions,transactionTypetc, transactionAmounttc,transactionDatetc); 
	   totalRevenuetxt.setText(FormFunctions.calculateRevenue(foundTransactions).toString());
	
	   
    	startDatepicker.setValue(LocalDate.now());
     endDatepicker.setValue(LocalDate.now());
     int year=LocalDate.now().getYear();
  	 ObservableList<String> monthsList=FXCollections.observableArrayList("January","February","March","April","May","June","July","August","September","October","November","December");
  	 ObservableList<Double> monthlyRevenues=FXCollections.observableArrayList();
     for(int month=1;month<=12;month++) 
     {
    	 
        YearMonth yearMonth= YearMonth.of(year, month);
        LocalDate monthStarts=yearMonth.atDay(1);
    	LocalDate monthEnds=yearMonth.atEndOfMonth();
        
     monthlyRevenues.add(FormFunctions.calculateRevenue(db.bringTransactions(sql, monthStarts, monthEnds)));
        
    	
     }
     
     
     
   
     
     
     
     
     
     CategoryAxis xAxis=new CategoryAxis();
     NumberAxis yAxis=new NumberAxis();
     revenueChart.setTitle("Revenues by Months");
     xAxis.setLabel("Months");
     yAxis.setLabel("Revenues");
     XYChart.Series<String, Double> revenueSeries=new XYChart.Series<>();
     for (int i=0; i<12;i++) 
     {
    	 
    	 revenueSeries.getData().add(new XYChart.Data<>(monthsList.get(i),monthlyRevenues.get(i)));
    	 
     }
     
     revenueChart.getData().add(revenueSeries);
     db.closeConnection();
   }catch(Exception e) 
   {
	   
	   e.printStackTrace();
	   
   }
     
     
     
    }

}