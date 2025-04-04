package com.springboot.moneyManagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.moneyManagement.entity.MoneyDetails;
import com.springboot.moneyManagement.entity.PeopleDetails;
import com.springboot.moneyManagement.repository.PeopleRepository;

@Service
public class PeopleService {

	@Autowired
	private final PeopleRepository peopleRepository;

	@Autowired
	private final MoneyDetailsService moneyDetailsService;

	@Autowired
	public PeopleService(PeopleRepository peopleRepository, MoneyDetailsService moneyDetailsService) {
		this.peopleRepository = peopleRepository;
		this.moneyDetailsService = moneyDetailsService;
	}

	public PeopleDetails savePeopleDetails(PeopleDetails peopleDetails) {
		if (peopleDetails.getMoneyDetails() != null) {
			for (MoneyDetails moneyDetails : peopleDetails.getMoneyDetails()) {
				moneyDetails.setPeopleDetails(peopleDetails);
				moneyDetailsService.saveMoneyDetails(moneyDetails);
			}
		}
		return peopleRepository.save(peopleDetails);
	}

	public List<PeopleDetails> getAllPeopleDetails() {
		return peopleRepository.findAll();
	}

	public Optional<PeopleDetails> getPeopleDetailsById(Long id) {
		return peopleRepository.findById(id);
	}

	public Optional<PeopleDetails> getPeopleDetailsByfirstName(String firstName) {
		return peopleRepository.findByFirstName(firstName);
	}

	public PeopleDetails updatePeopleDetails(Long id, PeopleDetails updatedDetails) {

		Optional<PeopleDetails> pDetails = peopleRepository.findById(id);
		if (pDetails.isPresent()) {
			PeopleDetails peopleDetails = pDetails.get();
			peopleDetails.setFirstName(updatedDetails.getFirstName());
			peopleDetails.setLastName(updatedDetails.getLastName());
			peopleDetails.setVillage(updatedDetails.getVillage());
			peopleDetails.setDistrict(updatedDetails.getDistrict());
			peopleDetails.setZipCode(updatedDetails.getZipCode());

			if (updatedDetails.getMoneyDetails() != null) {
				for (MoneyDetails moneyDetails : updatedDetails.getMoneyDetails()) {
					moneyDetails.setPeopleDetails(peopleDetails);
					moneyDetailsService.updateMoneyDetails(moneyDetails.getId(), moneyDetails);
				}
			}
			return peopleRepository.save(peopleDetails);
		} else {
			throw new RuntimeException("People Details Not found for update");
		}
	}

	public void deletePeopleDetails(Long id) {
		Optional<PeopleDetails> pDetails = peopleRepository.findById(id);
		if (pDetails.isPresent()) {
			PeopleDetails peopleDetails = pDetails.get();
			
			if (peopleDetails.getMoneyDetails() != null) {
				for (MoneyDetails moneyDetails : peopleDetails.getMoneyDetails()) {
					moneyDetailsService.deleteMoneyDetails(moneyDetails.getId());
				}
			}
			peopleRepository.deleteById(id);
		} else {
			throw new RuntimeException("People Details Not found for deletion");
		}
	}
	
    public Optional<PeopleDetails> getPeopleDetailsWithMoney(Long id) {
        Optional<PeopleDetails> peopleDetails = peopleRepository.findById(id);
        if (peopleDetails.isPresent()) {
           
            List<MoneyDetails> moneyDetails = moneyDetailsService.getAllMoneyDetailsByPeopleId(id);
            peopleDetails.get().setMoneyDetails(moneyDetails);
        }
        return peopleDetails;
    }
    
    

}
