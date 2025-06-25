package com.axis.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="Transaction")
public class Transaction {

	@Id
	private String transactionid;
	private LocalDateTime datetime;
	private double amount;
	private String description;
	private String transactiontype;
	

	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accid", referencedColumnName = "accid")
    public Account account;
	
	public Transaction() {
		super();
	}

	public Transaction(String transactionid, LocalDateTime datetime, double amount, String description, String transactiontype, Account account) {
		super();
		this.transactionid = transactionid;
		this.datetime = datetime;
		this.amount = amount;
		this.description = description;
		this.transactiontype = transactiontype;
		this.account = account;
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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "Transaction [transactionid=" + transactionid + ", datetime=" + datetime + ", amount=" + amount
				+ ", description=" + description + ", transactiontype=" + transactiontype + ", account=" + account
				+ "]";
	}
	
}
