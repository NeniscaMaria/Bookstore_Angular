package ro.ubb.catalog.web.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.core.service.PurchaseService;
import ro.ubb.catalog.web.converter.PurchaseConverter;
import ro.ubb.catalog.web.dto.ClientDto;
import ro.ubb.catalog.web.dto.PurchaseDto;
import ro.ubb.catalog.web.dto.PurchasesDto;

import java.util.Optional;

@RestController
public class PurchaseController {
    public static final Logger log = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurchaseConverter purchaseConverter;

    @RequestMapping(value = "/purchases",method = RequestMethod.GET)
    PurchasesDto getPurchases(){
        log.trace("getPurchases - method entered");
        PurchasesDto result = new PurchasesDto(purchaseConverter.convertModelsToDtos(purchaseService.getAllPurchases()));
        log.trace("getPurchases - method finished. Returns b={}",result);
        return result;
    }

    @RequestMapping(value = "/purchases", method = RequestMethod.POST)
    PurchaseDto savePurchase(@RequestBody PurchaseDto purchaseDto){
        log.trace("savePurchase - method entered n = {}", purchaseDto);
        PurchaseDto result = purchaseConverter.convertModelToDto(purchaseService.addPurchase(
                purchaseConverter.convertDtoToModel(purchaseDto)));
        log.trace("savePurchase - method finished n={}",result);
        return result;
    }

    @RequestMapping(value = "/purchases/{id}",method = RequestMethod.PUT)
    PurchaseDto updatePurchase(@PathVariable Long id, @RequestBody PurchaseDto purchaseDto){
        log.trace("updatePurchase - method entered id={}, n={}",id, purchaseDto);
        PurchaseDto result = purchaseConverter.convertModelToDto(
                purchaseService.updatePurchase(id, purchaseConverter.convertDtoToModel(purchaseDto)));
        log.trace("updatePurchase - method finished returns c={}",result);
        return result;
    }

    @RequestMapping(value = "/purchases/{id}", method = RequestMethod.DELETE)
    PurchaseDto deletePurchase(@PathVariable Long id){
        log.trace("deletePurchase - method entered id = {}",id);
        Purchase result = purchaseService.removePurchase(id).get();
        log.trace("deletePurchase - method finished");
        return purchaseConverter.convertModelToDto(result);
    }

    @RequestMapping(value = "/purchases/filter/{id}", method = RequestMethod.GET)
    PurchasesDto filterByClientID(@PathVariable Long id){
        log.trace("filterByClientID - method entered id={}",id);
        PurchasesDto result = new PurchasesDto(purchaseConverter.convertModelsToDtos(
                purchaseService.filterPurchasesByClientID(id)
        ));
        log.trace("filterByClientID - method finished returns c={}",result);
        return result;
    }

    @RequestMapping(value = "/sort/purchases/{dir}", method = RequestMethod.POST)
    PurchasesDto sort(@PathVariable Sort.Direction dir, @RequestBody String ...a ){
        log.trace("sort - method entered dir={} args={}",dir,a);
        PurchasesDto result = new PurchasesDto(purchaseConverter.convertModelsToDtos(
                purchaseService.sort(dir,a)
        ));
        log.trace("sort finished returns c={}",result);
        return result;
    }

    @RequestMapping(value = "/purchases/{idPurchase}", method = RequestMethod.GET)
    PurchaseDto findOnePurchase(@PathVariable Long id){
        log.trace("findOnePurchase - method entered id={}",id);
        PurchaseDto result = purchaseConverter.convertModelToDto(
                purchaseService.findOnePurchase(id).get()
        );
        log.trace("findOnePurchase finished returns c={}",result);
        return result;
    }
}
