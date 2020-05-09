package ro.ubb.catalog.core.model.validators;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
