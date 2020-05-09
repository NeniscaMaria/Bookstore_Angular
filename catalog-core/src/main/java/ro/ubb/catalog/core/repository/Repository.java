package ro.ubb.catalog.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;
import ro.ubb.catalog.core.model.BaseEntity;

@NoRepositoryBean
public interface Repository<ID extends Serializable, T extends BaseEntity<ID>>
extends JpaRepository<T,ID> {
}
