package com.cts.careNexus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Referral;

public interface ReferralRepository
        extends JpaRepository<Referral, Long>{
}
