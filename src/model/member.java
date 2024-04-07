package model;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

public  class member {

	private int memberID;
	private String memberName;
	private String membershipPeriod;
	private String memberMembership;
    private String gender;
    private String phoneNumber;  
    private LocalDate birthDate;
    private LocalDate membershipStart;
    private LocalDate membershipEnd;
    private double balance;
  
    private LocalDate creationDate;
	
	
    
    

	
	public int getMemberID() // getters and setters
	{
		return memberID;
	}
	
	public String getMemberName() 
	{
		return memberName; 
	}
	
	public void setMemberName(String name) 
	{
		this.memberName=name;
	}
	
	public String getMembershipPeriod() 
	{
		return membershipPeriod;
	}

	public void setMembershipPeriod(String membershipPeriod) 
	{
		this.membershipPeriod=membershipPeriod;
	}

	public String getMemberMembership() 
	{
		return memberMembership;
	}

	public void setMemberMembership(String memberMembership)
	{
		this.memberMembership=memberMembership;
	}

	public String getGender() 
	{
		return gender;
	}
	
	public void setGender(String gender)
	{
		this.gender=gender;
	}
	
	public String getPhoneNumber() 
	{
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber=phoneNumber;
	}
	
	public LocalDate getBirthDate() 
	{
		return birthDate;
	}
	
	public void setBirthDate(LocalDate birthDate)
	{
		this.birthDate=birthDate;
	}
	
	public LocalDate getMembershipStart() 
	{
		return membershipStart;
	}
	
	public void setMembershipStart(LocalDate membershipStart)
	{
		this.membershipStart=membershipStart;
	}
	
	public LocalDate getMembershipEnd() 
	{
		return membershipEnd;
	}

	public void setMembershipEnd(LocalDate membershipEnd)
	{
		this.membershipEnd=membershipEnd;
	}
	
	public double getBalance() 
	{
		return balance;
	}
	
	public void setBalance(double balance)
	{
		this.balance=balance;
	}
	
	
	
	public LocalDate getCreationDate() 
	{
		return creationDate;
	}
	
	public void setCreationDate(LocalDate creationDate)
	{
		this.creationDate=creationDate;
	}
	
public member(String memberName,String membershipPeriod,String memberMembership,String gender,String phoneNumber,LocalDate birthDate,LocalDate membershipStart, LocalDate membershipEnd,double balance,LocalDate creationDate) throws InvocationTargetException 
{
	//sqle yazarken kullan
	
	this.memberName=memberName;
	this.membershipPeriod=membershipPeriod;
	this.memberMembership=memberMembership;
	this.gender=gender;
	this.phoneNumber=phoneNumber;
	this.birthDate=birthDate;
	this.membershipStart=membershipStart;
	this.membershipEnd=membershipEnd;
	this.balance=balance;
	this.creationDate=creationDate;
}

	 public member(int memberID,String memberName,String membershipPeriod,String memberMembership,String gender,String phoneNumber,LocalDate birthDate,LocalDate membershipStart, LocalDate membershipEnd,double balance,LocalDate creationDate) throws InvocationTargetException
	{
		//sqlden çekerken kullan
		
		this.memberName=memberName;
		this.membershipPeriod=membershipPeriod;
		this.memberMembership=memberMembership;
		this.gender=gender;
		this.phoneNumber=phoneNumber;
		this.birthDate=birthDate;
		this.membershipStart=membershipStart;
		this.membershipEnd=membershipEnd;
		this.balance=balance;
		this.memberID=memberID;
		this.creationDate=creationDate;

			
			
			
			
			
}

	
	
	
	
}
