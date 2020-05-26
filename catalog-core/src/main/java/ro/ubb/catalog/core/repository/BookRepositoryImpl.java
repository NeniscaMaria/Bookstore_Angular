package ro.ubb.catalog.core.repository;
import org.hibernate.Session;
import org.hibernate.ejb.HibernateEntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Purchase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl extends CustomRepositorySupport implements BookRepositoryCustom {
    private static final Logger log = LoggerFactory.getLogger(BookRepositoryImpl.class);

    @Override
    @Transactional
    public int getTotalStockJPQL() {
        log.trace("getTotalStockJPQL - method entered");
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select sum(a.inStock) from Book a");
        long result = -1;
        try{
            result = (long) query.getSingleResult();
        }catch(IllegalStateException | PersistenceException ex){
            log.trace("getTotalStockJPQL - an error occurred "+ex.getMessage()+ex.getCause());
        }
        log.trace("getTotalStockJPQL - method finished r={}",result);
        return (int)result;
    }

    @Override
    @Transactional
    public int getTotalStockCriteria() {
        log.trace("getTotalStockCriteria - method entered");
        int result = -1;
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Integer> query = criteriaBuilder.createQuery(Integer.class);
        Root<Book> root = query.from(Book.class);
        query.select(criteriaBuilder.sum(root.get("inStock")));
        try {
            result = entityManager.createQuery(query).getSingleResult();
        }catch(Exception ex){
            log.trace("getTotalStockCriteria error "+ ex.getMessage()+" "+ex.getCause());
        }
        log.trace("getTotalStockCriteria - method finished r={}",result);
        return result;
    }

    @Override
    @Transactional
    public int getTotalStockNative() {
        log.trace("getTotalStockNative - method entered");
        int result = -1;
        try {
            HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
            Session session = hibernateEntityManager.getSession();
            org.hibernate.Query query = session.createSQLQuery("select sum(b.instock) from book b ");
            result = query.getFirstResult();
        }catch (Exception ex){
            log.trace("getTotalStockNative an error occurred "+ex.getMessage()+" "+ex.getCause());
        }
        log.trace("getTotalStockNative - method finished r={}",result);
        return result;
    }

    @Override
    public List<Long> getBookIDsSortedByNumberOfPurchasesJPQL() {
        log.trace("getBookIDsSortedByNumberOfPurchasesJPQL - method entered");
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select a.book.id from Purchase a\n" +
                "group by a.book.id\n" +
                "order by count(a.book.id) desc");
        log.trace("getBookIDsSortedByNumberOfPurchasesJPQL - executing query");
        List result = new ArrayList();
        try{
            result = query.getResultList();
        }catch(IllegalStateException | PersistenceException ex){
            log.trace("getBookIDsSortedByNumberOfPurchasesJPQL - an error occurred "+ex.getMessage()+ex.getCause());
        }
        log.trace("getBookIDsSortedByNumberOfPurchasesJPQL - method finished r={}",result);
        return result;
    }

    @Override
    public List<Long> getBooksIDsSortedByNumberOfPurchasesCriteria() {
        log.trace("getBooksIDsSortedByNumberOfPurchasesCriteria - method entered");
        List result = new ArrayList();
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Purchase> query = criteriaBuilder.createQuery(Purchase.class);
        Root<Purchase> root = query.from(Purchase.class);
        query.select(root.get("book").get("id"));
        query.groupBy(root.get("book").get("id"));
        query.orderBy(criteriaBuilder.desc(criteriaBuilder.count(root.get("book").get("id"))));
        result = entityManager.createQuery(query).getResultList();
        log.trace("getBooksIDsSortedByNumberOfPurchasesCriteria - method finished r={}",result);
        return result;
    }

    @Override
    @Transactional
    public List<Long> getBooksIDsSortedByNumberOfPurchasesNative() {
        log.trace("getBooksIDsSortedByNumberOfPurchasesNative - method entered");
        List result = new ArrayList();
        try {
            HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
            Session session = hibernateEntityManager.getSession();
            org.hibernate.Query query = session.createSQLQuery("select a.bid from purchase a\n" +
                    "group by a.bid\n" +
                    "order by count(a.bid) desc");
            result = query.getResultList();
        }catch (Exception ex){
            log.trace("an error occurred "+ex.getMessage()+" "+ex.getCause());
        }
        log.trace("getBooksIDsSortedByNumberOfPurchasesNative - method finished r={}",result);
        return result;
    }
}
