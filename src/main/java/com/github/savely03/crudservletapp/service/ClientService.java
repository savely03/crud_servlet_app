package com.github.savely03.crudservletapp.service;

import com.github.savely03.crudservletapp.dto.ClientDto;
import com.github.savely03.crudservletapp.exception.ClientNotFoundException;
import com.github.savely03.crudservletapp.mapper.ClientMapper;
import com.github.savely03.crudservletapp.repository.ClientRepository;
import org.mapstruct.factory.Mappers;

import java.util.List;

public class ClientService implements CrudService<ClientDto> {
    private static final ClientService INSTANCE = new ClientService();
    private final ClientRepository clientRepository = ClientRepository.getInstance();
    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    private ClientService() {
    }

    public static ClientService getInstance() {
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
        return clientRepository.findById(id)
                .map(clientMapper::toDto)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        return clientMapper.toDto(clientRepository.save(clientMapper.toEntity(clientDto)));
    }

    @Override
    public ClientDto update(Long id, ClientDto clientDto) {
        clientRepository.findById(id).orElseThrow(
                () -> new ClientNotFoundException(id)
        );
        clientDto.setId(id);
        clientRepository.update(clientMapper.toEntity(clientDto));
        return clientDto;
    }

    @Override
    public void deleteById(Long id) {
        if (!clientRepository.deleteById(id)) {
            throw new ClientNotFoundException(id);
        }
    }

    @Override
    public boolean exists(Long id) {
        return clientRepository.exists(id);
    }


}
