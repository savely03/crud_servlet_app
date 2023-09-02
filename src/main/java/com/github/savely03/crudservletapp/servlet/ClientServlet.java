package com.github.savely03.crudservletapp.servlet;

import com.github.savely03.crudservletapp.dto.ClientDto;
import com.github.savely03.crudservletapp.service.ClientService;
import com.github.savely03.crudservletapp.validation.ClientDtoValidator;
import jakarta.servlet.annotation.WebServlet;

import static com.github.savely03.crudservletapp.mapper.JsonMapper.OBJECT_MAPPER;

@WebServlet("/api/v1/client")
public class ClientServlet extends BaseServlet<ClientDto> {
    public ClientServlet() {
        super(ClientService.getInstance(), ClientDtoValidator.getInstance(), s -> OBJECT_MAPPER.readValue(s, ClientDto.class));
    }

}
