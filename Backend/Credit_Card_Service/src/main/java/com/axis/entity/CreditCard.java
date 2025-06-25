package com.axis.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
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
@Table(name="CreditCard")
public class CreditCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int creditcardid;
	private String creditcardname;
	private String creditcardnumber;
	private String creditcardcvv;
	private double creditcardlimit;	
	 private double balance=creditcardlimit;
	private LocalDate expirydate;
	private double loanamount;
	private String status;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accid", referencedColumnName = "accid")
	@OnDelete(action = OnDeleteAction.CASCADE)
    public Account account;

	public CreditCard() {
		super();
	}

	public CreditCard(String creditcardname, String creditcardnumber, String creditcardcvv, double creditcardlimit, LocalDate expirydate, String status, Account account) {
		super();
		this.creditcardname = creditcardname;
		this.creditcardnumber = creditcardnumber;
		this.creditcardcvv = creditcardcvv;
		this.creditcardlimit = creditcardlimit;
		this.expirydate = expirydate;
		this.status = status;
		this.account = account;
		this.balance = creditcardlimit;
	}

	public int getCreditcardid() {
		return creditcardid;
	}

	public void setCreditcardid(int creditcardid) {
		this.creditcardid = creditcardid;
	}

	public String getCreditcardname() {
		return creditcardname;
	}

	public void setCreditcardname(String creditcardname) {
		this.creditcardname = creditcardname;
	}

	public String getCreditcardnumber() {
		return creditcardnumber;
	}

	public void setCreditcardnumber(String creditcardnumber) {
		this.creditcardnumber = creditcardnumber;
	}

	public String getCreditcardcvv() {
		return creditcardcvv;
	}

	public void setCreditcardcvv(String creditcardcvv) {
		this.creditcardcvv = creditcardcvv;
	}

	public double getCreditcardlimit() {
		return creditcardlimit;
	}

	public void setCreditcardlimit(double creditcardlimit) {
		this.creditcardlimit = creditcardlimit;
	}

	  public double getBalance() {
	        return balance;
	    }

	    public void setBalance(double balance) {
	        this.balance = balance;
	    }
	
	public LocalDate getExpirydate() {
		return expirydate;
	}

	public void setExpirydate(LocalDate expirydate) {
		this.expirydate = expirydate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	
	public double getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(double loanamount) {
		this.loanamount = loanamount;
	}

	@Override
	public String toString() {
		return "CreditCard [creditcardid=" + creditcardid + ", creditcardname=" + creditcardname + ", creditcardnumber="
				+ creditcardnumber + ", creditcardcvv=" + creditcardcvv + ", creditcardlimit=" + creditcardlimit
				+ ", expirydate=" + expirydate + ", status=" + status + ", account=" + account + "]";
	}
	
}
