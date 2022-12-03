package com.example.backendjavaspring.controller.implement;

import com.example.backendjavaspring.authority.MyUserDetail;
import com.example.backendjavaspring.controller.UserController;
import com.example.backendjavaspring.model.Utils;
import com.example.backendjavaspring.model.dto.*;
import com.example.backendjavaspring.model.entity.Bill;
import com.example.backendjavaspring.model.entity.Role;
import com.example.backendjavaspring.model.entity.User;
import com.example.backendjavaspring.service.BillService;
import com.example.backendjavaspring.service.RoleService;
import com.example.backendjavaspring.service.UserService;
import com.example.backendjavaspring.service.implement.RoleServiceImp;
import com.example.backendjavaspring.service.implement.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.backendjavaspring.model.Constants.*;

@RestController
@RequestMapping("/api/users")
public class UserControllerImp implements UserController {
    private final Logger logger = LoggerFactory.getLogger(UserControllerImp.class);
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final BillService billService;



    @Autowired
    public UserControllerImp(RoleServiceImp roleService, UserServiceImp userService, PasswordEncoder encoder, BillService billService) {
        this.roleService = roleService;
        this.userService = userService;
        this.encoder = encoder;
        this.billService = billService;
    }



    @Override
    public ResponseEntity<?> addUser(UserRequestDTO userRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (userRequest.getPassword() == null || userRequest.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TẠO TÀI KHOẢN THẤT BẠI MẬT KHẨU KHÔNG ĐƯỢC TRỐNG!");
            }

            if(!Utils.validateEmail(userRequest.getEmail())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TẠO TÀI KHOẢN THẤT BẠI MẬT KHẨU KHÔNG ĐƯỢC TRỐNG!");
            }

            if (userService.isUserEmailExist(userRequest.getEmail())) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TẠO TÀI KHOẢN THẤT BẠI ĐỊA CHỈ EMAIL ĐÃ TỒN TẠI TRÊN HỆ THỐNG!");
            }

            if (authentication.isAuthenticated() && !authentication.getName().equals(ANONYMOUS_USER)) {
                MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
                User userFromToken = myUserDetail.getUser();
                if (userFromToken.getRole().getRoleId() == ROLE_ADMIN_ID) {
                    Role role = roleService.findRoleById(ROLE_ADMIN_ID);
                    User user = new User(userRequest.getEmail(), userRequest.getFullName(), userRequest.getPassword(), userRequest.getAddress(), userRequest.getPhone(), role);
                    userService.createUser(user);
                } else {
                    Role role = roleService.findRoleById(ROLE_USER_ID);
                    User user = new User(userRequest.getEmail(), userRequest.getFullName(), userRequest.getPassword(), userRequest.getAddress(), userRequest.getPhone(), role);
                    userService.createUser(user);
                }

            } else {
                Role role = roleService.findRoleById(ROLE_USER_ID);
                User user = new User(userRequest.getEmail(), userRequest.getFullName(), userRequest.getPassword(), userRequest.getAddress(), userRequest.getPhone(), role);
                userService.createUser(user);
            }

        } catch (Exception e) {
            logger.error("User Controller error: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TẠO TÀI KHOẢN THẤT BẠI!");
        }

        return ResponseEntity.status(HttpStatus.OK).body("TẠO TÀI KHOẢN THÀNH CÔNG!");
    }


    @Override
    public ResponseEntity<?> getUser(Authentication authentication) {
        try {
            if (authentication.isAuthenticated()) {
                MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
                User userFromToken = myUserDetail.getUser();

                UserResponseDTO userResponse = new UserResponseDTO();
                userResponse.setEmail(userFromToken.getEmailId());
                userResponse.setAddress(userFromToken.getAddress());
                userResponse.setFullName(userFromToken.getFullname());
                userResponse.setPhone(userFromToken.getPhone());
                userResponse.setRole(userFromToken.getRole().getRoleName());
                return ResponseEntity.status(HttpStatus.OK).body(userResponse);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NGƯỜI DÙNG CHƯA ĐƯỢC XÁC THỰC HOẶC HẾT HẠN!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NGƯỜI DÙNG CHƯA ĐƯỢC XÁC THỰC HOẶC HẾT HẠN!");
        }
    }

    @Override
    public ResponseEntity<?> getAllBillByUser(Authentication authentication) {
        List<BillDTO> result = new ArrayList<>();
        if (authentication.isAuthenticated()) {
            MyUserDetail myUserDetail = (MyUserDetail) authentication.getPrincipal();
            User userFromToken = myUserDetail.getUser();
            List<Bill> bills = billService.findBillByPurchaserEmail(userFromToken.getEmailId());
            for(Bill bill : bills){
                BillDTO billDTO = new BillDTO();
                billDTO.setBillId(bill.getBillId());
                billDTO.setPrice(bill.getPrice());
                billDTO.setPurchaserEmail(bill.getPurchaserEmail());
                billDTO.setPurchaserName(userFromToken.getFullname());
                billDTO.setStatus(bill.getStatus());
                billDTO.setFeeShip(bill.getFeeShip());
                String dateString = Utils.formatDate(bill.getPurchaseDate().getTime());
                billDTO.setPurchaseDate(dateString);
                result.add(billDTO);
            }
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NGƯỜI DÙNG CHƯA ĐƯỢC XÁC THỰC HOẶC HẾT HẠN!");
        }
    }

    @Override
    @PreAuthorize("hasPermission('ADMIN','ADMIN')")
    public ResponseEntity<?> getAllUser() {
        List<User> users = userService.getAllUserCustomer();
        List<UserResponseDTO> result = new ArrayList<>();
        for (User user : users) {
            int purchaseInvoice = billService.countBillByPurchaserEmail(user.getEmailId());
            UserResponseDTO userResponse = new UserResponseDTO();
            userResponse.setEmail(user.getEmailId());
            userResponse.setAddress(user.getAddress());
            userResponse.setFullName(user.getFullname());
            userResponse.setPhone(user.getPhone());
            userResponse.setRole(user.getRole().getRoleName());
            userResponse.setPurchaseInvoice(purchaseInvoice);
            result.add(userResponse);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(200, "THÀNH CÔNG!", result));
    }

    @Override
    public ResponseEntity<?> updateUser(UserRequestDTO userRequest) {
        try {
            User user = userService.findUserById(userRequest.getEmail());

            if (user != null) {
                boolean check = encoder.matches(userRequest.getPassword(), user.getPassword());
                if (!check) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MẬT KHẨU CŨ KHÔNG ĐÚNG!");
                }
                user.setPhone(userRequest.getPhone());
                user.setAddress(userRequest.getAddress());
                user.setFullname(userRequest.getFullName());
                userService.updateUser(user);
                return ResponseEntity.status(HttpStatus.OK).body("CẬP NHẬT THÀNH CÔNG!");
            }
        } catch (Exception e) {
            logger.error("ERROR UPDATE USER: ", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CẬP NHẬT THẤT BẠI!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("CẬP NHẬT THẤT BẠI!");
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordDTO changePasswordDTO) {
        User user = userService.findUserById(changePasswordDTO.getEmail());
        if (user != null) {
            boolean check = encoder.matches(changePasswordDTO.getOldPassword(), user.getPassword());
            if (!check) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MẬT KHẨU CŨ KHÔNG ĐÚNG!");
            }
            user.setPassword(encoder.encode(changePasswordDTO.getNewPassword()));
            userService.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("ĐỔI MẬT KHẨU THÀNH CÔNG!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EMAIL NGƯỜI DÙNG KHÔNG TỒN TẠI!");
        }
    }
}
