package com.github.savely03.crudservletapp.repository;

import com.github.savely03.crudservletapp.model.Client;
import com.github.savely03.crudservletapp.util.ConnectionManager;
import com.github.savely03.crudservletapp.util.HikariConnectionManager;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.github.savely03.crudservletapp.sql.ClientQuery.*;

public class ClientRepository implements CrudRepository<Client, Long> {

    private static final ClientRepository INSTANCE = new ClientRepository();
    private final ConnectionManager hikariConnection = HikariConnectionManager.getInstance();

    private ClientRepository() {
    }

    public static ClientRepository getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public List<Client> findAll() {
        try (Connection connection = hikariConnection.getConnection();
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
        try (Connection connection = hikariConnection.getConnection();
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
        try (Connection connection = hikariConnection.getConnection();
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
        try (Connection connection = hikariConnection.getConnection();
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
        try (Connection connection = hikariConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {

            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
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
