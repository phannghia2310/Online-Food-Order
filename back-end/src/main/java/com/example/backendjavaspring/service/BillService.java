package com.example.backendjavaspring.service;

import com.example.backendjavaspring.model.entity.Bill;

import java.util.List;
import java.util.Map;

public interface BillService {
    Bill createBill(Bill bill);
    List<Bill> getAllBill();
    List<Bill> findBillNew();
    List<Bill> findBillSucc();
    Bill updateBill(Bill bill);
    Bill findBillById(long id);
    List<Bill> findBillByPurchaserEmail(String purchaserEmail);
    int countBillByPurchaserEmail(String purchaserEmail);
    List<Bill> findBillByFiels(Map<String, String> map);
}
