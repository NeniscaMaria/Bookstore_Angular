package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true,exclude = "clients")
@Builder
@Table(name="book")
public class Book extends BaseEntity<Long> {
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
    @ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.ALL })
    @JoinTable(name="cb",
    joinColumns = @JoinColumn(name="bid"),
    inverseJoinColumns = @JoinColumn(name="cid"))
    private Set<Client> clients;

    @Override
    public String toString() {
        return "Book{" +
                "serialNumber='" + serialNumber + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", inStock=" + inStock +
                '}';
    }
}
