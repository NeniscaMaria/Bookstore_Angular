package ro.ubb.catalog.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.validators.ClientValidator;
import ro.ubb.catalog.core.model.validators.Validator;
import ro.ubb.catalog.core.model.validators.ValidatorException;
import ro.ubb.catalog.core.repository.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ClientServiceImpl implements ClientService{
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);
    @Autowired
    private Repository<Long, Client> repository;
    @Autowired
    private Validator<Client> validator;

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

    public Set<Client> getAllClients() {
        log.trace("getAlClients - method entered");
        Iterable<Client> clients = repository.findAll();
        Set<Client> result = StreamSupport.stream(clients.spliterator(), false).collect(Collectors.toSet());
        log.trace("getAllClients - method finished and returned clients={}",clients);
        return result;
    }

    public Set<Client> filterClientsByName(String s)  {
        log.trace("filterClientsByName - method entered name={}",s);
        Iterable<Client> clients = repository.findAll();
        Set<Client> filteredClients= new HashSet<>();
        clients.forEach(filteredClients::add);
        filteredClients.removeIf(student -> !student.getName().contains(s));
        log.trace("filterClientsByname - method finished and returned c={}",filteredClients);
        return filteredClients;
    }
    public Optional<Client> findOneClient(Long clientID) {
        return repository.findById(clientID);
    }
}
