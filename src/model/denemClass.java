package model;

import controller.tableController;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


public class denemClass {

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
	
	
	public denemClass(int amount,String itemName) 
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
