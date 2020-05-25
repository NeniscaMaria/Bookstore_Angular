package ro.ubb.catalog.web.converter;

import ro.ubb.catalog.core.model.BaseEntity;
import ro.ubb.catalog.core.model.Book;
import ro.ubb.catalog.web.dto.BaseDto;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseConverter<Model extends BaseEntity<Long>, Dto extends BaseDto>
        implements Converter<Model, Dto> {


    public Set<Long> convertModelsToIDs(Set<Model> models) {
        return models.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
    }


    public Set<Long> convertDTOsToIDs(Set<Dto> dtos) {
        return dtos.stream()
                .map(BaseDto::getId)
                .collect(Collectors.toSet());
    }

    public Set<Dto> convertModelsToDtos(Collection<Model> models) {
        return models.stream()
                .map(this::convertModelToDto)
                .collect(Collectors.toSet());
    }

    public Set<Model> convertDtosToModels(Collection<Dto> dtos) {
        return dtos.stream()
                .map(this::convertDtoToModel)
                .collect(Collectors.toSet());
    }

    protected abstract Model convertIDToModel(Long id);
}
