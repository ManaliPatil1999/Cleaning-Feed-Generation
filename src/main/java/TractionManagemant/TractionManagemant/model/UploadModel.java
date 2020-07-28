package TractionManagemant.TractionManagemant.model;

import javax.persistence.Column;

//import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="transaction")
public class UploadModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    
	@Column(name="transactionRef")
	@NotNull
	private String transactionRef;
	
	@Column(name="Reason")
	@NotNull
	private String reason;
	
	@Column(name="Date")
	@NotNull
	private String date;
	
	@Column(name="Payer")
	@NotNull
	private String payer;
	
	@Column(name="Payee")
	@NotNull
	private String payee;
	
	@Column(name="Amount")
	@NotNull
	private String amount;
	
	@Column(name="Status")
	@NotNull
	private String status;
	
	@Column(name="feedGenerated")
	@NotNull
	private String feedgenerated;
	
	
	public UploadModel()        //default constructor
	{
		super();
	}
	//parameterized constructor
	public UploadModel( String date, String payer, String payee, String amount, 
			String status, String transactionRef, String reason) {
		super();
		
		this.amount=amount;
		this.date= date;
		this.payee= payee;
		this.payer= payer;
		this.status= status;
		this.transactionRef=transactionRef;
		this.feedgenerated="false";
		this.reason=reason;
		
	}
	public UploadModel( String date, String payer, String payee, String amount, 
			String status, String transactionRef) {
		super();
		
		this.amount=amount;
		this.date= date;
		this.payee= payee;
		this.payer= payer;
		this.status= status;
		this.transactionRef=transactionRef;
		this.feedgenerated="false";
		
		
	}

	
	public String getFeedgenerated() {
		return feedgenerated;
	}

	public void setFeedgenerated(String feedgenerated) {
		this.feedgenerated = feedgenerated;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTransactionRef() {
		return transactionRef;
	}

	public void setTransactionRef(String transactionRef) {
		this.transactionRef = transactionRef;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String feedformat()
	{
		return ("Transaction Ref# : "+this.transactionRef+" Value Date : "+ this.date +" Payer : "+this.payer+ " Payee : "+this.payee+ " Amount : "+this.amount+" \n");
	}

}