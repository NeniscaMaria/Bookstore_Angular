package ro.ubb.catalog.core.service;

import org.hibernate.boot.spi.JpaOrmXmlPersistenceUnitDefaultAware;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.BaseEntity;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.core.model.validators.Validator;
import ro.ubb.catalog.core.model.validators.ValidatorException;
import ro.ubb.catalog.core.repository.PurchaseRepository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PurchaseServiceImpl implements PurchaseService{
    private static final Logger log = LoggerFactory.getLogger(PurchaseServiceImpl.class);
    @Autowired
    private PurchaseRepository repository;
    @Autowired
    private Validator<Purchase> validator;

    public Purchase addPurchase(Purchase purchase) throws ValidatorException {
        log.trace("addPurchase - method entered: purchase = {}",purchase);
        validator.validate(purchase);
        Purchase p = repository.save(purchase);
        log.trace("addPurchase - saved: purchase={}",purchase);
        log.trace("addPurchase - method finished");
        return p;
    }

    public Set<Purchase> sort(Sort.Direction dir, String ...a ){
        log.trace("Purchase: sort - method entered: dir = {}, filters ={}",dir,a);
        Iterable<Purchase> purchases = repository.findAll(Sort.by(dir,a));
        log.trace("Purchase - sort: purchases sorted sorted={}",purchases);
        Set<Purchase> result = StreamSupport.stream(purchases.spliterator(), false).collect(Collectors.toSet());
        log.trace("Purchase - sort: method finished. Returned s={}",result);
        return result;
    }

    public Optional<Purchase> removePurchase(Long ID) {
        log.trace("removePurchase - method entered: id={}",ID);
        try {
            repository.deleteById(ID);
        }catch(EmptyResultDataAccessException er){
            log.trace("removePurchase - method finished. No Purchase with this ID");
            return Optional.empty();
        }
        log.trace("removePurchase - removed id={}",ID);
        log.trace("removePurchase - method finished");
        return Optional.of(new Purchase());
    }

    @Transactional
    public Purchase updatePurchase(Long id, Purchase purchase) throws ValidatorException {
        log.trace("updatePurchase - method entered: purchase = {}",purchase);
        validator.validate(purchase);
        //repository.
        Purchase s = repository.findById(id).orElse(purchase);
        s.setBook(purchase.getBook());
        s.setClient(purchase.getClient());
        s.setNrBooks(purchase.getNrBooks());
        log.trace("updatePurchase - updated: s={}",s);
        log.trace("updateStudent - method finished");
        return s;
    }

    @Transactional
    public List<Purchase> getAllPurchases() {
        log.trace("getAllPurchases - method entered");
        List<Purchase> purchases= repository.findAll();
        log.trace("getAllPurchases - method finished. Returned: {} ",purchases);
        return purchases;
    }

    @Transactional
    public Set<Purchase> filterPurchasesByClientID(Long clientID)  {
        log.trace("filterPurchasesByClientID - method entered: id={}",clientID);
        Iterable<Purchase> purchases = repository.findAll();
        Set<Purchase> filteredPurchases= new HashSet<>();
        purchases.forEach(filteredPurchases::add);
        filteredPurchases.removeIf(purchase -> !purchase.getClient().getId().equals(clientID));
        log.trace("filterPurchasesByClientID - method finished");
        return filteredPurchases;
    }

    @Override
    public Optional<Purchase> findOnePurchase(Long id) {
        return repository.findById(id);
    }

}
