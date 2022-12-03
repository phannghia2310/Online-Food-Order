package com.example.backendjavaspring.service;

import com.example.backendjavaspring.model.entity.BillInfo;

import java.util.List;

public interface BillInfoService {
    BillInfo createBillInfo(BillInfo billInfo);
    List<BillInfo> getAllBillInfo();
    List<BillInfo> findBillInfoByBillId(long id);
}
