package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Purchase;

public interface PurchaseRepository extends Repository<Long, Purchase> {
    long deleteByclientID(Long clientID);
}
