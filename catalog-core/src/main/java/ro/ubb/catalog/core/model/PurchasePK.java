package ro.ubb.catalog.core.model;

import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PurchasePK implements Serializable {
    private Book book;
    private Client client;
}
