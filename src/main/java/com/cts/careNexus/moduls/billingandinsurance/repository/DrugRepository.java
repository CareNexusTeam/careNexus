package com.cts.careNexus.moduls.billingandinsurance.repository;


import com.cts.careNexus.moduls.billingandinsurance.enums.DrugStatus;
import com.cts.careNexus.moduls.billingandinsurance.model.DrugInventory;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<DrugInventory,Long> {
    public List<DrugInventory> findByCategoryAndStatus(String category, DrugStatus status);

}
