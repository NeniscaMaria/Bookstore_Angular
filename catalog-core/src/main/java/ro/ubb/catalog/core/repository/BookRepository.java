package ro.ubb.catalog.core.repository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;

import java.util.Set;


public interface BookRepository extends Repository<Long, Book>,BookRepositoryCustom {
    @Query("select distinct a from Book a")
    @EntityGraph(value = "bookWithPurchases", type = EntityGraph.EntityGraphType.LOAD)
    Set<Book> findAllWithPurchases();

    @Query("select distinct a from Book a where a.title = ?1")
    @EntityGraph(value = "bookWithPurchases", type = EntityGraph.EntityGraphType.LOAD)
    Set<Book> filterBooksByTitle(String title);
}
