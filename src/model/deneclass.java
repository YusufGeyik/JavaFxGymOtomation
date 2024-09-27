package model;

import javafx.event.ActionEvent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class deneclass {

	public Button getBt() {
		return bt;
	}


	public void setBt(Button bt) {
		this.bt = bt;
	}


	public CheckBox getChkbx() {
		return chkbx;
	}


	public void setChkbx(CheckBox chkbx) {
		this.chkbx = chkbx;
	}


	public TextField getTxt() {
		return txt;
	}


	public void setTxt(TextField txt) {
		this.txt = txt;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	private Button bt;
	
	private CheckBox chkbx;
	
	private TextField txt;
 
	
	int id;
	
	
	public deneclass(String txt,int id) 
	{
		this.bt=new Button();
		this.bt.setId("txt");
		this.bt.setText(txt);
		this.txt=new TextField();
		this.txt.setText(txt);
		this.id=id;
		this.bt.setOnAction(this::Click);
		
	}
	
	public String Click(ActionEvent e) 
	{
		
		System.out.println(e.getSource().toString());
		return null;
		
	}
	
	
}
