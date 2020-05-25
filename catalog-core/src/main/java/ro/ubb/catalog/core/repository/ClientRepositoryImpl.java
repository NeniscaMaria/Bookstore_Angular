package ro.ubb.catalog.core.repository;
import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;

public class ClientRepositoryImpl extends CustomRepositorySupport implements ClientRepositoryCustom {
    private static final Logger log = LoggerFactory.getLogger(ClientRepositoryImpl.class);
    @Override
    @Transactional
    public int deletePurchaseJPQL(Client client, Book book) {
        log.trace("deletePurchaseJPQL - method entered c={} b={}",client,book);
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("delete from Purchase p where p.client.id = :clientID and p.book.id = :bookID");
        query.setParameter("clientID",client.getId());
        query.setParameter("bookID",book.getId());
        log.trace("deletePurchaseJPQL - executing query");
        int result = -1;
        try{
            result = query.executeUpdate();
        }catch(IllegalStateException |PersistenceException ex){
            log.trace("deletePurchaseJPQL - an error occured"+ex.getMessage()+ex.getCause());
        }
        log.trace("deletePurchaseJPQL - method finished r={}",result);
        return result;
    }

    @Override
    public int deletePurchaseCriteria(Client client, Book book) {
        return 0;
    }

    @Override
    public int deletePurchaseNative(Client client, Book book) {
        return 0;
    }


}
