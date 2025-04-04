package com.springboot.moneyManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.moneyManagement.entity.MoneyDetails;

@Repository
public interface MoneyRepository extends JpaRepository<MoneyDetails, Long> {

	List<MoneyDetails> findByPeopleDetailsId(Long peopleDetailsId);
}
