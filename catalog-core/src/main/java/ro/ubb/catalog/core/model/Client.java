package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Client extends BaseEntity<Long> {
    @Column(nullable=false)
    private String serialNumber;
    @Column(nullable=false)
    private String name;

}
