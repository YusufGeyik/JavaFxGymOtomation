package model;

public class operations {

	private int memberID;
	private int operatorID;
	private int operationSum;
	private int oldbalance;
	private int newbalance;
	public enum OperationType {
        UYELIK_SATIS,
        GUNCELLEME,
        UYELIK_SILME,
        BUFE
    }
	private OperationType operationType;
	
	// Getter ve setter metotları
    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
	
	public int getMemberID()
	{
		return this.memberID;
	}

	
	public int getOperatorID()
	{
		return this.operatorID;
	}
	
	
	public int getOperationSum()
	{
		return this.operationSum;
	}
	
	
	public int getOldbalance()
	{
		return this.oldbalance;
	}
	
	
	public int getNewbalance()
	{
		return this.newbalance;
	}
	
	
	
	
	
	  // Constructor
	public operations(int memberID,int operatorID,int operationSum,int oldbalance,int newbalance,OperationType operationType) {
        this.memberID=memberID;
        this.operatorID=operatorID;
        this.operationSum=operationSum;
        this.oldbalance=oldbalance;
        this.newbalance=newbalance;
		this.operationType = operationType;
    }
	
	

    

  
    

    
	
	
}
