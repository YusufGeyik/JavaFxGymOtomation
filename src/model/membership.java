package model;

import java.lang.reflect.InvocationTargetException;

public class membership {
private String membershipName;
private double membershipPrice;
private String accesibleZones;
private String periods;
private double discountRate;// ready to multiply with base price in order to give discount for long-term memberships



public membership(String membershipName,double membershipPrice,String accesibleZones,String periods,double discountRate) throws InvocationTargetException
{
	this.membershipName=membershipName;
	this.membershipPrice=membershipPrice;
	this.periods=periods;
	this.accesibleZones=accesibleZones;
	this.discountRate=discountRate;
}

public double getDiscountRate() {
	return discountRate;
}

public void setDiscountRate(double discountRate) {
	this.discountRate = discountRate;
}

public String getPeriods() {
	return periods;
}

public void setPeriods(String periods) {
	this.periods = periods;
}

public String getMembershipName() 
{
	
return membershipName;
}

public void setMembershipName(String membershipName) 
{
	this.membershipName=membershipName;

}

public void setMembershipPrice(double membershipPrice) 
{
	this.membershipPrice=membershipPrice;

}

public double getMembershipPrice() 
{
	
return membershipPrice;
}

public void setAccesibleZones(String accesibleZones) 
{
	this.accesibleZones=accesibleZones;

}


public String getAccesibleZones() 
{
	
return accesibleZones;
}





}
