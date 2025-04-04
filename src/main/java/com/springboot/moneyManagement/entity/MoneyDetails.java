package com.springboot.moneyManagement.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "MONEY_DETAILS")
public class MoneyDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "people_details_id", nullable = false)
	@JsonBackReference
	private PeopleDetails peopleDetails;

	@Column(name = "amount", nullable = false)
	private double amount;

	@Column(name = "percentage")
	private double percentage;

	@Column(name = "start_Date", nullable = false)
	private Date startDate;

	@Column(name = "end_Date")
	private Date endDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PeopleDetails getPeopleDetails() {
		return peopleDetails;
	}

	public void setPeopleDetails(PeopleDetails peopleDetails) {
		this.peopleDetails = peopleDetails;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "MoneyDetails [id=" + id + ", peopleDetails=" + peopleDetails + ", amount=" + amount + ", percentage="
				+ percentage + ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}

}
