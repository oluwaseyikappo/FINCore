package com.fintaxpro.tax.entity;

import com.fintaxpro.tax.enums.FilingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "tax_computations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxComputation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double income;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private FilingStatus filingStatus;

    private Double taxAmount;

    private Double taxCredit;

    private Double netTax;
}
