package ro.ubb.catalog.core.repository;

import java.util.List;

public interface BookRepositoryCustom {
    //total number of books in stock
    int getTotalStockJPQL();
    int getTotalStockCriteria();
    int getTotalStockNative();
    //top books
    List<Long> getBookIDsSortedByNumberOfPurchasesJPQL();
    List<Long> getBooksIDsSortedByNumberOfPurchasesCriteria();
    List<Long> getBooksIDsSortedByNumberOfPurchasesNative();
}
