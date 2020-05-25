package ro.ubb.catalog.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Purchase;
import ro.ubb.catalog.web.dto.PurchaseDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PurchaseConverter implements ConverterAbstract<Purchase, PurchaseDto> {
    @Autowired
    private BookConverter bookConverter;
    @Autowired
    private ClientConverter clientConverter;
    @Override
    public Purchase convertDtoToModel(PurchaseDto dto) throws ParseException {
        return Purchase.builder()
                .book(bookConverter.convertIDToModel(dto.getBookID()))
                .client(clientConverter.convertIDToModel(dto.getClientID()))
                .date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dto.getDate()))
                .build();
    }

    @Override
    public PurchaseDto convertModelToDto(Purchase purchase) {
        return PurchaseDto.builder()
                .clientID(purchase.getClient().getId())
                .bookID(purchase.getBook().getId())
                .date(purchase.getDate().toString())
                .build();
    }
    public Set<PurchaseDto> convertModelsToDtos(Collection<Purchase> models) {
        return models.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toSet());
    }

}
