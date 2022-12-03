package com.example.backendjavaspring.controller;

import com.example.backendjavaspring.model.dto.BillInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface BillController {
    @GetMapping("/get_all_bill")
    ResponseEntity<?> getAllBill();

    @GetMapping("/get_bill_process")
    ResponseEntity<?> getAllBillNew();

    @GetMapping("/get_bill_success")
    ResponseEntity<?> getAllBillSucc();

    @PostMapping(value = "/add_bill")
    ResponseEntity<?> addBill(@Valid @RequestBody List<BillInfoDTO> itemInfos, Authentication authentication);

    @GetMapping("/get_bill/{id}")
    ResponseEntity<?> findBillById(@PathVariable long id);

    @PutMapping(value = "/update_bill_status/{id}")
    ResponseEntity<?> updateBillStatus(@PathVariable long id);

    @GetMapping("/search_bill")
    ResponseEntity<?> findBillByFiels(@RequestParam Map<String, String> map);
}
