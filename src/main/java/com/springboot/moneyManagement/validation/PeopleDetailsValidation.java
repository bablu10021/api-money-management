package com.springboot.moneyManagement.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.moneyManagement.repository.PeopleRepository;

@Component
public class PeopleDetailsValidation {

	@Autowired
	private final PeopleRepository peopleRepository;

	@Autowired
	public PeopleDetailsValidation(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

}
