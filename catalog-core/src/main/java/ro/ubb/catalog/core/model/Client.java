package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@Table(name="client")
public class Client extends BaseEntity<Long> {
    @Column(nullable=false)
    private String serialNumber;
    @Column(nullable=false)
    private String name;
    @OneToMany(cascade = { CascadeType.ALL }, mappedBy = "client")
    private Set<Purchase> purchaseSet;

}
