package cz.muni.fi.pa165.ddtroops.mvc.validators;

import cz.muni.fi.pa165.ddtroops.dto.TroopUpdateDTO;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by pstanko.
 * @author pstanko
 */
public class TroopUpdateDTOValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return TroopUpdateDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
