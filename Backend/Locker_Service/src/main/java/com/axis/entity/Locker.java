package com.axis.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Locker")
public class Locker {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int lockerid;
	private String lockertype;
	private String lockerlocation;
	private String lockersize;
	private double lockerprice;
	 @Column(name = "amount_paid")
	    private boolean amountPaid=false;
	private String lockerstatus;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accid", referencedColumnName = "accid")
	@OnDelete(action = OnDeleteAction.CASCADE)
    public Account account;

	public Locker() {
		super();
	}

	

	public Locker( String lockertype, String lockerlocation, String lockersize, double lockerprice,
			boolean amountPaid, String lockerstatus, Account account) {
		super();
		
		this.lockertype = lockertype;
		this.lockerlocation = lockerlocation;
		this.lockersize = lockersize;
		this.lockerprice = lockerprice;
		this.amountPaid = amountPaid;
		this.lockerstatus = lockerstatus;
		this.account = account;
	}



	public int getLockerid() {
		return lockerid;
	}

	public void setLockerid(int lockerid) {
		this.lockerid = lockerid;
	}

	public String getLockertype() {
		return lockertype;
	}

	public void setLockertype(String lockertype) {
		this.lockertype = lockertype;
	}

	public String getLockerlocation() {
		return lockerlocation;
	}

	public void setLockerlocation(String lockerlocation) {
		this.lockerlocation = lockerlocation;
	}

	public String getLockersize() {
		return lockersize;
	}

	public void setLockersize(String lockersize) {
		this.lockersize = lockersize;
	}

	public double getLockerprice() {
		return lockerprice;
	}

	public void setLockerprice(double lockerprice) {
		this.lockerprice = lockerprice;
	}

	public String getLockerstatus() {
		return lockerstatus;
	}

	public void setLockerstatus(String lockerstatus) {
		this.lockerstatus = lockerstatus;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	

    public boolean isAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(boolean amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    

	@Override
	public String toString() {
		return "Locker [lockerid=" + lockerid + ", lockertype=" + lockertype + ", lockerlocation=" + lockerlocation
				+ ", lockersize=" + lockersize + ", lockerprice=" + lockerprice + ", lockerstatus=" + lockerstatus
				+ ", account=" + account + "]";
	}
	
}
