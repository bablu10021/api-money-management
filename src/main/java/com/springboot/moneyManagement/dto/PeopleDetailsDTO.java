package com.springboot.moneyManagement.dto;

import com.springboot.moneyManagement.entity.PeopleDetails;

public class PeopleDetailsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String village;
    private String district;
    private String zipCode;

    public PeopleDetailsDTO(PeopleDetails peopleDetails) {
        this.id = peopleDetails.getId();
        this.firstName = peopleDetails.getFirstName();
        this.lastName = peopleDetails.getLastName();
        this.village = peopleDetails.getVillage();
        this.district = peopleDetails.getDistrict();
        this.zipCode = peopleDetails.getZipCode();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
    
    
}