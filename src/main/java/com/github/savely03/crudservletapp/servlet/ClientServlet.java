package com.github.savely03.crudservletapp.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savely03.crudservletapp.dto.ClientDto;
import com.github.savely03.crudservletapp.exception.ClientNotFoundException;
import com.github.savely03.crudservletapp.exception.ClientValidationException;
import com.github.savely03.crudservletapp.service.impl.ClientServiceImpl;
import com.github.savely03.crudservletapp.util.ObjectMapperConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/client")
public class ClientServlet extends HttpServlet {
    private ClientServiceImpl clientService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        clientService = ClientServiceImpl.getInstance();
        objectMapper = ObjectMapperConfig.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        try {
            String id = req.getParameter("id");
            if (Objects.isNull(id)) {
                objectMapper.writeValue(writer, clientService.findAll());
            } else {
                objectMapper.writeValue(writer, clientService.findById(Long.valueOf(id)));
            }
        } catch (ClientValidationException e) {
            resp.setStatus(e.getHttStatus());
            writer.write(e.getMessage());
        } finally {
            writer.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        try {
            ClientDto clientDto = objectMapper.readValue(reader, ClientDto.class);
            objectMapper.writeValue(writer, clientService.save(clientDto));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (ClientValidationException e) {
            resp.setStatus(e.getHttStatus());
            writer.write(e.getMessage());
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(e.getMessage());
        } finally {
            reader.close();
            writer.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        PrintWriter writer = resp.getWriter();
        try {
            String id = Optional.ofNullable(req.getParameter("id")).orElseThrow(ClientValidationException::new);
            ClientDto clientDto = objectMapper.readValue(reader, ClientDto.class);
            objectMapper.writeValue(writer, clientService.update(Long.valueOf(id), clientDto));
        } catch (ClientValidationException | ClientNotFoundException e) {
            resp.setStatus(e.getHttStatus());
            writer.write(e.getMessage());
        } catch (JsonProcessingException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(e.getMessage());
        } finally {
            reader.close();
            writer.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        try {
            String id = Optional.ofNullable(req.getParameter("id")).orElseThrow(ClientValidationException::new);
            clientService.deleteById(Long.valueOf(id));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (ClientNotFoundException e) {
            resp.setStatus(e.getHttStatus());
            writer.write(e.getMessage());
        } finally {
            writer.close();
        }
    }
}
