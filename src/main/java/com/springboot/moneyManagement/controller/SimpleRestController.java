package com.springboot.moneyManagement.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.moneyManagement.entity.MoneyDetails;
import com.springboot.moneyManagement.entity.PeopleAndMoneyDetailsDTO;
import com.springboot.moneyManagement.entity.PeopleDetails;
import com.springboot.moneyManagement.response.ApiResponse;
import com.springboot.moneyManagement.service.MoneyDetailsService;
import com.springboot.moneyManagement.service.PeopleService;

@Component
@Configuration
@RestController
@RequestMapping("api/v1")
public class SimpleRestController {

	private static final Logger logger = LoggerFactory.getLogger(SimpleRestController.class);

	@Autowired
	private final PeopleService peopleService;

	@Autowired
	private final MoneyDetailsService moneyDetailsService;

	@Autowired
	public SimpleRestController(PeopleService peopleService, MoneyDetailsService moneyDetailsService) {
		this.peopleService = peopleService;
		this.moneyDetailsService = moneyDetailsService;
	}

	@PostMapping("/peopleDetails")
	public ResponseEntity<ApiResponse<PeopleDetails>> savePeopleDetails(@RequestBody PeopleDetails peopleDetails) {
		logger.info("Received request to save people details: {}", peopleDetails);
		logger.info("PeopleDetails Object: " + peopleDetails.toString()); // Added logging
		PeopleDetails newPeoples = peopleService.savePeopleDetails(peopleDetails);
		logger.info("Saved people details: {}", newPeoples);

		ApiResponse<PeopleDetails> response = new ApiResponse<>(200, "People Details saved successfully", newPeoples);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/peopleDetailsWithMoney")
	public ResponseEntity<ApiResponse<PeopleDetails>> savePeopleAndMoneyDetails(
			@RequestBody PeopleAndMoneyDetailsDTO dto) {
		logger.info("Received request to save people and money details: {}", dto);

		PeopleDetails savedPeople = peopleService.savePeopleDetails(dto.getPeopleDetails());

		if (dto.getMoneyDetails() != null && !dto.getMoneyDetails().isEmpty()) {
			for (MoneyDetails moneyDetails : dto.getMoneyDetails()) {
				moneyDetails.setPeopleDetails(savedPeople);
				moneyDetailsService.saveMoneyDetails(moneyDetails);
			}
		}

		ApiResponse<PeopleDetails> response = new ApiResponse<>(200, "People and Money Details saved successfully",
				savedPeople);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("peopleDetails")
	public List<PeopleDetails> getAllPeopleDetails() {
		return peopleService.getAllPeopleDetails();
	}

	@GetMapping("peopleDetails/id/{id}")
	public ResponseEntity<ApiResponse<PeopleDetails>> getPeopleDetailsById(@PathVariable Long id) {
		Optional<PeopleDetails> pDetails = peopleService.getPeopleDetailsById(id);

		if (pDetails.isPresent()) {
			return ResponseEntity.ok(new ApiResponse<>(200, "Success", pDetails.get()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponse<>(404, "PeopleDetails not found with ID: " + id, null));

	}

	@GetMapping("peopleDetails/name/{firstName}")
	public ResponseEntity<ApiResponse<Object>> getPeopleDetailsByFirstName(@PathVariable String firstName) {
		Optional<PeopleDetails> pDetails = peopleService.getPeopleDetailsByfirstName(firstName);

		if (pDetails.isPresent()) {
			return ResponseEntity.ok(new ApiResponse<>(200, "Success", pDetails.get()));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponse<>(404, "PeopleDetails not found with Name: " + firstName, null));

	}
	
    @GetMapping("/peopleDetailsWithMoney/{id}")
    public ResponseEntity<ApiResponse<PeopleDetails>> getPeopleDetailsWithMoney(@PathVariable Long id) {
        Optional<PeopleDetails> pDetails = peopleService.getPeopleDetailsWithMoney(id);

        if (pDetails.isPresent()) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Success", pDetails.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, "PeopleDetails not found with ID: " + id, null));
        }
    }	

	@PutMapping("peopleDetails/{id}")
	public ResponseEntity<ApiResponse<PeopleDetails>> updatePeopleAndMoneyDetails(@PathVariable Long id,
			@RequestBody PeopleAndMoneyDetailsDTO dto) {
		Optional<PeopleDetails> pDetailsOptional = peopleService.getPeopleDetailsById(id);

		if (pDetailsOptional.isPresent()) {
			PeopleDetails existingPeopleDetails = pDetailsOptional.get();
			existingPeopleDetails.setFirstName(dto.getPeopleDetails().getFirstName());
			existingPeopleDetails.setLastName(dto.getPeopleDetails().getLastName());
			existingPeopleDetails.setVillage(dto.getPeopleDetails().getVillage());
			existingPeopleDetails.setDistrict(dto.getPeopleDetails().getDistrict());
			existingPeopleDetails.setZipCode(dto.getPeopleDetails().getZipCode());

			PeopleDetails updatedPeople = peopleService.savePeopleDetails(existingPeopleDetails);

			if (dto.getMoneyDetails() != null && !dto.getMoneyDetails().isEmpty()) {
				for (MoneyDetails moneyDetails : dto.getMoneyDetails()) {
					moneyDetails.setPeopleDetails(updatedPeople);
					moneyDetailsService.saveMoneyDetails(moneyDetails);
				}
			}

			ApiResponse<PeopleDetails> response = new ApiResponse<>(200,
					"People and Money Details updated successfully", updatedPeople);
			return ResponseEntity.ok(response);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponse<>(404, "PeopleDetails not found with ID: " + id, null));
	}

	@DeleteMapping("peopleDetails/{id}")
	public ResponseEntity<ApiResponse<PeopleDetails>> deletePeopleDetails(@PathVariable Long id) {
		peopleService.deletePeopleDetails(id);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(200, "People Details deleted successfully", null));

	}
}