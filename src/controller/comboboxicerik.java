package controller;

	import javafx.application.Application;
	import javafx.collections.FXCollections;
	import javafx.scene.Scene;
	import javafx.scene.control.ComboBox;
	import javafx.scene.control.ListCell;
	import javafx.scene.control.ListView;
	import javafx.scene.image.Image;
	import javafx.scene.image.ImageView;
	import javafx.scene.layout.VBox;
	import javafx.stage.Stage;

	public class comboboxicerik extends Application {

	    @Override
	    public void start(Stage primaryStage) {
	        ComboBox<String> comboBox = new ComboBox<>();
	        ListView<String> listView = new ListView<>();
	        
	        comboBox.setCellFactory(param -> new ListCell<String>() {
	            @Override
	            protected void updateItem(String item, boolean empty) {
	                super.updateItem(item, empty);
	                
	                if (empty || item == null) {
	                    setText(null);
	                    setGraphic(null);
	                } else {
	                    setText(item);
	                    // Örnek olarak her öğe için aynı resmi kullanıyoruz
	                    ImageView imageView = new ImageView(new Image("path/to/your/image.png"));
	                    imageView.setFitWidth(20);
	                    imageView.setFitHeight(20);
	                    setGraphic(imageView);
	                }
	            }
	        });

	        // Örnek içerik ekleyelim
	        comboBox.setItems(FXCollections.observableArrayList("Item 1", "Item 2", "Item 3"));

	        VBox root = new VBox(comboBox, listView);
	        Scene scene = new Scene(root, 300, 200);
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("ComboBox with ImageView Example");
	        primaryStage.show();
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
	}

