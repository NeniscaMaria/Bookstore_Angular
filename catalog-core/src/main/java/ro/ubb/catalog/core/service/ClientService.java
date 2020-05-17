package ro.ubb.catalog.core.service;

import org.springframework.data.domain.Sort;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.validators.ValidatorException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClientService {
    Client addClient(Client client) throws ValidatorException;
    Optional<Client> removeClient(Long ID);
    Set<Client> sort(Sort.Direction dir, String ...a );
    Client updateClient(Long id, Client client);
    List<Client> getAllClients();
    Set<Client> filterClientsByName(String s);
    Optional<Client> findOneClient(Long clientID);
    Client addBookToClient(Client client, Book book);
    Set<Book> getBooks(Long id);

    Client removeBookFromClient(Client convertDtoToModel, Book convertDtoToModel1);
}
