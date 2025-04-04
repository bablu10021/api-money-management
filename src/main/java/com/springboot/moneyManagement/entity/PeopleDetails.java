package com.springboot.moneyManagement.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "PEOPLE_DETAILS")
public class PeopleDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "village", nullable = false)
	private String village;
	
	@Column(name = "district", nullable = false)
	private String district;
	
	@Column(name = "zip_code")
	private String zipCode;
	
	@OneToMany(mappedBy = "peopleDetails", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
    private List<MoneyDetails> moneyDetails;
	
    public List<MoneyDetails> getMoneyDetails() {
		return moneyDetails;
	}

	public void setMoneyDetails(List<MoneyDetails> moneyDetails) {
		this.moneyDetails = moneyDetails;
	}

	public PeopleDetails() {
    }

    public PeopleDetails(String firstName, String lastName, String village, String district, String zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.village = village;
        this.district = district;
        this.zipCode = zipCode;
    }

	public Long getId() {
		return id;
	}

	public void setId(long id) {
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

	@Override
	public String toString() {
	    return "PeopleDetails{" +
	           "id=" + id +
	           ", firstName='" + firstName + '\'' +
	           ", lastName='" + lastName + '\'' +
	           ", village='" + village + '\'' +
	           ", district='" + district + '\'' +
	           ", zipCode='" + zipCode + '\'' +
	           '}';
	}
	
	
}