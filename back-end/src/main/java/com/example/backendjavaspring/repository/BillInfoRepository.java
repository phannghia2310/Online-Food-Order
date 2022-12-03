package com.example.backendjavaspring.repository;

import com.example.backendjavaspring.model.entity.BillInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillInfoRepository extends JpaRepository<BillInfo, Long> {
    List<BillInfo> findBillInfoByBillBillId(long id);
}
