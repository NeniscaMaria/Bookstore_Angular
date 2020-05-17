package ro.ubb.catalog.core.model;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true,exclude = "books")
@Builder
@Table(name="client")
public class Client extends BaseEntity<Long> {
    @Column(nullable=false)
    private String serialNumber;
    @Column(nullable=false)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "clients")
    private Set<Book> books;
    @Override
    public String toString() {
        return "Client{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
