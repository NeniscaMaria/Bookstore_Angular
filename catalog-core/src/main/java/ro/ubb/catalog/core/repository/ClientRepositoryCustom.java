package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;

import java.util.List;

public interface ClientRepositoryCustom {
    //delete purchase function
    int deletePurchaseJPQL(Client client, Book book);
    int deletePurchaseCriteria(Client client, Book book);
    int deletePurchaseNative(Client client, Book book);

    //top clients
    List<Long> getClientsIDsSortedByNumberOfPurchasesJPQL();
    List<Long> getClientsIDsSortedByNumberOfPurchasesCriteria();
    List<Long> getClientsIDsSortedByNumberOfPurchasesNative();
}
