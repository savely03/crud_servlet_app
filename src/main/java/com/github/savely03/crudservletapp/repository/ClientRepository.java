package com.github.savely03.crudservletapp.repository;

import com.github.savely03.crudservletapp.dto.ClientWithCntCarsDto;
import com.github.savely03.crudservletapp.model.Client;
import com.github.savely03.crudservletapp.util.ConnectionPool;
import lombok.SneakyThrows;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import static com.github.savely03.crudservletapp.sql.ClientQuery.*;

public class ClientRepository implements CrudRepository<Client> {

    private static final ClientRepository INSTANCE = new ClientRepository();

    private ClientRepository() {
    }

    public static ClientRepository getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public List<Client> findAll() {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {

            List<Client> clients = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                clients.add(buildClient(resultSet));
            }

            return clients;
        }
    }

    @SneakyThrows
    @Override
    public Optional<Client> findById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Client client = null;

            if (resultSet.next()) {
                client = buildClient(resultSet);
            }

            return Optional.ofNullable(client);
        }
    }

    @SneakyThrows
    @Override
    public Client save(Client client) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setDate(2, Date.valueOf(client.getDateBirthday()));
            preparedStatement.setShort(3, client.getGender());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                client.setId(resultSet.getLong("id"));
            }

            return client;
        }
    }

    @SneakyThrows
    @Override
    public Client update(Client client) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {

            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setDate(2, Date.valueOf(client.getDateBirthday()));
            preparedStatement.setShort(3, client.getGender());
            preparedStatement.setLong(4, client.getId());
            preparedStatement.executeUpdate();

            return client;
        }
    }

    @SneakyThrows
    @Override
    public boolean deleteById(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    @Override
    public boolean exists(Long id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EXISTS)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            long count = 0;

            if (resultSet.next()) {
                count = resultSet.getLong("cnt_client");
            }

            return count > 0;
        }
    }

    @SneakyThrows
    public List<ClientWithCntCarsDto> getCountOrderedCarsByClient() {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CNT_CARS_GROUP_BY_CLIENT)) {

            List<ClientWithCntCarsDto> clients = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                clients.add(ClientWithCntCarsDto.builder()
                        .id(resultSet.getLong("id"))
                        .fullName(resultSet.getString("full_name"))
                        .countCars(resultSet.getInt("cnt"))
                        .build());
            }

            return clients;
        }
    }

    @SneakyThrows
    public List<String> getFullNameWithMostOrderedCars() {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FULL_NAMES_CLIENTS_WITH_MAX_ORDERS_CNT)) {

            List<String> fullNames = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                fullNames.add(resultSet.getString("full_name"));
            }

            return fullNames;
        }
    }

    @SneakyThrows
    private Client buildClient(ResultSet resultSet) {
        return Client.builder()
                .id(resultSet.getLong("id"))
                .fullName(resultSet.getString("full_name"))
                .dateBirthday(resultSet.getDate("date_birthday").toLocalDate())
                .gender(resultSet.getShort("gender"))
                .build();
    }
}
