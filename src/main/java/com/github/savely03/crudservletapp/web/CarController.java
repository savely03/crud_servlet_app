package com.github.savely03.crudservletapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savely03.crudservletapp.exception.CarNotFoundException;
import com.github.savely03.crudservletapp.service.CarService;
import com.github.savely03.crudservletapp.service.impl.CarServiceImpl;
import com.github.savely03.crudservletapp.util.ObjectMapperConfig;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;

@Path("/car")
public class CarController {
    private final CarService carService = CarServiceImpl.getInstance();
    private final ObjectMapper objectMapper = ObjectMapperConfig.getInstance();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getCarById(@PathParam("id") Long id) {
        try {
            return Response.ok(objectMapper.writeValueAsString(carService.findById(id))).build();
        } catch (CarNotFoundException e) {
            return Response.status(e.getResponse().getStatus()).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/count-ordered")
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getCountCars(@QueryParam("color") String color) {
        if (color == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Укажите параметр - color").build();
        }
        return Response.ok(
                objectMapper.writeValueAsString(carService.getCountCars(color))
        ).build();
    }
}
