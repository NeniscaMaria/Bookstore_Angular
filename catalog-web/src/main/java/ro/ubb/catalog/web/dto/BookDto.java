package ro.ubb.catalog.web.dto;


import lombok.*;
import ro.ubb.catalog.core.model.Client;

import javax.persistence.Column;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class BookDto extends BaseDto{
    private String serialNumber;
    private String title;
    private String author;
    private int year;
    private double price;
    private int inStock;
    private Set<Long> purchases;
}
