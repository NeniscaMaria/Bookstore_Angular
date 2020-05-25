package ro.ubb.catalog.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


@NamedEntityGraph(name = "bookWithPurchases", attributeNodes = @NamedAttributeNode(value="purchases"))

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
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
    @OneToMany(mappedBy = "book",orphanRemoval = true,fetch = FetchType.LAZY,cascade = { CascadeType.ALL })
    private Set<Purchase> purchases = new HashSet<>();

    public Set<Client> getClients(){
        return Collections.unmodifiableSet(
                purchases.stream().map(Purchase::getClient)
                .collect(Collectors.toSet())
        );
    }

    public void addClient(Client client){
        Purchase purchase = new Purchase();
        purchase.setClient(client);
        purchase.setBook(this);
        purchases.add(purchase);
    }

    public void addDate(Client client, Date date){
        Purchase purchase = new Purchase();
        purchase.setClient(client);
        purchase.setBook(this);
        purchase.setDate(date);
        purchases.add(purchase);
    }

    public void removeClient(Client client){
        this.purchases = purchases.stream().filter(p->p.getClient()!=client && p.getBook()!=this).collect(Collectors.toSet());

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book that = (Book) o;

        return serialNumber.equals(that.serialNumber);
    }

    @Override
    public int hashCode() {
        return serialNumber.hashCode();
    }


}
