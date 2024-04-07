package model;

public class Operator {

	private String operatorName;
	private String operatorPassword;
	
	
	
	public String getOperatorPassword() {
		return operatorPassword;
	}


	public void setOperatorPassword(String operatorPassword) {
		this.operatorPassword = operatorPassword;
	}


	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}


	public String getOperatorName() 
	{
		return operatorName; 
	}
	
	
	public void setName(String operatorName) // getters and setter for name
	{
		this.operatorName=operatorName;
	}
	
	 public Operator(String operatorName) 
	 {
		 this.operatorName=operatorName;
		 
	 }

	
}
