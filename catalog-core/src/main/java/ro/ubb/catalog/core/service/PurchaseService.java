package ro.ubb.catalog.core.service;

import org.springframework.data.domain.Sort;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.core.model.validators.ValidatorException;

import java.util.Optional;
import java.util.Set;

public interface PurchaseService {
    Purchase addPurchase(Purchase purchase) throws ValidatorException;
    Set<Purchase> sort(Sort.Direction dir, String ...a );
    Optional<Purchase> removePurchase(Long ID);
    Purchase updatePurchase(Long id, Purchase purchase) throws ValidatorException;
    Set<Purchase> getAllPurchases();
    Set<Purchase> filterPurchasesByClientID(Long clientID);
    void removePurchaseByClientID(Long clientID);
    Optional<Purchase> findOnePurchase(Long id);
}
