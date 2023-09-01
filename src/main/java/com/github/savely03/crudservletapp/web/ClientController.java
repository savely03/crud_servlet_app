package com.github.savely03.crudservletapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savely03.crudservletapp.exception.ClientNotFoundException;
import com.github.savely03.crudservletapp.service.ClientService;
import com.github.savely03.crudservletapp.service.impl.ClientServiceImpl;
import com.github.savely03.crudservletapp.util.ObjectMapperConfig;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;

@Path("/client")
public class ClientController {
    private final ClientService clientService = ClientServiceImpl.getInstance();
    private final ObjectMapper objectMapper = ObjectMapperConfig.getInstance();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getClientById(@PathParam("id") Long id) {
        try {
            return Response.ok(objectMapper.writeValueAsString(clientService.findById(id))).build();
        } catch (ClientNotFoundException e) {
            return Response.status(e.getResponse().getStatus()).build();
        }
    }

    @GET
    @Path("/count-cars-ordered")
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getCountOrderedCarsByClient() {
        try {
            return Response.ok(
                    objectMapper.writeValueAsString(clientService.getCountOrderedCarsByClient())
            ).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/fullName/most-cars-ordered")
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getFullNameWithMostOrderedCars() {
        try {
            return Response.ok(
                    objectMapper.writeValueAsString(clientService.getFullNameWithMostOrderedCars())
            ).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
