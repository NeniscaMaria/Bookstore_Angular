package ro.ubb.catalog.core.repository;
import javax.persistence.*;
import javax.persistence.criteria.*;

import org.hibernate.Session;
import org.hibernate.jpa.HibernateEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Purchase;

import java.util.ArrayList;
import java.util.List;

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
    @Transactional
    public int deletePurchaseCriteria(Client client, Book book) {
        log.trace("deletePurchaseCriteria - method entered c={} b={}",client,book);
        int result = 0;
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Purchase> criteriaDelete = criteriaBuilder.createCriteriaDelete(Purchase.class);
        Root<Purchase> e = criteriaDelete.from(Purchase.class);
        criteriaDelete.where(criteriaBuilder.and(criteriaBuilder.equal(e.get("client"),client),
                criteriaBuilder.equal(e.get("book"),book)));
        result = entityManager.createQuery(criteriaDelete).executeUpdate();
        log.trace("deletePurchaseCriteria - method finished r={}",result);
        return result;
    }

    @Override
    @Transactional
    public int deletePurchaseNative(Client client, Book book) {
        log.trace("deletePurchaseNative - method entered c={} b={}",client,book);
        int result = 0;
        try {
            HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
            Session session = hibernateEntityManager.getSession();
            org.hibernate.Query query = session.createSQLQuery("delete from purchase p where p.cid = :cid and p.bid = :bid")
                    .setParameter("cid", client.getId())
                    .setParameter("bid", book.getId());
            result = query.executeUpdate();
        }catch (Exception ex){
            log.trace("an error occurred "+ex.getMessage()+" "+ex.getCause());
        }
        log.trace("deletePurchaseNative - method finished r={}",result);
        return result;
    }

    @Override
    public List<Long> getClientsIDsSortedByNumberOfPurchasesJPQL() {
        log.trace("getClientsIDsSortedByNumberOfPurchases - method entered");
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select a.client.id from Purchase a\n" +
                "group by a.client.id\n" +
                "order by count(a.client.id) desc");
        log.trace("getClientsIDsSortedByNumberOfPurchases - executing query");
        List result = new ArrayList();
        try{
            result = query.getResultList();
        }catch(IllegalStateException |PersistenceException ex){
            log.trace("getClientsIDsSortedByNumberOfPurchases - an error occurred "+ex.getMessage()+ex.getCause());
        }
        log.trace("getClientsIDsSortedByNumberOfPurchases - method finished r={}",result);
        return result;
    }

    @Override
    public List<Long> getClientsIDsSortedByNumberOfPurchasesCriteria() {
        log.trace("getClientsIDsSortedByNumberOfPurchasesCriteria - method entered");
        List result = new ArrayList();
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> query = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = query.from(Purchase.class);
        query.select(root.get("client").get("id"));
        query.groupBy(root.get("client").get("id"));
        query.orderBy(criteriaBuilder.desc(criteriaBuilder.count(root.get("client").get("id"))));
        result = entityManager.createQuery(query).getResultList();
        log.trace("getClientsIDsSortedByNumberOfPurchasesCriteria - method finished r={}",result);
        return result;
    }

    @Override
    @Transactional
    public List<Long> getClientsIDsSortedByNumberOfPurchasesNative() {
        log.trace("getClientsIDsSortedByNumberOfPurchasesNative - method entered");
        List<Long> result = new ArrayList();

        try {
            HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
            Session session = hibernateEntityManager.getSession();
            org.hibernate.Query query = session.createSQLQuery("select a.cid from purchase a\n" +
                    "group by a.cid\n" +
                    "order by count(a.cid) desc");
            query.getResultList().stream().forEach(r->{
                result.add(Long.parseLong(r.toString()));
            });
        }catch (Exception ex){
            log.trace("an error occurred "+ex.getMessage()+" "+ex.getCause());
        }
        log.trace("getClientsIDsSortedByNumberOfPurchasesNative - method finished r={}",result);
        return result;
    }


}
