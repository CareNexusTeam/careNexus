package com.cts.careNexus.billingandinsurance.repository;

import com.cts.careNexus.billingandinsurance.entities.InsuranceClaim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceClaimRepository extends JpaRepository<InsuranceClaim,Long> {

}
