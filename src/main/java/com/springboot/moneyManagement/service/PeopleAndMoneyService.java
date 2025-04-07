package com.springboot.moneyManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springboot.moneyManagement.dto.PeopleAndMoneyDetailsDTO;
import com.springboot.moneyManagement.entity.MoneyDetails;
import com.springboot.moneyManagement.entity.PeopleDetails;
import com.springboot.moneyManagement.repository.PeopleRepository;
import com.springboot.moneyManagement.response.ApiResponse;
import com.springboot.moneyManagement.validation.MoneyDetailsValidation;

import jakarta.transaction.Transactional;

@Service
public class PeopleAndMoneyService {

	@Autowired
	private final MoneyService moneyService;

	@Autowired
	private final PeopleService peopleService;
	
	@Autowired
	private final PeopleRepository peopleRepository;

	@Autowired
	private final MoneyDetailsValidation moneyDetailsValidation;

	@Autowired
	public PeopleAndMoneyService(PeopleRepository peopleRepository, MoneyService moneyService,
			PeopleService peopleService, MoneyDetailsValidation moneyDetailsValidation,
			MoneyDetailsValidation peopleDetailsValidation) {
		this.moneyService = moneyService;
		this.peopleService = peopleService;
		this.peopleRepository = peopleRepository;
		this.moneyDetailsValidation = moneyDetailsValidation;
	}

	/**
	 * Used to save the people and money details If people already present then save
	 * money details for that people
	 * 
	 * @param dto
	 * @return
	 */
	@Transactional
	public ResponseEntity<ApiResponse<PeopleDetails>> savePeopleAndMoneyDetails(PeopleAndMoneyDetailsDTO dto) {
		PeopleDetails savedPeople;
		StringBuilder errorMessages = new StringBuilder();
		Optional<PeopleDetails> existingPeopleOpt = moneyDetailsValidation.checkPeopleAlreadyExists(dto);
		if (existingPeopleOpt.isPresent()) {
			savedPeople = existingPeopleOpt.get();
		} else {
			savedPeople = peopleService.savePeopleDetails(dto.getPeopleDetails());
		}
		if (dto.getMoneyDetails() != null && !dto.getMoneyDetails().isEmpty()) {
			for (MoneyDetails moneyDetails : dto.getMoneyDetails()) {
				moneyDetails.setPeopleDetails(savedPeople);

				StringBuilder validationMsg = moneyDetailsValidation.moneyDetailsIsValid(moneyDetails);
				if (validationMsg.isEmpty()) {
					moneyService.saveMoneyDetails(moneyDetails);
				} else {
					errorMessages.append(validationMsg).append(" ");
				}
			}
		}
		if (errorMessages.length() > 0) {
			throw new RuntimeException("Invalid money details: " + errorMessages.toString());
		}
		ApiResponse<PeopleDetails> response = new ApiResponse<>(201, "Saved successfully", savedPeople);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Update the people details and money details
	 * 
	 * @param id
	 * @param dto
	 * @return
	 */
	public ResponseEntity<ApiResponse<PeopleDetails>> updatePeopleAndMoneyDetails(Long id,
			PeopleAndMoneyDetailsDTO dto) {
		Optional<PeopleDetails> pDetailsOptional = peopleService.getPeopleDetailsById(id);

		if (pDetailsOptional.isPresent()) {
			PeopleDetails existing = pDetailsOptional.get();

			PeopleDetails updatedData = dto.getPeopleDetails();
			existing.setFirstName(updatedData.getFirstName());
			existing.setLastName(updatedData.getLastName());
			existing.setVillage(updatedData.getVillage());
			existing.setDistrict(updatedData.getDistrict());
			existing.setZipCode(updatedData.getZipCode());

			PeopleDetails updatedPeople = peopleService.savePeopleDetails(existing);

			if (dto.getMoneyDetails() != null && !dto.getMoneyDetails().isEmpty()) {
				for (MoneyDetails moneyDetails : dto.getMoneyDetails()) {
					moneyDetails.setPeopleDetails(updatedPeople);
					moneyService.updateMoneyDetails(moneyDetails.getId(), moneyDetails);
				}
			}

			return ResponseEntity
					.ok(new ApiResponse<>(200, "People and Money Details updated successfully", updatedPeople));
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponse<>(404, "PeopleDetails not found with ID: " + id, null));
	}

	/**
	 * Get People and money details based on people id
	 * 
	 * @param id
	 * @return
	 */
	public Optional<PeopleDetails> getPeopleDetailsWithMoney(Long id) {
		Optional<PeopleDetails> peopleDetails = peopleRepository.findById(id);
		if (peopleDetails.isPresent()) {

			List<MoneyDetails> moneyDetails = moneyService.getAllMoneyDetailsByPeopleId(id);
			peopleDetails.get().setMoneyDetails(moneyDetails);
		}
		return peopleDetails;
	}

}
