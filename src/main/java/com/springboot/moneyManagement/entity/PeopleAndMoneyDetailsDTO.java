package com.springboot.moneyManagement.entity;

import java.util.List;

public class PeopleAndMoneyDetailsDTO {

    private PeopleDetails peopleDetails;
    private List<MoneyDetails> moneyDetails;

    // Getters and setters
    public PeopleDetails getPeopleDetails() {
        return peopleDetails;
    }

    public void setPeopleDetails(PeopleDetails peopleDetails) {
        this.peopleDetails = peopleDetails;
    }

    public List<MoneyDetails> getMoneyDetails() {
        return moneyDetails;
    }

    public void setMoneyDetails(List<MoneyDetails> moneyDetails) {
        this.moneyDetails = moneyDetails;
    }
}
