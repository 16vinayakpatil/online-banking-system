package com.axis.entity;

public class GiftCardResponse {

	private String giftcardname;
	private String recipientname;
	private String recipientemail;
	private double giftcardamount;

	public GiftCardResponse() {
		super();
	}

	public GiftCardResponse(String giftcardname, String recipientname, String recipientemail, double giftcardamount) {
		super();
		this.giftcardname = giftcardname;
		this.recipientname = recipientname;
		this.recipientemail = recipientemail;
		this.giftcardamount = giftcardamount;
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
	
}
