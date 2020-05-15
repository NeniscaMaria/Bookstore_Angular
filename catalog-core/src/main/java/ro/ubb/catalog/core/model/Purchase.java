package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@Table(name="purchase")
public class Purchase extends BaseEntity<Long> {

    @ManyToOne()
    private Client client;
    @ManyToOne
    private Book book;
    @Column
    private int nrBooks;


}
