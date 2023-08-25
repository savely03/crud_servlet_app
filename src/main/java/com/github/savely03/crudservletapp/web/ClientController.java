package com.github.savely03.crudservletapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savely03.crudservletapp.service.ClientService;
import com.github.savely03.crudservletapp.service.impl.ClientServiceImpl;
import com.github.savely03.crudservletapp.util.ObjectMapperConfig;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;

@Path("/client")
public class ClientController {

    private final ClientService clientService = ClientServiceImpl.getInstance();
    private final ObjectMapper objectMapper = ObjectMapperConfig.getInstance();

    @GET()
    @Path("/cars-ordered")
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getCountOrderedCarsByClient() {
        return Response.ok(
                objectMapper.writeValueAsString(clientService.getCountOrderedCarsByClient())
        ).build();
    }

    @GET
    @Path("/fullName/max-cars-ordered")
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getFullNameWithMostOrderedCars() {
        return Response.ok(
                objectMapper.writeValueAsString(clientService.getFullNameWithMostOrderedCars())
        ).build();
    }
}
