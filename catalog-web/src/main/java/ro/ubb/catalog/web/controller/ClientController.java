package ro.ubb.catalog.web.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import ro.ubb.catalog.core.service.ClientService;
import ro.ubb.catalog.web.converter.ClientConverter;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.ClientsDto;

@RestController
public class ClientController {
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientConverter clientConverter;

    @RequestMapping(value = "/clients",method = RequestMethod.GET)
    ClientsDto getClients(){
        log.trace("getClients - method entered");
        ClientsDto result = new ClientsDto(clientConverter.convertModelsToDtos(clientService.getAllClients()));
        log.trace("getClients - method finished. Returns clients={}",result);
        return result;
    }

    @RequestMapping(value = "/clients", method = RequestMethod.POST)
    ClientDto saveClient(@RequestBody ClientDto clientDto){
        log.trace("saveClient - method entered client = {}",clientDto);
        ClientDto result = clientConverter.convertModelToDto(clientService.addClient(
                clientConverter.convertDtoToModel(clientDto)
        ));
        log.trace("saveClient - method finished client={}",result);
        return result;
    }

    @RequestMapping(value = "/clients/{id}",method = RequestMethod.PUT)
    ClientDto updateClient(@PathVariable Long id, @RequestBody ClientDto clientDto){
        log.trace("updateClient - method entered id={}, client={}",id,clientDto);
        ClientDto result = clientConverter.convertModelToDto(
                clientService.updateClient(id, clientConverter.convertDtoToModel(clientDto))
        );
        log.trace("updateClient - method finished returns client={}",result);
        return result;
    }

    @RequestMapping(value = "/clients/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        log.trace("deleteClient - method entered id = {}",id);
        clientService.removeClient(id);
        log.trace("deleteClient - method finished");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/clients/filter/{name}", method = RequestMethod.GET)
    ClientsDto filterByName(@PathVariable String name){
        log.trace("filterByName - method entered name={}",name);
        ClientsDto result = new ClientsDto(clientConverter.convertModelsToDtos(
                clientService.filterClientsByName(name)
        ));
        log.trace("filterByName - method finished returns c={}",result);
        return result;
    }

    @RequestMapping(value = "/clients/find/{id}", method = RequestMethod.GET)
    ClientDto findOneClient(@PathVariable Long id){
        log.trace("findOneClient - method entered id={}",id);
        ClientDto result = clientConverter.convertModelToDto(
                clientService.findOneClient(id).get()
        );
        log.trace("findOneClient finished returns c={}",result);
        return result;
    }

    @RequestMapping(value = "/sort/clients/{dir}", method = RequestMethod.POST)
    ClientsDto sort(@PathVariable Sort.Direction dir, @RequestBody String ...a ){
        log.trace("sort - method entered dir={} args={}",dir,a);
        ClientsDto result = new ClientsDto(clientConverter.convertModelsToDtos(
                clientService.sort(dir,a)
        ));
        log.trace("sort finished returns c={}",result);
        return result;
    }
}
