package com.github.savely03.crudservletapp.service;

import com.github.savely03.crudservletapp.dto.ClientDto;
import com.github.savely03.crudservletapp.exception.ClientNotFoundException;
import com.github.savely03.crudservletapp.mapper.ClientMapper;
import com.github.savely03.crudservletapp.repository.ClientRepository;
import com.github.savely03.crudservletapp.util.ConnectionPool;
import lombok.SneakyThrows;
import org.mapstruct.factory.Mappers;

import java.sql.Connection;
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

    @SneakyThrows
    @Override
    public List<ClientDto> findAll() {
        try (Connection connection = ConnectionPool.getConnection()) {
            return clientRepository.findAll(connection).stream()
                    .map(clientMapper::toDto)
                    .toList();
        }
    }

    @SneakyThrows
    @Override
    public ClientDto findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            return clientRepository.findById(id, connection)
                    .map(clientMapper::toDto)
                    .orElseThrow(() -> new ClientNotFoundException(id));
        }
    }

    @Override
    public ClientDto save(ClientDto clientDto) {
        Connection connection = ConnectionPool.getConnectionWithNoAutoCommit();
        return wrapInTransaction(() ->
                clientMapper.toDto(clientRepository.save(clientMapper.toEntity(clientDto), connection)), connection);
    }

    @SneakyThrows
    @Override
    public ClientDto update(Long id, ClientDto clientDto) {
        try (Connection connection = ConnectionPool.getConnection()) {
            clientRepository.findById(id, connection).orElseThrow(
                    () -> new ClientNotFoundException(id)
            );
            clientDto.setId(id);
            clientRepository.update(clientMapper.toEntity(clientDto), connection);
            return clientDto;
        }
    }

    @SneakyThrows
    @Override
    public void deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            if (!clientRepository.deleteById(id, connection)) {
                throw new ClientNotFoundException(id);
            }
        }
    }

    @SneakyThrows
    @Override
    public boolean exists(Long id) {
        try (Connection connection = ConnectionPool.getConnection()) {
            return clientRepository.exists(id, connection);
        }
    }
}
