package com.example.backendjavaspring.service.implement;

import com.example.backendjavaspring.model.entity.BillInfo;
import com.example.backendjavaspring.repository.BillInfoRepository;
import com.example.backendjavaspring.service.BillInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillInfoServiceImp implements BillInfoService {
    private final BillInfoRepository billInfoRepository;

    @Autowired
    public BillInfoServiceImp(BillInfoRepository billInfoRepository) {
        this.billInfoRepository = billInfoRepository;
    }

    @Override
    public BillInfo createBillInfo(BillInfo billInfo) {
        return billInfoRepository.save(billInfo);
    }

    @Override
    public List<BillInfo> getAllBillInfo() {
        return billInfoRepository.findAll();
    }

    @Override
    public List<BillInfo> findBillInfoByBillId(long id) {
        return billInfoRepository.findBillInfoByBillBillId(id);
    }


}
