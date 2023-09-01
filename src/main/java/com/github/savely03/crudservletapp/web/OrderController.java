package com.github.savely03.crudservletapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savely03.crudservletapp.exception.OrderNotFoundException;
import com.github.savely03.crudservletapp.service.OrderService;
import com.github.savely03.crudservletapp.service.impl.OrderServiceImpl;
import com.github.savely03.crudservletapp.util.ObjectMapperConfig;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;

@Path("/order")
public class OrderController {
    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final ObjectMapper objectMapper = ObjectMapperConfig.getInstance();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getOrderById(@PathParam("id") Long id) {
        try {
            return Response.ok(objectMapper.writeValueAsString(orderService.findById(id))).build();
        } catch (OrderNotFoundException e) {
            return Response.status(e.getResponse().getStatus()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/month/most-cars-ordered")
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response findMonthsWithMostOrderedCars() {
        try {
            return Response.ok(
                    objectMapper.writeValueAsString(orderService.findMonthsWithMostOrdersCars())
            ).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
