package com.springboot.moneyManagement.repository;
import com.springboot.moneyManagement.entity.PeopleDetails;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<PeopleDetails, Long>{

	Optional<PeopleDetails> findByFirstName(String firstName);
}
