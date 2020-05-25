package ro.ubb.catalog.web.converter;

import java.text.ParseException;

public interface ConverterAbstract<Model,Dto> {
    Model convertDtoToModel(Dto dto) throws ParseException;

    Dto convertModelToDto(Model model);
}
