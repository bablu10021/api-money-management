package com.springboot.moneyManagement.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.moneyManagement.entity.MoneyDetails;

public class MoneyDetailsDTO {
	private long id;
	private Long people_id;
	private double amount;
	private double percentage;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date startDate;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date endDate;

	public MoneyDetailsDTO(MoneyDetails moneyDetails) {
		super();
		this.id = moneyDetails.getId();
		this.people_id = moneyDetails.getPeopleDetails().getId();
		this.amount = moneyDetails.getAmount();
		this.percentage = moneyDetails.getPercentage();
		this.startDate = moneyDetails.getStartDate();
		this.endDate = moneyDetails.getEndDate();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getPeople_id() {
		return people_id;
	}

	public void setPeople_id(Long people_id) {
		this.people_id = people_id;
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
}
