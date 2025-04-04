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

import com.springboot.moneyManagement.dto.MoneyDetailsDTO;
import com.springboot.moneyManagement.dto.PeopleAndMoneyDetailsDTO;
import com.springboot.moneyManagement.dto.PeopleDetailsDTO;
import com.springboot.moneyManagement.entity.MoneyDetails;
import com.springboot.moneyManagement.entity.PeopleDetails;
import com.springboot.moneyManagement.response.ApiResponse;
import com.springboot.moneyManagement.service.MoneyService;
import com.springboot.moneyManagement.service.PeopleAndMoneyService;
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
	private final MoneyService moneyService;

	@Autowired
	private final PeopleAndMoneyService peopleAndMoneyService;

	@Autowired
	public SimpleRestController(PeopleService peopleService, MoneyService moneyService,
			PeopleAndMoneyService peopleAndMoneyService) {
		this.peopleService = peopleService;
		this.moneyService = moneyService;
		this.peopleAndMoneyService = peopleAndMoneyService;
	}

	/**
	 * Save people details with money details
	 * @param dto
	 * @return
	 */
	@PostMapping("/peopleDetailsWithMoney")
	public ResponseEntity<ApiResponse<PeopleDetails>> savePeopleAndMoneyDetails(
			@RequestBody PeopleAndMoneyDetailsDTO dto) {
		logger.info("Received request to save people and money details: {}", dto);
		return peopleAndMoneyService.savePeopleAndMoneyDetails(dto);
	}
	
	/**
	 * Update people details and money details
	 * @param id
	 * @param dto
	 * @return
	 */
	@PutMapping("updatePeopleMoneyDetails/{id}")
	public ResponseEntity<ApiResponse<PeopleDetails>> updatePeopleAndMoneyDetails(@PathVariable Long id,
			@RequestBody PeopleAndMoneyDetailsDTO dto) {
		return peopleAndMoneyService.updatePeopleAndMoneyDetails(id, dto);
	}
	
	/**
	 * get- People with Money Details by using- People ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/peopleDetailsWithMoney/{id}")
	public ResponseEntity<ApiResponse<PeopleDetails>> getPeopleDetailsWithMoney(@PathVariable Long id) {
		Optional<PeopleDetails> pDetails = peopleAndMoneyService.getPeopleDetailsWithMoney(id);

		if (pDetails.isPresent()) {
			return ResponseEntity.ok(new ApiResponse<>(200, "Success", pDetails.get()));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse<>(404, "PeopleDetails not found with ID: " + id, null));
		}
	}

	/**
	 * Get all people list
	 * 
	 * @return
	 */
	@GetMapping("/peopleDetails")
	public List<PeopleDetailsDTO> getAllPeopleDetails() {
		List<PeopleDetails> peopleList = peopleService.getAllPeopleDetails();
		return peopleList.stream().map(PeopleDetailsDTO::new).toList();
	}

	/**
	 * get only people details based on people id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/peopleDetails/id/{id}")
	public ResponseEntity<ApiResponse<PeopleDetailsDTO>> getPeopleDetailsById(@PathVariable Long id) {
		Optional<PeopleDetails> pDetails = peopleService.getPeopleDetailsById(id);

		if (pDetails.isPresent()) {
			PeopleDetailsDTO responseDTO = new PeopleDetailsDTO(pDetails.get());
			return ResponseEntity.ok(new ApiResponse<>(200, "Success", responseDTO));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponse<>(404, "PeopleDetails not found with ID: " + id, null));

	}

	/**
	 * get only people details based on people name
	 * 
	 * @param firstName
	 * @return
	 */
	@GetMapping("peopleDetails/name/{firstName}")
	public ResponseEntity<ApiResponse<Object>> getPeopleDetailsByFirstName(@PathVariable String firstName) {
		Optional<PeopleDetails> pDetails = peopleService.getPeopleDetailsByfirstName(firstName);

		if (pDetails.isPresent()) {
			PeopleDetailsDTO responseDTO = new PeopleDetailsDTO(pDetails.get());
			return ResponseEntity.ok(new ApiResponse<>(200, "Success", responseDTO));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponse<>(404, "PeopleDetails not found with Name: " + firstName, null));
	}

	/**
	 * get- All Money Details
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/moneyDetails")
	public List<MoneyDetailsDTO> getMoneyDetails() {
		List<MoneyDetails> moneyDetails = moneyService.getMoneyDetails();
		return moneyDetails.stream().map(MoneyDetailsDTO::new).toList();
	}

	@DeleteMapping("peopleDetails/{id}")
	public ResponseEntity<ApiResponse<PeopleDetails>> deletePeopleDetails(@PathVariable Long id) {
		peopleService.deletePeopleDetails(id);

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse<>(200, "People Details deleted successfully", null));

	}
}