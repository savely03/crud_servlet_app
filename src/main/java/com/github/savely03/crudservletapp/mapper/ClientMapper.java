package com.github.savely03.crudservletapp.mapper;

import com.github.savely03.crudservletapp.dto.ClientDto;
import com.github.savely03.crudservletapp.model.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper extends GeneralMapper<ClientDto, Client> {
}
