package controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.FormFunctions;
import model.denemClass;

public class tableController {

	


	
	
	

	
  /* outer model class

 
   @FXML
    private TableColumn<denemClass, Button> add;

    @FXML
    private TableColumn<denemClass, TextField> amountField;

    @FXML
    private TableColumn<denemClass, CheckBox> checkbox;



    @FXML
    private TableColumn<denemClass, String> itemName;

    @FXML
    private TableView<denemClass> itemView;*/
	
	 
	@FXML  // innerClass
    private TableColumn<innerClass, Button> add;

    @FXML
    private TableColumn<innerClass, TextField> amountField;

    @FXML
    private TableColumn<innerClass, CheckBox> checkbox;



    @FXML
    private TableColumn<innerClass, String> itemName;

    @FXML
    private TableView<innerClass> itemView;
	
    @FXML 
    Spinner<Integer> xLayout;
   
    @FXML 
    Spinner<Integer> Ylayout;
	

    @FXML
    private ImageView img;
    @FXML
    private Slider sliderHorizantal;

    @FXML
    private Slider sliderVertical;
    @FXML
    private AnchorPane pane1;
    @FXML
    TextField txt;
    @FXML
    TextField txtt;
    public static String hello() 
    {
    	
    	return "hello";
    	
    }
    ObservableList<Button> butonlar=FXCollections.observableArrayList();
    ObservableList<denemClass> itemler=FXCollections.observableArrayList();
    ObservableList<innerClass>itemssubClassList=FXCollections.observableArrayList();
    
    
    @FXML
    public void initialize() 
    {

    	txt.setText("txt");
    	txtt.setText("txtt");
     img.setImage(new Image("/images/operatorvector.png"));
     img.setPreserveRatio(false);
     sliderHorizantal.setShowTickLabels(true);
     sliderHorizantal.setShowTickMarks(false);
     sliderHorizantal.setMajorTickUnit(5);
     sliderHorizantal.setBlockIncrement(10);
     sliderHorizantal.setMax(100);
     sliderVertical.setShowTickLabels(true);
     sliderVertical.setShowTickMarks(true);
     sliderVertical.setMajorTickUnit(5);
     sliderVertical.setBlockIncrement(10);
     sliderVertical.setMax(100);
     SpinnerValueFactory<Integer> spY=new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000);
     SpinnerValueFactory<Integer> spx=new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000);
     xLayout.setValueFactory(spx);
     Ylayout.setValueFactory(spY);
    	
     xLayout.valueProperty().addListener((obs,oldValue,newValue)->
     {img.setLayoutX(newValue);
    	 
     
     
     }

    		 );
     Ylayout.valueProperty().addListener((obs,oldValue,newValue)->{img.setLayoutY(newValue);});
    	sliderHorizantal.valueProperty().addListener((obs, oldValue,newValue)->{
    		
    		img.setFitWidth((double) newValue);
    		
    		
    	});
    	
    	sliderVertical.valueProperty().addListener((obs, oldValue,newValue)->{
    		
    		img.setFitHeight((double)newValue);
    		
    	});
    	
    	
    	
    	innerClass item=new innerClass(0,"item"
    			+ "");
    	itemssubClassList.add(item);
    	
    	 itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
 	    amountField.setCellValueFactory(new PropertyValueFactory<>("amountField"));
 	    checkbox.setCellValueFactory(new PropertyValueFactory<>("checkbox"));
 	    add.setCellValueFactory(new PropertyValueFactory<>("add"));
 	    
 	    itemView.setItems(itemssubClassList);    
    	
    	
           /*denemClass item=new denemClass(0,"item");
           itemler.add(item);
    	
    
     
	    itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
	    amountField.setCellValueFactory(new PropertyValueFactory<>("amountField"));
	    checkbox.setCellValueFactory(new PropertyValueFactory<>("checkbox"));
	    add.setCellValueFactory(new PropertyValueFactory<>("add"));
	    
	    itemView.setItems(itemler);    */


    	
    	
    	
    }
    public class innerClass {

    	private Button add;
    	public Button getAdd() {
    		return add;
    	}


    	public void setAdd(Button add) {
    		this.add = add;
    	}


    	public TextField getAmountField() {
    		return amountField;
    	}


    	public void setAmountField(TextField amountField) {
    		this.amountField = amountField;
    	}


    	public String getItemName() {
    		return itemName;
    	}


    	public void setItemName(String itemName) {
    		this.itemName = itemName;
    	}


    	public CheckBox getCheckbox() {
    		return checkbox;
    	}


    	public void setCheckbox(CheckBox checkbox) {
    		this.checkbox = checkbox;
    	}


    	private TextField amountField;
    	private String itemName;
    	private CheckBox checkbox;
    	
    	
    	public  innerClass(int amount,String itemName) 
    	{
    		
    		this.add=new Button("itemName");
    		this.add.setId(itemName);
    		this.add.setOnAction(event->{
    			System.out.println(event.getSource());
    			System.out.println(amountField.getText());
    			if(checkbox.isSelected()==true) 
    			{
    				System.out.println(checkbox.isSelected());
    				
    			}
    		 
    		    System.out.println(tableController.hello());
    		    
    		    FormFunctions f=new FormFunctions();
    		   System.out.println(f.hello());		
    		   
    		   
    		});
    	    this.amountField=new TextField();
    	    this.amountField.setText(String.valueOf(amount));
    	    this.itemName=itemName;
    	    this.checkbox=new CheckBox();
    	    
    		
    		
    	}
    	
  
    
    
}
}
