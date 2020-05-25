package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.service.ClientService;
import ro.ubb.catalog.web.converter.PurchaseConverter;
import ro.ubb.catalog.web.dto.PurchaseDto;
import java.util.Set;

@RestController
public class PurchaseController {
    private static final Logger log = LoggerFactory.getLogger(PurchaseController.class);
    @Autowired
    private ClientService clientService;
    @Autowired
    private PurchaseConverter purchaseConverter;

    @RequestMapping(value = "/purchases/{clientID}", method = RequestMethod.GET)
    public Set<PurchaseDto> getPurchases(@PathVariable final Long clientID) {
        log.trace("getPurchases - method entered: clientID={}", clientID);
        Client client = clientService.findOneClient(clientID).get();
        Set<PurchaseDto> purchases = purchaseConverter.convertModelsToDtos(client.getPurchases());
        log.trace("getPurchases - method finished p={}",purchases);
        return purchases;
    }

}
