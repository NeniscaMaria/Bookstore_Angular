package ro.ubb.catalog.core.model;
import lombok.*;
import javax.persistence.*;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NamedEntityGraph(name = "clientWithPurchases", attributeNodes = @NamedAttributeNode("purchases"))
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name="client")
@ToString
public class Client extends BaseEntity<Long> {
    @Column(nullable=false)
    private String serialNumber;
    @Column(nullable=false)
    private String name;
    @OneToMany(mappedBy = "client",orphanRemoval = true,fetch = FetchType.LAZY,cascade = { CascadeType.ALL })
    private Set<Purchase> purchases = new HashSet<>();

    public Set<Book> getBooks(){
        purchases = purchases == null ? new HashSet<>():purchases;
        return Collections.unmodifiableSet(
                this.purchases.stream()
                .map(Purchase::getBook)
                .collect(Collectors.toSet())
        );
    }

    public void addBook(Book book){
        Purchase purchase = new Purchase();
        purchase.setBook(book);
        purchase.setClient(this);
        purchases.add(purchase);
    }

    public void removeBook(Book book){
        this.purchases = purchases.stream().filter(p->p.getBook()!=book && p.getClient()!=this).collect(Collectors.toSet());
    }

    public void addDate(Book book, Date date){
        Purchase purchase = new Purchase();
        purchase.setBook(book);
        purchase.setDate(date);
        purchase.setClient(this);
        purchases.add(purchase);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client that = (Client) o;

        return serialNumber.equals(that.serialNumber);
    }

    @Override
    public int hashCode() {
        return serialNumber.hashCode();
    }


}
