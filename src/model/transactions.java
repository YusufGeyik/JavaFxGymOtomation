package model;

import java.time.LocalDate;

public class transactions {

	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	public int getTransActionID() {
		return transActionID;
	}
	public void setTransActionID(int transActionID) {
		this.transActionID = transActionID;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	double amount;
	LocalDate transactionDate;
    int transActionID;
    String transactionType;
	public transactions(double amount, LocalDate transactionDate,String transactionType) {
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.transactionType=transactionType;
	}
}
