package com.github.savely03.crudservletapp.servlet;

import com.github.savely03.crudservletapp.dto.ClientDto;
import com.github.savely03.crudservletapp.service.impl.ClientServiceImpl;
import com.github.savely03.crudservletapp.validation.ClientDtoValidator;
import jakarta.servlet.annotation.WebServlet;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/api/v1/client")
public class ClientServlet extends BaseServlet<ClientDto> {
    public ClientServlet() {
        super(ClientServiceImpl.getInstance(), ClientDtoValidator.getInstance());
    }

    @Override
    public ClientDto readObject(BufferedReader reader) throws IOException {
        return objectMapper.readValue(reader, ClientDto.class);
    }
}
