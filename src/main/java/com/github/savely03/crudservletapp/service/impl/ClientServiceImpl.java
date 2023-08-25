package com.github.savely03.crudservletapp.service.impl;

import com.github.savely03.crudservletapp.dto.ClientDto;
import com.github.savely03.crudservletapp.dto.ClientWithCntCarsDto;
import com.github.savely03.crudservletapp.exception.ClientNotFoundException;
import com.github.savely03.crudservletapp.mapper.ClientMapper;
import com.github.savely03.crudservletapp.repository.ClientRepository;
import com.github.savely03.crudservletapp.service.ClientService;
import com.github.savely03.crudservletapp.validation.ClientDtoValidator;
import com.github.savely03.crudservletapp.validation.Validator;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class ClientServiceImpl implements ClientService {
    private static final ClientServiceImpl INSTANCE = new ClientServiceImpl();
    private final ClientRepository clientRepository = ClientRepository.getInstance();
    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);
    private final Validator<ClientDto> validator = ClientDtoValidator.getInstance();

    private ClientServiceImpl() {
    }

    public static ClientServiceImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public List<ClientDto> findAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDto)
                .toList();
    }

    @Override
    public ClientDto findById(Long id) {
        validator.validateId(id);
        return clientRepository.findById(id)
                .map(clientMapper::toDto)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        validator.validate(clientDto);
        return clientMapper.toDto(clientRepository.save(clientMapper.toEntity(clientDto)));
    }

    @Override
    public ClientDto update(Long id, ClientDto clientDto) {
        validator.validate(id, clientDto);
        clientRepository.findById(id).orElseThrow(
                () -> new ClientNotFoundException(id)
        );
        clientDto.setId(id);
        clientRepository.update(clientMapper.toEntity(clientDto));
        return clientDto;
    }

    @Override
    public void deleteById(Long id) {
        validator.validateId(id);
        if (!clientRepository.deleteById(id)) {
            throw new ClientNotFoundException(id);
        }
    }

    @Override
    public List<ClientWithCntCarsDto> getCountOrderedCarsByClient() {
        return clientRepository.getCountOrderedCarsByClient();
    }

    @Override
    public List<String> getFullNameWithMostOrderedCars() {
        return clientRepository.getFullNameWithMostOrderedCars();
    }
}
