package com.fintaxpro.tax.repository;

import com.fintaxpro.tax.entity.TaxComputation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public interface TaxComputationRepository extends JpaRepository<TaxComputation, Long> {

    Logger log = LoggerFactory.getLogger(TaxComputationRepository.class);

}
