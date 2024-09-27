/*
package controller;


import java.io.IOException;
import java.util.Optional;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.ListSpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.FormFunctions;

public class deneme {
    @FXML
	Spinner<Integer> spinInt;
    @FXML
    Spinner <String> spString;
    @FXML
    Spinner<String> stringspinner1;
    @FXML 
    Slider s1;
	
   private static ObservableList<String> renkler= FXCollections.observableArrayList("Mavi");
  
   SpinnerValueFactory<String> stringDeger= new ListSpinnerValueFactory<String>(renkler);
   
   public void onChange(ObservableValue<? extends Integer>obs,int oldValue, int newValue) 
   {
	   System.out.println("");
   }
    
    
public void initialize() throws IOException 
{
	/*Stage stage1=new Stage();
	AnchorPane pane1=(AnchorPane)FXMLLoader.load(getClass().getResource("/view/loginPage.fxml"));
	Scene scene= new Scene(pane1);
	stage1.setScene(scene);
	stage1.show();

	FXMLLoader loader=new FXMLLoader(getClass().getResource("/view/loginPage.fxml"));
	
	
	AnchorPane pane2=(AnchorPane)loader.load();
	loginPage login=loader.getController();
	Scene scene2=new Scene(pane2,400,600);
	
	
	Stage stage2=new Stage();
	stage2.setScene(scene2);
	stage2.setOpacity(0.5);
	stage2.setTitle("Exam prep"); //******Bunla başlıkları ata iconalrda ata programa
	stage2.centerOnScreen();
	stage2.initStyle(StageStyle.DECORATED);
	
	stage2.show();
	
	AnchorPane pane=FXMLLoader.load(getClass().getResource("/view/payment.fxml"));
	pane2.getChildren().setAll(pane);
    
    ObservableList<String> birds=FXCollections.observableArrayList("Büyük Yakup","Yakup","Manolya");
    SpinnerValueFactory<String> birdsValue= new ListSpinnerValueFactory<>(birds);
    stringspinner1.setValueFactory(birdsValue);
    
    SpinnerValueFactory<Integer> degerInt=new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10,1);
    spinInt.setValueFactory(degerInt);
    
    
    	spinInt.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_LEFT_VERTICAL);
        spinInt.valueProperty().addListener(this::onChange);
        
        s1.setMin(0);
        s1.setMax(100);
        s1.setShowTickLabels(true);
        s1.setShowTickMarks(true);
        s1.setMajorTickUnit(10); // Ana işaretlerin aralığı
        s1.setMinorTickCount(9); // Her ana işaret arasında 9 tane küçük işaret

        
        s1.valueProperty().addListener((obs,oldValue,newValue)->System.out.println(newValue));
        
    
        
       model.database db= new model.database();
       
        ChoiceDialog<String> ch1=new ChoiceDialog<String>("Cinsiyet",FormFunctions.bringPackageNames(db.bringPackages()));
        Optional<String> respons=ch1.showAndWait();
        System.out.println(respons.get());
        ChoiceDialog<String> ch= new ChoiceDialog<String>("Cinsiyet", FormFunctions.getGenders());
        Optional<String> response=ch.showAndWait();
        System.out.println(response);
        
        
        
     
}    
    
}
*/

package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.deneclass;

public class deneme {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<deneclass,Button> bt;

    @FXML
    private TableColumn<deneclass, CheckBox> checkbox;

    @FXML
    private TableColumn<deneclass, Integer> id;
    @FXML
    private TableView<deneclass> tbl;

    @FXML
    private TableColumn<deneclass, TextField> txt;

    @FXML
    void initialize() {
     int btque=0;
     Button[] bts=new Button[5];
    	ObservableList<deneclass> list=FXCollections.observableArrayList();
    	list.add(new deneclass("txt",0));
    	
        bt.setCellValueFactory(new PropertyValueFactory<>("bt"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        checkbox.setCellValueFactory(new PropertyValueFactory<>("chkbx"));
        txt.setCellValueFactory(new PropertyValueFactory<>("txt"));
       
    	tbl.setItems(list);
    	
    	
    	
    	
    }

}







