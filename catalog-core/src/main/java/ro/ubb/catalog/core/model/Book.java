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
public class Book extends BaseEntity<Long> {
    /*
    A book has a serialNumber (String), name (string),
    author (String), year of publication (int)
     */
    @Column(nullable=false)
    private String serialNumber;
    @Column(nullable=false)
    private String title;
    @Column(nullable=false)
    private String author;
    @Column(nullable=false)
    private int year;
    @Column(nullable=false)
    private double price;
    @Column(nullable=false)
    private int inStock;


}
