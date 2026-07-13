package com.cts.careNexus.moduls.workflow_emr.repository;

import com.cts.careNexus.moduls.workflow_emr.entity.Referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Long> {

    List<Referral> findByConsultationID(Integer consultationID);

    List<Referral> findByStatus(String status);

    List<Referral> findByPriority(Referral.Priority priority);
}