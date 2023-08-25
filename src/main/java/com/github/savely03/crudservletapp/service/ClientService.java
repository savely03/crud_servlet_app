package com.github.savely03.crudservletapp.service;

import com.github.savely03.crudservletapp.dto.ClientDto;
import com.github.savely03.crudservletapp.dto.ClientWithCntCarsDto;

import java.util.List;

public interface ClientService extends CrudService<ClientDto, Long> {
    List<ClientWithCntCarsDto> getCountOrderedCarsByClient();

    List<String> getFullNameWithMostOrderedCars();
}
