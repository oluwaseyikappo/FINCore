package com.fintaxpro.tax.controller;

import com.fintaxpro.tax.dto.TaxComputationRequest;
import com.fintaxpro.tax.dto.TaxComputationResponse;
import com.fintaxpro.tax.entity.TaxComputation;
import com.fintaxpro.tax.service.TaxComputationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.fintaxpro.tax.repository.TaxComputationRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fintaxpro.exception.TaxRecordNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/tax")
@RequiredArgsConstructor
public class TaxComputationController {

    private static final Logger log = LoggerFactory.getLogger(TaxComputationController.class);

    private final TaxComputationService service;
    private final TaxComputationRepository taxComputationRepository;

    @Operation(
            summary = "Compute tax for a user",
            description = "Takes income, filing status, deduction, and credit to compute federal tax."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tax computed successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error")
    })
    @PostMapping("/compute")
    public TaxComputationResponse computeTax(
            @Valid @RequestBody TaxComputationRequest request) {

        log.info("Received tax computation request: income={}, filingStatus={}, deduction={}, credit={}",
                request.getIncome(), request.getFilingStatus(), request.getDeduction(), request.getCredit());

        TaxComputationResponse response = service.computeTax(request);

        log.info("Tax computation completed successfully. Final tax={}", response.getNetTax());

        return response;
    }

    @Operation(
            summary = "Get all tax computations (paginated)",
            description = "Returns a paginated list of tax computations."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    })
    @GetMapping("/all")
    public Page<TaxComputation> getAllComputations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Fetching paginated tax computations: page={}, size={}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        return taxComputationRepository.findAll(pageable);
    }

    @Operation(
            summary = "Get a single tax computation by ID",
            description = "Returns timestamps and tax details for a specific computation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Record retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    @GetMapping("/timestamps/{id}")
    public TaxComputation getTimestamps(@PathVariable Long id) {
        log.info("Fetching tax computation with ID={}", id);

        return taxComputationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Tax computation with ID={} not found", id);
                    return new TaxRecordNotFoundException("Tax record not found for ID: " + id);
                });
    }

    @Operation(
            summary = "Update a tax computation by ID",
            description = "Updates income, filing status, deduction, or credit for an existing tax computation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Record updated successfully"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    @PutMapping("/update/{id}")
    public TaxComputation updateComputation(
            @PathVariable Long id,
            @Valid @RequestBody TaxComputationRequest request) {

        log.info("Attempting to update tax computation with ID={}", id);

        TaxComputation record = taxComputationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot update. Tax computation with ID={} not found", id);
                    return new TaxRecordNotFoundException("Tax record not found for ID: " + id);
                });

        log.info("Updating fields for tax computation with ID={}", id);

        record.setIncome(request.getIncome());
        record.setFilingStatus(request.getFilingStatus());

        if (request.getCredit() != null) {
            record.setTaxCredit(request.getCredit());
        }

        TaxComputation updatedRecord = taxComputationRepository.save(record);

        log.info("Tax computation with ID={} updated successfully", id);

        return updatedRecord;
    }

    @Operation(
            summary = "Delete a tax computation by ID",
            description = "Deletes a specific tax computation record from the database."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Record deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Record not found")
    })
    @DeleteMapping("/delete/{id}")
    public String deleteComputation(@PathVariable Long id) {
        log.info("Attempting to delete tax computation with ID={}", id);

        TaxComputation record = taxComputationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot delete. Tax computation with ID={} not found", id);
                    return new TaxRecordNotFoundException("Tax record not found for ID: " + id);
                });

        taxComputationRepository.delete(record);

        log.info("Tax computation with ID={} deleted successfully", id);

        return "Tax computation with ID " + id + " deleted successfully.";
    }
}
