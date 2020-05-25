package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;

public interface ClientRepositoryCustom {
    //delete purchase function
    int deletePurchaseJPQL(Client client, Book book);
    int deletePurchaseCriteria(Client client, Book book);
    int deletePurchaseNative(Client client, Book book);
}
