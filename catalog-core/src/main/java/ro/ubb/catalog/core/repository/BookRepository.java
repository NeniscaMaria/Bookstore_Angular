package ro.ubb.catalog.core.repository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.catalog.core.model.Book;
import java.util.Set;


public interface BookRepository extends Repository<Long, Book> {
    @Query("select distinct a from Book a")
    @EntityGraph(value = "bookWithPurchases", type = EntityGraph.EntityGraphType.LOAD)
    Set<Book> findAllWithPurchases();
}
