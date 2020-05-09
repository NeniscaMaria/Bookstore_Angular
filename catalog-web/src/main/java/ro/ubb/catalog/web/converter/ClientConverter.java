package ro.ubb.catalog.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.web.dto.ClientDto;

@Component
public class ClientConverter extends BaseConverter<Client, ClientDto> {
    @Override
    public Client convertDtoToModel(ClientDto dto) {
        if(dto==null)
            return new Client();
        Client client = Client.builder()
                .serialNumber(dto.getSerialNumber())
                .name(dto.getName())
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
                .build();
        clientDto.setId(client.getId());
        return clientDto;
    }
}
