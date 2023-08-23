package com.github.savely03.crudservletapp.validation;

import com.github.savely03.crudservletapp.dto.ClientDto;
import com.github.savely03.crudservletapp.exception.ClientValidationException;

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
            throw new ClientValidationException();
        }
    }

    @Override
    public void validateId(Long id) {
        if (id <= 0) {
            throw new ClientValidationException();
        }
    }
}
