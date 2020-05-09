package ro.ubb.catalog.core.model.validators;


import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Client;

@Component
public class ClientValidator implements Validator<Client> {
    private boolean checkNull(String stringToBeChecked){ //checks if the string is valid, i.emit is not empty
        return stringToBeChecked.equals("") || stringToBeChecked.equals(" ");
    }
    private boolean isNameValid(String name){ //a person has at least 2 names and there must not be any special characters or numbers in the name
        String[] arrOfStr = name.split(" ", 4);
        return arrOfStr.length>=2 && !name.contains("!,.:;?/(){}[]@#$%^&*_+=-1234567890");
    }
    private boolean isSerialNumberValid(String number){//no spaces in the serial number and no special characters
        String[] arrOfStr = number.split(" ", 3);
        return arrOfStr.length==1 && !number.contains("!,.:;?/(){}[]@#$%^&*_+=-");
    }
    @Override
    public void validate(Client entity) throws ValidatorException {
        if(entity==null)
            throw new ValidatorException("Client is null.");
        if(checkNull(entity.getName()) || !isNameValid(entity.getName()))
            throw new ValidatorException("Please insert a valid name.");
        if(checkNull(entity.getSerialNumber()) || !isSerialNumberValid(entity.getSerialNumber()))
            throw new ValidatorException("Please insert a valid serial number.");
    }
}
