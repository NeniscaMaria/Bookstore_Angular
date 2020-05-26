package ro.ubb.catalog.core.model;
import lombok.*;
import javax.persistence.*;
import java.util.Date;

@NamedEntityGraph(name = "purchases", attributeNodes = {
        @NamedAttributeNode("client")
})
@Entity
@Table(name = "purchase")
@IdClass(PurchasePK.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Purchase {
    @Id
    @ManyToOne(optional=false,fetch = FetchType.LAZY)
    @JoinColumn(name="bid")
    private Book book;

    @Id
    @ManyToOne(optional=false,fetch = FetchType.LAZY)
    @JoinColumn(name="cid")
    private Client client;

    @Column(name="date",nullable = false)
    private Date date;

    @Override
    public String toString(){
        return "Client id "+client.getId()+ " book id "+book.getId() + " date "+date;
    }
}
