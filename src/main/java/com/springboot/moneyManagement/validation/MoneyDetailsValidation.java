package com.springboot.moneyManagement.validation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springboot.moneyManagement.dto.PeopleAndMoneyDetailsDTO;
import com.springboot.moneyManagement.entity.MoneyDetails;
import com.springboot.moneyManagement.entity.PeopleDetails;
import com.springboot.moneyManagement.repository.PeopleRepository;

@Component
public class MoneyDetailsValidation {

	@Autowired
	private final PeopleRepository peopleRepository;

	@Autowired
	public MoneyDetailsValidation(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

	/**
	 * Check people details is already exists
	 * 
	 * @param dto
	 * @return
	 */
	public Optional<PeopleDetails> checkPeopleAlreadyExists(PeopleAndMoneyDetailsDTO dto) {

		Optional<PeopleDetails> existingPeople = peopleRepository
				.findByFirstNameAndLastNameAndVillageAndDistrictAndZipCode(dto.getPeopleDetails().getFirstName(),
						dto.getPeopleDetails().getLastName(), dto.getPeopleDetails().getVillage(),
						dto.getPeopleDetails().getDistrict(), dto.getPeopleDetails().getZipCode());
		return existingPeople;
	}

	/**
	 * validate money details values
	 * 
	 * @param moneyDetails
	 * @return
	 */
	public StringBuilder moneyDetailsIsValid(MoneyDetails moneyDetails) {
		StringBuilder message = new StringBuilder();
		if (moneyDetails.getStartDate() == null) {
			message = message.append("Start Date can not be empty or null for money details.");
		}
		if (moneyDetails.getAmount() == 0) {
			message.append("Amount can not be empty or null, please give valid amount.");
		}
		if (moneyDetails.getEndDate() != null && moneyDetails.getStartDate() != null) {
			if (moneyDetails.getEndDate().before(moneyDetails.getStartDate())) {
				message.append("End date can be before start date.");
			}
		}
		return message;
	}

}
