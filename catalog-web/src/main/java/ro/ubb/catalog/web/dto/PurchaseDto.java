package ro.ubb.catalog.web.dto;

import lombok.*;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.core.model.Client;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class PurchaseDto extends BaseDto{
    private Book book;
    private Client client;
    private int nrBooks;
}
