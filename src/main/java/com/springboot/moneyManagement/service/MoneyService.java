package com.springboot.moneyManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.moneyManagement.entity.MoneyDetails;
import com.springboot.moneyManagement.repository.MoneyRepository;

@Service
public class MoneyService {

	@Autowired
	private final MoneyRepository moneyRepository;

	@Autowired
	public MoneyService(MoneyRepository moneyRepository) {
		this.moneyRepository = moneyRepository;
	}

	public MoneyDetails saveMoneyDetails(MoneyDetails moneyDetails) {
		return moneyRepository.save(moneyDetails);
	}

	public List<MoneyDetails> getAllMoneyDetailsByPeopleId(Long peopleId) {
		return moneyRepository.findByPeopleDetailsId(peopleId);
	}

	public Optional<MoneyDetails> getMoneyDetailsById(Long id) {
		return moneyRepository.findById(id);
	}
	
	public List<MoneyDetails> getMoneyDetails() {
		return moneyRepository.findAll();
	}

	/**
	 * Update money details
	 * @param id
	 * @param updatedMoneyDetails
	 * @return
	 */
	public MoneyDetails updateMoneyDetails(Long id, MoneyDetails updatedMoneyDetails) {
		Optional<MoneyDetails> existingMoneyDetails = moneyRepository.findById(id);
		if (existingMoneyDetails.isPresent()) {
			MoneyDetails moneyDetails = existingMoneyDetails.get();
			moneyDetails.setAmount(updatedMoneyDetails.getAmount());
			moneyDetails.setPercentage(updatedMoneyDetails.getPercentage());
			moneyDetails.setStartDate(updatedMoneyDetails.getStartDate());
			moneyDetails.setEndDate(updatedMoneyDetails.getEndDate());
			return moneyRepository.save(moneyDetails);
		} else {
			throw new RuntimeException("Money Details Not found for update");
		}
	}

	public void deleteMoneyDetails(Long id) {
		moneyRepository.deleteById(id);
	}
}
