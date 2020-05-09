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
public class Purchase extends BaseEntity<Long> {

    @Column(nullable=false)
    private Long clientID;
    @Column(nullable=false)
    private Long bookID;
    @Column(nullable=false)
    private int nrBooks;


}
