package com.example.backendjavaspring.controller.implement;

import com.example.backendjavaspring.authority.MyUserDetail;
import com.example.backendjavaspring.controller.BillController;
import com.example.backendjavaspring.model.Utils;
import com.example.backendjavaspring.model.dto.*;
import com.example.backendjavaspring.model.entity.Bill;
import com.example.backendjavaspring.model.entity.BillInfo;
import com.example.backendjavaspring.model.entity.Product;
import com.example.backendjavaspring.model.entity.User;
import com.example.backendjavaspring.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.backendjavaspring.model.Constants.*;
import static com.example.backendjavaspring.model.Utils.*;

@PropertySource(value = "classpath:content.properties", encoding = "UTF-8")
@RestController
@RequestMapping("/api/bills")
public class BillControllerImp implements BillController {
    private final BillService billService;
    private final UserService userService;
    private final ProductService productService;
    private final BillInfoService billInfoService;
    private final MailService mailService;

    @Value("${contentHeaderMail}")
    String contentHeaderMail;
    @Value("${contentFooterrMail}")
    String contentFooterrMail;
    @Value("${contentHeaderAdmin}")
    String contentHeaderAdmin;
    @Value("${adminMail}")
    String adminMail;

    @Autowired
    public BillControllerImp(BillService billService, UserService userService, ProductService productService, BillInfoService billInfoService, MailService mailService) {
        this.billService = billService;
        this.userService = userService;
        this.productService = productService;
        this.billInfoService = billInfoService;
        this.mailService = mailService;
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN','ADMIN')")
    public ResponseEntity<?> getAllBill() {
        List<Bill> bills = billService.getAllBill();
        List<BillDTO> results = new ArrayList<>();
        for (Bill bill : bills) {
            User user = userService.findUserById(bill.getPurchaserEmail());
            if(user != null){
                String dateString = Utils.formatDate(bill.getPurchaseDate().getTime());
                results.add(new BillDTO(bill.getBillId(), bill.getPurchaserEmail(), user.getFullname(), dateString, bill.getPrice(), bill.getFeeShip(), bill.getStatus()));
            }else {
                results.add(new BillDTO());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "THÀNH CÔNG!", results));
    }

    @Override
    public ResponseEntity<?> getAllBillNew() {
        List<Bill> bills = billService.findBillNew();
        List<BillDTO> results = new ArrayList<>();
        for (Bill bill : bills) {
            User user = userService.findUserById(bill.getPurchaserEmail());
            String dateString = Utils.formatDate(bill.getPurchaseDate().getTime());
            results.add(new BillDTO(bill.getBillId(), bill.getPurchaserEmail(), user.getFullname(), dateString, bill.getPrice(), bill.getFeeShip(), bill.getStatus()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "THÀNH CÔNG!", results));
    }

    @Override
    public ResponseEntity<?> getAllBillSucc() {
        List<Bill> bills = billService.findBillSucc();
        List<BillDTO> results = new ArrayList<>();
        for (Bill bill : bills) {
            User user = userService.findUserById(bill.getPurchaserEmail());
            String dateString = Utils.formatDate(bill.getPurchaseDate().getTime());
            results.add(new BillDTO(bill.getBillId(), bill.getPurchaserEmail(), user.getFullname(), dateString, bill.getPrice(), bill.getFeeShip(), bill.getStatus()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "THÀNH CÔNG!", results));
    }


    @Override
    public ResponseEntity<?> findBillById(long id) {
        List<BillInfo> billInfos = billInfoService.findBillInfoByBillId(id);
        List<BillDetailDTO> result = new ArrayList<>();
        if (billInfos.size() > 0) {
            Bill bill = billInfos.get(0).getBill();
            User user = userService.findUserById(bill.getPurchaserEmail());
            int purchaseInvoice = billService.countBillByPurchaserEmail(bill.getPurchaserEmail());
            int amountPurchased = 0;
            List<ProductResponseDTO> productResponses = new ArrayList<>();
            for (BillInfo billInfo : billInfos) {
                amountPurchased += billInfo.getAmount();
                productResponses.add(new ProductResponseDTO(billInfo.getProduct(), billInfo.getAmount()));
            }
            BillDetailDTO billDetailDTO = new BillDetailDTO();
            billDetailDTO.setId(id);
            billDetailDTO.setTotalAmount(amountPurchased);
            billDetailDTO.setProductResponse(productResponses);
            billDetailDTO.setTotalPrice(bill.getPrice());
            billDetailDTO.setStatus(bill.getStatus());
            billDetailDTO.setFeeShip(bill.getFeeShip());
            String dateString = Utils.formatDate(bill.getPurchaseDate().getTime());
            billDetailDTO.setPurchaseDate(dateString);
            billDetailDTO.setUserResponse(new UserResponseDTO(user.getEmailId(), user.getPhone(), user.getFullname(), user.getAddress(), user.getRole().getRoleName(), purchaseInvoice, user.getRegistraionDate().toString()));
            result.add(billDetailDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "THÀNH CÔNG!", result));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404, "KHÔNG TÌM THẤY HOÁ ĐƠN!", result));
    }

    @Override
    public ResponseEntity<?> updateBillStatus(long id) {
        Bill bill = billService.findBillById(id);
        List<Bill> result = new ArrayList<>();
        if(bill == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404, "KHÔNG TÌM THẤY HOÁ ĐƠN!", new ArrayList<>()));
        }
        if(bill.getStatus().equals(STATUS_SUCC)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404, "TRẠNG THÁI ĐƠN HÀNG ĐÃ ĐƯỢC GIAO HÀNG!", new ArrayList<>()));
        }
        if(bill.getStatus().equals(STATUS_NEW)){
            bill.setStatus(STATUS_SUCC);
            Bill billResult = billService.updateBill(bill);
            result.add(billResult);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "CẬP NHẬT THÀNH CÔNG!", result));
        }
        bill.setStatus(STATUS_NEW);
        Bill billResult = billService.updateBill(bill);
        result.add(billResult);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "CẬP NHẬT THÀNH CÔNG!", result));
    }

    @Override
    public ResponseEntity<?> findBillByFiels(Map<String, String> map) {
        List<Bill> bills = billService.findBillByFiels(map);
        List<BillDTO> result = new ArrayList<>();
        for(Bill bill : bills){
            User user = userService.findUserById(bill.getPurchaserEmail());
            if(user != null){
                BillDTO billDTO = new BillDTO();
                billDTO.setBillId(bill.getBillId());
                billDTO.setPurchaserEmail(bill.getPurchaserEmail());
                billDTO.setStatus(bill.getStatus());
                billDTO.setPrice(bill.getPrice());
                billDTO.setFeeShip(bill.getFeeShip());
                String dateString = Utils.formatDate(bill.getPurchaseDate().getTime());
                billDTO.setPurchaseDate(dateString);
                billDTO.setPurchaserName(user.getFullname());
                result.add(billDTO);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "CẬP NHẬT THÀNH CÔNG!", result));
    }

    @Override
    public ResponseEntity<?> addBill(List<BillInfoDTO> itemInfos, Authentication authentication) {
        MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
        String email = myUserDetail.getUser().getEmailId();
        User user = userService.findUserById(email);
        List<Bill> billResult = new ArrayList<>();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404, "KHÔNG TÌM THẤY NGƯỜI DÙNG!", null));
        } else {
            Bill bill = new Bill(user.getEmailId());
            BigDecimal price = BigDecimal.ZERO;
            BigDecimal fee = BigDecimal.ZERO;
            bill.setPrice(price);
            bill.setFeeShip(fee);
            bill.setStatus(STATUS_PROC);

            Bill result = billService.createBill(bill);
            billResult.add(result);

            StringBuilder billInfoStr = new StringBuilder(startHtml);
            for (BillInfoDTO billInfoDTO : itemInfos) {
                Product product = productService.findProductById(billInfoDTO.getProductId());
                if (product != null) {
                    BillInfo billInfo = new BillInfo();
                    billInfo.setBill(bill);
                    billInfo.setProduct(product);
                    billInfo.setAmount(billInfoDTO.getAmount());
                    price = price.add(BigDecimal.valueOf((billInfoDTO.getAmount() * product.getProductPrice())));
                    billInfoService.createBillInfo(billInfo);
                    product.setProductAmount(product.getProductAmount() - billInfoDTO.getAmount());
                    product.setAmountSold(product.getAmountSold() + billInfoDTO.getAmount());
                    productService.updateProduct(product);
                    billInfoStr.append(rowHtml(bill, product, billInfoDTO));
                }
            }
            result.setPrice(price);
            result.setFeeShip(fee);
            Bill finalBill = billService.updateBill(result);
            if (finalBill != null) {
                String userInfo = userInfo(user);
                String end = endHtml(price, fee);
                String content = "<h5>" + contentHeaderMail + "</h5>" + userInfo + billInfoStr + end +  "<h5>" + contentFooterrMail + "</h5>";
                String contentAdmin = "<h5>" + contentHeaderAdmin + "</h5>" + userInfo + billInfoStr + end;

                mailService.sendEmail(email, content);
                mailService.sendEmail(adminMail, contentAdmin);
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "ĐẶT HÀNG THÀNH CÔNG THÀNH CÔNG!", billResult));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseDTO(404, "ĐẶT HÀNG THẤT BẠI!", null));
        }
    }


}
