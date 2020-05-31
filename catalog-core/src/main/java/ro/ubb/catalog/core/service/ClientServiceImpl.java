package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.validators.Validator;
import ro.ubb.catalog.core.model.validators.ValidatorException;
import ro.ubb.catalog.core.repository.*;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientServiceImpl implements ClientService{
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    @Autowired
    private ClientRepository repository;
    @Autowired
    private Validator<Client> validator;
    @Value("${db.method}")
    private String method;

    public Client addClient(Client client) throws ValidatorException {
        log.trace("addClient - method entered client={}",client);
        validator.validate(client);
        Client result = repository.save(client);
        log.trace("addClient - method finished client = {}",client);
        return result;
    }

    public Optional<Client> removeClient(Long ID) {
        log.trace("removeClient - method entered id={}",ID);
        try {
            repository.deleteById(ID);
        }catch(EmptyResultDataAccessException er){
            log.trace("removeClient - method finished. No Purchase with this ID");
            return Optional.empty();
        }
        log.trace("removeClient - client deleted id={}",ID);
        log.trace("removeClient - method finished");
        return Optional.of(new Client());
    }

    public Set<Client> sort(Sort.Direction dir, String ...a ){
        log.trace("Client: sort method entered dir={} filters={}",dir,a);
        Iterable<Client> clients = repository.findAll(Sort.by(dir,a));
        Set<Client> result = StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
        log.trace("Client: sort method finished. Returned: c={}",result);
        return result;
    }

    @Transactional
    public Client updateClient(Long id, Client client) {
        log.trace("updateClient - method entered: client={}", client);
        validator.validate(client);
        Client update = repository.findById(id).orElse(client);
        update.setSerialNumber(client.getSerialNumber());
        update.setName(client.getName());
        log.trace("updateClient - method finished");
        return update;
    }

    public List<Client> getAllClients() {
        log.trace("getAllClients - method entered");
        List<Client> clients = repository.findAllWithPurchases();
        log.trace("getAllClients - method finished and returned clients={}",clients);
        return clients;
    }

    public Set<Client> filterClientsByName(String s)  {
        log.trace("filterClientsByName - method entered name={}",s);
        Set<Client> filteredClients = repository.filterClientsAfterName(s);
        log.trace("filterClientsByname - method finished and returned c={}",filteredClients);
        return filteredClients;
    }

    @Transactional
    public Optional<Client> findOneClient(Long clientID) {
        log.trace("findOneClient - method entered id={}",clientID);
        Optional<Client> client = repository.findById(clientID);
        log.trace("findOneClient - method finsihed c={}",client);
        return client;
    }

    @Override
    public Client addBookToClient(Client client, Book book, String date) {
        log.trace("addBookToClient - method entered: c={} b={} d={}", client,book,date);
        if(date.equals("a"))
            client.addDate(book,new Date());
        else{
            try {
                client.addDate(book, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date));
            } catch (ParseException e) {
                log.trace("error at parsing date d={}"+e.getMessage(),date);
            }
        }
        repository.save(client);
        log.trace("addBookToClient - updated: s={}", client);
        return client;
    }

    @Override
    @Transactional
    public Set<Book> getBooks(Long id) {
        log.trace("getBooks entered id={}",id);
        Set<Book> result = repository.findById(id).get().getBooks();
        log.trace("getBooks finished id={} b={}",id,result);
        return result;
    }

    @Override
    public int removePurchase(Client client, Book book) {
        log.trace("removePurchase - method entered c={} b={} method={}",client,book,method);
        int result = -1;
        String function = "deletePurchase"+method;
        try{
            Method functionToUse = ClientRepositoryCustom.class.getMethod(function, new Class[]{Client.class, Book.class});
            result = (int) functionToUse.invoke(repository,client,book);
        }catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException ex){
            log.trace("removePurchase - {} {}",function,ex.getMessage());
        }
        log.trace("removePurchase - method finished r={}",result);
        return result;
    }

    @Override
    public List<Long> getClientIDsSortedByNumberOfPurchases() {
        log.trace("getClientIDsSortedByNumberOfPurchases - method entered m={}",method);
        List<Long> result = new ArrayList<>();
        String function = "getClientsIDsSortedByNumberOfPurchases"+method;
        try{
            Method functionToUse = ClientRepositoryCustom.class.getMethod(function);
            result = (List<Long>) functionToUse.invoke(repository,null);
        }catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException ex){
            log.trace("getClientIDsSortedByNumberOfPurchases - {} {}",function,ex.getMessage());
        }
        log.trace("getClientIDsSortedByNumberOfPurchases - method finished r={}",result);
        return result;
    }
}
