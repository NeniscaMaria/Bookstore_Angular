package ro.ubb.catalog.core.repository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import ro.ubb.catalog.core.model.Client;
import java.util.List;
import java.util.Set;

public interface ClientRepository extends Repository<Long, Client>, ClientRepositoryCustom {
    @Query("select distinct a from Client a")
    @EntityGraph(value = "clientWithPurchases", type = EntityGraph.EntityGraphType.LOAD)
    List<Client> findAllWithPurchases();

    @Query("select distinct a from Client a where a.name = ?1")
    @EntityGraph(value = "clientWithPurchases", type = EntityGraph.EntityGraphType.LOAD)
    Set<Client> filterClientsAfterName(String name);
}
