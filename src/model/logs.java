package model;

import java.time.LocalDate;

public class logs {

	
	int logID;
	String logType;
	String logOperator;
	LocalDate logDate;
	String logDetails;
	public int getLogID() {
		return logID;
	}
	public void setLogID(int logID) {
		this.logID = logID;
	}
	public String getLogType() {
		return logType;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public String getLogOperator() {
		return logOperator;
	}
	public void setLogOperator(String logOperator) {
		this.logOperator = logOperator;
	}
	public LocalDate getLogDate() {
		return logDate;
	}
	public void setLogDate(LocalDate logDate) {
		this.logDate = logDate;
	}
	public String getLogDetails() {
		return logDetails;
	}
	public void setLogDetails(String logDetails) {
		this.logDetails = logDetails;
	}
	
	public logs(String logType,String logOperator,LocalDate logDate,String logDetails) 
	{
		this.logType=logType;
		this.logOperator=logOperator;
		this.logDate=logDate;
		this.logDetails=logDetails;
		
		
		
		
	}

	public logs(int logID,String logType,String logOperator,LocalDate logDate,String logDetails) 
	{
		this.logID=logID;
		this.logType=logType;
		this.logOperator=logOperator;
		this.logDate=logDate;
		this.logDetails=logDetails;
		
		
		
		
	}
}
