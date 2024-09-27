package model;

public class Operator {

	
	private String operatorName;
	private String operatorPassword;
	

	private boolean registerMember;
	private boolean updateMember;
	private boolean createPackage;
	private boolean updatePackage;
	private boolean sell;
	private boolean registerInv;
	private boolean updateInv;
	private boolean accessToLogs;
	private boolean takesPayment;
    private Roles role;
	
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOperatorPassword() {
		return operatorPassword;
	}
	public void setOperatorPassword(String operatorPassword) {
		this.operatorPassword = operatorPassword;
	}
	public boolean isRegisterMember() {
		return registerMember;
	}
	public void setRegisterMember(boolean registerMember) {
		this.registerMember = registerMember;
	}
	public boolean isUpdateMember() {
		return updateMember;
	}
	public void setUpdateMember(boolean updateMember) {
		this.updateMember = updateMember;
	}
	public boolean isCreatePackage() {
		return createPackage;
	}
	public void setCreatePackage(boolean createPackage) {
		this.createPackage = createPackage;
	}
	public boolean isUpdatePackage() {
		return updatePackage;
	}
	public void setUpdatePackage(boolean updatePackage) {
		this.updatePackage = updatePackage;
	}
	public boolean isSell() {
		return sell;
	}
	public void setSell(boolean sell) {
		this.sell = sell;
	}
	public boolean isRegisterInv() {
		return registerInv;
	}
	public void setRegisterInv(boolean registerInv) {
		this.registerInv = registerInv;
	}
	public boolean isUpdateInv() {
		return updateInv;
	}
	public void setUpdateInv(boolean updateInv) {
		this.updateInv = updateInv;
	}
	public boolean isAccessToLogs() {
		return accessToLogs;
	}
	public void setAccessToLogs(boolean accessToLogs) {
		this.accessToLogs = accessToLogs;
	}
	public boolean isTakesPayment() {
		return takesPayment;
	}
	public void setTakesPayment(boolean takesPayments) {
		this.takesPayment = takesPayments;
	}
	public Operator(String operatorName, String operatorPassword, boolean registerMember, boolean updateMember,
			boolean createPackage, boolean updatePackage, boolean sell, boolean registerInv, boolean updateInv,
			boolean accessToLogs, boolean takesPayment,Roles role) {
		super();
		this.operatorName = operatorName;
		this.operatorPassword = operatorPassword;
		this.registerMember = registerMember;
		this.updateMember = updateMember;
		this.createPackage = createPackage;
		this.updatePackage = updatePackage;
		this.sell = sell;
		this.registerInv = registerInv;
		this.updateInv = updateInv;
		this.accessToLogs = accessToLogs;
		this.takesPayment = takesPayment;
		this.role=role;
	}

	
}
