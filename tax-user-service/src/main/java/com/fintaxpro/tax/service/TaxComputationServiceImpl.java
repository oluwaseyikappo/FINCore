package com.fintaxpro.tax.service;

import com.fintaxpro.tax.dto.TaxComputationRequest;
import com.fintaxpro.tax.dto.TaxComputationResponse;
import com.fintaxpro.tax.entity.TaxComputation;
import com.fintaxpro.tax.repository.TaxComputationRepository;
import org.example.taxengine.TaxEngine;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class TaxComputationServiceImpl implements TaxComputationService {

    private static final Logger log = LoggerFactory.getLogger(TaxComputationServiceImpl.class);

    private final TaxComputationRepository taxComputationRepository;

    private final TaxEngine taxEngine = new TaxEngine();

    @Override
    public TaxComputationResponse computeTax(TaxComputationRequest request) {

        log.info("Starting tax computation for income={}, filingStatus={}, deduction={}, credit={}",
                request.getIncome(), request.getFilingStatus(), request.getDeduction(), request.getCredit());

        double income = request.getIncome();
        double deduction = request.getDeduction() != null ? request.getDeduction() : 0.0;
        double credit = request.getCredit() != null ? request.getCredit() : 0.0;

        double taxableIncome = income - deduction;
        if (taxableIncome < 0) taxableIncome = 0;

        log.debug("Computed taxable income={}", taxableIncome);

        double tax = taxEngine.calculateTax(taxableIncome);

        log.debug("Tax before credits={}", tax);

        tax = tax - credit;
        if (tax < 0) tax = 0;

        log.info("Final tax after applying credits={}", tax);

        TaxComputationResponse response = new TaxComputationResponse();
        response.setTaxAmount(tax);
        response.setTaxCredit(credit);
        response.setNetTax(tax);
        response.setRefundOrOwed(-tax);

        // SAVE TO DATABASE
        TaxComputation entity = new TaxComputation();
        entity.setIncome(income);
        entity.setFilingStatus(request.getFilingStatus());
        entity.setTaxAmount(tax);
        entity.setTaxCredit(credit);
        entity.setNetTax(tax);

        taxComputationRepository.save(entity);

        log.info("Tax computation saved successfully for income={}", income);

        return response;
    }
}
