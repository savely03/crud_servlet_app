package com.github.savely03.crudservletapp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.savely03.crudservletapp.service.CarService;
import com.github.savely03.crudservletapp.service.impl.CarServiceImpl;
import com.github.savely03.crudservletapp.util.ObjectMapperConfig;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.SneakyThrows;

@Path("/car")
public class CarController {
    private final CarService carService = CarServiceImpl.getInstance();
    private final ObjectMapper objectMapper = ObjectMapperConfig.getInstance();

    @GET
    @Path("/ordered")
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
