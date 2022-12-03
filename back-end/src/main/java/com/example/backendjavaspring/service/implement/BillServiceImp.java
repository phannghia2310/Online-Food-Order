package com.example.backendjavaspring.service.implement;

import com.example.backendjavaspring.model.entity.Bill;
import com.example.backendjavaspring.repository.BillRepository;
import com.example.backendjavaspring.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.backendjavaspring.model.Constants.*;

@Service
public class BillServiceImp implements BillService {
    private final BillRepository billRepository;
    private final EntityManager entityManager;

    private boolean orderDateDesc = true;
    private boolean orderPriceDesc = true;

    @Autowired
    public BillServiceImp(BillRepository billRepository, EntityManager entityManager) {
        this.billRepository = billRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Bill createBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public List<Bill> getAllBill() {
        return billRepository.findAll();
    }

    @Override
    public List<Bill> findBillNew() {
        return billRepository.findBillByStatus(STATUS_NEW);
    }

    @Override
    public List<Bill> findBillSucc() {
        return billRepository.findBillByStatus(STATUS_SUCC);
    }

    @Override
    public Bill updateBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Override
    public Bill findBillById(long id) {
        return billRepository.findById(id).orElse(null);
    }

    @Override
    public List<Bill> findBillByPurchaserEmail(String purchaserEmail) {
        return billRepository.findBillByPurchaserEmailEquals(purchaserEmail);
    }
    @Override
    public int countBillByPurchaserEmail(String purchaserEmail) {
        return billRepository.countBillByPurchaserEmailEquals(purchaserEmail);
    }

    @Override
    public List<Bill> findBillByFiels(Map<String, String> map) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Bill> criteriaQuery = criteriaBuilder.createQuery(Bill.class);
        Root<Bill> root = criteriaQuery.from(Bill.class);
        List<Order> orders = new ArrayList<>();
        Predicate[] arrPredicate = addQueryCondition(criteriaBuilder, root, map, orders);
        criteriaQuery.select(root).where(arrPredicate).orderBy(orders);
        TypedQuery<Bill> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    private Predicate[] addQueryCondition(CriteriaBuilder criteriaBuilder, Root<Bill> root, Map<String, String> map, List<Order> orders) {
        map.values().removeAll(Collections.singleton(null));
        map.values().removeAll(Collections.singleton(""));
        List<Predicate> predicates = new ArrayList<>();
        for (String key : map.keySet()) {
            switch (key) {
                case SEARCH_FIELD_BILL_ID:
                    predicates.add(criteriaBuilder.equal(root.get(BILL_ID), Long.parseLong(map.get(key).trim())));
                    break;
                case SEARCH_FIELD_BILL_USER_EMAIL:
                    predicates.add(criteriaBuilder.equal(root.get(BILL_USER_EMAIL), map.get(key).trim()));
                    break;
                case SEARCH_FIELD_PRICE_ORDER_ASC:
                    orderPriceDesc = !orderPriceDesc;
                    addOrder(criteriaBuilder, root, orders, BILL_SUM, orderPriceDesc);
                    break;
                case SEARCH_FIELD_DATE_ORDER_ASC:
                    orderDateDesc = !orderDateDesc;
                    addOrder(criteriaBuilder, root, orders, BILL_DATE, orderDateDesc);
                    break;
            }
        }
        if(orders.isEmpty()){
            orders.add(criteriaBuilder.desc(root.get(BILL_DATE)));
        }
        Predicate[] predicatesArr = new Predicate[predicates.size()];
        return predicates.toArray(predicatesArr);
    }

    private void addOrder(CriteriaBuilder criteriaBuilder, Root<Bill> root, List<Order> orders, String field, boolean condition){
        if(condition){
            orders.add(criteriaBuilder.desc(root.get(field)));
        }else{
            orders.add(criteriaBuilder.asc(root.get(field)));
        }
    }

}
