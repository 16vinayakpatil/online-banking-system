package com.axis.entity;

import java.time.LocalDateTime;

public class TransactionResponse {

	private String transactionid;
	private LocalDateTime datetime;
	private double amount;
	private String description;
	private String transactiontype;
	private int accid;
	private String accno;
	
	public TransactionResponse() {
		super();
	}

	public TransactionResponse(String transactionid, LocalDateTime datetime,int accid,String accno, double amount, String description,String transactiontype) {
		super();
		this.transactionid = transactionid;
		this.datetime = datetime;
		this.accid=accid;
		this.accno=accno;
		this.amount = amount;
		this.description = description;
		this.transactiontype = transactiontype;
	}

	public String getTransactionid() {
		return transactionid;
	}

	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}
	
	

	public int getAccid() {
		return accid;
	}

	public void setAccid(int accid) {
		this.accid = accid;
	}
	
	

	public String getAccno() {
		return accno;
	}

	public void setAccno(String accno) {
		this.accno = accno;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	@Override
	public String toString() {
		return "TransactionResponse [transactionid=" + transactionid + ", datetime=" + datetime + ", amount=" + amount
				+ ", description=" + description + ", transactiontype=" + transactiontype + "]";
	}
	
}
