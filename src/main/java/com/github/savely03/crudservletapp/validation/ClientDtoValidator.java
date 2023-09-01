package com.github.savely03.crudservletapp.validation;

import com.github.savely03.crudservletapp.dto.ClientDto;
import com.github.savely03.crudservletapp.exception.ValidationException;

public class ClientDtoValidator extends Validator<ClientDto> {
    private static final ClientDtoValidator INSTANCE = new ClientDtoValidator();

    private ClientDtoValidator() {
    }

    public static ClientDtoValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public void validate(ClientDto clientDto) {
        if (clientDto.getFullName() == null ||
            clientDto.getDateBirthday() == null ||
            clientDto.getGender() == null ||
            clientDto.getGender() < 1 ||
            clientDto.getGender() > 2) {
            throw new ValidationException();
        }
    }
}
