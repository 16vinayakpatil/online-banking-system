package com.axis.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accid;
	private int userid;
	private String accno;
	private String acctype;
	private String ifsccode;
	private double balance;
	private String status;
	
	public Account() {
		super();
	}

	public Account(int userid, String accno, String acctype, String ifsccode, double balance, String status) {
		super();
		this.userid = userid;
		this.accno = accno;
		this.acctype = acctype;
		this.ifsccode = "DEMO0015014";
		this.balance = 10000;
		this.status = "PENDING";
	}

	public int getAccid() {
		return accid;
	}

	public void setAccid(int accid) {
		this.accid = accid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public String getAcctype() {
		return acctype;
	}

	public void setAcctype(String acctype) {
		this.acctype = acctype;
	}

	public String getIfsccode() {
		return ifsccode;
	}

	public void setIfsccode(String ifsccode) {
		this.ifsccode = ifsccode;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Account Number - " + accno + "\nAccount Type   - " + acctype + "\nIFSC Code      - " + ifsccode + "\nBalance        - " + balance + "\nStatus         - " + status;
	}

}
