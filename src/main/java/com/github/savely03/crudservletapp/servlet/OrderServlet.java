package com.github.savely03.crudservletapp.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savely03.crudservletapp.dto.OrderDto;
import com.github.savely03.crudservletapp.exception.OrderNotFoundException;
import com.github.savely03.crudservletapp.exception.OrderValidationException;
import com.github.savely03.crudservletapp.service.OrderService;
import com.github.savely03.crudservletapp.service.impl.OrderServiceImpl;
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

@WebServlet("/api/v1/order")
public class OrderServlet extends HttpServlet {
    private OrderService orderService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        orderService = OrderServiceImpl.getInstance();
        objectMapper = ObjectMapperConfig.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        try {
            String id = req.getParameter("id");
            if (Objects.isNull(id)) {
                objectMapper.writeValue(writer, orderService.findAll());
            } else {
                objectMapper.writeValue(writer, orderService.findById(Long.valueOf(id)));
            }
        } catch (OrderValidationException | OrderNotFoundException e) {
            resp.setStatus(e.getResponse().getStatus());
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
            OrderDto orderDto = objectMapper.readValue(reader, OrderDto.class);
            objectMapper.writeValue(writer, orderService.save(orderDto));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (OrderValidationException e) {
            resp.setStatus(e.getResponse().getStatus());
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
            String id = Optional.ofNullable(req.getParameter("id")).orElseThrow(OrderValidationException::new);
            OrderDto orderDto = objectMapper.readValue(reader, OrderDto.class);
            objectMapper.writeValue(writer, orderService.update(Long.valueOf(id), orderDto));
        } catch (OrderValidationException | OrderNotFoundException e) {
            resp.setStatus(e.getResponse().getStatus());
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
            String id = Optional.ofNullable(req.getParameter("id")).orElseThrow(OrderValidationException::new);
            orderService.deleteById(Long.valueOf(id));
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (OrderNotFoundException e) {
            resp.setStatus(e.getResponse().getStatus());
            writer.write(e.getMessage());
        } finally {
            writer.close();
        }
    }
}
