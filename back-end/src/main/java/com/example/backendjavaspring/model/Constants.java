package com.example.backendjavaspring.model;

import java.math.BigDecimal;

public class Constants {
    public static final String SEARCH_FIELD_PRODUCT_PRICE_FROM = "price_from";
    public static final String SEARCH_FIELD_PRODUCT_PRICE_TO = "price_to";
    public static final String SEARCH_FIELD_PRODUCT_NAME = "product_name";
    public static final String SEARCH_FIELD_PRODUCT_AMOUNT_FROM = "amount_from";
    public static final String SEARCH_FIELD_PRODUCT_TYPE = "category_id";
    public static final String SEARCH_FIELD_PRODUCT_AMOUNT_TO = "amount_to";

    public static final String SEARCH_FIELD_BILL_ID = "bill_id";
    public static final String SEARCH_FIELD_BILL_USER_EMAIL = "user_email";
    public static final String SEARCH_FIELD_BILL_USER_NAME = "user_name";
    public static final String SEARCH_FIELD_BILL_SUM_PRICE_FROM = "sum_from";
    public static final String SEARCH_FIELD_BILL_SUM_PRICE_TO = "sum_to";
    public static final String SEARCH_FIELD_DATE_GREATER = "date_from";
    public static final String SEARCH_FIELD_DATE_LESS = "date_to";
    public static final String SEARCH_FIELD_DATE_ORDER_ASC = "order_date_asc";
    public static final String SEARCH_FIELD_PRICE_ORDER_ASC = "order_price_asc";

    public static final String BILL_USER_EMAIL = "purchaserEmail";
    public static final String BILL_USER_NAME = "fullname";
    public static final String BILL_ID = "billId";
    public static final String BILL_DATE = "purchaseDate";
    public static final String BILL_SUM = "price";


    public static final BigDecimal FEE = new BigDecimal(3200);
    public static final String STATUS_PROC = "PROCESSING";
    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_SUCC = "SUCC";

    public static final String PRODUCT_PRICE = "productPrice";
    public static final String PRODUCT_AMOUNT = "productAmount";
    public static final String PRODUCT_NAME = "productName";

    public static final int ROLE_ADMIN_ID = 1;
    public static final int ROLE_USER_ID = 2;
    public static final String ANONYMOUS_USER = "anonymousUser";

    public static final String URLDOWLOADIMAGE = "https://storage.googleapis.com/demojavaspring.appspot.com/";

    public static final String EXTENSION_IMAGE = "jpg,jpeg,png,gif";
}
