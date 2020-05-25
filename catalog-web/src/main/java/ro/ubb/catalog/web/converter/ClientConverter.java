package ro.ubb.catalog.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.BaseEntity;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.service.ClientService;
import ro.ubb.catalog.web.dto.BooksDto;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.ClientsDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientConverter extends BaseConverter<Client, ClientDto> {
    @Autowired
    private ClientService clientService;
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        if(dto==null)
            return new Client();
        Client client = Client.builder()
                .serialNumber(dto.getSerialNumber())
                .name(dto.getName())
                .purchases(new HashSet<>())
                .build();
        client.setId(dto.getId());
        return client;
    }

    @Override
    public ClientDto convertModelToDto(Client client) {
        if(client == null)
            return new ClientDto();
        ClientDto clientDto = ClientDto.builder()
                .serialNumber(client.getSerialNumber())
                .name(client.getName())
                .purchases(client.getBooks().stream()
                .map(BaseEntity::getId).collect(Collectors.toSet()))
                .build();
        clientDto.setId(client.getId());
        return clientDto;
    }

    @Override
    protected Client convertIDToModel(Long id) {
        return clientService.findOneClient(id).get();
    }
}
