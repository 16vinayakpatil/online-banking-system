package com.axis.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="GiftCard")
public class GiftCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int giftcardid;
	private String giftcardname;
	private String recipientname;
	private String recipientemail;
	private double giftcardamount;
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accid", referencedColumnName = "accid")
    public Account account;

	public GiftCard() {
		super();
	}

	public GiftCard(String giftcardname, String recipientname, String recipientemail, double giftcardamount, Account account) {
		super();
		this.giftcardname = giftcardname;
		this.recipientname = recipientname;
		this.recipientemail = recipientemail;
		this.giftcardamount = giftcardamount;
		this.account = account;
	}

	public int getGiftcardid() {
		return giftcardid;
	}

	public void setGiftcardid(int giftcardid) {
		this.giftcardid = giftcardid;
	}

	public String getGiftcardname() {
		return giftcardname;
	}

	public void setGiftcardname(String giftcardname) {
		this.giftcardname = giftcardname;
	}

	public String getRecipientname() {
		return recipientname;
	}

	public void setRecipientname(String recipientname) {
		this.recipientname = recipientname;
	}

	public String getRecipientemail() {
		return recipientemail;
	}

	public void setRecipientemail(String recipientemail) {
		this.recipientemail = recipientemail;
	}

	public double getGiftcardamount() {
		return giftcardamount;
	}

	public void setGiftcardamount(double giftcardamount) {
		this.giftcardamount = giftcardamount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "GiftCard [giftcardid=" + giftcardid + ", giftcardname=" + giftcardname + ", recipientname="
				+ recipientname + ", recipientemail=" + recipientemail + ", giftcardamount=" + giftcardamount
				+ ", account=" + account + "]";
	}
	
}
