package com.github.savely03.crudservletapp.service.impl;

import com.github.javafaker.Faker;
import com.github.savely03.crudservletapp.dto.ClientDto;
import com.github.savely03.crudservletapp.dto.ClientWithCntCarsDto;
import com.github.savely03.crudservletapp.exception.ClientNotFoundException;
import com.github.savely03.crudservletapp.exception.ClientValidationException;
import com.github.savely03.crudservletapp.mapper.ClientMapper;
import com.github.savely03.crudservletapp.model.Client;
import com.github.savely03.crudservletapp.repository.ClientRepository;
import com.github.savely03.crudservletapp.service.ClientService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    private static ClientService out;
    private static ClientRepository clientRepository;
    private final Faker faker = new Faker();
    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @BeforeAll
    static void beforeAll() {
        try (MockedStatic<ClientRepository> clientRepositoryMocked = mockStatic(ClientRepository.class)) {
            clientRepository = mock(ClientRepository.class);
            clientRepositoryMocked.when(ClientRepository::getInstance).thenReturn(clientRepository);
            out = ClientServiceImpl.getInstance();
        }
    }

    @BeforeEach
    void setUp() {
        reset(clientRepository);
    }

    @Test
    void findAllTest() {
        List<Client> clients = generateClients();
        List<ClientDto> clientsDto = clients.stream()
                .map(clientMapper::toDto)
                .toList();

        when(clientRepository.findAll()).thenReturn(clients);

        assertThat(out.findAll()).isEqualTo(clientsDto);
        assertThat(out.findAll()).hasSize(clients.size());

    }

    @Test
    void findByIdTest() {
        Client client = generateClient();
        ClientDto clientDto = clientMapper.toDto(client);

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));

        assertThat(out.findById(client.getId())).isEqualTo(clientDto);
    }

    @Test
    void findByIdWhenClientNotFoundTest() {
        assertThatExceptionOfType(ClientNotFoundException.class).isThrownBy(
                () -> out.findById(1L)
        );
    }

    @Test
    void saveTest() {
        Client client = generateClient();
        ClientDto clientDto = clientMapper.toDto(client);

        when(clientRepository.save(client)).thenReturn(client);

        assertThat(out.save(clientDto)).isEqualTo(clientDto);
    }

    @Test
    void saveWhenIncorrectValidationTest() {
        ClientDto clientDto = ClientDto.builder().build();

        assertThatExceptionOfType(ClientValidationException.class).isThrownBy(
                () -> out.save(clientDto)
        );
    }

    @Test
    void updateTest() {
        Client client = generateClient();
        ClientDto clientDto = clientMapper.toDto(client);

        when(clientRepository.findById(client.getId())).thenReturn(Optional.of(client));
        when(clientRepository.update(client)).thenReturn(client);

        assertThat(out.update(clientDto.getId(), clientDto)).isEqualTo(clientDto);
    }

    @Test
    void updateWhenClientNotFoundTest() {
        Client client = generateClient();
        ClientDto clientDto = clientMapper.toDto(client);

        assertThatExceptionOfType(ClientNotFoundException.class).isThrownBy(
                () -> out.update(client.getId(), clientDto)
        );
    }

    @Test
    void updateWhenIncorrectValidationTest() {
        ClientDto clientDto = ClientDto.builder().build();

        assertThatExceptionOfType(ClientValidationException.class).isThrownBy(
                () -> out.update(clientDto.getId(), clientDto)
        );
    }

    @Test
    void deleteByIdTest() {
        Long id = ThreadLocalRandom.current().nextLong(1, 1000);

        when(clientRepository.deleteById(id)).thenReturn(true);

        out.deleteById(id);

        verify(clientRepository).deleteById(id);
    }

    @Test
    void deleteByIdWhenClientNotFoundTest() {
        assertThatExceptionOfType(ClientNotFoundException.class).isThrownBy(
                () -> out.deleteById(1L)
        );
    }

    @Test
    void getCountOrderedCarsByClientTest() {
        List<ClientWithCntCarsDto> clients = Collections.singletonList(ClientWithCntCarsDto.builder()
                .id(1L)
                .countCars(faker.random().nextInt(1, 100))
                .fullName(faker.name().fullName())
                .build());

        when(clientRepository.getCountOrderedCarsByClient()).thenReturn(clients);

        assertThat(out.getCountOrderedCarsByClient()).isEqualTo(clients);
    }

    @Test
    void getFullNameWithMostOrderedCarsTest() {
        List<String> fullNames = generateClients(5).stream()
                .map(Client::getFullName)
                .toList();

        when(clientRepository.getFullNameWithMostOrderedCars()).thenReturn(fullNames);

        assertThat(out.getFullNameWithMostOrderedCars()).isEqualTo(fullNames);
        assertThat(out.getFullNameWithMostOrderedCars()).hasSize(fullNames.size());
    }

    private Client generateClient() {
        return Client.builder()
                .id(1L).fullName(faker.name().fullName())
                .gender((short) ThreadLocalRandom.current().nextInt(1, 3))
                .dateBirthday(LocalDate.now())
                .build();
    }

    private List<Client> generateClients(int count) {
        return LongStream.iterate(1, i -> i + 1)
                .limit(count)
                .mapToObj(i -> {
                    Client client = generateClient();
                    client.setId(i);
                    return client;
                })
                .toList();
    }

    private List<Client> generateClients() {
        return generateClients(10);
    }
}