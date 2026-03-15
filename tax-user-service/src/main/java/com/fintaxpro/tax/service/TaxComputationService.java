package com.fintaxpro.tax.service;

import com.fintaxpro.tax.dto.TaxComputationRequest;
import com.fintaxpro.tax.dto.TaxComputationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface TaxComputationService {

    Logger log = LoggerFactory.getLogger(TaxComputationService.class);

    TaxComputationResponse computeTax(TaxComputationRequest request);
}
