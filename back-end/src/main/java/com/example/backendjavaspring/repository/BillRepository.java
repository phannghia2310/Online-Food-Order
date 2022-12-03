package com.example.backendjavaspring.repository;

import com.example.backendjavaspring.model.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findBillByPurchaserEmailEquals(String purchaserEmail);
    int countBillByPurchaserEmailEquals(String purchaserEmail);
    List<Bill> findBillByStatus(String status);
}
