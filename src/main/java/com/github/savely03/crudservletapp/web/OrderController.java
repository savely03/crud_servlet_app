package com.github.savely03.crudservletapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savely03.crudservletapp.service.OrderService;
import com.github.savely03.crudservletapp.service.impl.OrderServiceImpl;
import com.github.savely03.crudservletapp.util.ObjectMapperConfig;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;

@Path("/order")
public class OrderController {
    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final ObjectMapper objectMapper = ObjectMapperConfig.getInstance();

    @GET
    @Path("/month/most-cars-ordered")
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response findMonthsWithMostOrdersCars() {
        return Response.ok(
                objectMapper.writeValueAsString(orderService.findMonthsWithMostOrdersCars())
        ).build();
    }
}
