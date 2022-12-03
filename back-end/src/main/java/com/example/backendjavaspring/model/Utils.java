package com.example.backendjavaspring.model;

import com.example.backendjavaspring.model.dto.BillInfoDTO;
import com.example.backendjavaspring.model.entity.Bill;
import com.example.backendjavaspring.model.entity.Product;
import com.example.backendjavaspring.model.entity.User;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String styleThTd = " style='min-width: 100px ;color: #000;" +
            "  border: 1px solid #eee;" +
            "  padding: 12px 35px;" +
            "  border-collapse: collapse;'";

    public static String styleThTdRed = " style='min-width: 100px ;color: red;" +
            "  border: 1px solid #eee;" +
            "  padding: 12px 35px;" +
            "  border-collapse: collapse;'";

    public static String styleTable = " style='margin: 25px auto;" +
            "border-collapse: collapse;" +
            "border: 1px solid #eee;" +
            "border-bottom: 2px solid #00cccc;" +
            "box-shadow: 0px 0px 20px rgba(0, 0, 0, 0.1)," +
            "0px 10px 20px rgba(0, 0, 0, 0.05)," +
            "0px 20px 20px rgba(0, 0, 0, 0.05)," +
            "0px 30px 20px rgba(0, 0, 0, 0.05);'";

    public static String th =
            "<thead style='background: #FAFAFA'>" +
                    "<tr>" +
                    "    <th"+ styleThTd +">Mã Hoá Đơn</th>" +
                    "    <th"+ styleThTd +">Mã Sản Phẩm</th>" +
                    "    <th"+ styleThTd +">Tên Sản Phẩm</th>" +
                    "    <th"+ styleThTd +">Số Lượng</th>" +
                    "    <th"+ styleThTd +">Giá Sản Phẩm</th>" +
                    "    <th"+ styleThTd +">Ngày Mua</th>" +
                    "    <th"+ styleThTd +">Tổng Giá</th>" +
                    "</tr>" +
                    "</thead>";

    public static String rowHtml(Bill bill, Product product, BillInfoDTO billInfoDTO) {
        return "<tr>" +
               "  <td" + styleThTd + ">" + bill.getBillId() + "</td>" +
               "  <td" + styleThTd + ">" + product.getProductId() + "</td>" +
               "  <td" + styleThTd + ">" + product.getProductName() + "</td>" +
               "  <td" + styleThTd + ">" + billInfoDTO.getAmount() + "</td>" +
               "  <td" + styleThTd + ">" + product.getProductPrice() + "</td>" +
               "  <td" + styleThTd + ">" + formatDate(bill.getPurchaseDate().getTime()) + "</td>" +
               "  <td" + styleThTd + ">" + billInfoDTO.getAmount() * product.getProductPrice() + "</td>" +
               "</tr>";
    }

    public static String userInfo(User user) {
        return  "<table" + styleTable + ">" +
                "<tr>" +
                "  <th" + styleThTd + ">Email</th>" +
                "  <th" + styleThTd + ">Họ Và Tên</th>" +
                "  <th" + styleThTd + ">Địa Chỉ</th>" +
                "  <th" + styleThTd + ">SDT</th>" +
                "</tr>" +
                "<tr>" +
                "  <td" + styleThTd + ">" + user.getEmailId() + "</td>" +
                "  <td" + styleThTd + ">" + user.getFullname() + "</td>" +
                "  <td" + styleThTd + ">" + user.getAddress() + "</td>" +
                "  <td" + styleThTd + ">" + user.getPhone() + "</td>" +
                "</tr>" +
                "</table>";
    }

    public static String startHtml ="<table" + styleTable +">" + "<tbody>" + th;

    public static String endHtml(BigDecimal price, BigDecimal fee){
        return  "<tr>" +
                "  <th colspan='6' rowspan='3'>PHÍ VẬN CHUYỂN<br/><br/>THÀNH TIỀN<br/><br/>TỔNG</th>" +
                "  <td" + styleThTd +">"+ fee +"</td>" +
                "</tr>" +
                "<tr>" +
                "  <td" + styleThTd +">"+ price +"</td>" +
                "</tr>" +
                "<tr>" +
                "  <td" + styleThTdRed +">"+ price.add(fee) +"</td>" +
                "</tr>" +
                "</tbody>" +
                "</table>";
    }

    public static String formatDate(long timestamp){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        return dateFormat.format(date);
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
