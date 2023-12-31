package com.github.savely03.crudservletapp.repository;

import com.github.savely03.crudservletapp.model.Client;
import lombok.SneakyThrows;

import java.sql.*;

import static com.github.savely03.crudservletapp.sql.ClientQuery.INSERT;
import static com.github.savely03.crudservletapp.sql.ClientQuery.UPDATE;

public class ClientRepository implements CrudRepository<Client> {
    private static final String TABLE_NAME = "clients";

    private static final ClientRepository INSTANCE = new ClientRepository();

    private ClientRepository() {
    }

    public static ClientRepository getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    @Override
    public Client saveOrUpdate(Client client, Connection connection) {
        PreparedStatement preparedStatement = null;
        try {
            if (client.getId() == null || !exists(client.getId(), connection)) {
                preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            } else {
                preparedStatement = connection.prepareStatement(UPDATE);
                preparedStatement.setLong(4, client.getId());
            }

            preparedStatement.setString(1, client.getFullName());
            preparedStatement.setDate(2, Date.valueOf(client.getDateBirthday()));
            preparedStatement.setShort(3, client.getGender());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                client.setId(resultSet.getLong("id"));
            }
            return client;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @SneakyThrows
    @Override
    public Client buildEntity(ResultSet resultSet) {
        return Client.builder()
                .id(resultSet.getLong("id"))
                .fullName(resultSet.getString("full_name"))
                .dateBirthday(resultSet.getDate("date_birthday").toLocalDate())
                .gender(resultSet.getShort("gender"))
                .build();
    }
}
